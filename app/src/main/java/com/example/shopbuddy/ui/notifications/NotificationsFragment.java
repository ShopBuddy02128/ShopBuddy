package com.example.shopbuddy.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private ListView listView;
    CheckedTextView checkedTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = (ListView) root.findViewById(R.id.mainAlarmList);
        ArrayList<String> alarmItemArrayList = new ArrayList<String>();
        AlarmAdapter alarmAdapter = new AlarmAdapter(requireActivity(),alarmItemArrayList);
        //binding.mainAlarmList.setAdapter(alarmAdapter);
        listView.setAdapter(alarmAdapter);


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

        binding.mainAlarmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checkedTextView = (CheckedTextView) root.findViewById(R.id.check_alarm);
                if(checkedTextView.isChecked()){
                    checkedTextView.setCheckMarkDrawable(0);
                    checkedTextView.setChecked(false);
                    Log.i("CHECKTEXTVIEWCLICK", "List item is already clicked");
                }else{
                    checkedTextView.setCheckMarkDrawable(R.color.ShopLightBuddyBlue);
                    checkedTextView.setChecked(true);
                    Log.i("CHECKTEXTVIEWCLICK", "List item was clicked");
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
    }
}