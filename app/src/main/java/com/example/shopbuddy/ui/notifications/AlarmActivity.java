package com.example.shopbuddy.ui.notifications;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.ActivityItemViewBinding;

public class AlarmActivity extends Activity {

    ActivityItemViewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityItemViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        binding.itemviewName.setText(name);

    }


}
