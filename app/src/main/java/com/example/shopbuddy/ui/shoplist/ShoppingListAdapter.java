package com.example.shopbuddy.ui.shoplist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shopbuddy.R;
import com.example.shopbuddy.models.Item;

import java.util.List;

public class ShoppingListAdapter extends BaseAdapter {
    private LayoutInflater inflater;//We use it in different methods
    private List<Item> itemList;

    //initialize NoteListAdapter
    public ShoppingListAdapter(Activity activity, List<Item> items){
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemList = items;

    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Item getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;

        if(convertView == null)
            vi = inflater.inflate(R.layout.lists_row_layout, null); // create layout from raw_layout

        TextView title = (TextView) vi.findViewById(R.id.listTitle);
        title.setText(getItem(position).getTitle());

        TextView price = (TextView) vi.findViewById(R.id.listPrice);
        price.setText("" + getItem(position).getPrice());

        return vi;
    }
}
