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
import com.example.shopbuddy.models.ShopListItem;
import com.example.shopbuddy.utils.ImageLoadTask;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapter extends ArrayAdapter<ShopListItem> {

    private static final String TAG = "ListAdapter";

    public ListAdapter(@NonNull Context context, @NonNull ArrayList<ShopListItem> shopListItems) {
        super(context, R.layout.list_item, shopListItems);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ShopListItem shopListItem = getItem(position);

        Log.i("bruh", "getView called");

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView name = convertView.findViewById(R.id.item_name);
        TextView brand = convertView.findViewById(R.id.item_brand);
        TextView price = convertView.findViewById(R.id.item_price);
        TextView qty= convertView.findViewById(R.id.item_qty);

        ImageLoadTask task = new ImageLoadTask((CircleImageView) imageView);
        Log.i(TAG, position + "Url = " + shopListItem.imageUrl);
        task.execute(shopListItem.imageUrl);

        name.setText(shopListItem.name);
        brand.setText(shopListItem.brand);
        price.setText(shopListItem.price);
        qty.setText(shopListItem.qty);

        return convertView;
    }
}

















