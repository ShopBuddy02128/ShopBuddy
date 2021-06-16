package com.example.shopbuddy.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shopbuddy.databinding.ActivityItemViewBinding;

public class AlarmActivity extends AppCompatActivity {

    ActivityItemViewBinding bind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityItemViewBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        Intent intent = getIntent();
        String name = intent.getStringExtra("alarm");


    }


}
