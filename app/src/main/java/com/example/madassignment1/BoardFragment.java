package com.example.madassignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

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
        createBoard(20);
        return boardView;
    }

    public void createBoard(int boardSize) {
        // Create the board container
        LinearLayout boardContainer = new LinearLayout(getContext());
        boardContainer.setOrientation(LinearLayout.VERTICAL);
        // Calculate the button size
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int buttonSize = (int) (Math.min(screenWidth, screenHeight) / boardSize * 0.9);
        // Create the rows and cells
        for (int i = 0; i < boardSize; i++) {
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < boardSize; j++) {
                // Create a button
                Button button = new Button(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonSize, buttonSize);
                params.setMargins(0, 0, 0, 0);
                button.setLayoutParams(params);
                button.setBackgroundResource(R.drawable.button_outline);
                button.setPadding(0, 0, 0, 0);
                button.setText(" ");
                button.setOnClickListener(v -> button.setText("X"));
                // Add the button to the row
                row.addView(button);
            }
            // Add the row to the board container
            boardContainer.addView(row);
        }
        // Add the board container to the fragment
        boardFragment.addView(boardContainer);
    }
}