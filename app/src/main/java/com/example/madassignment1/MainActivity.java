package com.example.madassignment1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {

    final LoginFragment loginFragment = new LoginFragment();


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


}