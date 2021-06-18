package com.example.shopbuddy.ui.offer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentFirstBinding;
import com.example.shopbuddy.models.DiscountItem;
import com.example.shopbuddy.models.FoodWasteFromStore;
import com.example.shopbuddy.models.ShoppingList;
import com.example.shopbuddy.models.Store;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.example.shopbuddy.ui.shoplist.ListsListAdapter;
import com.example.shopbuddy.ui.shoplist.ShopListFragment;
import com.example.shopbuddy.utils.DummyData;

import java.util.ArrayList;
import java.util.List;

public class FoodWasteItemsAdapter extends BaseAdapter {

    private FragmentFirstBinding binding;
    private LayoutInflater inflater;//We use it in different methods
    private List<DiscountItem> itemList;

    //initialize NoteListAdapter
    public FoodWasteItemsAdapter(Activity activity, List<DiscountItem> items){
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemList = items;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public DiscountItem getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public void setData(List<DiscountItem> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;

        if(convertView == null)
            vi = inflater.inflate(R.layout.offer_store_layout, parent, false); // create layout from raw_layout

        TextView title = (TextView) vi.findViewById(R.id.offer_store_title);
        title.setText(getItem(position).getTitle());

        TextView price = (TextView) vi.findViewById(R.id.offer_store_items);
        price.setText("Nedsat pris: " + getItem(position).getPrice());

        return vi;
    }
}
