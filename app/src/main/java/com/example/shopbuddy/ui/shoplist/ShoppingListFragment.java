package com.example.shopbuddy.ui.shoplist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shopbuddy.models.Item;
import com.example.shopbuddy.models.ShoppingList;
import com.example.shopbuddy.databinding.FragmentSecondBinding;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ListView list;
    private ShoppingList sList;

    public void setShoppingList(ShoppingList sList) {
        this.sList = sList;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);

        list = binding.itemsList;

        ArrayList<Item> items = sList.getItems();

        ShoppingListAdapter adapter = new ShoppingListAdapter(getActivity(), items);
        list.setAdapter(adapter);
        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext().getApplicationContext(), items.get(position).getTitle(), Toast.LENGTH_LONG).show();
            }
        });

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

