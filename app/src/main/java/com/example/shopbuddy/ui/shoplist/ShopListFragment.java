package com.example.shopbuddy.ui.shoplist;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.shopbuddy.databinding.FragmentShoplistBinding;
import com.example.shopbuddy.models.ShoppingList;
import com.example.shopbuddy.services.AuthService;
import com.example.shopbuddy.services.FirestoreHandler;
import com.example.shopbuddy.models.ShopListItem;
import com.example.shopbuddy.services.ToastService;

import java.text.DecimalFormat;
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
    public static double shoppingListPrice = 0;
    // test lists in firebase
    public String shoppingListId;
    // end test lists
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
        setupListView();
        setupAutocomplete();
        setupRefresher();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // find better way to update view
        dbHandler.getShoppingListContents(shoppingListId);
        binding.totalPrice.setText("Total: " + new DecimalFormat("#.##").format(shoppingListPrice));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupListView() {
        shopListItems = new ArrayList<>();

        listAdapter = new ListAdapter(this.requireContext(), this.requireActivity(), shopListItems);

        binding.list.setAdapter(listAdapter);
        binding.list.setClickable(true);
        binding.list.setOnItemClickListener((parent, view, position, id) -> {
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

            startActivity(i);
        });
    }

    private void setupAutocomplete() {
        ac = binding.shoplistAutocomplete;

        acItems = new ArrayList<>();

        ac.addTextChangedListener(new AutocompleteTextChangedListener(this));
        acAdapter = new AutocompleteAdapter(this.requireActivity(),  acItems);
        ac.setAdapter(acAdapter);

        ac.setHint("Søg i varer");

        ac.setOnItemClickListener((parent, view, position, id) -> {
            ShopListItem item = acAdapter.getShopListItem(position);
            Log.i(TAG, item.toString());

            RelativeLayout rl = (RelativeLayout) view;
            TextView tv = (TextView) rl.getChildAt(0);
            ac.setText("");

            tryAddItem(item);
        });
    }

    private void tryAddItem(ShopListItem item) {
        // check if key exists, and if not, insert into db
        if (!shopListItems.stream().anyMatch(i -> i.itemId.equals(item.itemId))) {
            dbHandler.addItemToShoppingList(item.itemId, shoppingListId, shoppingList.getSize());
            boolean positivePriceAdjustment = true;
            dbHandler.updateShoppingListPrice(
                    shoppingListId,
                    AuthService.getCurrentUserId(),
                    Double.parseDouble(item.price),
                    positivePriceAdjustment);
        }
        else
            ToastService.makeToast("Item already in list", Toast.LENGTH_SHORT);
    }

    private void setupRefresher() {
        SwipeRefreshLayout refresher = binding.swipeRefresher;
        refresher.setOnRefreshListener(() -> {
            dbHandler.getShoppingListContents(shoppingListId);
            refresher.setRefreshing(false);
        });
    }

    public void setShoppingListId(String id) {
        this.shoppingListId = id;
    }
}