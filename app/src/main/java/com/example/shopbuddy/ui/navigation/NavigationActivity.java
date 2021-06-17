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
//import com.example.shopbuddy.ui.map.MapFragment;
import com.example.shopbuddy.ui.map.MapFragmentPlaceholder;
import com.example.shopbuddy.ui.notifications.NotificationsFragment;
import com.example.shopbuddy.ui.offer.OfferFragment;
import com.example.shopbuddy.ui.shoplist.ListsListFragment;
import com.example.shopbuddy.ui.shoplist.ShoppingListFragment;


public class NavigationActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private ListsListFragment listsListFragment;
    private ShoppingListFragment shoppingListFragment;
    private MapFragmentPlaceholder mapFragment;
    private NotificationsFragment notificationsFragment;
    private OfferFragment offerFragment;

    private Fragment currentFragment;

    private ImageView menuButton1, menuButton2, menuButton3, menuButton4;
    private TextView menuButton1Text, menuButton2Text, menuButton3Text, menuButton4Text;

    private final int MAP_FRAGMENT_ID = 1;
    private final int OFFER_FRAGMENT_ID = 2;
    private final int SHOPLIST_FRAGMENT_ID = 3;
    private final int ALARM_FRAGMENT_ID = 4;
    private final int BACK_BUTTON_ID = 5;

    //Used to keep track of what to add to backstack
    boolean firstFragmentUsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Classic
        super.onCreate(savedInstanceState);
        int orientation = getResources().getConfiguration().orientation;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Instantiate the buttons
        setupMenuButtons(binding);

        //Instantiate the fragments
        listsListFragment = new ListsListFragment();
        listsListFragment.setNavigationActivity(this);
        shoppingListFragment = new ShoppingListFragment();
        mapFragment = new MapFragmentPlaceholder();
        notificationsFragment = new NotificationsFragment();
        offerFragment = new OfferFragment();


        //Start by going to first fragment
        changePage(MAP_FRAGMENT_ID);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
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
                changeToFragment(mapFragment, MAP_FRAGMENT_ID);

                break;
            case 2:
                changeToFragment(offerFragment, OFFER_FRAGMENT_ID);

                break;
            case 3:
                changeToFragment(listsListFragment, SHOPLIST_FRAGMENT_ID);

                break;
            case 4:
                changeToFragment(notificationsFragment, ALARM_FRAGMENT_ID);

                break;

        }
    }

    public void setButtonVisibilities(int pagenumber) {
        switch(pagenumber){
            case MAP_FRAGMENT_ID:
                menuButton1Text.setVisibility(View.VISIBLE);
                menuButton2Text.setVisibility(View.GONE);
                menuButton3Text.setVisibility(View.GONE);
                menuButton4Text.setVisibility(View.GONE);
                break;

            case OFFER_FRAGMENT_ID:
                menuButton1Text.setVisibility(View.GONE);
                menuButton2Text.setVisibility(View.VISIBLE);
                menuButton3Text.setVisibility(View.GONE);
                menuButton4Text.setVisibility(View.GONE);
                break;

            case SHOPLIST_FRAGMENT_ID:
                menuButton1Text.setVisibility(View.GONE);
                menuButton2Text.setVisibility(View.GONE);
                menuButton3Text.setVisibility(View.VISIBLE);
                menuButton4Text.setVisibility(View.GONE);
                break;

            case ALARM_FRAGMENT_ID:
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

        currentFragment = fragment;
        updateMenuButtons(navButton);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        updateMenuButtons(5);
    }

    public void updateMenuButtons(int navButton) {
        //Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        //if (navButton == MAP_FRAGMENT_ID) setButtonVisibilities(MAP_FRAGMENT_ID);
        //if (navButton == OFFER_FRAGMENT_ID) setButtonVisibilities(OFFER_FRAGMENT_ID);
        //if (navButton == SHOPLIST_FRAGMENT_ID) setButtonVisibilities(SHOPLIST_FRAGMENT_ID);
        //if (navButton == ALARM_FRAGMENT_ID) setButtonVisibilities(ALARM_FRAGMENT_ID);
        //if (navButton == BACK_BUTTON_ID){
            if (currentFragment == mapFragment) setButtonVisibilities(MAP_FRAGMENT_ID);
            if (currentFragment == offerFragment) setButtonVisibilities(OFFER_FRAGMENT_ID);
            if (currentFragment == listsListFragment || currentFragment == shoppingListFragment) setButtonVisibilities(SHOPLIST_FRAGMENT_ID);
            if (currentFragment == notificationsFragment) setButtonVisibilities(ALARM_FRAGMENT_ID);
        //}
    }

}