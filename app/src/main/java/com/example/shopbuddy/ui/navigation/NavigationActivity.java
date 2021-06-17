package com.example.shopbuddy.ui.navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.ActivityMainBinding;
import com.example.shopbuddy.ui.map.MapFragment;
import com.example.shopbuddy.ui.notifications.NotificationsFragment;
import com.example.shopbuddy.ui.offer.OfferFragment;
import com.example.shopbuddy.ui.shoplist.ListsListFragment;
import com.example.shopbuddy.ui.shoplist.ShopListFragment;


public class NavigationActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private ListsListFragment listsListFragment;
    private ShopListFragment shopListFragment;
    private MapFragment mapFragment;
    private NotificationsFragment notificationsFragment;
    private OfferFragment offerFragment;

    private ImageView menuButton1, menuButton2, menuButton3, menuButton4;
    private TextView menuButton1Text, menuButton2Text, menuButton3Text, menuButton4Text;

    boolean firstFragmentUsed = false;

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
        offerFragment = new OfferFragment();


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
        switch(pagenumber){
            case 1:
                changeToFragment(mapFragment, 1);

                break;
            case 2:
                changeToFragment(offerFragment, 2);

                break;
            case 3:
                changeToFragment(shopListFragment, 3);

                break;
            case 4:
                changeToFragment(notificationsFragment, 4);

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
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        if(firstFragmentUsed == false){
            firstFragmentUsed = true;
        }
        else{
            ft.addToBackStack(null);
        }
        ft.commit();

        updateMenuButtons(navButton);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        updateMenuButtons(5);
    }

    public void updateMenuButtons(int navButton) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (navButton == 1) setButtonVisibilities(1);
        if (navButton == 2) setButtonVisibilities(2);
        if (navButton == 3) setButtonVisibilities(3);
        if (navButton == 4) setButtonVisibilities(4);
        if (navButton == 5){
            if (f == mapFragment) setButtonVisibilities(1);
            if (f == offerFragment) setButtonVisibilities(2);
            if (f == listsListFragment || f == shopListFragment) setButtonVisibilities(3);
            if (f == notificationsFragment) setButtonVisibilities(4);
        }
    }

}