package com.example.smartcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class shoppingActivity extends AppCompatActivity implements recycleview_adapter_categories.ItemClickListener { // Generic class
    recycleview_adapter_categories adapter;
    Button cart_button;
    RecyclerView items_list;
    ProgressBar load;
    // here all the items will be saved and will be sent to my cart activity
    HashMap<String, item> myCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        myCart = new HashMap<>();
        cart_button = findViewById(R.id.cart_button);
        items_list = findViewById(R.id.list);
        load = findViewById(R.id.progressBar);

        cart_button.setVisibility(View.GONE);
        items_list.setVisibility(View.GONE);


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


        // get items:

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("categories").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<String> list = new ArrayList<>();

                if (dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String buttonName = ds.getKey();
                        list.add(buttonName);
                    }


                    setRecycleView(list);

                    cart_button.setVisibility(View.VISIBLE);
                    items_list.setVisibility(View.VISIBLE);
                    load.setVisibility(View.GONE);

                }
                else
                    controller.toast(getApplicationContext(), "error 11675");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }


        });

        updateNum(0); // change button's value
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) { // Receive intent
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    ArrayList<item> list = data.getExtras().getParcelableArrayList("items");
                String s="";
                  for (int i=0; i<list.size(); i++){
                      item tmp = list.get(i);
                      myCart.put(list.get(i).getName(), list.get(i));
                      s+= tmp.getName() + ", " + tmp.getCategory() + ";";
                      updateNum(myCart.size());

                }

                controller.toast(this, s);

                }
                else{
                    controller.toast(this, "error 32452");
                }
            }
        }
    }

    private void updateNum(int size) {
        cart_button.setText("Cart (" + size + ")");
    }

    private void setRecycleView(ArrayList<String> items_list)
    {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new recycleview_adapter_categories(this, items_list);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent a = new Intent(getApplicationContext() , chooseItemsActivity.class);
        a.putExtra("index", adapter.getItem(position)+"");
        startActivityForResult(a, 1);
    }

    public void collectItems(View view) {
            }
}




