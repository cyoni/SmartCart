package com.example.smartcart;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;

import java.util.HashMap;

public class LastShoppingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_shopping);


    }

    public void lastShop2(View view) {
      final  Button b = (Button) findViewById(R.id.button9);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = mDatabase.getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference ref = dbRef.child("users").child(mAuth.getCurrentUser().getUid()).child("items");

        //these 4 lines are just to add new items, we need to cut them later on and paste on ShoppingActivity
        HashMap<String, String> hm = new HashMap<>();
        hm.put("banana", "20");
        hm.put("apple", "30");
        ref.child(Calendar.getInstance().getTime().toString()).setValue(hm);





        // Attach a listener to read the data at our posts reference
        dbRef.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userBoard user = dataSnapshot.getValue(userBoard.class);
                Gson gson = new Gson();
                String metaData = gson.toJson(user);
                b.setText(metaData);
                Toast.makeText(LastShoppingActivity.this,user.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               Toast.makeText(LastShoppingActivity.this,"The read failed: " + databaseError.getCode(),Toast.LENGTH_LONG).show();
            }
        });





    }
}
