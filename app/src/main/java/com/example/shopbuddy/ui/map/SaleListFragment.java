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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentSalelistBinding;

import com.example.shopbuddy.models.DiscountItem;
import com.example.shopbuddy.models.ShopListItem;

import com.example.shopbuddy.services.DiscountForStoreService;


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
                Toast.makeText(this.getActivity(), "Henter tilbud", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Tilbud kunne ikke hentes", Toast.LENGTH_SHORT).show();

            }
        } else if (shopName.toLowerCase().contains("føtex")){
            try {
                new DiscountForStoreService( this, "Føtex", 20).start();
                Toast.makeText(this.getContext(), "Henter tilbud", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Tilbud kunne ikke hentes", Toast.LENGTH_SHORT).show();
            }
        } else if(shopName.toLowerCase().contains("bilka")) {
            try {
                new DiscountForStoreService(this, "Blika", 20).start();
                Toast.makeText(this.getContext(), "Henter tilbud", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this.getContext(), "Tilbud kunne ikke hentes", Toast.LENGTH_SHORT).show();
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
        Log.i(TAG, "A list of discount items has been returned");


        ArrayList<ShopListItem> shopListItemArrayList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            ShopListItem shopListItem = new ShopListItem(items.get(i).getTitle(),
                    items.get(i).getBrand(),
                    String.valueOf(items.get(i).getPrice()),
                    null,
                    null,
                    null);
            shopListItemArrayList.add(shopListItem);
            Log.i(TAG, "The following item was created" + shopListItem);
        }

        //Get reference for the listview
        listView = binding.saleListview;

        // set the list adapter
        adapter = new SaleListAdapter(getActivity(), R.layout.sale_list_item, shopListItemArrayList);
        getActivity().runOnUiThread(() -> { listView.setAdapter(adapter);});

        Log.i(TAG, "The adapter was created and set ");


        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO - add ability to add items to the shopping list
            }
        });


    }




}
