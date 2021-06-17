package com.example.shopbuddy.ui.shoplist;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.shopbuddy.models.ShopListItem;

import java.util.ArrayList;

public class AutocompleteTextChangedListener implements TextWatcher {
    public static final String TAG = "ACTextChangedListener";
    ShopListFragment frag;

    public AutocompleteTextChangedListener(ShopListFragment frag){
        this.frag = frag;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {    }

    @Override
    public void afterTextChanged(Editable s) {    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {
        try{

            // if you want to see in the logcat what the user types
            Log.e(TAG, "User input: " + userInput);

            // update the adapater
            frag.acAdapter.notifyDataSetChanged();

            // get suggestions from the database
            ArrayList<ShopListItem> myObjs = frag.dbHandler.queryForSuggestions(userInput.toString());

            // update the adapter
            frag.acAdapter = new AutocompleteAdapter(frag.requireContext(), myObjs);

            frag.ac.setAdapter(frag.acAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
