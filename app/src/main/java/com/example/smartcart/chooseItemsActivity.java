package com.example.smartcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

public class chooseItemsActivity extends AppCompatActivity { // Generic class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_items);


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


        String cat = getIntent().getStringExtra("index"); // category name

        // get items:




        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("items").child(cat).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                EditText list = findViewById(R.id.list);
                String items = "";
                if (dataSnapshot.exists()){

                    Iterator<DataSnapshot> a = dataSnapshot.getChildren().iterator();
                    DataSnapshot tmp;

                    while (a.hasNext()) {
                        tmp = a.next();
                        final HashMap<String, Object> dataMap = (HashMap<String, Object>) tmp.getValue();

                        String q = dataMap.get("quantity")+"";
                        String price = dataMap.get("price")+"";
                        String x = tmp.getKey() + "";
                        items += "name: " + tmp.getKey() +", price: "+ price + ", quantity: "  + q +"\n";

                    }
                    list.setText(items);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }


        });


    }

}


