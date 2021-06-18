package com.example.shopbuddy.ui.offer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopbuddy.databinding.FragmentOfferLayoutBinding;
import com.example.shopbuddy.models.FoodWasteFromStore;
import com.example.shopbuddy.ui.navigation.NavigationActivity;
import com.example.shopbuddy.utils.DummyData;
import com.example.shopbuddy.utils.JSONReader;

import java.util.ArrayList;
import java.util.List;

public class OfferFragment extends Fragment {

    private FragmentOfferLayoutBinding binding;

    private ListView listView;
    private ArrayList<FoodWasteFromStore> fwfs = new ArrayList<>();

    private FoodWasteStoreAdapter adapter;
    private NavigationActivity main;

    public OfferFragment(ArrayList<FoodWasteFromStore> fwfs){
        this.fwfs = fwfs;
    }

    public void setNavigationActivity(NavigationActivity main){
        this.main = main;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOfferLayoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = binding.listOfOffers;
        adapter = new FoodWasteStoreAdapter(getActivity(), fwfs);

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                main.offerItemsFragment = new OfferItemsFragment(fwfs.get(position));
                main.changeToFragment(main.offerItemsFragment);
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