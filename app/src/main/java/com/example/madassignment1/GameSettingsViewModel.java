package com.example.madassignment1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameSettingsViewModel extends ViewModel {
    private final MutableLiveData<Integer> currentBoardSize = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> currentMatchCondition= new MutableLiveData<>(0);
    private final MutableLiveData<Integer> currentPlayerOneMarker = new MutableLiveData<>(R.drawable.x);
    private final MutableLiveData<Integer> currentPlayerTwoMarker = new MutableLiveData<>(R.drawable.o);

    private final MutableLiveData<Integer> avatarId = new MutableLiveData<>(2131230954);

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

    public LiveData<Integer> getAvatarId() {
        return avatarId;
    }
    public void setAvatarId(int value) {
        avatarId.setValue(value);
    }



}
