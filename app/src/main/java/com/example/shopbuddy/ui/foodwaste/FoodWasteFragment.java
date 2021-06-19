package com.example.shopbuddy.ui.foodwaste;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import com.example.shopbuddy.services.DiscountFoodWasteService;
import com.example.shopbuddy.ui.map.MapFragment;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodWasteFragment extends Fragment {

    private FragmentFoodWasteLayoutBinding binding;

    private ListView listView;
    private ArrayList<FoodWasteFromStore> fwfs = new ArrayList<>();

    private FoodWasteStoreAdapter adapter;
    private NavigationActivity main;
    private MapFragment mapFragment;

    public FoodWasteFragment(ArrayList<FoodWasteFromStore> fwfs, MapFragment mapFragment){
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

        listView = binding.listOfOffers;
        adapter = new FoodWasteStoreAdapter(getActivity(), fwfs);

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                main.changeToFragment(new FoodWasteItemsFragment(fwfs.get(position)), main.OFFERS_BUTTON);
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