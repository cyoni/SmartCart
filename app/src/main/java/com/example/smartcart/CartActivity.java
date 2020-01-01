package com.example.smartcart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity  implements recycleview_adapter_shopping.ItemClickListener, recycleview_adapter_shopping.MyAdapterListener { //
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
                onBackPressed();
            }
        });


        Intent intent = getIntent(); // get intent from shoppingActivity - to set the items that the user choose to the shopping list
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            myCart = (HashMap<String, item>) bundle.getSerializable("my_items");
            setRecycleView();
        }

        if (myCart.size() == 0)
            controller.toast(this, "Your cart is empty");

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, shoppingActivity.class);
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("items", adapter.getItems(null));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
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
        adapter = new recycleview_adapter_shopping(this, list, 1, this);
        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);
        getSubTotal(list);

    }
    private void getSubTotal(ArrayList<item> items){

        TextView t = findViewById(R.id.subtotal);
        int total = 0;
        for (int i=0; i< items.size(); i++){
            total += items.get(i).price*items.get(i).getMyQuantity();
        }
        t.setText("Subtotal: ₪" + total);

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public void buy(View view) {

        controller.toast(this, "buy now..");
    }


    @Override
    public void onContainerClick(ArrayList<item> items) {
        getSubTotal(items);
    }
}
