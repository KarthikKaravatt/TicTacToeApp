package com.example.madassignment1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // initialising fragments & fragment manager
    HomepageFragment homeFragment = new HomepageFragment();
    GameSettingsFragment settingsFragment = new GameSettingsFragment();
    GameSelectionFragment selectionFragment = new GameSelectionFragment();
    LeaderboardFragment leaderboardFragment = new LeaderboardFragment();
    LoginFragment loginFragment = new LoginFragment();
    StatisticsFragment statsFragment = new StatisticsFragment();
    FragmentManager fm = getSupportFragmentManager();
    Fragment mainFragment = fm.findFragmentById(R.id.MainActivityFrameLayout);
    private final List<String> usernameList = new ArrayList<>();


    // initial app startup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            return;
        }
        loadLoginFragment();
    }

    // loads homepage
    private void loadHomepageFragment() {
        fm.beginTransaction().replace(R.id.MainActivityFrameLayout, homeFragment).commit();
    }

    // loads settings
    private void loadSettingsFragment() {
        fm.beginTransaction().replace(R.id.MainActivityFrameLayout, settingsFragment).commit();
    }

    // loads game selection
    private void loadSelectionFragment() {
        fm.beginTransaction().replace(R.id.MainActivityFrameLayout, selectionFragment).commit();
    }

    // loads leaderboard
    private void loadLeaderboardFragment() {
        fm.beginTransaction().replace(R.id.MainActivityFrameLayout, leaderboardFragment).commit();
    }

    // loads login
    private void loadLoginFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Check if the loginFragment is already added to the back stack by finding it by its tag
        LoginFragment existingFragment = (LoginFragment) fragmentManager.findFragmentByTag("login_fragment");

        if (existingFragment == null) {
            // The fragment is not added, so create and add it
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.MainActivityFrameLayout, loginFragment, "login_fragment");
            transaction.addToBackStack("login_fragment"); // Add the transaction to the back stack
            transaction.commit();
        } else {
            // The fragment is already added, so just pop the back stack to navigate back to it
            fragmentManager.popBackStackImmediate("login_fragment", 0);
        }
    }

    // loads user statistics
    private void loadStatsFragment() {
        fm.beginTransaction().replace(R.id.MainActivityFrameLayout, statsFragment).commit();
    }



}