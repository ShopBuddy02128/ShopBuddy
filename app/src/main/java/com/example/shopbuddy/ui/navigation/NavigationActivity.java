package com.example.shopbuddy.ui.navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.ActivityMainBinding;
import com.example.shopbuddy.ui.map.MapFragment;
import com.example.shopbuddy.ui.notifications.NotificationsFragment;
import com.example.shopbuddy.ui.foodwaste.FoodWasteFragment;
import com.example.shopbuddy.ui.foodwaste.FoodWasteItemsFragment;
import com.example.shopbuddy.ui.shoplist.ListsListFragment;
import com.example.shopbuddy.ui.shoplist.ShopListFragment;
import com.example.shopbuddy.utils.DummyData;
import com.example.shopbuddy.utils.JSONReader;


public class NavigationActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    public ListsListFragment listsListFragment;
    public ShopListFragment shopListFragment;
    public MapFragment mapFragment;
    public NotificationsFragment notificationsFragment;
    public FoodWasteFragment foodWasteFragment;
    public FoodWasteItemsFragment foodWasteItemsFragment;
    private Fragment currentFragment;

    private ImageView menuButton1, menuButton2, menuButton3, menuButton4;
    private TextView menuButton1Text, menuButton2Text, menuButton3Text, menuButton4Text;

    boolean firstFragmentUsed = false;

    public final int MAP_BUTTON = 1;
    public final int OFFERS_BUTTON = 2;
    public final int SHOPLIST_BUTTON = 3;
    public final int ALARM_BUTTON = 4;
    public final int BACK_BUTTON = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Classic
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Instantiate the buttons
        setupMenuButtons(binding);

        //Instantiate the fragments
        listsListFragment = new ListsListFragment();
        listsListFragment.setNavigationActivity(this);

        shopListFragment = new ShopListFragment();

        mapFragment = new MapFragment();

        notificationsFragment = new NotificationsFragment();

        foodWasteFragment = new FoodWasteFragment(JSONReader.getFoodWasteFromJson(DummyData.jsonExample));
        foodWasteFragment.setNavigationActivity(this);

        foodWasteItemsFragment = new FoodWasteItemsFragment();
        foodWasteItemsFragment.setNavigationActivity(this);


        //Start by going to first fragment
        changePage(1);
    }


    public void setupMenuButtons(ActivityMainBinding binding){
        menuButton1 = binding.menuButton1;
        menuButton1Text = binding.menuButton1Text;
        menuButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(1);
            }
        });
        menuButton2 = binding.menuButton2;
        menuButton2Text = binding.menuButton2Text;
        menuButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(2);
            }
        });
        menuButton3 = binding.menuButton3;
        menuButton3Text = binding.menuButton3Text;
        menuButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(3);
            }
        });
        menuButton4 = binding.menuButton4;
        menuButton4Text = binding.menuButton4Text;
        menuButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(4);
            }
        });
    }

    public void changePage(int pagenumber){
        ActionBar actionBar = getSupportActionBar();
        switch(pagenumber){
            case 1:
                changeToFragment(mapFragment);
                actionBar.setTitle(getString(R.string.menu_button_1));

                break;
            case 2:
                changeToFragment(foodWasteFragment);
                actionBar.setTitle(getString(R.string.menu_button_2));


                break;
            case 3:
                changeToFragment(shopListFragment);
                actionBar.setTitle(getString(R.string.menu_button_3));


                break;
            case 4:
                changeToFragment(notificationsFragment);
                actionBar.setTitle(getString(R.string.menu_button_4));


                break;

        }
    }

    public void setButtonVisibilities(int pagenumber) {
        switch(pagenumber){
            case 1:
                menuButton1Text.setVisibility(View.VISIBLE);
                menuButton2Text.setVisibility(View.GONE);
                menuButton3Text.setVisibility(View.GONE);
                menuButton4Text.setVisibility(View.GONE);
                break;

            case 2:
                menuButton1Text.setVisibility(View.GONE);
                menuButton2Text.setVisibility(View.VISIBLE);
                menuButton3Text.setVisibility(View.GONE);
                menuButton4Text.setVisibility(View.GONE);
                break;

            case 3:
                menuButton1Text.setVisibility(View.GONE);
                menuButton2Text.setVisibility(View.GONE);
                menuButton3Text.setVisibility(View.VISIBLE);
                menuButton4Text.setVisibility(View.GONE);
                break;

            case 4:
                menuButton1Text.setVisibility(View.GONE);
                menuButton2Text.setVisibility(View.GONE);
                menuButton3Text.setVisibility(View.GONE);
                menuButton4Text.setVisibility(View.VISIBLE);
        }
    }

    public void changeToFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        if(!firstFragmentUsed){
            firstFragmentUsed = true;
        }
        else{
            ft.addToBackStack(null);
        }
        ft.commit();

        currentFragment = fragment;
        updateMenuButtons();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        updateMenuButtons();
    }

    public void updateMenuButtons() {

        if (currentFragment == mapFragment) setButtonVisibilities(1);
        if (currentFragment == foodWasteFragment) setButtonVisibilities(2);
        if (currentFragment == listsListFragment || currentFragment == shopListFragment) setButtonVisibilities(3);
        if (currentFragment == notificationsFragment) setButtonVisibilities(4);

    }

}