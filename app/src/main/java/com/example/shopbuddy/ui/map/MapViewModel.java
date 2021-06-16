package com.example.shopbuddy.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {
    private SavedStateHandle state;

    public MapViewModel(SavedStateHandle savedStateHandle) {
        state = savedStateHandle;
    }

}
