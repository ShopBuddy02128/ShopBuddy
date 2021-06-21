package com.example.shopbuddy.ui.navigation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import com.example.shopbuddy.MainActivity;
import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.ActivityMainBinding;
import com.example.shopbuddy.models.CustomNavBar;
import com.example.shopbuddy.services.AlarmService;
import com.example.shopbuddy.services.AuthService;
import com.example.shopbuddy.services.FirestoreHandler;
import com.example.shopbuddy.services.NavigationService;
import com.example.shopbuddy.services.ToastService;
import com.example.shopbuddy.ui.map.MapFragment;
import com.example.shopbuddy.ui.notifications.NotificationsFragment;
import com.example.shopbuddy.ui.foodwaste.FoodWasteFragment;
import com.example.shopbuddy.ui.shoplist.ListsListFragment;
import com.example.shopbuddy.ui.shoplist.ShopListFragment;
import com.example.shopbuddy.utils.CustomBackStack;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class NavigationActivity extends AppCompatActivity implements LocationListener {
    private ActivityMainBinding binding;


    private ListsListFragment listsListFragment;
    private ShopListFragment shopListFragment;
    private MapFragment mapFragment;
    private NotificationsFragment notificationsFragment;
    private FoodWasteFragment foodWasteFragment;

    private ImageView menuButton1, menuButton2, menuButton3, menuButton4;
    private TextView menuButton1Text, menuButton2Text, menuButton3Text, menuButton4Text;

    private ActionBar actionBar;
    private TextView actionBarTitle;
    private String[] actionBarTitles;

    private CustomNavBar navbar;

    ArrayList<String> discountAlarmItems = new ArrayList<String>();

    private CustomBackStack customBackStack;
    private boolean locationChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Classic
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setup custom actionbar
        actionBar = getSupportActionBar();
        actionBarTitles = new String[]{"null", getString(R.string.menu_button_1), getString(R.string.menu_button_2), getString(R.string.menu_button_3), getString(R.string.menu_button_4)};
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.activity_custom_actionbar, null);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(v);
        actionBarTitle = (TextView) findViewById(R.id.custom_actionbar_title);
        ((Button) findViewById(R.id.custom_actionbar_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthService.getmAuth().signOut();
                Intent createNavigationActivity = new Intent(NavigationActivity.this, MainActivity.class);
                createNavigationActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(createNavigationActivity);
                AlarmService.alarmDiscountRemove();
                ToastService.makeToast("Logged out", Toast.LENGTH_SHORT);
            }
        });


        //Instantiate the fragments
        listsListFragment = new ListsListFragment();
        listsListFragment.setNavigationActivity(this);

        shopListFragment = new ShopListFragment();
        // Prepare Database for user and their shopping lists
        new FirestoreHandler().prepareShoppingListForUser(AuthService.getCurrentUserId(), AuthService.getmAuth().getCurrentUser().getEmail(), shopListFragment);


        mapFragment = new MapFragment();

        notificationsFragment = new NotificationsFragment(this);
        AlarmService.setNotificationsFragment(notificationsFragment);

        try {
            AlarmService.createDiscountAlarm();
        } catch (Exception e) {
            Log.e("Error",e.getMessage());
        }
        // Make sure the user has an alarmlist in the DB
        new FirestoreHandler().prepareAlarmListForUser(AuthService.getCurrentUserId());


        foodWasteFragment = new FoodWasteFragment(mapFragment);
        foodWasteFragment.setNavigationActivity(this);

        LocationManager locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

        //setupNavbar and navigation service
        setupNavBar(binding);
        new NavigationService();
        NavigationService.setNavigationActivity(this);
        NavigationService.setCustomNavBar(navbar);
        NavigationService.setPredifinedTabRoots(new Fragment[]{mapFragment, foodWasteFragment, shopListFragment, notificationsFragment});

        //Start by going to first fragment
        NavigationService.changePage(NavigationService.MAP_PAGE);
    }


    public void setupNavBar(ActivityMainBinding binding){

        menuButton1 = binding.menuButton1;
        menuButton1Text = binding.menuButton1Text;
        menuButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationService.changePage(NavigationService.MAP_PAGE);
            }
        });
        menuButton2 = binding.menuButton2;
        menuButton2Text = binding.menuButton2Text;
        menuButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationService.changePage(NavigationService.FOOD_WASTE_PAGE);
            }
        });
        menuButton3 = binding.menuButton3;
        menuButton3Text = binding.menuButton3Text;
        menuButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationService.changePage(NavigationService.SHOP_LIST_PAGE);
            }
        });
        menuButton4 = binding.menuButton4;
        menuButton4Text = binding.menuButton4Text;
        menuButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationService.changePage(NavigationService.ALARM_PAGE);
            }
        });

        navbar = new CustomNavBar();
        navbar.setButtons(menuButton1, menuButton2, menuButton3, menuButton4);
        navbar.setButtonText(menuButton1Text, menuButton2Text, menuButton3Text, menuButton4Text);
    }

    @Override
    public void onBackPressed(){
        if(NavigationService.canGoBack()) {
            super.onBackPressed();
            NavigationService.goBack();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(!locationChecked) {
            locationChecked = true;
            try {
                LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                double latitude = userLatLng.latitude;
                double longitude = userLatLng.longitude;

                List<Address> addresses = new Geocoder(getApplicationContext(), Locale.getDefault()).getFromLocation(latitude, longitude, 1);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public TextView getActionBarTitle(){
        return actionBarTitle;
    }
}