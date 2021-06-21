package com.example.shopbuddy.ui.map;

import android.annotation.SuppressLint;
import  androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentSalelistBinding;

import com.example.shopbuddy.models.DiscountItem;
import com.example.shopbuddy.models.FoodWasteFromStore;
import com.example.shopbuddy.models.ShopListItem;

import com.example.shopbuddy.services.DiscountForStoreService;
import com.example.shopbuddy.ui.shoplist.ItemActivity;
import com.example.shopbuddy.ui.shoplist.ListAdapter;

import com.example.shopbuddy.utils.DummyData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SaleListFragment extends Fragment {
    private static final String TAG = "SaleListFragment";

    public FragmentSalelistBinding binding;

    private List<DiscountItem> items = null;
    private String shopName;

    private AppCompatActivity mActivityContext;

    private SaleListAdapter adapter;
    private ListView listView;


    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        mActivityContext = (AppCompatActivity) context;

    }


    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.i(TAG, "Entered onCreateView");

        //Set the view
        binding = FragmentSalelistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Get discount items
        shopName = mActivityContext.getSupportActionBar().getTitle().toString();
        if(shopName.toLowerCase().contains("netto")){
            try {
                new DiscountForStoreService(this, "Netto", 20).start();

            } catch (Exception e) {

            }
        } else if (shopName.toLowerCase().contains("føtex")){
            try {
                new DiscountForStoreService( this, "Føtex", 20).start();
            } catch (Exception e) {
                // Failed to get request, most likely caused by not calling a correct store option
            }
        } else if(shopName.toLowerCase().contains("bilka")) {
            try {
                new DiscountForStoreService(this, "Blika", 20).start();
            } catch (Exception e) {
                // Failed to get request, most likely caused by not calling a correct store option
            }
        }



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void finishRequest(List<DiscountItem> listOfDiscountsForStore) {
        items = listOfDiscountsForStore;
        Log.i(TAG, "A list of discount items has been returned and the first item is" + items.get(2).getTitle());


        ArrayList<ShopListItem> shopListItemArrayList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            ShopListItem shopListItem = new ShopListItem(items.get(i).getTitle(),
                    items.get(i).getBrand(),
                    String.valueOf(items.get(i).getPrice()),
                    null,
                    null,
                    "bruh");
            shopListItemArrayList.add(shopListItem);
            Log.i(TAG, "The following item was created" + shopListItem);
        }

        //Get reference for the listview
        listView = binding.saleListview;
        // set the list adapter
        adapter = new SaleListAdapter(getActivity(), R.layout.sale_list_item, shopListItemArrayList);
        //DET ER DET HER DER GIVER PROBLEMER : I/System.out: Only the original thread that created a view hierarchy can touch its views.
        listView.setAdapter(adapter);
        Log.i(TAG, "The adapter was created and set ");


    }




}
