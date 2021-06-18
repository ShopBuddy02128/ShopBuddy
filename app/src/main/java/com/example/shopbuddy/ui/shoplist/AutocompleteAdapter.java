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
import com.example.shopbuddy.utils.TextFormatter;

import java.util.ArrayList;
import java.util.List;

public class AutocompleteAdapter extends ArrayAdapter<ShopListItem> {
    final String TAG = "AutocompleteCustomArrayAdapter";

    ArrayList<ShopListItem> data;

    public AutocompleteAdapter(@NonNull Context context, @NonNull List<ShopListItem> objects) {
        super(context, R.layout.autocomplete_row, objects);

        Log.i("bruh", "Adapter created with: " + objects.toString());

        this.data = (ArrayList<ShopListItem>) objects;
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        try{
            if(convertView==null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.autocomplete_row, parent, false);
            }

            ShopListItem objectShopListItem = data.get(position);

            TextView textViewItem = convertView.findViewById(R.id.textViewItem);
            String concatString = TextFormatter.toNameFormat(objectShopListItem.name) + ", " + TextFormatter.toNameFormat(objectShopListItem.brand);
            textViewItem.setText(concatString);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

}
