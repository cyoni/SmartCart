package com.example.smartcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class menu extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        a = findViewById(R.id.guestAccount);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Button a = findViewById(R.id.guestAccount);
            a.setText("My Account");
            Button b = findViewById(R.id.managerLogin);
            b.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.back_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
        if (a.getText().equals("My Account")){
            Intent a = new Intent(this, myAccountActivity.class);
            startActivity(a);
        }
        else {
            Intent a = new Intent(this, LoginActivity.class);
            startActivity(a);
        }
        finish();
    }
}
