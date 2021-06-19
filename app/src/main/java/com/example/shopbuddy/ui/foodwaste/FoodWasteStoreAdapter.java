package com.example.shopbuddy.ui.foodwaste;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentFirstBinding;
import com.example.shopbuddy.models.FoodWasteFromStore;
import com.example.shopbuddy.models.Store;

import java.util.ArrayList;
import java.util.List;

public class FoodWasteStoreAdapter extends BaseAdapter {

    private FragmentFirstBinding binding;
    private LayoutInflater inflater;//We use it in different methods
    private ArrayList<FoodWasteFromStore> list;

    //initialize NoteListAdapter
    public FoodWasteStoreAdapter(Activity activity, ArrayList<FoodWasteFromStore> items){
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = items;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FoodWasteFromStore getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;

        if(convertView == null)
            vi = inflater.inflate(R.layout.offer_store_layout, parent, false); // create layout from raw_layout

        TextView title = (TextView) vi.findViewById(R.id.offer_store_title);
        title.setText(getItem(position).getStore().getName());

        TextView price = (TextView) vi.findViewById(R.id.offer_store_items);
        price.setText("Antal nedsatte varer: " + getItem(position).getItems().size());

        return vi;
    }
}