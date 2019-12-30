package com.example.smartcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class chooseItemsActivity extends AppCompatActivity implements recycleview_adapter_shopping.ItemClickListener { // Generic class
    recycleview_adapter_shopping adapter;
    Button ok_button;
    RecyclerView items_list;
    ProgressBar load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_items);

         ok_button = findViewById(R.id.ok_button);
        items_list = findViewById(R.id.list);
        load = findViewById(R.id.progressBar);

        ok_button.setVisibility(View.GONE);
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


        String cat = getIntent().getStringExtra("index"); // get category name

        // get items:

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("items").child(cat).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  EditText list = findViewById(R.id.list);
                String items = "";
                if (dataSnapshot.exists()){
                    ArrayList<item> list = new ArrayList<>();

                    Iterator<DataSnapshot> a = dataSnapshot.getChildren().iterator();
                    DataSnapshot tmp;

                    while (a.hasNext()) {
                        tmp = a.next();
                        final HashMap<String, Object> dataMap = (HashMap<String, Object>) tmp.getValue();

                        String q = dataMap.get("quantity")+""; // available quantity
                        String price = dataMap.get("price")+"";
                        String name = tmp.getKey() + "";
                        item tmpItem = new item(name, Integer.valueOf(price), Integer.valueOf(q));
                        list.add(tmpItem);

                    }

                        setRecycleView(list);

                    ok_button.setVisibility(View.VISIBLE);
                    items_list.setVisibility(View.VISIBLE);
                    load.setVisibility(View.GONE);

                }
                else
                    controller.toast(getApplicationContext(), "error 1124");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }


        });

    }

    private void setRecycleView(ArrayList<item> list )
    {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new recycleview_adapter_shopping(this, list);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
      //  Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void collectItems(View view) {
        Intent intent = new Intent();
        intent.putExtra("newList", adapter.getItems());
        setResult(RESULT_OK, intent);
        finish();
      }
}


