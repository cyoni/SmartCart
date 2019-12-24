package com.example.smartcart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.type.Money;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;


public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    userBoard _user = null;


    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*         mAuth  = FirebaseAuth.getInstance();// get user
         mDatabase = FirebaseDatabase.getInstance().getReference();*/

        contextOfApplication = getApplicationContext();

        // set action bar:
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        View view = getSupportActionBar().getCustomView();
        ImageView img = view.findViewById(R.id.image_action);
        img.setImageResource(R.drawable.menu_button);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            openMenu();
            }
        });


        Gson gson = new Gson(); // set user Data
        _user = gson.fromJson(getIntent().getStringExtra("userMetaData"), userBoard.class);
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // getIntent() should always return the most recent

        Gson gson = new Gson(); // set user Data
        _user = gson.fromJson(intent .getStringExtra("userMetaData"), userBoard.class);
        setIntent(intent);
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    private void openMenu() { //open menu button
        Gson gson = new Gson();
        String metaData = gson.toJson(_user); // convert metaData to JSON



        Intent a = new Intent(this, menu.class); // opens the menu
        a.putExtra("userMetaData", metaData);
        startActivity(a);

    }


    public void addSomething(final View view) {


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("items").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    Iterator<DataSnapshot> a = dataSnapshot.getChildren().iterator();
                    System.out.println(dataSnapshot.getChildrenCount() + "= num");
                    DataSnapshot tmp;

                    while (a.hasNext()) {
                        tmp = a.next();
                        final HashMap<String, Object> dataMap = (HashMap<String, Object>) tmp.getValue();

                        String n = dataMap.get("price")+"";
                        String x = tmp.getKey() + "";
                        System.out.println("name: " + x + ", price: " + n);


                    }

                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



/*
        //Get datasnapshot at your "users" root node
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectPhoneNumbers((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });*/

    }

    private void collectPhoneNumbers(Map<String,Object> users) {


        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){
            //Get user map

          System.out.println( entry.getValue());


            //Get phone field and append to list
           // phoneNumbers.add(singleUser.get("email")+"");
        }

     ///   System.out.println(phoneNumbers.toString());
    }


    public void shoppingActivity(View view) {

        Intent s = new Intent(this, shoppingActivity.class);
        startActivity(s);

    }
    public void lastShopping(View view)
    {
        if (_user == null){
            Intent s = new Intent(this, LoginActivity.class);
            startActivity(s);
        }
        else{
        Intent s = new Intent(this,LastShoppingActivity.class);
        startActivity(s);}
    }

}
