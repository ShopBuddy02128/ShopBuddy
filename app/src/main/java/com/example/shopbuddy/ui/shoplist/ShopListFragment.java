package com.example.shopbuddy.ui.shoplist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentShoplistBinding;
import com.example.shopbuddy.devutils.DummyData;
import com.example.shopbuddy.models.Item;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class ShopListFragment extends Fragment {

    private static final String TAG = "ShopListFragment";

    private ShopListViewModel shopListViewModel;
    private FragmentShoplistBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "Entered onCreateView");
        shopListViewModel =
                new ViewModelProvider(this).get(ShopListViewModel.class);

        binding = FragmentShoplistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Item> itemArrayList = new ArrayList<>();

        for (int i = 0; i < DummyData.imageIds.length; i++) {
            Item item = new Item(DummyData.names[i],
                    DummyData.brands[i],
                    DummyData.prices[i],
                    DummyData.qtys[i],
                    DummyData.imageIds[i]);
            itemArrayList.add(item);
        }

        ListAdapter listAdapter = new ListAdapter(requireActivity(), itemArrayList);

        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener((parent, view, position, id) -> {
            Log.i(TAG, ""+position+" clicked.");

            Intent i = new Intent(requireActivity(), ItemActivity.class);
            i.putExtra("name", DummyData.names[position]);
            i.putExtra("brand", DummyData.brands[position]);
            i.putExtra("price", DummyData.prices[position]);
            i.putExtra("qty", DummyData.qtys[position]);
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