package com.example.madassignment1;

import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimerViewModel extends ViewModel {
    private MutableLiveData<Long> timeRemaining = new MutableLiveData<>(30000L);
    private CountDownTimer timer;

    public LiveData<Long> getTimeRemaining() {
        return timeRemaining;
    }

    public void startTimer() {
        timer = new CountDownTimer(timeRemaining.getValue(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining.setValue(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timeRemaining.setValue(0L);
            }
        }.start();
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        stopTimer();
    }

    public void resetTimer() {
        stopTimer();
        timeRemaining.setValue(30000L);
        startTimer();
    }
}
