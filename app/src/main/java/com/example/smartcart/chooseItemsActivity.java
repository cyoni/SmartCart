package com.example.smartcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.smartcart.recycleview.recycleview_adapter_shopping;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class chooseItemsActivity extends AppCompatActivity implements recycleview_adapter_shopping.ItemClickListener  { // Generic class
    recycleview_adapter_shopping adapter;
    Button ok_button;
    RecyclerView items_list;
    ProgressBar load;
    ArrayList<item> list;
    HashMap<String, item> cat_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_items);

         ok_button = findViewById(R.id.ok_button);
        items_list = findViewById(R.id.list);
        load = findViewById(R.id.progressBar);

        ok_button.setVisibility(View.GONE);
        items_list.setVisibility(View.GONE);
        cat_list = new HashMap<>();

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




        EditText search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {        }

            @Override
            public void afterTextChanged(Editable editable) {
                 filterList(controller.makeUpperLetter(editable.toString()));
            }
        });


        Intent intent = getIntent(); // get intent from shoppingActivity - to set the items that the user choose to the shopping list
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            cat_list = (HashMap<String, item>) bundle.getSerializable("myCart_for_cat");
        }


       final String cat = getIntent().getStringExtra("index"); // get category name

        // get items:

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("items").child(cat).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                     list = new ArrayList<>();

                    Iterator<DataSnapshot> a = dataSnapshot.getChildren().iterator();
                    DataSnapshot tmp;
                    while (a.hasNext()) {
                        tmp = a.next();
                        final HashMap<String, Object> dataMap = (HashMap<String, Object>) tmp.getValue();
                        String q = dataMap.get("quantity")+""; // available quantity
                        String price = dataMap.get("price")+"";
                        String name = tmp.getKey()+"";

                        item tmpItem;
                            if (cat_list.get(name) != null){ // if you already appear in the list
                                 tmpItem = cat_list.get(name);
                                 list.add(tmpItem);
                            }
                            else{ // otherwise
                               tmpItem = new item(name, cat, Double.valueOf(price), 0, Integer.parseInt(q));
                               list.add(tmpItem);
                        }
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

    private void filterList(String what) {
        ArrayList<item> search_list = new ArrayList<>();
        if (what.trim().length() == 0) setRecycleView(list);
        else{
            for (int i=0; i<list.size(); i++){
                if (list.get(i).getName().contains(what)) search_list.add(list.get(i));
            }
            setRecycleView(search_list);
        }
    }

    private void setRecycleView(ArrayList<item> list )
    {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new recycleview_adapter_shopping(this, list,"");
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
    }


    public void collectItems(View view) {

        Intent intent = new Intent(this, shoppingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("items", adapter.getItems());
        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);
        finish();
      }


}
