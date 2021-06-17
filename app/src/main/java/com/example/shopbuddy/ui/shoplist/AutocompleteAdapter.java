package com.example.shopbuddy.ui.shoplist;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shopbuddy.R;
import com.example.shopbuddy.models.ShopListItem;

import java.util.List;

public class AutocompleteAdapter extends ArrayAdapter<ShopListItem> {
    final String TAG = "AutocompleteCustomArrayAdapter";

    Context mContext;
    int layoutResourceId;


    public AutocompleteAdapter(@NonNull Context context, @NonNull List<ShopListItem> objects) {
        super(context, R.layout.autocomplete_row, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        try{
            if(convertView==null)
            {

                convertView = LayoutInflater.from(getContext()).inflate(R.layout.autocomplete_row, parent, false);
            }

            ShopListItem objectShopListItem = getItem(position);

            TextView textViewItem = convertView.findViewById(R.id.textViewItem);
            String concatString = objectShopListItem.name + ", " + objectShopListItem.brand;
            textViewItem.setText(concatString);

            // in case you want to add some style, you can do something like:
            textViewItem.setBackgroundColor(Color.CYAN);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("bruh", "ac.getView called -> " + convertView);
        return convertView;
    }

}
