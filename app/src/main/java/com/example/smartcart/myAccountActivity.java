package com.example.smartcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class myAccountActivity extends AppCompatActivity {
    userBoard user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

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


        Gson gson = new Gson(); // get user object
        user = gson.fromJson(getIntent().getStringExtra("userMetaData"), userBoard.class);
    }



    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        controller.toast(this, "See you next time!" );

        Intent a = new Intent(this, MainActivity.class);
        a.putExtra("userMetaData", "");
        startActivity(a);
        finish();
    }

    public void profileAccount(View view) {
        Gson gson = new Gson();
        String metaData = gson.toJson(user);

        Intent a = new Intent(this, userProfileActivity.class);
        a.putExtra("userMetaData", metaData);
        startActivity(a);
    }
}
