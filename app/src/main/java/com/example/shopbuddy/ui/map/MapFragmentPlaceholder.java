package com.example.shopbuddy.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.shopbuddy.databinding.MapPlaceholderFragmentLayoutBinding;

public class MapFragmentPlaceholder extends Fragment {

    MapPlaceholderFragmentLayoutBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState
    ) {
        binding = MapPlaceholderFragmentLayoutBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

}

