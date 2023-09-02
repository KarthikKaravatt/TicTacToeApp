package com.example.madassignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // find the login button
        Button loginButton = rootView.findViewById(R.id.LoginFragmentButton);

        // find the username text box
        TextInputEditText usernameEditText = rootView.findViewById(R.id.usernameText);

        // set a click listener on the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the username
                String username = usernameEditText.getText().toString().trim();

                if (!username.isEmpty()){
                    if (((MainActivity) requireActivity()).usernameExists(username)){
                        Toast.makeText(requireContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // handle the username
                        ((MainActivity) requireActivity()).handleUsername(username);

                        // perform the fragment transaction to load HomepageFragment
                        loadHomepageFragment();
                    }
                }

                else{
                    Toast.makeText(requireContext(), "Username cannot be empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }

    // method to load the HomepageFragment
    private void loadHomepageFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new HomepageFragment()).commit();
    }
}
