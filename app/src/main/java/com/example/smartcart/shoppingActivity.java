package com.example.smartcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class shoppingActivity extends AppCompatActivity implements recycleview_adapter_categories.ItemClickListener { // Generic class
    recycleview_adapter_categories adapter;
    Button cart_button;
    RecyclerView items_list;
    ProgressBar load;
    // here all the items will be saved and will be sent to my cart activity
    HashMap<String, Integer> myCart;

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


      //  String cat = getIntent().getStringExtra("index"); // get category name

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
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) { // Receive intent
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String newList  = data.getStringExtra("newList");
                String items[] = newList.split(";");

                if (items.length > 0) {
                    // update my cart:

                    // first check if the item exists
                    for (int i=0; i<items.length; i++){
                         String item[] = items[i].split(",");
                         String name = item[0]+"";
                         int q = Integer.parseInt(item[1]);
                         myCart.put(name, q);

                    }
                    if (myCart.size() > 0)
                        cart_button.setText("Cart (" + myCart.size() +")");

                }

            }
        }
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







/*
package com.example.smartcart;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class shoppingActivity extends AppCompatActivity {
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);


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

        // download data and set buttons dynamically:
        downloadData();

    }

    private void downloadData() { // get the list of the categories and allocate a new button
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String buttonName = ds.getKey();
                        setButton(buttonName);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setButton(final String buttonName) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            Button btn = new Button(this);
            btn.setId(index++);
            final int id_ = btn.getId();
            btn.setText(buttonName);
            btn.setTextSize(20);
            btn.setPadding(5,5,5,5);
            GradientDrawable button_style = new GradientDrawable();
            button_style.setShape(GradientDrawable.RECTANGLE);
            button_style.setStroke(1, Color.WHITE);
            button_style.setColor(Color.rgb(33,171,79));
            btn.setBackground(button_style);
            LinearLayout l = findViewById(R.id.lin);
            l.addView(btn, params);
            btn = findViewById(id_);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent a = new Intent(getApplicationContext() , chooseItemsActivity.class);
                    a.putExtra("index", buttonName);
                    startActivity(a);
                }
            });

    }

}
*/
