package com.example.shopbuddy.ui.shoplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shopbuddy.R;
import com.example.shopbuddy.models.Item;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Item> {

    public ListAdapter(@NonNull Context context, @NonNull ArrayList<Item> items) {
        super(context, R.layout.list_item, items);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item item = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView name = convertView.findViewById(R.id.item_name);
        TextView brand = convertView.findViewById(R.id.item_brand);
        TextView price = convertView.findViewById(R.id.item_price);
        TextView qty= convertView.findViewById(R.id.item_qty);

        imageView.setImageResource(R.drawable.haha);
        name.setText(item.name);
        brand.setText(item.brand);
        price.setText(item.price);
        qty.setText(item.qty);

        return convertView;
    }
}

















