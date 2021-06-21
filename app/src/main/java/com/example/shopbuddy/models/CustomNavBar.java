package com.example.shopbuddy.models;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomNavBar {

    private int MAP_PAGE = 0;
    private int FOOD_WASTE_PAGE = 1;
    private int SHOP_LIST_PAGE = 2;
    private int ALARM_PAGE = 3;

    public ImageView mapButton, foodwasteButton, shoplistButton, alarmButton;
    public TextView mb1t, mb2t, mb3t, mb4t; //Menu Button X Text

    public CustomNavBar(){

    }

    public void setButtons(ImageView mapButton, ImageView foodwasteButton, ImageView shoplistButton, ImageView alarmButton){
        this.mapButton = mapButton;
        this.foodwasteButton = foodwasteButton;
        this.shoplistButton = shoplistButton;
        this.alarmButton = alarmButton;
    }

    public void setButtonText(TextView mb1t, TextView mb2t, TextView mb3t, TextView mb4t){
        this.mb1t = mb1t;
        this.mb2t = mb2t;
        this.mb3t = mb3t;
        this.mb4t = mb4t;
    }

    public void setButtonVisibilities(int pagenumber) {
        switch(pagenumber){
            case 0:
                mb1t.setVisibility(View.VISIBLE);
                mb2t.setVisibility(View.GONE);
                mb3t.setVisibility(View.GONE);
                mb4t.setVisibility(View.GONE);
                break;

            case 1:
                mb1t.setVisibility(View.GONE);
                mb2t.setVisibility(View.VISIBLE);
                mb3t.setVisibility(View.GONE);
                mb4t.setVisibility(View.GONE);
                break;

            case 2:
                mb1t.setVisibility(View.GONE);
                mb2t.setVisibility(View.GONE);
                mb3t.setVisibility(View.VISIBLE);
                mb4t.setVisibility(View.GONE);
                break;

            case 3:
                mb1t.setVisibility(View.GONE);
                mb2t.setVisibility(View.GONE);
                mb3t.setVisibility(View.GONE);
                mb4t.setVisibility(View.VISIBLE);
        }
    }

    public void updateMenuButtons(int navButton) {

        if (navButton == MAP_PAGE) setButtonVisibilities(MAP_PAGE);
        else if (navButton == FOOD_WASTE_PAGE) setButtonVisibilities(FOOD_WASTE_PAGE);
        else if (navButton == SHOP_LIST_PAGE) setButtonVisibilities(SHOP_LIST_PAGE);
        else if (navButton == ALARM_PAGE) setButtonVisibilities(ALARM_PAGE);

    }
}
