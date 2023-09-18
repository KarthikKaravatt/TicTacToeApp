package com.example.madassignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameSettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private final List<String> boardSizes = List.of("3x3", "4x4", "5x5", "6x6", "7x7",
            "8x8", "9x9", "10x10", "11x11", "12x12", "13x13", "14x14", "15x15", "16x16", "17x17",
            "18x18", "19x19", "20x20");
    private final List<String> matchConditions = List.of("3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20");
    // TODO: Implement custom markers ?
    private final Map<String, Integer> markerImages = Map.of(
            "X", R.drawable.x,
            "O", R.drawable.o,
            "Flower", R.drawable.flower,
            "Don Cheadle", R.drawable.doncheadle,
            "Robot", R.drawable.robot);
    private final Map<Integer, String> markerImagesReversed = switchKeysAndValues(markerImages);
    private final List<String> markers = new ArrayList<>(markerImages.keySet());
    private TextInputLayout boardSizeInputLayout;
    private TextInputLayout matchConditionInputLayout;
    private AutoCompleteTextView boardSizeDropdown;
    private AutoCompleteTextView matchConditionDropdown;

    private ImageButton backButton;
    private Button startGameButton;

    private AutoCompleteTextView player1MarkerDropdown;
    private AutoCompleteTextView player2MarkerDropdown;
    private ImageView player1MarkerImageView;
    private ImageView player2MarkerImageView;

    private int currentBoardSize = 0;
    private int currentMatchCondition = 0;

    private String currentMarkerPlayer1 = "X";
    private String currentMarkerPlayer2 = "O";

    private FragmentManager fragmentManager;
    private GameFragment gameFragment = new GameFragment();
    private BoardViewModel boardViewModel;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameSettings.
     */
    // TODO: Rename and change types and number of parameters
    public static GameSettingsFragment newInstance(String param1, String param2) {
        GameSettingsFragment fragment = new GameSettingsFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // restart activity on screen rotate otherwise the drop downs do not save
        if (savedInstanceState != null) {
            GameSettingsFragment frag = new GameSettingsFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.MainActivityFrameLayout, frag);
            fragmentTransaction.commit();
        }
        fragmentManager = requireActivity().getSupportFragmentManager();
        // Inflate the layout for this fragment
        View gameSettingsView = inflater.inflate(R.layout.fragment_game_settings, container, false);
        // find the views
        backButton = gameSettingsView.findViewById(R.id.back_button);
        startGameButton = gameSettingsView.findViewById(R.id.startButton);
        boardSizeDropdown = gameSettingsView.findViewById(R.id.BoardSizeOptionTextView);
        boardSizeInputLayout = gameSettingsView.findViewById(R.id.BoardSizeOptionTextInputLayout);
        matchConditionInputLayout = gameSettingsView.findViewById(R.id.WinConditionOptionTextInputLayout);
        matchConditionDropdown = gameSettingsView.findViewById(R.id.MatchConditionDropDownTextView);
        player1MarkerDropdown = gameSettingsView.findViewById(R.id.Player1MarkerSelectorDropDownTextView);
        player2MarkerDropdown = gameSettingsView.findViewById(R.id.Player2MarkerSelectorDropDownTextView);
        player1MarkerImageView = gameSettingsView.findViewById(R.id.player1markerImage);
        player2MarkerImageView = gameSettingsView.findViewById(R.id.player2markerImage);
        boardViewModel = new ViewModelProvider(requireActivity()).get(BoardViewModel.class);
        // Connect drop down menu with the base element
        ArrayAdapter<String> boardSizeAdapter = new ArrayAdapter<>(requireActivity(), R.layout.list_item, boardSizes);
        ArrayAdapter<String> matchConditionAdapter = new ArrayAdapter<>(requireActivity(), R.layout.list_item, matchConditions);
        ArrayAdapter<String> markerAdapter = new ArrayAdapter<>(requireActivity(), R.layout.list_item, markers);
        boardSizeDropdown.setAdapter(boardSizeAdapter);
        matchConditionDropdown.setAdapter(matchConditionAdapter);
        player1MarkerDropdown.setAdapter(markerAdapter);
        player2MarkerDropdown.setAdapter(markerAdapter);
        setDefaultConditions();
        setPlayerOneMarker(currentMarkerPlayer1);
        setPlayerTwoMarker(currentMarkerPlayer2);
        setListeners();
        boardViewModel.getGamePaused().observe(getViewLifecycleOwner(), gamePaused-> {
            if (gamePaused){
                boardSizeInputLayout.setVisibility(View.GONE);
                matchConditionInputLayout.setVisibility(View.GONE);
                boardSizeDropdown.setVisibility(View.GONE);
                matchConditionDropdown.setVisibility(View.GONE);
                startGameButton.setVisibility(View.GONE);
            }
            else {
                boardSizeInputLayout.setVisibility(View.VISIBLE);
                matchConditionInputLayout.setVisibility(View.VISIBLE);
                boardSizeDropdown.setVisibility(View.VISIBLE);
                matchConditionDropdown.setVisibility(View.VISIBLE);
                startGameButton.setVisibility(View.VISIBLE);
            }
        });
        // On click listeners for each drop down menu
        return gameSettingsView;
    }

    private void setDefaultConditions() {
        currentMarkerPlayer1 = markerImagesReversed.get(boardViewModel.getPlayer1Marker());
        currentMarkerPlayer2 = markerImagesReversed.get(boardViewModel.getPlayer2Marker());
        boardSizeDropdown.setText(boardSizes.get(boardViewModel.getBoardSize() - 3), false);
        matchConditionDropdown.setText(matchConditions.get(boardViewModel.getWinCondition() - 3), false);
        player1MarkerDropdown.setText(currentMarkerPlayer1, false);
        player2MarkerDropdown.setText(currentMarkerPlayer2, false);
    }
    public static <K, V> Map<V, K> switchKeysAndValues(Map<K, V> originalMap) {
        Map<V, K> switchedMap = new HashMap<>();
        for (Map.Entry<K, V> entry : originalMap.entrySet()) {
            switchedMap.put(entry.getValue(), entry.getKey());
        }
        return switchedMap;
    }

    private void setPlayerOneMarker(String marker) {
        Integer imageResource = markerImages.get(marker);
        assert imageResource != null;
        player1MarkerImageView.setImageResource(imageResource);
        boardViewModel.setPlayer1Marker(imageResource);
        if(Boolean.FALSE.equals(boardViewModel.getGamePaused().getValue())){
            boardViewModel.setCurrentPlayer1Marker(imageResource);
        }
    }

    private void setPlayerTwoMarker(String marker) {
        Integer imageResource = markerImages.get(marker);
        assert imageResource != null;
        player2MarkerImageView.setImageResource(imageResource);
        boardViewModel.setPlayer2Marker(imageResource);
        if(Boolean.FALSE.equals(boardViewModel.getGamePaused().getValue())){
            boardViewModel.setCurrentPlayer2Marker(imageResource);
        }
    }

    // private method to load the game selection fragment when the back button is clicked
    private void loadGameSelectionFragment() {
        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new GameSelectionFragment()).commit();
    }

    private void loadGameFragment() {
        gameFragment = new GameFragment();
        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, gameFragment).commit();
    }

    private void setListeners() {

        boardSizeDropdown.setOnItemClickListener((adapterView, view, i, l) -> {
            // If the match condition is greater than the board size, then the board size is invalid
            if (i < currentMatchCondition) {
                boardSizeDropdown.setText(boardSizes.get(currentBoardSize), false);
                Toast.makeText(requireActivity(), "Error: selection", Toast.LENGTH_SHORT).show();
            } else {
                currentBoardSize = i;
                boardViewModel.setBoardSize(currentBoardSize + 3);
            }
        });
        matchConditionDropdown.setOnItemClickListener((adapterView, view, i, l) -> {
            // If the match condition is greater than the board size, then the board size is invalid
            if (i > currentBoardSize) {
                matchConditionDropdown.setText(matchConditions.get(currentMatchCondition), false);
                Toast.makeText(requireActivity(), "Error: Invalid selection", Toast.LENGTH_SHORT).show();
            } else {
                currentMatchCondition = i;
                boardViewModel.setWinCondition(currentMatchCondition + 3);
            }
        });
        player1MarkerDropdown.setOnItemClickListener((adapterView, view, i, l) -> {
            String marker = adapterView.getItemAtPosition(i).toString();
            if (marker.equals(currentMarkerPlayer2)) {
                player1MarkerDropdown.setText(currentMarkerPlayer1, false);
                Toast.makeText(requireActivity(), "Error: Invalid selection", Toast.LENGTH_SHORT).show();
            } else {
                currentMarkerPlayer1 = marker;
                setPlayerOneMarker(currentMarkerPlayer1);
            }
        });
        player2MarkerDropdown.setOnItemClickListener((adapterView, view, i, l) -> {
            String marker = adapterView.getItemAtPosition(i).toString();
            if (marker.equals(currentMarkerPlayer1)) {
                player2MarkerDropdown.setText(currentMarkerPlayer2, false);
                Toast.makeText(requireActivity(), "Error: Invalid selection", Toast.LENGTH_SHORT).show();
            } else {
                currentMarkerPlayer2 = marker;
                setPlayerTwoMarker(currentMarkerPlayer2);
            }
        });

        // on click listener for the start game button
        startGameButton.setOnClickListener(button -> {
            // perform the fragment transaction to load new game fragment
            loadGameFragment();
        });

        // on click listener for the back button
        backButton.setOnClickListener(button -> {
            // perform the fragment transaction to load new game fragment
            if (Boolean.TRUE.equals(boardViewModel.getGamePaused().getValue())) {
                loadPauseFragment();
            }
            else {
                loadGameSelectionFragment();
            }
        });
    }
    public void loadPauseFragment() {
        // get fragment manager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // begin the fragment transaction
        fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, new PauseFragment()).commit();
    }
}