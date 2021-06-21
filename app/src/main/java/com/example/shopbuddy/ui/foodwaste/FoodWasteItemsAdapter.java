package com.example.shopbuddy.ui.foodwaste;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentFirstBinding;
import com.example.shopbuddy.models.DiscountItem;
import com.example.shopbuddy.models.ShopListItem;
import com.example.shopbuddy.utils.ImageLoader;
import com.google.android.gms.maps.model.Circle;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodWasteItemsAdapter extends BaseAdapter {

    private FragmentFirstBinding binding;
    private LayoutInflater inflater;//We use it in different methods
    private List<ShopListItem> itemList;
    private Activity activity;

    //initialize NoteListAdapter
    public FoodWasteItemsAdapter(Activity activity, List<ShopListItem> items){
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemList = items;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public ShopListItem getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public void setData(List<ShopListItem> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;

        if(convertView == null)
            vi = inflater.inflate(R.layout.foodwaste_item_layout, parent, false); // create layout from raw_layout

        TextView title = (TextView) vi.findViewById(R.id.foodwaste_item_title);
        title.setText(getItem(position).name);

        TextView stock = (TextView) vi.findViewById(R.id.foodwaste_item_stock);
        stock.setText("På lager: " + (int) Double.parseDouble(getItem(position).qty) + " stk");

        TextView price = (TextView) vi.findViewById(R.id.foodwaste_item_newprice);
        price.setText("Nedsat pris: " + getItem(position).price);

        TextView oldprice = (TextView) vi.findViewById(R.id.foodwaste_item_oldprice);
        oldprice.setText("Original pris: " + getItem(position).oldPrice);

        return vi;
    }

    public void setImage(Bitmap bitmap){

    }
}
