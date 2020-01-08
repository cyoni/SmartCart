package com.example.smartcart;


import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartcart.dialog.search;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    userBoard _user = null;
    private ArrayList<item> myCart;
    search s;


    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCart = new ArrayList<>();
        contextOfApplication = getApplicationContext();
        mAuth = FirebaseAuth.getInstance();

        // set action bar:
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        View view = getSupportActionBar().getCustomView();
        ImageView img = view.findViewById(R.id.image_action);
        img.setImageResource(R.drawable.menu_button);

        EditText search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {        }

            @Override
            public void afterTextChanged(Editable editable) {
            openSearch(controller.makeUpperLetter(editable.toString()));
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            openMenu();
            }
        });

         Intent a = getIntent();
         Gson gson = new Gson(); // set user Data

            _user = gson.fromJson(a.getStringExtra("userMetaData"), userBoard.class);
            getCart(a);


            listenToDataBase();
    }

    private void listenToDataBase() {

        if (mAuth.getCurrentUser() == null) return;

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("notifications").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    controller.notification(getApplicationContext(), "Smart Cart", dataSnapshot.getValue()+"");
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("notifications").child(mAuth.getCurrentUser().getUid()).removeValue();


                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // getIntent() should always return the most recent

        Gson gson = new Gson(); // set user Data
        _user = gson.fromJson(intent .getStringExtra("userMetaData"), userBoard.class);
        setIntent(intent);
        myCart.clear();
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    private void openSearch(String data){
        s = new search(this, myCart);
        s.show();

        Button ok = s.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCart =  s.getItems();
                s.cancel();
            }
        });

        s.sendData(data);

    }

    private void openMenu() { //open menu button
        Gson gson = new Gson();
        String metaData = gson.toJson(_user); // convert metaData to JSON

        Intent a = new Intent(this, menu.class); // opens the menu
        a.putExtra("userMetaData", metaData);
        startActivity(a);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) { // Receive intent from chooseItemsActivity (update my cart)
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
            getCart(data);
            }
        }
    }


    private void getCart(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            myCart = bundle.getParcelableArrayList("restore_items");
        }
    }


    public void lastShopping(View view)
    {
        if (_user == null || FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent s = new Intent(this, LoginActivity.class);
            startActivity(s);
        }
        else{
        Intent s = new Intent(this,LastShoppingActivity.class);
        startActivity(s);}
    }

    public void shoppingActivity(View view) {
            Gson gson = new Gson();
            String metaData = gson.toJson(_user); // convert metaData to JSON
            Intent a = new Intent(getApplicationContext(), shoppingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("restore_items", myCart);
            a.putExtra("userMetaData", metaData);
            a.putExtras(bundle);
            startActivityForResult(a, 1);
        //}
    }


}
