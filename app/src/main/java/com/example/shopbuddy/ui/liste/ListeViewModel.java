package com.example.shopbuddy.ui.liste;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Indkøbsliste Her");
    }

    public LiveData<String> getText() {
        return mText;
    }
}