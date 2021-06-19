package com.example.shopbuddy.ui.notifications;

import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopbuddy.MainActivity;
import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentNotificationsBinding;
import com.example.shopbuddy.models.AlarmItem;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    public FragmentNotificationsBinding binding;

    private ArrayList<String> alarmItemArrayList;
    private AlarmAdapter alarmAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        alarmItemArrayList = new ArrayList<>();
        alarmItemArrayList.add("Havredrik");
        alarmAdapter = new AlarmAdapter(requireActivity(),alarmItemArrayList);
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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}