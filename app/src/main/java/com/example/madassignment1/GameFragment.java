package com.example.madassignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BoardFragment boardFragment = new BoardFragment();
    private TextView timeRemainingDisplay;
    private TextView turnsLeftDisplay;
    private TimerViewModel timerViewModel;




    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View gameView = inflater.inflate(R.layout.fragment_game, container, false);
        FrameLayout gameFrameLayout = gameView.findViewById(R.id.fragment_game_board);
        timeRemainingDisplay = gameView.findViewById(R.id.time_remaining_text_view);
        turnsLeftDisplay = gameView.findViewById(R.id.turns_remaining_text_view);
        Button undoButton = gameView.findViewById(R.id.undo_turn_button);
        Button resetButton = gameView.findViewById(R.id.reset_button);
        timerViewModel = new ViewModelProvider(requireActivity()).get(TimerViewModel.class);
        timerViewModel.getTimeRemaining().observe(getViewLifecycleOwner(), timeRemaining -> {
            String timeRemainingString = "TimeRemaining: " + String.valueOf(timeRemaining / 1000);
            timeRemainingDisplay.setText(timeRemainingString);
        });
        resetButton.setOnClickListener(view -> boardFragment.resetGrid());
        undoButton.setOnClickListener(view -> boardFragment.undoLastTurn());
        getChildFragmentManager().beginTransaction().replace(gameFrameLayout.getId(), boardFragment).commit();
        return gameView;
    }


    @Override
    public void onStart() {
        super.onStart();
        timerViewModel.startTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        timerViewModel.stopTimer();
    }
}