package com.example.shopbuddy.ui.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shopbuddy.R;
import com.example.shopbuddy.models.ShopListItem;

import java.util.ArrayList;




public class SaleListAdapter extends ArrayAdapter<ShopListItem> {

    private static final String TAG = "SaleListAdapter";
    private LayoutInflater inflater;
    private Context mContext;
    private int mResource;
    ArrayList<ShopListItem> list;

    static class ViewHolder {
        TextView name;
        TextView price;
        TextView brand;

    }

    public SaleListAdapter(Context context, int resource, ArrayList<ShopListItem> shopListItemArrayList) {
        super(context, resource, shopListItemArrayList);
        mContext = context;
        mResource = resource;
        list = shopListItemArrayList;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ShopListItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View result;

        // Get the values
        String itemName =getItem(position).name;
        String itemBrand = getItem(position).brand;
        String itemPrice = getItem(position).price + " kr.";


        ViewHolder holder = new ViewHolder();

        if (convertView == null){
            holder.name = (TextView) convertView.findViewById(R.id.sale_item_name);
            holder.brand= (TextView) convertView.findViewById(R.id.sale_item_brand);
            holder.price = (TextView) convertView.findViewById(R.id.sale_item_price);

            result = convertView;

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.name.setText(itemName);
        holder.brand.setText(itemBrand);
        holder.price.setText(itemPrice);



        return result;

    }
}
