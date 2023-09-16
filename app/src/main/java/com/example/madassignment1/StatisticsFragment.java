package com.example.madassignment1;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView gamesWonText;
    private TextView gamesLostText;
    private TextView gamesTiedText;
    private TextView gamesPlayedText;
    private TextView percentWonText;
    private BoardViewModel boardViewModel;
    private GameSettingsViewModel gameSettingsViewModel;
    private ImageView profilePicture;

    private TextView usernameText;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
        ImageButton backButton = rootView.findViewById(R.id.back_button);
         gamesTiedText = rootView.findViewById(R.id.drawText);
         gamesPlayedText = rootView.findViewById(R.id.gamesPlayedText);
         gamesLostText = rootView.findViewById(R.id.lossText);
         gamesWonText = rootView.findViewById(R.id.winText);
         percentWonText = rootView.findViewById(R.id.winPercentText);
        usernameText = rootView.findViewById(R.id.textView);
        boardViewModel = new ViewModelProvider(requireActivity()).get(BoardViewModel.class);
        gameSettingsViewModel = new ViewModelProvider(requireActivity()).get(GameSettingsViewModel.class);
        profilePicture = rootView.findViewById(R.id.imageView);
        double percent;

        usernameText.setText(boardViewModel.getUsername().getValue());
        profilePicture.setImageResource(gameSettingsViewModel.getAvatarId().getValue());

        // if no games have been played yet, set the win percentage to 0.
        if (boardViewModel.getGamesPlayed().getValue() == 0)
        {
            percent = 0.0;
        }
        else {
            percent = ((double) boardViewModel.getGamesWon().getValue() / boardViewModel.getGamesPlayed().getValue()) * 100;
            percent =  Math.round(percent * 10.0) / 10.0;
        }
        gamesTiedText.setText("Games Tied: " + boardViewModel.getGamesTied().getValue().toString());
        gamesLostText.setText("Games Lost: " + boardViewModel.getGamesLost().getValue().toString());
        gamesWonText.setText("Games Won: "+ boardViewModel.getGamesWon().getValue().toString());
        gamesPlayedText.setText("Games Played: "+ boardViewModel.getGamesPlayed().getValue().toString());
        percentWonText.setText("Win Percentage: " + percent +"%");


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform the fragment transaction to load HomepageFragment
                loadHomepageFragment();
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    private void loadHomepageFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new HomepageFragment()).commit();
    }
}