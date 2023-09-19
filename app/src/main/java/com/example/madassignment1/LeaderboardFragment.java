package com.example.madassignment1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private BoardViewModel boardViewModel;

    private int wins1, wins2, wins3, wins4, userWins;
    private String username1, username2, username3, username4, username;
    private TextView firstPlace, secondPlace, thirdPlace, fourthPlace, fifthPlace;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaderboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderboardFragment newInstance(String param1, String param2) {
        LeaderboardFragment fragment = new LeaderboardFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        ImageButton backButton = rootView.findViewById(R.id.back_button);
        boardViewModel = new ViewModelProvider(requireActivity()).get(BoardViewModel.class);

        firstPlace = rootView.findViewById(R.id.firstName);
        secondPlace = rootView.findViewById(R.id.secondName);
        thirdPlace = rootView.findViewById(R.id.thirdName);
        fourthPlace = rootView.findViewById(R.id.fourthName);
        fifthPlace = rootView.findViewById(R.id.fifthName);

        initialiseData();

        String[] usernames = {username1, username2, username3, username4, username};
        int[] wins = {wins1, wins2, wins3, wins4, userWins};


        for (int i = 0; i < wins.length - 1; i++) {
            for (int j = i + 1; j < wins.length; j++) {
                if (wins[i] < wins[j]) {
                    //re-order by win number
                    int tempWins = wins[i];
                    wins[i] = wins[j];
                    wins[j] = tempWins;
                    // re-order usernames to match
                    String tempUsername = usernames[i];
                    usernames[i] = usernames[j];
                    usernames[j] = tempUsername;
                }
            }
        }

        firstPlace.setText(usernames[0] + " - " + wins[0] + " wins");
        secondPlace.setText(usernames[1] + " - " + wins[1] + " wins");
        thirdPlace.setText(usernames[2] + " - " + wins[2] + " wins");
        fourthPlace.setText(usernames[3] + " - " + wins[3] + " wins");
        fifthPlace.setText(usernames[4] + " - " + wins[4] + " wins");

        backButton.setOnClickListener(v -> {
            // perform the fragment transaction to load HomepageFragment
            loadHomepageFragment();
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

    private void initialiseData()
    {
        wins1=10;
        wins2=7;
        wins3=4;
        wins4=2;
        userWins =  boardViewModel.getGamesWon().getValue();
        username1 = "sajib";
        username2 = "gridmaster";
        username3 = "mango";
        username4 = "xochamp";
        username=(boardViewModel.getUsername().getValue());
    }

}