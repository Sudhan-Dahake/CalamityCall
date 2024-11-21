package com.example.nationalweathersystem.ui.boards;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BoardsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BoardsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is boards fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}