package com.example.shopbuddy.ui.notifications;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.shopbuddy.R;
import java.util.ArrayList;

public class AlarmAdapter extends ArrayAdapter<String> {

    public AlarmAdapter(@NonNull Context context, ArrayList<String> alarmItems) {
        super(context, 0, alarmItems);
    }

    public View getView(int position, View convertView, ViewGroup viewGroup){
        //Get data item from a given position.
        String item = getItem(position);

        //Check if existing view is reused. If not, then inflate the view.
        if (convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_alarm_item, viewGroup, false);
        }

        //Lookup View
        CheckedTextView checkedTextView = (CheckedTextView) convertView.findViewById(R.id.check_alarm);

        checkedTextView.setText(item);
        checkedTextView.setChecked(false);
        Log.i("CHECKTEXTITEM", "See if item is checked");

        return convertView;
    }
}
