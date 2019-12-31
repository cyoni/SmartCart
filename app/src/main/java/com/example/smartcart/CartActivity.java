package com.example.smartcart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity  implements recycleview_adapter_shopping.ItemClickListener  { //
    recycleview_adapter_shopping adapter;
    HashMap<String, item> myCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        myCart = new HashMap<>();

        // set action bar:
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        View view = getSupportActionBar().getCustomView();
        ImageView img = view.findViewById(R.id.image_action);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent(); // get intent from shoppingActivity - to set the items that the user choose to the shopping list
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            myCart = (HashMap<String, item>) bundle.getSerializable("my_items");
            setRecycleView();
        }
        else
            controller.toast(this, "Your cart is empty");


    }



    private void setRecycleView()
    {
        ArrayList<item> list = new ArrayList<>();
        for (Map.Entry<String, item> entry : myCart.entrySet()) {
            list.add(entry.getValue());
        }

            // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new recycleview_adapter_shopping(this, list);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {

    }
}
