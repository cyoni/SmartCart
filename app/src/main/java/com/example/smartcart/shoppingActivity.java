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
