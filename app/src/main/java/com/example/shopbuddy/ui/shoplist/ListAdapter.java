package com.example.shopbuddy.ui.shoplist;

import android.content.Context;
import android.util.Log;
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
import com.example.shopbuddy.services.ImageLoadTask;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapter extends ArrayAdapter<Item> {

    private static final String TAG = "ListAdapter";

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

        ImageLoadTask task = new ImageLoadTask((CircleImageView) imageView);
        Log.i(TAG, position + "Url = " + item.imageUrl);
        task.execute(item.imageUrl);

        name.setText(item.name);
        brand.setText(item.brand);
        price.setText(item.price);
        qty.setText(item.qty);

        return convertView;
    }
}
















