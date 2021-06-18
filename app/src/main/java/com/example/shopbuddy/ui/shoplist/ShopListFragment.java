package com.example.shopbuddy.ui.shoplist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopbuddy.R;
import com.example.shopbuddy.databinding.FragmentShoplistBinding;
import com.example.shopbuddy.models.ShoppingList;
import com.example.shopbuddy.services.FirestoreHandler;
import com.example.shopbuddy.utils.DummyData;
import com.example.shopbuddy.models.ShopListItem;

import java.util.ArrayList;
import java.util.Objects;

public class ShopListFragment extends Fragment {

    private static final String TAG = "ShopListFragment";

    private ShopListViewModel shopListViewModel;
    public FragmentShoplistBinding binding;

    public ShoplistAutocomplete ac;
    public AutocompleteAdapter acAdapter;

    public ListView listView;
    public ListAdapter listAdapter;
    public ShoppingList shoppingList;
    public ArrayList<ShopListItem> shopListItems;

    public FirestoreHandler dbHandler;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "Entered onCreateView");
        shopListViewModel =
                new ViewModelProvider(this).get(ShopListViewModel.class);

        binding = FragmentShoplistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dbHandler = new FirestoreHandler(this.requireContext(), this);

        ArrayList<ShopListItem> shopListItemArrayList = new ArrayList<>();

        // TODO currently loads default (test) list
        dbHandler.getShoppingListContents("fmeAODU9GwgSy0amghYH");

        ListAdapter listAdapter = new ListAdapter(this.requireContext(), shopListItemArrayList);

        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener((parent, view, position, id) -> {
            Log.i(TAG, ""+position+" clicked.");

            Intent i = new Intent(requireActivity(), ItemActivity.class);
            i.putExtra("name", shopListItems.get(position).name);
            i.putExtra("brand", shopListItems.get(position).brand);
            i.putExtra("price", shopListItems.get(position).price);
            i.putExtra("qty", shopListItems.get(position).qty);
            i.putExtra("imageUrl", shopListItems.get(position).imageUrl);
            startActivity(i);
        });

        // TODO autocomplete
        ac = binding.shoplistAutocomplete;

        ac.setOnItemClickListener((parent, view, position, id) -> {
            Log.i(TAG, "ShopListItem #" + position + " clicked");
            RelativeLayout rl = (RelativeLayout) view;
            TextView tv = (TextView) rl.getChildAt(0);
            ac.setText(tv.getText().toString());
        });

        ArrayList<ShopListItem> objectItemData = new ArrayList<>();

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