package com.example.madassignment1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameOverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameOverFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button homeButton;
    private BoardViewModel boardViewModel;
    private ImageView winnerImage;
    private TextView outcome;

    public GameOverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameOverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameOverFragment newInstance(String param1, String param2) {
        GameOverFragment fragment = new GameOverFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_game_over, container, false);
        homeButton = rootView.findViewById(R.id.homeButton);
        winnerImage = rootView.findViewById(R.id.winnerImage);
        outcome = rootView.findViewById(R.id.outcome);
        boardViewModel = new ViewModelProvider(requireActivity()).get(BoardViewModel.class);

        if (savedInstanceState == null)
        {
            Boolean isTieValue = boardViewModel.getTie().getValue();

            boardViewModel.setGamesPlayed(boardViewModel.getGamesPlayed().getValue()+1);
            if (isTieValue)
            {
                outcome.setText("Tie");
                boardViewModel.setGamesTied(boardViewModel.getGamesTied().getValue()+1);
                boardViewModel.setWinner(0);
            }
            else
            {
                if (boardViewModel.isTurnOver())
                {
                    winnerImage.setBackgroundResource(boardViewModel.getPlayer2Marker());
                    boardViewModel.setGamesWon(boardViewModel.getGamesWon().getValue()+1);
                    boardViewModel.setWinner(1);
                }
                else
                {
                    winnerImage.setBackgroundResource(boardViewModel.getPlayer1Marker());
                    boardViewModel.setGamesLost(boardViewModel.getGamesLost().getValue()+1);
                    boardViewModel.setWinner(2);
                }
            }
        }
        else
        {
            Boolean isTieValue = boardViewModel.getTie().getValue();
            if (isTieValue)
            {
                outcome.setText("Tie");
            }
            else
            {
                if (boardViewModel.getWinner().getValue() == 1)
                {
                    winnerImage.setBackgroundResource(boardViewModel.getPlayer2Marker());
                }
                else if (boardViewModel.getWinner().getValue() == 2)
                {
                    winnerImage.setBackgroundResource(boardViewModel.getPlayer1Marker());
                }
            }
        }
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform the fragment transaction to load HomepageFragment
                loadHomepageFragment();
            }
        });
        boardViewModel.resetBoard();
        return rootView;
    }

    private void loadHomepageFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new HomepageFragment()).commit();
    }

}