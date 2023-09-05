package com.example.madassignment1;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    // initialising fragments & fragment manager
    HomepageFragment homeFragment = new HomepageFragment();
    GameSettingsFragment settingsFragment = new GameSettingsFragment();
    GameSelectionFragment selectionFragment = new GameSelectionFragment();
    LeaderboardFragment leaderboardFragment = new LeaderboardFragment();
    LoginFragment loginFragment = new LoginFragment();
    StatisticsFragment statsFragment = new StatisticsFragment();
    FragmentManager fm = getSupportFragmentManager();
    private List<String> usernameList = new ArrayList<>();

    // initial app startup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadLoginFragment();
    }

    // handle username from login fragment
    public void handleUsername(String username) {
        usernameList.add(username);
    }

    public boolean usernameExists(String username) {
        return usernameList.contains(username);
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
        fm.beginTransaction().replace(R.id.MainActivityFrameLayout,  statsFragment).commit();
    }

}