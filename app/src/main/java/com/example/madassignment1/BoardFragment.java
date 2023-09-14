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


    private TimerViewModel timerViewModel;
    private BoardViewModel boardViewModel;


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
        boardViewModel = new ViewModelProvider(requireActivity()).get(BoardViewModel.class);
        timerViewModel.getTimeRemaining().observe(getViewLifecycleOwner(), timeRemaining -> {
            if (timeRemaining == 0L) {
                switchTurns();
                timerViewModel.resetTimer();
            }
        });
        if (boardViewModel.hasLayout()) {
            rebuildBoard();
        } else {
            // create the board if it doesn't exist
            LinearLayout layout = createBoard(boardViewModel.getBoardSize());
            boardViewModel.board = grid;
            boardViewModel.setBoardLayout(layout);
            boardFragment.addView(layout);
        }
        return boardView;
    }

    public void rebuildBoard() {
        // Must remove the the board layout from its parent before adding it to a new parent
        // because it can only have one parent
        ViewGroup boardLayout = boardViewModel.getBoardLayout();
        ViewGroup parent = (ViewGroup) boardLayout.getParent();
        if (parent != null) {
            parent.removeView(boardLayout);
        }
        // derive the button size from the board size so it scales
        int buttonSize = (int)
                (Math.min(getResources().getDisplayMetrics().widthPixels,
                        getResources().getDisplayMetrics().heightPixels)
                        / boardViewModel.getBoardSize() * 0.9);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonSize, buttonSize);
        // add the layout params to the buttons
        for (int i = 0; i < boardLayout.getChildCount(); i++) {
            View row = boardLayout.getChildAt(i);
            for (int j = 0; j < ((ViewGroup) row).getChildCount(); j++) {
                View button = ((ViewGroup) row).getChildAt(j);
                if (button instanceof ImageButton) {
                    button.setLayoutParams(params);
                }
            }
        }
        // add the layout to the fragment
        boardFragment.addView(boardLayout);
        // set the grid to the board
        grid = boardViewModel.board;
    }

    public LinearLayout createBoard(int boardSize) {
        // initial checks
        assert boardSize > 2 &&
                boardViewModel.getWinCondition() > 1 &&
                boardViewModel.getPlayer1Marker() != boardViewModel.getPlayer2Marker()
                && boardViewModel.getWinCondition() <= boardSize : "Invalid board configuration";
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
                    // if the cell is empty and the game is not over
                    if (button.getTag() == null && !boardViewModel.isGameOver()) {
                        // decrement the moves available and increment the moves made
                        boardViewModel.decrementMovesAvailable();
                        boardViewModel.incrementMovesMade();
                        setLastTurn(button);
                        boardViewModel.setTurnOver();
                        // set the button to the current player's marker
                        int turn = boardViewModel.isTurnOver() ? boardViewModel.getPlayer1Marker() : boardViewModel.getPlayer2Marker();
                        button.setBackgroundResource(turn);
                        button.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        button.setTag(turn);
                        timerViewModel.resetTimer();
                        boardViewModel.setGameOver(checkGameCondition(turn));
                        // if the game is over or there is a tie
                        if (isTie()) {
                            boardViewModel.setTie(true);
                            boardViewModel.setGameOver(true);
                            timerViewModel.stopTimer();
                        }else if (boardViewModel.isGameOver()) {
                            timerViewModel.stopTimer();
                        }
                    }
                });
                // add the button to the row and the grid
                row.addView(button);
                grid.get(i).add(button);
            }
            // add the row to the board
            boardContainer.addView(row);
        }
        return boardContainer;
    }


    //TODO: Maybe only allow for undo to be used once per turn
    private void setLastTurn(ImageButton button) {
        // find the location of the last move on the grid
        for (int i = 0; i < grid.size(); i++) {
            if (grid.get(i).contains(button)) {
                boardViewModel.setLastMoveX(i);
                boardViewModel.setLastMoveY(grid.get(i).indexOf(button));
                break;
            }
        }
    }

    public void undoLastTurn() {
        // undo the last turn if there is one
        if (boardViewModel.getMovesMade().getValue() > 0 && !boardViewModel.isGameOver()) {
            ImageButton button = grid.get(boardViewModel.getLastMoveX()).get(boardViewModel.getLastMoveY());
            // reset the cell
            button.setTag(null);
            button.setBackgroundResource(R.drawable.button_outline);
            boardViewModel.setTurnOver();
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
                    if (++rowWins == boardViewModel.getWinCondition()) return true;
                } else rowWins = 0;
                // Check column
                if (grid.get(j).get(i).getTag() != null && (int) grid.get(j).get(i).getTag() == player) {
                    if (++colWins == boardViewModel.getWinCondition()) return true;
                } else colWins = 0;
            }
            // Check left diagonal
            if (grid.get(i).get(i).getTag() != null && (int) grid.get(i).get(i).getTag() == player) {
                if (++leftDiagonalWins == boardViewModel.getWinCondition()) return true;
            } else leftDiagonalWins = 0;
            // Check right diagonal
            if (grid.get(grid.size() - i - 1).get(i).getTag() != null && (int) grid.get(grid.size() - i - 1).get(i).getTag() == player) {
                if (++rightDiagonalWins == boardViewModel.getWinCondition()) return true;
            } else rightDiagonalWins = 0;
        }
        // Check other diagonals
        for (int offset = 1; offset <= grid.size() - boardViewModel.getWinCondition(); offset++) {
            int leftDiagonalWinsTop = 0, leftDiagonalWinsBottom = 0, rightDiagonalWinsTop = 0, rightDiagonalWinsBottom = 0;
            for (int i = 0; i < grid.size() - offset; i++) {
                // Check top-left diagonal
                if (grid.get(i + offset).get(i).getTag() != null && (int) grid.get(i + offset).get(i).getTag() == player) {
                    if (++leftDiagonalWinsTop == boardViewModel.getWinCondition()) return true;
                } else leftDiagonalWinsTop = 0;
                // Check bottom-left diagonal
                if (grid.get(i).get(i + offset).getTag() != null && (int) grid.get(i).get(i + offset).getTag() == player) {
                    if (++leftDiagonalWinsBottom == boardViewModel.getWinCondition()) return true;
                } else leftDiagonalWinsBottom = 0;
                // Check top-right diagonal
                if (grid.get(grid.size() - i - offset - 1).get(i).getTag() != null && (int) grid.get(grid.size() - i - offset - 1).get(i).getTag() == player) {
                    if (++rightDiagonalWinsTop == boardViewModel.getWinCondition()) return true;
                } else rightDiagonalWinsTop = 0;
                // Check bottom-right diagonal
                if (grid.get(grid.size() - i - 1).get(i + offset).getTag() != null && (int) grid.get(grid.size() - i - 1).get(i + offset).getTag() == player) {
                    if (++rightDiagonalWinsBottom == boardViewModel.getWinCondition()) return true;
                } else rightDiagonalWinsBottom = 0;
            }
        }
        return false;
    }

    public boolean isTie() {
        // if all the cells are filled
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.size(); j++) {
                if (grid.get(i).get(j).getTag() == null) {
                    return false;
                }
            }
        }
        return true;
    }


    public void resetGrid() {
        // reset the grid
        for (ArrayList<ImageButton> row : grid) {
            for (ImageButton button : row) {
                button.setTag(null);
                button.setBackgroundResource(R.drawable.button_outline);
            }
        }
        // reset the view models
        boardViewModel.resetMovesAvailable();
        boardViewModel.resetMovesMade();
        boardViewModel.turnOver.setValue(false);
        boardViewModel.setGameOver(false);
        timerViewModel.resetTimer();
    }

    public void switchTurns() {
        boardViewModel.setTurnOver();
    }
}