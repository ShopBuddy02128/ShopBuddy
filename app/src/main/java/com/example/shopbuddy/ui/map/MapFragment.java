package com.example.shopbuddy.ui.map;

import android.Manifest;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;


import com.example.shopbuddy.GoogleMapsHelper.GetNearbyPlacesData;
import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentMapBinding;


import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    private MapViewModel mapViewModel;
    private FragmentMapBinding binding;
    private GoogleMap map;
    private MapView mapView;
    private Spinner spType;
    private Button btFind;
    private LatLng userLatLng;
    private int PROXIMITY_RADIUS = 5000;
    double latitude;
    double longitude;

    private final static int LOCATION_PERMISSION = 1;

    Task<Location> locationTask;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getContext());
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION_PERMISSION);


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        btFind = binding.btFind;

        btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick2();
            }
        });

        View root = binding.getRoot();
        return root;


    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.mapView);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this.getContext(), permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this.getActivity(), new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(this.getActivity(), "Permission already granted", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this.getActivity(), "Location Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(this.getActivity(), "Location Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
    }

    private void zoomToUserLocation() {
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                latitude = userLatLng.latitude;
                longitude = userLatLng.longitude;
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15));
            }
        });

    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;

        if(ContextCompat.checkSelfPermission(this.getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            map.setMyLocationEnabled(true);

        }
        locationTask = fusedLocationProviderClient.getLastLocation();
        zoomToUserLocation();


    }

    public void onClick2(){
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();

        EditText tf_location = (EditText) binding.TFLocation;
        tf_location.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(tf_location.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
        String location =tf_location.getText().toString();


        switch (location){
            case "Netto":
                map.clear();
                String url = getUrl(latitude,longitude,location);
                dataTransfer[0] = map;
                dataTransfer[1] = url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(this.getContext(), "showing nearby Netto", Toast.LENGTH_LONG).show();
                break;
            case "Føtex":
                map.clear();
                url = getUrl(latitude,longitude,location);
                dataTransfer[0] = map;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(this.getContext(), "showing nearby Føtex", Toast.LENGTH_LONG).show();
                break;
            case "Bilka":
                map.clear();
                url = getUrl(latitude,longitude,location);
                dataTransfer[0] = map;
                dataTransfer[1] = url;


                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(this.getContext(), "showing nearby Bilka", Toast.LENGTH_LONG).show();
                break;

        }


    }

    private String getUrl(double latitude, double longitude, String nearbyPlace){
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");
        googlePlaceUrl.append("query="+nearbyPlace);
        googlePlaceUrl.append("&location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyB5XfRLJLSH2HRD6HX9oj8BcAkAv_QcoE8");

        return googlePlaceUrl.toString();
    }


}
