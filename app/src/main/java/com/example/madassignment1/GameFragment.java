package com.example.madassignment1;

import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private final BoardFragment boardFragment = new BoardFragment();
    private TextView timeRemainingDisplay;
    private TextView turnsLeftDisplay;
    private ImageView playerTurnMarkerDisplay;
    private ImageView playerAvatarDisplay;
    private TimerViewModel timerViewModel;
    private BoardViewModel boardViewModel;
    private ImageButton pauseButton;



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

        pauseButton = gameView.findViewById(R.id.pause_button);
        long delayMillis = 3000;
        Handler handler = new Handler();
        GameSettingsViewModel gameSettingsViewModel = new ViewModelProvider(requireActivity()).get(GameSettingsViewModel.class);
        FrameLayout gameFrameLayout = gameView.findViewById(R.id.fragment_game_board);
        Button undoButton = gameView.findViewById(R.id.undo_turn_button);
        Button resetButton = gameView.findViewById(R.id.reset_button);
        MediatorLiveData<Pair<Integer, Integer>> mediator = new MediatorLiveData<>();
        timeRemainingDisplay = gameView.findViewById(R.id.time_remaining_text_view);
        turnsLeftDisplay = gameView.findViewById(R.id.turns_remaining_text_view);
        playerTurnMarkerDisplay = gameView.findViewById(R.id.playerTurn_image_view);
        playerAvatarDisplay = gameView.findViewById(R.id.avatar_image_view);
        timerViewModel = new ViewModelProvider(requireActivity()).get(TimerViewModel.class);
        boardViewModel = new ViewModelProvider(requireActivity()).get(BoardViewModel.class);
        assert gameSettingsViewModel.getAvatarId().getValue() != null;
        playerAvatarDisplay.setImageResource(gameSettingsViewModel.getAvatarId().getValue());


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
            if(turnOver) {
                if (boardViewModel.isAi()){
                    undoButton.setOnClickListener(null);
                    undoButton.getBackground().setAlpha(64);
                }
                playerTurnMarkerDisplay.setImageResource(boardViewModel.getPlayer2Marker());
            } else {
                undoButton.setOnClickListener(view -> boardFragment.undoLastTurn());
                boardViewModel.setUndoUsed(true);
                undoButton.getBackground().setAlpha(255);
                playerTurnMarkerDisplay.setImageResource(boardViewModel.getPlayer1Marker());
            }
        });
        boardViewModel.getGameOver().observe(getViewLifecycleOwner(), gameOver -> {
            if(! boardViewModel.isTie()){
                if(gameOver) {
                    Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
                }
            }
        });
        boardViewModel.getTie().observe(getViewLifecycleOwner(), tie -> {
            if(tie) {
                Toast.makeText(getContext(), "Tie", Toast.LENGTH_SHORT).show();
            }
        });

        // observer game over
        resetButton.setOnClickListener(view -> boardFragment.resetGrid());

        pauseButton.setOnClickListener(v -> {
            // perform the fragment transaction to load PauseFragment
            loadPauseFragment();
        });

        // if the game is over, you can no longer click the pause button
        // previously, it would crash if you tried to
        boardViewModel.getGameOver().observe(getViewLifecycleOwner(), isGameOver -> {
            // Here, you can react to changes in the 'gameOver' LiveData
            if (isGameOver) {
            pauseButton.setEnabled(false);
                if (boardViewModel.isGameOver()) {
                    timerViewModel.stopTimer();
                    handler.postDelayed(this::loadGameOverFragment, delayMillis);
                }
            }
        });
        boardViewModel.getTie().observe(getViewLifecycleOwner(), getIsTie -> {
            if (getIsTie ) {
                pauseButton.setEnabled(false);
            }
        });






        getChildFragmentManager().beginTransaction().replace(gameFrameLayout.getId(), boardFragment).commit();
        return gameView;
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

    private void loadPauseFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new PauseFragment()).commit();
    }
    private void loadGameOverFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new GameOverFragment()).commit();
    }


}