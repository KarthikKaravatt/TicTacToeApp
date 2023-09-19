package com.example.madassignment1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class GameSelectionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private BoardViewModel boardViewModel;

    public GameSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameSelectionFragment newInstance(String param1, String param2) {
        GameSelectionFragment fragment = new GameSelectionFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_game_selection, container, false);
        // find the back button
        ImageButton backButton = (ImageButton) rootView.findViewById(R.id.back_button);
        // find human vs human button
        Button humanVsHumanButton = rootView.findViewById(R.id.humanVsHumanButton);
        // find human vs AI button
        Button humanVsAiButton = rootView.findViewById(R.id.humanVsAiButton);
        boardViewModel = new ViewModelProvider(requireActivity()).get(BoardViewModel.class);

        humanVsHumanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform the fragment transaction to load new game fragment
                boardViewModel.setAi(false);
                loadGameSettingsFragment();
            }
        });
        humanVsAiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform the fragment transaction to load new game fragment
                loadGameSettingsFragment();
                boardViewModel.setAi(true);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform the fragment transaction to load new game fragment
                loadHomepageFragment();
            }
        });


        return rootView;
    }

    private void loadHomepageFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new HomepageFragment()).commit();
    }

    private void loadGameSettingsFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new GameSettingsFragment()).commit();
    }
}