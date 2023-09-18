package com.example.madassignment1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameSettingsViewModel extends ViewModel {
    private final MutableLiveData<Integer> avatarId = new MutableLiveData<>(R.drawable.avatar_default);
    public LiveData<Integer> getAvatarId() {
        return avatarId;
    }
    public void setAvatarId(int value) {
        avatarId.setValue(value);
    }
}
