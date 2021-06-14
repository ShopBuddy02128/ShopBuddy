package com.example.shopbuddy.ui.tilbud;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TilbudViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TilbudViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Tilbudsavis");
    }

    public LiveData<String> getText() {
        return mText;
    }
}