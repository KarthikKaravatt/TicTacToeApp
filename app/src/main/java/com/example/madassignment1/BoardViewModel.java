package com.example.madassignment1;

import android.widget.ImageButton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class BoardViewModel extends ViewModel {
    public LiveData<ArrayList<ArrayList<ImageButton>>> board = new MutableLiveData<>();
    public LiveData<Integer> turnsLeft;
    public LiveData<Integer> movesAvailable;

}
