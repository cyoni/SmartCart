package com.example.smartcart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class menu extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button a;
    String name;
    FirebaseUser currentUser;

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

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
         currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Button a = findViewById(R.id.guestAccount);
            name = currentUser.getEmail();
            a.setText("My Account ("+ name +")");
            Button b = findViewById(R.id.managerLogin);
            b.setVisibility(View.GONE);
        }

    }


  /*  public void customerLogin(View view) {
        Intent a = new Intent(this, LoginActivity.class);
        startActivity(a);
        finish();
    }
*/
    public void managerLogin(View view) {
        Intent a = new Intent(this, LoginManagerActivity.class);
        startActivity(a);
        finish();
    }





    public void customerLogin(View view) {
        if (a.getText().equals("My Account ("+ name +")")){
            Intent a = new Intent(this, myAccountActivity.class);
            startActivity(a);
        }
        else {
            Intent a = new Intent(this, LoginActivity.class);
            startActivity(a);
        }
        finish();
    }

    public void comingSoon(View view) {
        controller.toast(this, "Coming soon!");
    }
}
