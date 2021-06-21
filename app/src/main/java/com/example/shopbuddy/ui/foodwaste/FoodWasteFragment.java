package com.example.shopbuddy.ui.foodwaste;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopbuddy.databinding.FragmentFoodWasteLayoutBinding;
import com.example.shopbuddy.models.FoodWasteFromStore;
import com.example.shopbuddy.services.FoodWasteFetcher;
import com.example.shopbuddy.services.NavigationService;
import com.example.shopbuddy.ui.map.MapFragment;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.example.shopbuddy.utils.DummyData;
import com.example.shopbuddy.utils.JSONReader;

import java.util.ArrayList;

public class FoodWasteFragment extends Fragment {

    private FragmentFoodWasteLayoutBinding binding;

    private ListView listView;
    private ArrayList<FoodWasteFromStore> fwfs = JSONReader.getFoodWasteFromJson(DummyData.jsonExample);

    private FoodWasteStoreAdapter adapter;
    private NavigationActivity main;
    private MapFragment mapFragment;

    public FoodWasteFragment(MapFragment mapFragment){
        this.fwfs = fwfs;
        this.mapFragment = mapFragment;
    }

    public void setNavigationActivity(NavigationActivity main){
        this.main = main;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFoodWasteLayoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String zipCode = mapFragment.getZipcode();
        try {
            FoodWasteFetcher dataFetcher = new FoodWasteFetcher(this, zipCode);

            dataFetcher.getData(data -> finishRequest(data));
        } catch(Exception e) {
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void finishRequest(ArrayList<FoodWasteFromStore> foodWasteDiscounts) {

        ArrayList<FoodWasteFromStore> all = new ArrayList<>();
        for(FoodWasteFromStore f : foodWasteDiscounts){
            if(f.getItems().size() > 0) all.add(f);
        }

        fwfs = all;
        Log.i("DINFAR", fwfs.toString());
        //adapter.notifyDataSetChanged();

        listView = binding.listOfOffers;
        Log.i("DIN FAR", getActivity().toString());
        adapter = new FoodWasteStoreAdapter(getActivity(), fwfs);


        listView.setAdapter(adapter);
        listView.setClickable(true);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavigationService.changeToFragment(new FoodWasteItemsFragment(fwfs.get(position)), NavigationService.FOOD_WASTE_PAGE);
            }
        });


    }
}