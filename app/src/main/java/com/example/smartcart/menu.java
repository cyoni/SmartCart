package com.example.smartcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.smartcart.dialog.confirmPurchase;
import com.example.smartcart.dialog.sendNotification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;


public class menu extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button a;
    private userBoard user;
    private  String metaData;
    private sendNotification s;
    private EditText t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        a = findViewById(R.id.guestAccount);



        // set action bar:
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        View view =getSupportActionBar().getCustomView();
        ImageView img = view.findViewById(R.id.image_action);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        EditText search = findViewById(R.id.search);
        search.setVisibility(View.INVISIBLE);

        Gson gson = new Gson();
        user = gson.fromJson(getIntent().getStringExtra("userMetaData"), userBoard.class);

        metaData = gson.toJson(user);


        Button notification_button = findViewById(R.id.button13);
        Button addItems_button = findViewById(R.id.button12);
        Button newOrders = findViewById(R.id.button4);
        // Check if user is signed in (non-null) and update UI accordingly.

        if (user != null){
            Button a = findViewById(R.id.guestAccount); // user login button
            a.setText("My Account ("+ user.getEmail() +")");
            Button b = findViewById(R.id.managerLogin);
            // check if user is manager:

            if (user.isManager()){ // if you're manager then..
                // add items for manager button:
                addItems_button.setVisibility(View.VISIBLE);
                newOrders.setVisibility(View.VISIBLE);


            }

            b.setVisibility(View.GONE); // if a user is connected then hide manager login button but leave the other button - just change its value
        }

        if (user == null || user.isManager() == false){
            addItems_button.setVisibility(View.GONE);
            newOrders.setVisibility(View.GONE);
             notification_button.setVisibility(View.GONE);
        }

    }


    // button "manager login or sign up"
    public void managerLogin(View view) {
        Intent a = new Intent(this, LoginManagerActivity.class);
        startActivity(a);
        finish();
    }


    public void customerLogin(View view) {
        if (user != null){
            Intent a = new Intent(this, myAccountActivity.class);
            a.putExtra("userMetaData", metaData);
            startActivity(a);
        }
        else {
            Intent a = new Intent(this, LoginActivity.class);
            startActivity(a);
        }
        finish();
    }


    public void addItem(View view) {
          Intent a = new Intent(this, addItemActivity.class);
        a.putExtra("userMetaData", metaData);
        startActivity(a);

    }


    public void orders(View view) {
        Intent a = new Intent(getApplicationContext(), manager_orders_Activity.class);
        startActivity(a);
    }

    public void customerService(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:18002020205"));
        startActivity(intent);

    }



    public void sendMsg(String t) {
        final String msg = t;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> listId = new ArrayList<>();
                if (dataSnapshot.exists()){

                    for (DataSnapshot tmp : dataSnapshot.getChildren()) {
                        listId.add(tmp.getKey() + "");
                    }
                }

                for (int i=0; i<listId.size(); i++){
                    broadcast(listId.get(i), msg);
                }
                s.cancel();
                onBackPressed();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void broadcast(String id, String msg) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("notifications").child(id).setValue(msg);
    }


    public void sendNotification(View view) {

        s  = new sendNotification(this);
        s.show();

        Button b = s.findViewById(R.id.button);
        t = s.findViewById(R.id.editText);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Msg = t.getText().toString();
                sendMsg(Msg);

            }
        });

    }
}
