package com.example.madassignment1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameSettingsViewModel extends ViewModel {
    private MutableLiveData<Integer> currentBoardSize = new MutableLiveData<>(0);
    private MutableLiveData<Integer> currentMatchCondition= new MutableLiveData<>(0);
    private MutableLiveData<Integer> currentPlayerOneMarker = new MutableLiveData<>(R.drawable.x);
    private MutableLiveData<Integer> currentPlayerTwoMarker = new MutableLiveData<>(R.drawable.o);

    public void setCurrentBoardSize(int boardSize) {
        currentBoardSize.setValue(boardSize);
    }
    public LiveData<Integer> getCurrentBoardSize() {
        return currentBoardSize;
    }
    public void setCurrentMatchCondition(int matchCondition) {
        currentMatchCondition.setValue(matchCondition);
    }
    public LiveData<Integer> getCurrentMatchCondition() {
        return currentMatchCondition;
    }
    public void setCurrentPlayerOneMarker(int playerOneMarker) {
        currentPlayerOneMarker.setValue(playerOneMarker);
    }
    public LiveData<Integer> getCurrentPlayerOneMarker() {
        return currentPlayerOneMarker;
    }
    public void setCurrentPlayerTwoMarker(int playerTwoMarker) {
        currentPlayerTwoMarker.setValue(playerTwoMarker);
    }
    public LiveData<Integer> getCurrentPlayerTwoMarker() {
        return currentPlayerTwoMarker;
    }
    public void reset() {
        currentBoardSize.setValue(0);
        currentMatchCondition.setValue(0);
        currentPlayerOneMarker.setValue(R.drawable.x);
        currentPlayerTwoMarker.setValue(R.drawable.o);
    }

}