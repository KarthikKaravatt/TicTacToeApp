package com.example.madassignment1;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import com.google.android.material.textfield.TextInputEditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.res.Resources;
public class LoginFragment extends Fragment implements AvatarSelectListener{

    private ImageView avatarImage;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // find buttons
        Button loginButton = rootView.findViewById(R.id.LoginFragmentButton);
        Button avatarButton = rootView.findViewById(R.id.AvatarButton);
        // find the username text box
        TextInputEditText usernameEditText = rootView.findViewById(R.id.usernameText);

        // Find the AvatarImage ImageView
        avatarImage = rootView.findViewById(R.id.AvatarImage);
        avatarImage.setImageResource(R.drawable.avatar_default);

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
        // Context context = getContext(); <- I thought this would fix it but it didn't
        // Resources resources = context.getResources();
        //Drawable drawable = ResourcesCompat.getDrawable(resources, drawableResourceId, null);
        // avatarImage.setImageDrawable(drawable);

        Log.d("LoginFragment", "Received drawableResourceId: " + drawableResourceId);
        avatarImage.setVisibility(View.VISIBLE);

        // Update the avatarImage ImageView with the selected drawable
        avatarImage.setImageResource(drawableResourceId);

    }

    public void navigateToLoginFragment() {
        // Perform a fragment transaction to replace the current fragment with the LoginFragment
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new LoginFragment()).commit();
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