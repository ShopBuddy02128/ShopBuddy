package com.example.shopbuddy.ui.map;

import android.Manifest;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;


import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;


import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;


import com.example.shopbuddy.GoogleMapsHelper.GetNearbyPlacesData;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentMapBinding;


import com.example.shopbuddy.ui.notifications.NotificationsViewModel;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, AdapterView.OnItemSelectedListener, GoogleMap.OnInfoWindowClickListener {
    private FragmentMapBinding binding;

    private GoogleMap map;
    private MapView mapView;
    private Button btFind;
    String[] shopNames = {"Vælg forretning","Netto", "Føtex", "Bilka"};

    private LatLng userLatLng;
    private double latitude;
    private double longitude;
    private String zipcode;

    private int PROXIMITY_RADIUS = 5000;
    private final static int LOCATION_PERMISSION = 1;

    Task<Location> locationTask;
    FusedLocationProviderClient fusedLocationProviderClient;



    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getContext());
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMapBinding.inflate(inflater, container, false);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner shopSpinner = binding.spinner;
        shopSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the shop name list
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this.getContext(), R.layout.support_simple_spinner_dropdown_item, shopNames);
        shopSpinner.setAdapter(spinnerAdapter);

        View root = binding.getRoot();
        return root;


    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = binding.mapView;

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
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{permission}, requestCode);
        } else {
            Toast.makeText(this.getActivity(), "Tilladelse til lokation allerede givet", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this.getActivity(), "Tilladelse til lokalitetstjeneste givet", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getActivity(), "Tilladelse til lokalitetstjeneste afvist", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void zoomToUserLocation () {
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                try {
                    userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    latitude = userLatLng.latitude;
                    longitude = userLatLng.longitude;

                    List<Address> addresses = new Geocoder(getContext(), Locale.getDefault()).getFromLocation(latitude, longitude, 1);
                    zipcode = addresses.get(0).getPostalCode();

                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onMapReady (@NonNull @NotNull GoogleMap googleMap){
        MapsInitializer.initialize(getContext());
        map = googleMap;

        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            locationTask = fusedLocationProviderClient.getLastLocation();
            zoomToUserLocation();
        }

        map.setOnMarkerClickListener(this);

        InfoWindowAdapter markerInfoWindowAdapter = new InfoWindowAdapter(this.getContext());
        map.setInfoWindowAdapter(markerInfoWindowAdapter);

        map.setOnInfoWindowClickListener(this);


    }


    @Override
    public boolean onMarkerClick(@NonNull @NotNull Marker marker) {

        // Initialize the SDK
        Places.initialize(getActivity().getApplicationContext(), getString(R.string.google_map_key));

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this.getContext());

        final String placeId = String.valueOf(marker.getTag());
        final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.OPENING_HOURS);

        // Construct a request object, passing the place ID and fields array.
        final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            Log.i(TAG, "Place found: " + place.getName());


           String address = place.getAddress();
            String placeName = place.getName();
           marker.setTitle(placeName+"\n" + address);

           if(place.getOpeningHours() != null){
               List<String> openingHours = place.getOpeningHours().getWeekdayText();
               for (int i = 0; i < openingHours.size(); i++){
                   if (i>0 && i != openingHours.size()-1){
                       marker.setSnippet(marker.getSnippet() + openingHours.get(i) + "\n");
                   } else if(i == 0) {
                       marker.setSnippet(openingHours.get(i) + "\n");
                   } else {
                       marker.setSnippet(marker.getSnippet() + openingHours.get(i));
                   }
               }
           }

            marker.showInfoWindow();
            map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            map.animateCamera(CameraUpdateFactory.zoomTo(13));

        });
        return true;
    }


    private String getUrl (double latitude, double longitude, String nearbyPlace){
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");
        googlePlaceUrl.append("query=" + nearbyPlace);
        googlePlaceUrl.append("&location=" + latitude + "," + longitude);
        googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key=" + getString(R.string.google_map_key));


        return googlePlaceUrl.toString();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        String shopName = shopNames[position];


        switch (shopName) {
            case "Vælg forretning":
                // Do nothing
                break;
            case "Netto":
                map.clear();
                String url = getUrl(latitude, longitude, shopName);
                dataTransfer[0] = map;
                dataTransfer[1] = url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(this.getContext(), "Viser nærtliggende Netto", Toast.LENGTH_LONG).show();
                break;
            case "Føtex":
                map.clear();
                url = getUrl(latitude, longitude, shopName);
                dataTransfer[0] = map;
                dataTransfer[1] = url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(this.getContext(), "Viser nærtliggende Føtex", Toast.LENGTH_LONG).show();
                break;
            case "Bilka":
                map.clear();
                url = getUrl(latitude, longitude, shopName);
                dataTransfer[0] = map;
                dataTransfer[1] = url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(this.getContext(), "Viser nærtliggende Bilka", Toast.LENGTH_LONG).show();
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    // TODO Auto-generated method stub

    }

    @Override
    public void onInfoWindowClick(@NonNull @NotNull Marker marker) {
        Log.e(TAG ,"Window clicked");

        Intent intent = new Intent(this.getContext(), ShopChosenActivity.class);
        intent.putExtra("ShopInfo",marker.getTitle());
        intent.putExtra("ShopOpeningHours", marker.getSnippet());
        intent.putExtra("ShopId", String.valueOf(marker.getTag()));
        startActivity(intent);

    }

    public String getZipcode() {
        return zipcode;
    }
}



