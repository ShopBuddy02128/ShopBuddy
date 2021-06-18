package com.example.shopbuddy.ui.foodwaste;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OfferViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OfferViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Tilbudsavis");
    }

    public LiveData<String> getText() {
        return mText;
    }
}