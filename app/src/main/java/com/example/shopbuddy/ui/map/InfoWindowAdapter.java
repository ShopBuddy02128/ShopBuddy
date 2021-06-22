package com.example.shopbuddy.ui.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shopbuddy.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.jetbrains.annotations.NotNull;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;

    //Constructor
    public InfoWindowAdapter(Context context){
        this.context = context;
    }


    @Override
    public View getInfoWindow(@NonNull @NotNull Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(@NonNull @NotNull Marker marker) {

        //Get the inflater and inflate the view
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.map_marker_info_window, null);

        //Set the title and the description of the store
        TextView title = (TextView) v.findViewById(R.id.window_title);
        TextView description = (TextView) v.findViewById(R.id.window_description);
        title.setText(marker.getTitle());
        description.setText(marker.getSnippet());

        return v;
    }
}
