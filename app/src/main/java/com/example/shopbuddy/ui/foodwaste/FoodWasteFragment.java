package com.example.shopbuddy.ui.foodwaste;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopbuddy.databinding.FragmentFoodWasteLayoutBinding;
import com.example.shopbuddy.models.FoodWasteFromStore;
import com.example.shopbuddy.ui.navigation.NavigationActivity;

import java.util.ArrayList;

public class FoodWasteFragment extends Fragment {

    private FragmentFoodWasteLayoutBinding binding;

    private ListView listView;
    private ArrayList<FoodWasteFromStore> fwfs = new ArrayList<>();

    private FoodWasteStoreAdapter adapter;
    private NavigationActivity main;

    public FoodWasteFragment(ArrayList<FoodWasteFromStore> fwfs){
        this.fwfs = fwfs;
    }

    public void setNavigationActivity(NavigationActivity main){
        this.main = main;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFoodWasteLayoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = binding.listOfOffers;
        adapter = new FoodWasteStoreAdapter(getActivity(), fwfs);

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                main.foodWasteItemsFragment = new FoodWasteItemsFragment(fwfs.get(position));
                main.changeToFragment(main.foodWasteItemsFragment);
            }
        });

        return root;
    }

    public void setData(ArrayList<FoodWasteFromStore> items){
        fwfs = items;
        adapter.setData(items);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}