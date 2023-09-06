package com.example.madassignment1;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MediatorLiveData;
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
    private TextView playerTurnDisplay;
    private TimerViewModel timerViewModel;
    private BoardViewModel boardViewModel;



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
        Button undoButton = gameView.findViewById(R.id.undo_turn_button);
        Button resetButton = gameView.findViewById(R.id.reset_button);
        MediatorLiveData<Pair<Integer, Integer>> mediator = new MediatorLiveData<>();
        timeRemainingDisplay = gameView.findViewById(R.id.time_remaining_text_view);
        turnsLeftDisplay = gameView.findViewById(R.id.turns_remaining_text_view);
        playerTurnDisplay = gameView.findViewById(R.id.playerTurn_text_view);
        timerViewModel = new ViewModelProvider(requireActivity()).get(TimerViewModel.class);
        boardViewModel = new ViewModelProvider(requireActivity()).get(BoardViewModel.class);
        // observer time remaining
        timerViewModel.getTimeRemaining().observe(getViewLifecycleOwner(), timeRemaining -> {
            String timeRemainingString = "TimeRemaining: " + timeRemaining / 1000;
            timeRemainingDisplay.setText(timeRemainingString);
        });
        // observer both moves available and moves made
        mediator.addSource(boardViewModel.getMovesAvailable(), movesAvailable -> mediator.setValue(Pair.create(movesAvailable, boardViewModel.getMovesMade().getValue())));
        mediator.addSource(boardViewModel.getMovesMade(), movesMade -> mediator.setValue(Pair.create(boardViewModel.getMovesAvailable().getValue(), movesMade)));
        mediator.observe(getViewLifecycleOwner(), data -> {
            String text = "Turns Remaining: " + data.first;
            text += " Moves Made: " + data.second;
            turnsLeftDisplay.setText(text);
        });
        // observer player turn
        boardViewModel.turnOver.observe(getViewLifecycleOwner(), turnOver -> {
            String playerTurnString = "Player Turn: " + (turnOver ? "O" : "X");
            playerTurnDisplay.setText(playerTurnString);
        });

        // observer game over
        resetButton.setOnClickListener(view -> boardFragment.resetGrid());
        undoButton.setOnClickListener(view -> boardFragment.undoLastTurn());

        getChildFragmentManager().beginTransaction().replace(gameFrameLayout.getId(), boardFragment).commit();
        return gameView;
    }


    public void changeBoardSize(int boardSize) {
        // remove the old board fragment
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_game_board);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
        // create a new board fragment and reset the board by changing the board size
        boardViewModel.setBoardSize(boardSize);
        boardFragment = new BoardFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_game_board, boardFragment).commit();
    }
    public void changeWinCondition(int winCondition) {
        // remove the old board fragment
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_game_board);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
        // create a new board fragment and reset the board by changing the win condition
        boardViewModel.setWinCondition(winCondition);
        boardFragment = new BoardFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_game_board, boardFragment).commit();
    }

    public void setPlayer1Marker(int marker) {
        // only allow the player to change the marker if the game is over
        if(boardViewModel.isGameOver()) {
            boardViewModel.setPlayer1Marker(marker);
        }
    }
    public void setPlayer2Marker(int marker) {
        // only allow the player to change the marker if the game is over
        if(boardViewModel.isGameOver()) {
            boardViewModel.setPlayer2Marker(marker);
        }
    }
    // TODO: make it so if a you move to a different fragment, the timer stops and gameOver set to true

    @Override
    public void onStart() {
        // start the timer on start
        super.onStart();
        timerViewModel.startTimer();
    }

    @Override
    public void onStop() {
        // stop the timer on stop
        super.onStop();
        timerViewModel.stopTimer();
    }

}