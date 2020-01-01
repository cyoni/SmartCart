package com.example.smartcart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.DynamicLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    userBoard _user = null;
    private HashMap<String, item> myCart;


    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*         mAuth  = FirebaseAuth.getInstance();// get user
         mDatabase = FirebaseDatabase.getInstance().getReference();*/
        myCart = new HashMap<>();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) { // Receive intent from chooseItemsActivity (update my cart)
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();

                if (bundle != null) {
                    myCart = (HashMap<String, item>) bundle.getSerializable("restore_items");
                }
            }
        }
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

    public void shoppingActivity(View view) {

        Gson gson = new Gson();
        String metaData = gson.toJson(_user); // convert metaData to JSON
        Intent a = new Intent(getApplicationContext() , shoppingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("restore_items", myCart);
        a.putExtra("userMetaData", metaData);
        a.putExtras(bundle);
        startActivityForResult(a, 1);

    }


}
