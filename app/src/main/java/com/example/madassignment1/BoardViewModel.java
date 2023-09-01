package com.example.madassignment1;

import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class BoardViewModel extends ViewModel {
    private LiveData<LinearLayout> boardLayout;
    public LiveData<Integer> turnsLeft;
    public LiveData<Integer> movesAvailable;

    public void setBoardLayout(LinearLayout boardLayout) {
        this.boardLayout = new MutableLiveData<>(boardLayout);
    }
    public boolean hasLayout() {
        return boardLayout != null;
    }
    public LinearLayout getBoardLayout() {
        return boardLayout.getValue();
    }
}
