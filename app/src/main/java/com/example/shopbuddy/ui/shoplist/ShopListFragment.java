package com.example.shopbuddy.ui.shoplist;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopbuddy.MainActivity;
import com.example.shopbuddy.databinding.FragmentShoplistBinding;
import com.example.shopbuddy.models.ShoppingList;
import com.example.shopbuddy.services.AuthService;
import com.example.shopbuddy.services.FirestoreHandler;
import com.example.shopbuddy.models.ShopListItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShopListFragment extends Fragment {

    private static final String TAG = "ShopListFragment";

    private ShopListViewModel shopListViewModel;
    public FragmentShoplistBinding binding;

    public ShoplistAutocomplete ac;
    public ArrayList<ShopListItem> acItems;
    public AutocompleteAdapter acAdapter;

    public ListView listView;
    public ListAdapter listAdapter;
    public ShoppingList shoppingList;
    public String shoppingListId = "fmeAODU9GwgSy0amghYH";
    public ArrayList<ShopListItem> shopListItems;

    public FirestoreHandler dbHandler;

    private static final int NEW_QTY_REQUEST_CODE = 0x9988;
    private static int lastSelected;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "Entered onCreateView");
        shopListViewModel =
                new ViewModelProvider(this).get(ShopListViewModel.class);

        binding = FragmentShoplistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dbHandler = new FirestoreHandler(this.requireContext(), this);

        shopListItems = new ArrayList<>();

        // TODO currently loads default (test) list
        dbHandler.getShoppingListContents(shoppingListId);

        listAdapter = new ListAdapter(this.requireContext(), shopListItems);

        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener((parent, view, position, id) -> {
            Log.i(TAG, ""+position+" clicked.");

            Intent i = new Intent(requireActivity(), ItemActivity.class);
            i.putExtra("name", shopListItems.get(position).name);
            i.putExtra("brand", shopListItems.get(position).brand);
            i.putExtra("price", shopListItems.get(position).price);
            String itemId = shopListItems.get(position).itemId;
            i.putExtra("itemId", itemId);
            i.putExtra("qty", shoppingList.getItems().get(itemId));

            i.putExtra("imageUrl", shopListItems.get(position).imageUrl);

            i.putExtra("userId", AuthService.getCurrentUserId());
            i.putExtra("shoppingListId", shoppingListId);

//            startActivityForResult(i, NEW_QTY_REQUEST_CODE);
//            lastSelected = position;
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

        acItems = new ArrayList<>();

        ac.addTextChangedListener(new AutocompleteTextChangedListener(this));
        acAdapter = new AutocompleteAdapter(this.requireActivity(),  acItems);
        ac.setAdapter(acAdapter);

        // TODO end autocomplete
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // find better way to update view
        dbHandler.getShoppingListContents(shoppingListId);
        requireActivity().runOnUiThread(() -> listAdapter.notifyDataSetChanged());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}