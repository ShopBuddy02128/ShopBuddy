package com.example.shopbuddy.ui.navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.ActivityMainBinding;
import com.example.shopbuddy.services.ToastService;
import com.example.shopbuddy.ui.map.MapFragment;
import com.example.shopbuddy.ui.notifications.NotificationsFragment;
import com.example.shopbuddy.ui.foodwaste.FoodWasteFragment;
import com.example.shopbuddy.ui.foodwaste.FoodWasteItemsFragment;
import com.example.shopbuddy.ui.shoplist.ListsListFragment;
import com.example.shopbuddy.ui.shoplist.ShopListFragment;
import com.example.shopbuddy.utils.CustomBackStack;
import com.example.shopbuddy.utils.DummyData;
import com.example.shopbuddy.utils.JSONReader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class NavigationActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    public ListsListFragment listsListFragment;
    public ShopListFragment shopListFragment;
    public MapFragment mapFragment;
    public NotificationsFragment notificationsFragment;
    public FoodWasteFragment foodWasteFragment;

    private ImageView menuButton1, menuButton2, menuButton3, menuButton4;
    private TextView menuButton1Text, menuButton2Text, menuButton3Text, menuButton4Text;

    private ActionBar actionBar;
    private String[] actionBarTitles;

    boolean firstFragmentUsed = false;

    public final int MAP_BUTTON = 1;
    public final int OFFERS_BUTTON = 2;
    public final int SHOPLIST_BUTTON = 3;
    public final int ALARM_BUTTON = 4;
    public final int BACK_BUTTON = 5;

    private CustomBackStack customBackStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Classic
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            ToastService.makeToast("FAILED", Toast.LENGTH_SHORT);
        } else {
            ToastService.makeToast("TRUE", Toast.LENGTH_SHORT);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Instantiate the buttons
        setupMenuButtons(binding);

        //Instantiate the custom backstack
        customBackStack = new CustomBackStack();
        actionBar = getSupportActionBar();
        actionBarTitles = new String[]{"null", getString(R.string.menu_button_1), getString(R.string.menu_button_2),getString(R.string.menu_button_3),getString(R.string.menu_button_4)};

        //Instantiate the fragments
        listsListFragment = new ListsListFragment();
        listsListFragment.setNavigationActivity(this);

        shopListFragment = new ShopListFragment();

        mapFragment = new MapFragment();

        notificationsFragment = new NotificationsFragment(this);

        foodWasteFragment = new FoodWasteFragment(JSONReader.getFoodWasteFromJson(DummyData.jsonExample), mapFragment);
        foodWasteFragment.setNavigationActivity(this);


        //Start by going to first fragment
        changePage(MAP_BUTTON);

        discountAlarmItems.add("Havredrik");
    }


    public void setupMenuButtons(ActivityMainBinding binding){
        menuButton1 = binding.menuButton1;
        menuButton1Text = binding.menuButton1Text;
        menuButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(MAP_BUTTON);
            }
        });
        menuButton2 = binding.menuButton2;
        menuButton2Text = binding.menuButton2Text;
        menuButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(OFFERS_BUTTON);
            }
        });
        menuButton3 = binding.menuButton3;
        menuButton3Text = binding.menuButton3Text;
        menuButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(SHOPLIST_BUTTON);
            }
        });
        menuButton4 = binding.menuButton4;
        menuButton4Text = binding.menuButton4Text;
        menuButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(ALARM_BUTTON);
            }
        });
    }

    public void changePage(int pagenumber){
        switch(pagenumber){
            case 1:
                changeToFragment(mapFragment, MAP_BUTTON);

                break;
            case 2:
                changeToFragment(foodWasteFragment, OFFERS_BUTTON);

                break;
            case 3:
                changeToFragment(shopListFragment, SHOPLIST_BUTTON);

                break;
            case 4:
                changeToFragment(notificationsFragment, ALARM_BUTTON);

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

    public void changeToFragment(Fragment fragment, int navButton){
        FragmentManager fm = getSupportFragmentManager();

        if(fm.findFragmentById(R.id.fragment_container) == fragment) return;

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        if(!firstFragmentUsed){
            firstFragmentUsed = true;
        }
        else{
            ft.addToBackStack(null);
        }
        ft.commit();

        customBackStack.addToBackStack(navButton);
        updateMenuButtons();
    }

    @Override
    public void onBackPressed(){
        if(customBackStack.size() > 1) {
            super.onBackPressed();
            customBackStack.popCurrent();
            updateMenuButtons();
        }
    }

    public void updateMenuButtons() {

        int navButton = customBackStack.getCurrent();

        actionBar.setTitle(actionBarTitles[navButton]);

        if (navButton == MAP_BUTTON) setButtonVisibilities(MAP_BUTTON);
        else if (navButton == OFFERS_BUTTON) setButtonVisibilities(OFFERS_BUTTON);
        else if (navButton == SHOPLIST_BUTTON) setButtonVisibilities(SHOPLIST_BUTTON);
        else if (navButton == ALARM_BUTTON) setButtonVisibilities(ALARM_BUTTON);

    }


    ArrayList<String> discountAlarmItems = new ArrayList<String>();
    public void saveItems(ArrayList<String> items) {
        this.discountAlarmItems = items;
    }

    public ArrayList<String> getItems() {
        return this.discountAlarmItems;
    }
}