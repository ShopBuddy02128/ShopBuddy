package com.example.shopbuddy.ui.foodwaste;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbuddy.R;
import com.example.shopbuddy.services.ToastService;
import com.example.shopbuddy.utils.ImageLoadTask;
import com.example.shopbuddy.utils.ImageLoader;

import java.io.Serializable;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiscountItemActivity extends AppCompatActivity {

    String name, validTo, imageUrl;
    String price, oldPrice, savings;
    TextView nameText, priceText, oldPriceText, savingsText, validToText;
    ImageView image;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discount_item_activity);

        //Get data for item
        Intent origin = getIntent();
        name = origin.getStringExtra("name");
        price = origin.getStringExtra("price");
        oldPrice = origin.getStringExtra("oldPrice");
        validTo = origin.getStringExtra("validTo");
        imageUrl = origin.getStringExtra("imageUrl");
        savings = "" + (int) ((1 - Double.parseDouble(price)/Double.parseDouble(oldPrice)) * 100);

        //Start loading picture
        image = (ImageView) findViewById(R.id.discount_item_image);
        ImageLoader task = new ImageLoader(this, imageUrl);
        task.loadImage(bitmap -> setImage(bitmap));

        //Get textview in layout and insert text
        nameText = (TextView) findViewById(R.id.discount_item_title);
        nameText.setText(name);

        priceText = (TextView) findViewById(R.id.discount_new_price);
        priceText.setText("New price is " + price + ",-");

        oldPriceText = (TextView) findViewById(R.id.discount_old_price);
        oldPriceText.setText("Original price is " + oldPrice + ",-");

        savingsText = (TextView) findViewById(R.id.discount_saving);
        savingsText.setText("You save " + savings + "%");

        validToText = (TextView) findViewById(R.id.discount_validto_date);
        validToText.setText("Valid until " + validTo.substring(0,10));

        //Activate Buttons
        Button addButton = (Button) findViewById(R.id.discount_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastService.makeToast("Item added (not)", Toast.LENGTH_LONG);
            }
        });
        Button backButton = (Button) findViewById(R.id.discount_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void setImage(Bitmap bitmap){
        if(bitmap == null){
            Log.i("BITMAP", "Bitmap is null!!");
            return;
        }
        Log.i("BITMAP", "Bitmap is not null!!");
        image.setImageBitmap(bitmap);
    }
}
