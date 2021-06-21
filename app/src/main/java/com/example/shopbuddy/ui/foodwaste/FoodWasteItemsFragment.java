package com.example.shopbuddy.ui.foodwaste;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopbuddy.databinding.OfferItemsLayoutBinding;
import com.example.shopbuddy.models.DiscountItem;
import com.example.shopbuddy.models.FoodWasteFromStore;
import com.example.shopbuddy.models.ShopListItem;
import com.example.shopbuddy.models.Store;
import com.example.shopbuddy.ui.navigation.NavigationActivity;

import java.util.ArrayList;

public class FoodWasteItemsFragment extends Fragment {

    OfferItemsLayoutBinding binding;
    ArrayList<ShopListItem> items;
    Store store;

    private ListView listView;
    private FoodWasteItemsAdapter adapter;

    public FoodWasteItemsFragment(){

    }
    public FoodWasteItemsFragment(FoodWasteFromStore fwfs){
        this.items = fwfs.getItems();
        this.store = fwfs.getStore();
    }

    public void setData(FoodWasteFromStore fwfs){
        this.items = fwfs.getItems();
        this.store = fwfs.getStore();
        adapter.setData(items);
    }

    private NavigationActivity main;

    public void setNavigationActivity(NavigationActivity main){
        this.main = main;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = OfferItemsLayoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView description = binding.offersItemsTitleDescription;
        description.setText("Nedsatte varer fra " + store.getName() + ":");

        listView = binding.listOfOfferItems;
        adapter = new FoodWasteItemsAdapter(getActivity(), items);

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(requireActivity(), DiscountItemActivity.class);
                intent.putExtra("name", items.get(position).name);
                intent.putExtra("price", items.get(position).price);
                intent.putExtra("oldPrice", items.get(position).oldPrice);
                intent.putExtra("validTo", items.get(position).validTo.toString());

                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
