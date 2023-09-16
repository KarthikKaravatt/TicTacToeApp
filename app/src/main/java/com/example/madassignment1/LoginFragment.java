package com.example.madassignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.textfield.TextInputEditText;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
public class LoginFragment extends Fragment implements AvatarSelectListener{
    private BoardViewModel boardViewModel;
    private GameSettingsViewModel gameSettingsViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        boardViewModel = new ViewModelProvider(requireActivity()).get(BoardViewModel.class);
        gameSettingsViewModel = new ViewModelProvider(requireActivity()).get(GameSettingsViewModel.class);



        // find buttons
        Button loginButton = rootView.findViewById(R.id.LoginFragmentButton);
        Button avatarButton = rootView.findViewById(R.id.AvatarButton);
        // find the username text box
        TextInputEditText usernameEditText = rootView.findViewById(R.id.usernameText);

        addUsernames();

        usernameEditText.setText(boardViewModel.getUsername().getValue());

        // Find the AvatarImage ImageView
        ImageView avatarImage = rootView.findViewById(R.id.AvatarImage);

        // update avatarImage with the selected avatar each time fragment is reloaded
        avatarImage.setImageResource(gameSettingsViewModel.getAvatarId().getValue());

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
                    if (boardViewModel.getUsernameList().getValue().contains(username.toLowerCase())) {
                        Toast.makeText(requireContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        // perform the fragment transaction to load HomepageFragment
                        loadHomepageFragment();
                    }
                } else {
                    Toast.makeText(requireContext(), "Username cannot be empty!", Toast.LENGTH_SHORT).show();
                }

                boardViewModel.setUsername(username.toLowerCase());

            }
        });

        return rootView;
    }

    @Override
    public void onAvatarSelected(int drawableResourceId) {
        gameSettingsViewModel.setAvatarId(drawableResourceId);
    }

    // method to load the HomepageFragment
    private void loadHomepageFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new HomepageFragment()).commit();
    }
    private void addUsernames()
    {
        boardViewModel = new ViewModelProvider(requireActivity()).get(BoardViewModel.class);
        boardViewModel.addUsername("sajib");
        boardViewModel.addUsername("xochamp");
        boardViewModel.addUsername("mango");
        boardViewModel.addUsername("gridmaster");
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