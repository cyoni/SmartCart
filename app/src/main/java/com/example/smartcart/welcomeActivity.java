package com.example.smartcart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class welcomeActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private userBoard _user;
    private String metaData;
    private static final String CHANNEL_ID = "CHANNEL_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        mAuth  = FirebaseAuth.getInstance();// get user
        mDatabase = FirebaseDatabase.getInstance().getReference();
        createNotificationChannel();

    }

    protected void onStart() {
        super.onStart();
        getMetaData(); // get user data in case he is connected
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Item updates";
            String description = "something something";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
                metaData = gson.toJson(_user); // convert metaData to JSON

                getMyCart();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getMyCart() {
        mDatabase.child(String.format("cart")).child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<item> tmp_items = new ArrayList<>();

                if (dataSnapshot.exists()) {
                    String items = dataSnapshot.getValue().toString();

                    if (items.contains(";")) {
                        String[] my_items = items.split(";");
                        for (int i = 0; i < my_items.length; i++) {
                            String item[] = my_items[i].split(",");
                            //total += Double.parseDouble(item[4]);
                            item tmpItem = new item(item[1], item[0], Double.parseDouble(item[3]), Integer.parseInt(item[2]), 1000);
                            tmp_items.add(tmpItem);
                        }
                    }
                }

                // send User object and Cart to mainActivity
                Intent a = new Intent(getApplicationContext(), MainActivity.class); // opens the menu
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("restore_items" , tmp_items);
                a.putExtras(bundle);
                a.putExtra("userMetaData", metaData);
                startActivity(a);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



}
