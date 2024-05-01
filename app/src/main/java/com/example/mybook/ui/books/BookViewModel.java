package com.example.mybook.ui.books;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BookViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Click on the \"+\" sign to display the documents on the screen");
    }

    public LiveData<String> getText() {
        return mText;
    }
}