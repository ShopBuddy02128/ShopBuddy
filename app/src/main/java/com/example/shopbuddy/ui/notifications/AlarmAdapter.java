package com.example.shopbuddy.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import com.example.shopbuddy.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AlarmAdapter extends ArrayAdapter<String> {

    private HashMap<Integer, Boolean> checkedPositions = new HashMap<>();

    public AlarmAdapter(@NonNull Context context, @NonNull ArrayList<String> alarmItems) {
        super(context, R.layout.fragment_alarm_item, alarmItems);
    }

    public View getView(int position, View convertView, ViewGroup viewGroup){

        String item = getItem(position);

        if (convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_alarm_item, viewGroup, false);
        }

        CheckedTextView checkedTextView = convertView.findViewById(R.id.checked_text_view);
        checkedTextView.setChecked(false);
        checkedTextView.setText(item);

        checkedTextView.setOnClickListener(view -> {
            if(checkedTextView.isChecked()){
                checkedTextView.setChecked(false);
                checkedPositions.put(position, false);
            } else {
                checkedTextView.setChecked(true);
                checkedPositions.put(position, true);
            }
        });

        return convertView;
    }

    public boolean isChecked(int position) {
        if(checkedPositions.containsKey(position)) {
            return checkedPositions.get(position);
        }
        return false;
    }

    public void resetCheckedMap() {
        checkedPositions = new HashMap<>();
    }
}

