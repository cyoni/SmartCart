package com.example.smartcart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;


public class menu extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button a;
    userBoard user;
    String metaData;

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


        Gson gson = new Gson();
        user = gson.fromJson(getIntent().getStringExtra("userMetaData"), userBoard.class);

        metaData = gson.toJson(user);



        Button addItems_button = findViewById(R.id.button12);
        // Check if user is signed in (non-null) and update UI accordingly.

        if (user != null){
            Button a = findViewById(R.id.guestAccount); // user login button
            a.setText("My Account ("+ user.getEmail() +")");
            Button b = findViewById(R.id.managerLogin);
            // check if user is manager:

            if (user.isManager()){ // if you're manager then..
                // add items for manager button:
                addItems_button.setVisibility(View.VISIBLE);

            }

            b.setVisibility(View.GONE); // if a user is connected then hide manager login button but leave the other button - just change its value
        }

        if (user == null || user.isManager() == false){
            addItems_button.setVisibility(View.GONE);
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

    public void comingSoon(View view) {
        controller.toast(this, "Coming soon");
    }

    public void addItem(View view) {
          Intent a = new Intent(this, addItemActivity.class);
        a.putExtra("userMetaData", metaData);
        startActivity(a);

    }


}
