package com.example.madassignment1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

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
    private List<String> usernameList = new ArrayList<>();


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
        fm.beginTransaction().replace(R.id.MainActivityFrameLayout, loginFragment).commit();
    }

    // loads user statistics
    private void loadStatsFragment() {
        fm.beginTransaction().replace(R.id.MainActivityFrameLayout, statsFragment).commit();
    }



}