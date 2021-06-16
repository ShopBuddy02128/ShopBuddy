package com.example.shopbuddy.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shopbuddy.R;
import java.util.ArrayList;

public class AlarmAdapter extends ArrayAdapter<String> {

    public AlarmAdapter(@NonNull Context context, @NonNull ArrayList<String> alarms) {
        super(context, R.layout.fragment_alarm_item, alarms);
    }

    public View getView(@Nullable View view, @NonNull ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_alarm_item, viewGroup, false);
        }
        return null;
    }
}
