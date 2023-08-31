package com.example.madassignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

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

    private final List<String> boardSizes = List.of("3x3", "4x4", "5x5", "6x6", "7x7", "8x8");
    private final List<String> matchConditions = List.of("3", "4", "5", "6", "7", "8");
    // TODO: Implement custom markers ?
    private final List<String> markers = List.of("X", "O");
    private AutoCompleteTextView boardSizeDropdown;
    private AutoCompleteTextView matchConditionDropdown;

    private AutoCompleteTextView markerDropdown;
    private ImageView markerImageView;

    private int currentBoardSize = 0;
    private int currentMatchCondition = 0;

    private String currentMarker = "X";


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View gameSettingsView = inflater.inflate(R.layout.fragment_game_settings, container, false);
        boardSizeDropdown = gameSettingsView.findViewById(R.id.BoardSizeOptionTextView);
        matchConditionDropdown = gameSettingsView.findViewById(R.id.MatchConditionDropDownTextView);
        markerDropdown = gameSettingsView.findViewById(R.id.MarkerSelectorDropDownTextView);
        markerImageView = gameSettingsView.findViewById(R.id.markerImage);
        // Connect drop down menu with the base element
        ArrayAdapter<String> boardSizeAdapter = new ArrayAdapter<>(requireActivity(), R.layout.list_item, boardSizes);
        ArrayAdapter<String> matchConditionAdapter = new ArrayAdapter<>(requireActivity(), R.layout.list_item, matchConditions);
        ArrayAdapter<String> markerAdapter = new ArrayAdapter<>(requireActivity(), R.layout.list_item, markers);
        boardSizeDropdown.setAdapter(boardSizeAdapter);
        matchConditionDropdown.setAdapter(matchConditionAdapter);
        markerDropdown.setAdapter(markerAdapter);
        // default conditions
        boardSizeDropdown.setText(boardSizes.get(currentBoardSize), false);
        matchConditionDropdown.setText(matchConditions.get(currentMatchCondition), false);
        markerDropdown.setText(markers.get(0), false);
        setMarkerImage(currentMarker);
        // On click listeners for each drop down menu
        boardSizeDropdown.setOnItemClickListener((adapterView, view, i, l) -> {
            // If the match condition is greater than the board size, then the board size is invalid
            if (i <= currentMatchCondition) {
                boardSizeDropdown.setText(boardSizes.get(currentBoardSize), false);
                Toast.makeText(requireActivity(), "Error: Invalid selection", Toast.LENGTH_SHORT).show();
            } else {
                currentBoardSize = i;
            }
        });
        matchConditionDropdown.setOnItemClickListener((adapterView, view, i, l) -> {
            // If the match condition is greater than the board size, then the board size is invalid
            if (i > currentBoardSize) {
                matchConditionDropdown.setText(matchConditions.get(currentMatchCondition), false);
                Toast.makeText(requireActivity(), "Error: Invalid selection", Toast.LENGTH_SHORT).show();
            } else {
                currentMatchCondition = i;
            }
        });
        markerDropdown.setOnItemClickListener((adapterView, view, i, l) -> {
            currentMarker = adapterView.getItemAtPosition(i).toString();
            setMarkerImage(currentMarker);
        });
        return gameSettingsView;
    }

    private void setMarkerImage(String marker) {
        if (marker.equals("X")) {
            markerImageView.setImageResource(R.drawable.x);
        } else {
            markerImageView.setImageResource(R.drawable.o);
        }

    }
}