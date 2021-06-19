package com.example.shopbuddy.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.shopbuddy.R;
import com.example.shopbuddy.models.AlarmItem;
import com.google.android.material.transition.Hold;

import java.util.ArrayList;

public class AlarmAdapter extends ArrayAdapter<String> {

    private ArrayList<String> data;

    public AlarmAdapter(@NonNull Context context, @NonNull ArrayList<String> alarmItems) {
        super(context, R.layout.fragment_alarm_item, alarmItems);
        data = alarmItems;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup){
        //Get data item from a given position.
        String item = getItem(position);

        //Check if existing view is reused. If not, then inflate the view.
        if (convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_alarm_item, viewGroup, false);
        }

        //Lookup View
        CheckedTextView checkedTextView = convertView.findViewById(R.id.checked_text_view);
        checkedTextView.setText(item);

        checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkedTextView.isChecked()){
                    checkedTextView.setChecked(false);
                } else {
                    checkedTextView.setChecked(true);
                }
            }
        });

        return convertView;
    }

    public ArrayList<String> getData() {
        return data;
    }
}
