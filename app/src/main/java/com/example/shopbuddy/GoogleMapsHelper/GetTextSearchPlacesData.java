package com.example.shopbuddy.GoogleMapsHelper;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;

import java.util.HashMap;
import java.util.List;


public class GetTextSearchPlacesData extends AsyncTask<Object, String, String> {
    String googlePlacesData;
    GoogleMap mMap;
    String url;
    HashMap<String, Marker> markerList = new HashMap<>();


    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];

        DownloadURL downloadURL = new DownloadURL();

        try {
            googlePlacesData = downloadURL.readUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearByPlaceList = null;
        DataParser parser = new DataParser();
        nearByPlaceList = parser.parse(s);
        showTextSearchPlaces(nearByPlaceList);

    }

    //Method to show all the places in the list
    private void showTextSearchPlaces(List<HashMap<String,String>> nearbyPlaceList){
        for (int i = 0; i < nearbyPlaceList.size(); i++ ){

            MarkerOptions markerOptions = new MarkerOptions();

            HashMap<String,String> googlePlace = nearbyPlaceList.get(i);

            String placeName = googlePlace.get("place_name");
            System.out.println(placeName);

            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String id = googlePlace.get("reference");


            //Create marker
            LatLng latLng = new LatLng(lat,lng);
            markerOptions.position(latLng);

            //Depending on the store name set the color
            if (placeName.toLowerCase().contains("netto")){
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            } else if(placeName.toLowerCase().contains("føtex")){
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            } else if(placeName.toLowerCase().contains("bilka")){
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }

            Marker mMarker = mMap.addMarker(markerOptions);
            mMarker.setTag(id);
            markerList.put(id,mMarker);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));


        }
    }
}
