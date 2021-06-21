package com.example.shopbuddy.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentNotificationsBinding;
import com.example.shopbuddy.services.AuthService;
import com.example.shopbuddy.services.FirestoreHandler;
import com.example.shopbuddy.ui.navigation.NavigationActivity;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    public FragmentNotificationsBinding binding;

    private ArrayList<String> alarmItemArrayList = new ArrayList<>();
    private AlarmAdapter alarmAdapter;

    public NotificationsFragment(NavigationActivity parent) {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        new FirestoreHandler().getDiscountAlarmList(AuthService.getCurrentUserId(), this);

        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        alarmAdapter = new AlarmAdapter(requireActivity(),alarmItemArrayList);

        binding.mainAlarmList.setAdapter(alarmAdapter);

        EditText editText = (EditText) root.findViewById(R.id.new_alarm);

        binding.addButton.setOnClickListener(view -> {
            if(!editText.getText().toString().equals("")) {
                alarmItemArrayList.add(editText.getText().toString());
                editText.getText().clear();
                alarmAdapter.notifyDataSetChanged();
            }
        });

        binding.deleteButton.setOnClickListener(v -> removeSelectedFromList());

        return root;
    }

    public void removeSelectedFromList() {

        for(int i = alarmItemArrayList.size() - 1; i >= 0 ; i--) {
            if (alarmAdapter.isChecked(i)) {
                alarmItemArrayList.remove(i);
            }
        }
        alarmAdapter.notifyDataSetChanged();
        alarmAdapter.resetCheckedMap();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        new FirestoreHandler().updateDiscountList(AuthService.getCurrentUserId(), alarmItemArrayList);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateItemsList(ArrayList<String> items) {
        this.alarmItemArrayList.clear();
        this.alarmItemArrayList.addAll(items);
        alarmAdapter.notifyDataSetChanged();
    }

    public ArrayList<String> getAlarmItemArrayList() {
        return this.alarmItemArrayList;
    }
}