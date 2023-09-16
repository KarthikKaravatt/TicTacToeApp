package com.example.madassignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.textfield.TextInputEditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
public class LoginFragment extends Fragment implements AvatarSelectListener{

    public int avatarId = 2131230954;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // find buttons
        Button loginButton = rootView.findViewById(R.id.LoginFragmentButton);
        Button avatarButton = rootView.findViewById(R.id.AvatarButton);
        // find the username text box
        TextInputEditText usernameEditText = rootView.findViewById(R.id.usernameText);

        // Find the AvatarImage ImageView
        ImageView avatarImage = rootView.findViewById(R.id.AvatarImage);

        // update avatarImage with the selected avatar each time fragment is reloaded
        avatarImage.setImageResource(avatarId);

        // set a click listener on the change avatar button
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAvatarFragment();
            }
        });

        // set a click listener on the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the username
                String username = usernameEditText.getText() != null ? usernameEditText.getText().toString().trim() : "";

                if (!username.isEmpty()) {
                    if (((MainActivity) requireActivity()).usernameExists(username)) {
                        Toast.makeText(requireContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        // handle the username
                        ((MainActivity) requireActivity()).handleUsername(username);

                        // perform the fragment transaction to load HomepageFragment
                        loadHomepageFragment();
                    }
                } else {
                    Toast.makeText(requireContext(), "Username cannot be empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }

    @Override
    public void onAvatarSelected(int drawableResourceId) {
        avatarId = drawableResourceId;
    }

    // method to load the HomepageFragment
    private void loadHomepageFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new HomepageFragment()).commit();
    }

    private void loadAvatarFragment() {
        // Create the AvatarListFragment with the listener (listener is LoginFragment)
        AvatarListFragment avatarListFragment = new AvatarListFragment(this);

        // Begin the fragment transaction
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.MainActivityFrameLayout, avatarListFragment);
        transaction.addToBackStack("avatar_fragment"); // Add the transaction to the back stack
        transaction.commit();
    }
}