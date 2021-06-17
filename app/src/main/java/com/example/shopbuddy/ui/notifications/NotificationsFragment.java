package com.example.shopbuddy.ui.notifications;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private ArrayList<String> alarmItemArrayList = new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String[] items = {"Øko smør", "Havredrik"};
        alarmItemArrayList.addAll(Arrays.asList(items));

        AlarmAdapter alarmAdapter = new AlarmAdapter(requireActivity(),alarmItemArrayList);
        binding.mainAlarmList.setAdapter(alarmAdapter);

        EditText editText = (EditText) root.findViewById(R.id.new_alarm);
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().equals("")) {
                    String newAlarm = editText.getText().toString();
                    alarmItemArrayList.add(newAlarm);
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