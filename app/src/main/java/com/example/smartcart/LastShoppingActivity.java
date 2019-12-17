package com.example.smartcart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

public class LastShoppingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_shopping);


    }


    public void lastShop2(View view) {
        Button b = (Button) findViewById(R.id.button9);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = mDatabase.getReference();
        String id = (dbRef.push().getKey());

       DatabaseReference ref =  dbRef.child("users").child(id).child("LatestCart").getRef();

        Toast.makeText(LastShoppingActivity.this,"test1",Toast.LENGTH_LONG).show();


        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get object and use the values to update the UI
                LastShoppingActivity singleMessage = dataSnapshot.getValue(LastShoppingActivity.class);
                Toast.makeText(LastShoppingActivity.this,"test2",Toast.LENGTH_LONG).show();
                // Do something with the data
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });





//        dbRef.child("users").child("nkSvm4Hvjgb6UYUXpPAxUVvuTrA2").setValue(2,completionListener);



    }



//
//    DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
//        @Override
//        public void onComplete(DatabaseError databaseError,DatabaseReference databaseReference) {
//        if (databaseError != null)
//        {
//            Toast.makeText(LastShoppingActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
//        }
//        else
//        {
//            Toast.makeText(LastShoppingActivity.this,"Saved",Toast.LENGTH_LONG).show();
//        }
//
//
//        }
//    };
}
