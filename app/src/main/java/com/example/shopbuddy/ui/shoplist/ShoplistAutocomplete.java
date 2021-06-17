package com.example.shopbuddy.ui.shoplist;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class ShoplistAutocomplete extends AppCompatAutoCompleteTextView {

    public ShoplistAutocomplete(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ShoplistAutocomplete(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ShoplistAutocomplete(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    // this is how to disable AutoCompleteTextView filter
    @Override
    protected void performFiltering(final CharSequence text, final int keyCode) {
        String filterText = "";
        super.performFiltering(filterText, keyCode);
    }
}
