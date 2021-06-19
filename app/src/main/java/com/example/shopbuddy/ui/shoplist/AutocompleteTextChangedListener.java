package com.example.shopbuddy.ui.shoplist;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.shopbuddy.models.ShopListItem;
import com.example.shopbuddy.services.FirestoreHandler;

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
//            Log.e(TAG, "User input: " + userInput);

            // update the adapter
            frag.acAdapter.notifyDataSetChanged();

            // get suggestions from the database
           frag.dbHandler.queryForSuggestions(userInput.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
