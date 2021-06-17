package com.example.shopbuddy.ui.notifications;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private ArrayList<String> alarmItemArrayList;
    private AlarmAdapter alarmAdapter;

    private boolean fromBack = false;
    private AlarmAdapter savedAlarmAdapter;
    private ArrayList<String> savedDataItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        alarmItemArrayList = new ArrayList<String>();
        alarmAdapter = new AlarmAdapter(requireActivity(),alarmItemArrayList);

        if (fromBack){
            savedDataItems = alarmItemArrayList;
            savedAlarmAdapter = alarmAdapter;
        }

        binding.mainAlarmList.setAdapter(alarmAdapter);



        EditText editText = (EditText) root.findViewById(R.id.new_alarm);
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().equals("")) {
                    String newAlarm = editText.getText().toString();
                    alarmItemArrayList.add(newAlarm);
                    editText.getText().clear();
                    alarmAdapter.notifyDataSetChanged();
                }
            }
        });
/*
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                int count = listView.getCount();
                for (int i = count-1; i >= 0 ; i--){
                    if (checkedItems.get(i)){
                        alarmAdapter.remove(alarmItemArrayList.get(i));
                    }
                    checkedItems.clear();
                    alarmAdapter.notifyDataSetChanged();
                }
            }
        });
*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        savedAlarmAdapter = alarmAdapter;
        savedDataItems = alarmItemArrayList;
        fromBack = true;
    }
}