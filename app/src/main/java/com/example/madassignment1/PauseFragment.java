package com.example.madassignment1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PauseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PauseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private BoardViewModel boardViewModel;

    public PauseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PauseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PauseFragment newInstance(String param1, String param2) {
        PauseFragment fragment = new PauseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pause, container, false);
        Button returnButton = rootView.findViewById(R.id.returnButton);
        Button exitButton = rootView.findViewById(R.id.exitButton);
        Button settingsButton = rootView.findViewById(R.id.pauseSettingsButton);
        boardViewModel = new ViewModelProvider(requireActivity()).get(BoardViewModel.class);
        boardViewModel.setGamePaused(true);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform the fragment transaction to load HomepageFragment
                loadGameFragment();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform the fragment transaction to load HomepageFragment
                boardViewModel.setGamePaused(false);
                loadHomepageFragment();
                boardViewModel.resetBoard();
            }
        });
        settingsButton.setOnClickListener(view -> {
            loadSettingsFragment();
        });

        return rootView;
    }

    private void loadGameFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new GameFragment()).commit();
    }
    private void loadHomepageFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new HomepageFragment()).commit();
    }
    private void loadSettingsFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new GameSettingsFragment()).commit();
    }
}