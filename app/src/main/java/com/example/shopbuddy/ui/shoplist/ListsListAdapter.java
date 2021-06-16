package com.example.shopbuddy.ui.shoplist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shopbuddy.R;
import com.example.shopbuddy.models.ShoppingList;

import java.util.List;

public class ListsListAdapter extends BaseAdapter {
    private LayoutInflater inflater;//We use it in different methods
    private List<ShoppingList> listsList;

    //initialize NoteListAdapter
    public ListsListAdapter(Activity activity, List<ShoppingList> items){
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listsList = items;

    }

    @Override
    public int getCount() {
        return listsList.size();
    }

    @Override
    public ShoppingList getItem(int position) {
        return listsList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;

        if(convertView == null)
            vi = inflater.inflate(R.layout.lists_row_layout, parent, false); // create layout from raw_layout

        TextView title = (TextView) vi.findViewById(R.id.listTitle);
        title.setText(getItem(position).getTitle());

        TextView price = (TextView) vi.findViewById(R.id.listPrice);
        price.setText("" + getItem(position).getPrice());

        return vi;
    }
}

