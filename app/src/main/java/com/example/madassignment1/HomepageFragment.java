package com.example.madassignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class HomepageFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_homepage, container, false);

        // find the login button
        Button newGameButton = rootView.findViewById(R.id.NewGameButton);

        // find statistics button
        Button statisticsButton = rootView.findViewById(R.id.StatisticsButton);

        // find leaderboard button
        Button leaderboardButton = rootView.findViewById(R.id.LeaderboardButton);

        Button editProfileButton = rootView.findViewById(R.id.EditProfileButton);

        // set a click listener on the new game button
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform the fragment transaction to load new game fragment
                loadNewGameFragment();
            }
        });

        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform the fragment transaction to load statistics fragment
                loadStatisticsFragment();
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform the fragment transaction to load leaderboard
                loadLeaderboardFragment();
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform the fragment transaction to load login fragment
                loadLoginFragment();
            }
        });


        return rootView;
    }

    // method to load the HomepageFragment
    private void loadNewGameFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new GameSelectionFragment()).commit();
    }

    // method to load the StatisticsFragment
    private void loadStatisticsFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new StatisticsFragment()).commit();
    }

    // method to load the LeaderboardFragment
    private void loadLeaderboardFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new LeaderboardFragment()).commit();
    }
    private void loadLoginFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new LoginFragment()).commit();
    }
}
