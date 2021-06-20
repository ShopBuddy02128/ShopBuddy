package com.example.shopbuddy.models;

import com.example.shopbuddy.ui.notifications.AlarmAdapter;

public class AlarmItem {

    String alarmName;
    boolean checked;

    public AlarmItem(String alarmName){
        this.alarmName = alarmName;
        checked = false;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public boolean getChecked(){
        return checked;
    }
}
