package com.example.shopbuddy.ui.foodwaste;

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
import com.example.shopbuddy.models.Store;
import com.example.shopbuddy.ui.navigation.NavigationActivity;

import java.util.ArrayList;

public class OfferItemsFragment extends Fragment {

    OfferItemsLayoutBinding binding;
    ArrayList<DiscountItem> items;
    Store store;

    private ListView listView;
    private FoodWasteItemsAdapter adapter;

    public OfferItemsFragment(){

    }
    public OfferItemsFragment(FoodWasteFromStore fwfs){
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

        TextView titel = binding.offersItemsTitleId;
        titel.setText("Nedsatte varer fra " + store.getName() + ":");

        listView = binding.listOfOfferItems;
        adapter = new FoodWasteItemsAdapter(getActivity(), items);

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
