package com.example.smartcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class welcomeActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private userBoard _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        mAuth  = FirebaseAuth.getInstance();// get user
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }

    protected void onStart() {
        super.onStart();
        getMetaData(); // get user data in case he is connected
    }


    public void getMetaData() {

        if (mAuth.getCurrentUser() == null) {
            Intent a = new Intent(this, MainActivity.class);
            startActivity(a);
            finish();
            return;
        }

        mDatabase.child(String.format("users")).child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                _user = dataSnapshot.getValue(userBoard.class);

                Gson gson = new Gson();
                String metaData = gson.toJson(_user); // convert metaData to JSON
                setData(metaData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void setData(String metaData){
        Intent a = new Intent(this, MainActivity.class); // opens the menu
        a.putExtra("userMetaData", metaData);
        startActivity(a);
        finish();
    }


}
