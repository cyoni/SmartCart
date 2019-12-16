package com.example.smartcart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;


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
/*    *//*    Gson gson = new Gson();
       userBoard user = gson.fromJson(getIntent().getStringExtra("userMetaData"), userBoard.class);
        controller.toast(this, user.getAddress());*//*
    controller.toast(this, _user.getAddress());*/
    }
}
