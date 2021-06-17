package com.example.shopbuddy.ui.shoplist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentShoplistBinding;
import com.example.shopbuddy.services.FirestoreHandler;
import com.example.shopbuddy.utils.DummyData;
import com.example.shopbuddy.models.ShopListItem;

import java.util.ArrayList;
import java.util.Objects;

public class ShopListFragment extends Fragment {

    private static final String TAG = "ShopListFragment";

    private ShopListViewModel shopListViewModel;
    private FragmentShoplistBinding binding;

    public ShoplistAutocomplete ac;
    public AutocompleteAdapter acAdapter;
    public FirestoreHandler dbHandler;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "Entered onCreateView");
        shopListViewModel =
                new ViewModelProvider(this).get(ShopListViewModel.class);

        binding = FragmentShoplistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<ShopListItem> shopListItemArrayList = new ArrayList<>();

        for (int i = 0; i < DummyData.imageUrls.length; i++) {
            ShopListItem shopListItem = new ShopListItem(DummyData.names[i],
                    DummyData.brands[i],
                    DummyData.prices[i],
                    DummyData.qtys[i],
                    DummyData.imageUrls[i]);
            shopListItemArrayList.add(shopListItem);
        }

        ListAdapter listAdapter = new ListAdapter(requireActivity(), shopListItemArrayList);

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

        // TODO autocomplete
        dbHandler = new FirestoreHandler(this.requireContext());
        ac = (ShoplistAutocomplete) binding.shoplistAutocomplete;

        ac.setOnItemClickListener((parent, view, position, id) -> {
            Log.i(TAG, "ShopListItem #" + position + " clicked");
            RelativeLayout rl = (RelativeLayout) view;
            TextView tv = (TextView) rl.getChildAt(0);
            ac.setText(tv.getText().toString());
        });

        ArrayList<ShopListItem> objectItemData = new ArrayList<>();
        objectItemData.add(new ShopListItem("test", "test", "test", "test", "test"));

        ac.addTextChangedListener(new AutocompleteTextChangedListener(this));
        acAdapter = new AutocompleteAdapter(this.requireActivity(),  objectItemData);
        ac.setAdapter(acAdapter);
        Log.e(TAG,""+acAdapter);
        // TODO end autocomplete
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}