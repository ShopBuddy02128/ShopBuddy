package com.example.shopbuddy.ui.map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.shopbuddy.databinding.FragmentSalelistBinding;

import com.example.shopbuddy.models.DiscountItem;
import com.example.shopbuddy.models.ShopListItem;

import com.example.shopbuddy.ui.shoplist.ItemActivity;
import com.example.shopbuddy.ui.shoplist.ListAdapter;

import com.example.shopbuddy.utils.DummyData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SaleListFragment extends Fragment {
    private static final String TAG = "SaleListFragment";
    public FragmentSalelistBinding binding;
    public ListView listView;
    private List<DiscountItem> items;


    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.i(TAG, "Entered onCreateView");

        binding = FragmentSalelistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Get items from activity




        ArrayList<ShopListItem> shopListItemArrayList = new ArrayList<>();

        for (int i = 0; i < DummyData.imageUrls.length; i++) {
            ShopListItem shopListItem = new ShopListItem(DummyData.names[i],
                    DummyData.brands[i],
                    DummyData.prices[i],
                    DummyData.qtys[i],
                    DummyData.imageUrls[i],
                    "bro");
            shopListItemArrayList.add(shopListItem);
        }

        ListAdapter listAdapter = new ListAdapter(this.requireContext(), shopListItemArrayList);

        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener((parent, view, position, id) -> {
            Log.i(TAG, ""+position+" clicked.");

            Intent i = new Intent(requireActivity(), ItemActivity.class);
            i.putExtra("name", DummyData.names[position]);
            i.putExtra("brand", DummyData.brands[position]);
            i.putExtra("price", DummyData.prices[position]);
            i.putExtra("qty", DummyData.qtys[position]);
            i.putExtra("imageUrl", DummyData.imageUrls[position]);
            startActivity(i);
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
