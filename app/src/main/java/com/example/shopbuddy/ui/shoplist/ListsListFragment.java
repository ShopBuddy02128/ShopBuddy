package com.example.shopbuddy.ui.shoplist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopbuddy.databinding.FragmentFirstBinding;
import com.example.shopbuddy.models.ShopListItem;
import com.example.shopbuddy.models.ShoppingList;
import com.example.shopbuddy.services.NavigationService;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.example.shopbuddy.utils.DummyData;

import java.util.ArrayList;
import java.util.Objects;

public class ListsListFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ListView list;
    private NavigationActivity main;

    public void setNavigationActivity(NavigationActivity main){
        this.main = main;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        list = binding.listsList;

        String[] names = DummyData.names;
        String[] brands = DummyData.brands;
        String[] prices = DummyData.prices;
        String[] qtys = DummyData.qtys;
        String[] imageUrls = DummyData.imageUrls;

        ArrayList<ShoppingList> lists = new ArrayList<>();

        for(int i=0; i<names.length; i++){
            ShoppingList current = new ShoppingList(names[i]);
            for(int k=0; k<names.length; k++) {
                current.addItem("xJ7eYN13B3MBz22AWOO0", 1L); // tilføjer kun lasagneplader
            }
            lists.add(current);
        }

        ListsListAdapter adapter = new ListsListAdapter(requireActivity(), lists);
        list.setAdapter(adapter);
        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //shopListFragment.setData(lists.get(position));
                NavigationService.changeToFragment(new ShopListFragment(), NavigationService.SHOP_LIST_PAGE);
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