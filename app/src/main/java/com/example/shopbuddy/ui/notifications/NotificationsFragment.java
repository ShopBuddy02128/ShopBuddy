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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopbuddy.MainActivity;
import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentNotificationsBinding;
import com.example.shopbuddy.models.AlarmItem;
import com.example.shopbuddy.services.ToastService;
import com.example.shopbuddy.ui.navigation.NavigationActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    public FragmentNotificationsBinding binding;

    private ArrayList<String> alarmItemArrayList;
    private AlarmAdapter alarmAdapter;

    private NavigationActivity parent;
    public NotificationsFragment(NavigationActivity parent) {
        this.parent = parent;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        alarmItemArrayList = parent.getItems();

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

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSelectedFromList();
            }
        });

        return root;
    }

    public void removeSelectedFromList() {
        for(int i = alarmItemArrayList.size() - 1; i >= 0 ; i--) {
            String s = alarmAdapter.getItem(i);
            if (alarmAdapter.isChecked(i)) {
                alarmItemArrayList.remove(i);
            }
        }
        alarmAdapter.notifyDataSetChanged();
        alarmAdapter.resetCheckedMap();
    }

    public ArrayList<String> getItems() {
        return this.alarmItemArrayList;
    }

    @Override
    public void onPause() {
        parent.saveItems(alarmItemArrayList);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}