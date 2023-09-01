package com.example.madassignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FrameLayout boardFragment;
    private ArrayList<ArrayList<ImageButton>> grid = new ArrayList<>();
    private Boolean turnOver = false;
    private int playerOneIcon = R.drawable.x;
    private int playerTwoIcon = R.drawable.o;

    private Integer lastTurnY = -1;
    private Integer lastTurnX = -1;

    private boolean undoUsed = false;

    private TimerViewModel timerViewModel;


    private int boardSize = 3;
    private int winCondition = 3;
    private int movesAvailable = boardSize * boardSize;
    private int movesMade = 0;

    private boolean gameOver = false;


    public BoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
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
        View boardView = inflater.inflate(R.layout.fragment_board, container, false);
        boardFragment = boardView.findViewById(R.id.board_fragment);
        timerViewModel = new ViewModelProvider(requireActivity()).get(TimerViewModel.class);
        timerViewModel.getTimeRemaining().observe(getViewLifecycleOwner(), timeRemaining -> {
            if (timeRemaining == 0L) {
                switchTurns();
                timerViewModel.resetTimer();
            }
        });
        createBoard(boardSize);
        return boardView;
    }

    public void createBoard(int boardSize) {
        // initial checks
        assert boardSize > 2 && winCondition > 1 && playerOneIcon != playerTwoIcon && winCondition <= boardSize : "Invalid board configuration";
        if (grid != null) {
            boardFragment.removeAllViews();
            grid.clear();
        }
        // create the board, this is the grid
        LinearLayout boardContainer = new LinearLayout(getContext());
        boardContainer.setOrientation(LinearLayout.VERTICAL);
        // derive the button size from the board size so it scales
        int buttonSize = (int) (Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels) / boardSize * 0.9);
        // populate the grid with imageButtons
        for (int i = 0; i < boardSize; i++) {
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            grid.add(new ArrayList<>());
            for (int j = 0; j < boardSize; j++) {
                ImageButton button = new ImageButton(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonSize, buttonSize);
                // customise the button
                params.setMargins(0, 0, 0, 0);
                button.setLayoutParams(params);
                button.setBackgroundResource(R.drawable.button_outline);
                button.setPadding(0, 0, 0, 0);
                button.setImageResource(R.drawable.button_outline);
                button.setOnClickListener(v -> {
                    if (button.getTag() == null && !gameOver) {
                        movesAvailable--;
                        movesMade++;
                        setLastTurn(button);
                        turnOver = !turnOver;
                        int turn = turnOver ? playerOneIcon : playerTwoIcon;
                        button.setBackgroundResource(turn);
                        button.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        button.setTag(turn);
                        timerViewModel.resetTimer();
                        gameOver = checkGameCondition(turn);
                        if (gameOver || isTie()) {
                            gameOver = true;
                            timerViewModel.stopTimer();
                        }
                    }
                });
                row.addView(button);
                grid.get(i).add(button);
            }
            boardContainer.addView(row);
        }
        boardFragment.addView(boardContainer);
    }

    public int getMovesAvailable() {
        return movesAvailable;
    }

    public int getMovesMade() {
        return movesMade;
    }

    private void setLastTurn(ImageButton button) {
        // can only undo once per turn
        if (undoUsed) {
            undoUsed = false;
            return;
        }
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.size(); j++) {
                if (grid.get(i).get(j) == button) {
                    lastTurnY = i;
                    lastTurnX = j;
                    return;
                }
            }
        }
    }

    public void undoLastTurn() {
        if (lastTurnY != -1 && lastTurnX != -1 && !gameOver) {
            grid.get(lastTurnY).get(lastTurnX).setTag(null);
            grid.get(lastTurnY).get(lastTurnX).setBackgroundResource(R.drawable.button_outline);
            lastTurnY = -1;
            lastTurnX = -1;
            undoUsed = true;
            // switch turns after undo
            turnOver = !turnOver;
        }
    }

    private boolean checkGameCondition(int player) {
        int leftDiagonalWins = 0, rightDiagonalWins = 0;
        // iterate over the grid
        for (int i = 0; i < grid.size(); i++) {
            int rowWins = 0, colWins = 0;
            for (int j = 0; j < grid.size(); j++) {
                // Check row
                if (grid.get(i).get(j).getTag() != null && (int) grid.get(i).get(j).getTag() == player) {
                    if (++rowWins == winCondition) return true;
                } else rowWins = 0;
                // Check column
                if (grid.get(j).get(i).getTag() != null && (int) grid.get(j).get(i).getTag() == player) {
                    if (++colWins == winCondition) return true;
                } else colWins = 0;
            }
            // Check left diagonal
            if (grid.get(i).get(i).getTag() != null && (int) grid.get(i).get(i).getTag() == player) {
                if (++leftDiagonalWins == winCondition) return true;
            } else leftDiagonalWins = 0;
            // Check right diagonal
            if (grid.get(grid.size() - i - 1).get(i).getTag() != null && (int) grid.get(grid.size() - i - 1).get(i).getTag() == player) {
                if (++rightDiagonalWins == winCondition) return true;
            } else rightDiagonalWins = 0;
        }
        return false;
    }

    public boolean isTie() {
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.size(); j++) {
                if (grid.get(i).get(j).getTag() == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean getGameStatus() {
        return gameOver;
    }

    public void setPlayerOneIcon(int playerOneIcon) {
        if (playerOneIcon == playerTwoIcon)
            throw new IllegalArgumentException("Player one and player two cannot have the same icon");
        this.playerOneIcon = playerOneIcon;
    }

    public void setPlayerTwoIcon(int playerTwoIcon) {
        if (playerOneIcon == playerTwoIcon)
            throw new IllegalArgumentException("Player one and player two cannot have the same icon");
        this.playerTwoIcon = playerTwoIcon;
    }

    public void setBoardSize(int boardSize) {
        if (boardSize < 3) throw new IllegalArgumentException("Board size cannot be less than 3");
        this.boardSize = boardSize;
        resetGrid();
    }

    public void setWinCondition(int winCondition) {
        if (winCondition < 2)
            throw new IllegalArgumentException("Win condition cannot be less than 2");
        this.winCondition = winCondition;
        resetGrid();
    }

    public void resetGrid() {
        for (ArrayList<ImageButton> row : grid) {
            for (ImageButton button : row) {
                button.setTag(null);
                button.setBackgroundResource(R.drawable.button_outline);
            }
        }
        movesAvailable = boardSize * boardSize;
        movesMade = 0;
        gameOver = false;
    }

    public void switchTurns() {
        turnOver = !turnOver;
    }
}