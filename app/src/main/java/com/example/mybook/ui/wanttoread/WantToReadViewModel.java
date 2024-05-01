package com.example.mybook.ui.wanttoread;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WantToReadViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WantToReadViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is \"want to read\" fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}