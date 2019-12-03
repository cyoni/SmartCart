package com.example.smartcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.smartcart.manager_login_signup.login;
import com.example.smartcart.manager_login_signup.signup;

public class LoginManagerActivity extends AppCompatActivity {

    int what_fragment=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firstFragment();
    }

    public void firstFragment(){


        FragmentManager fm = getSupportFragmentManager();
        login fragTop = new login();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.contentFragment, fragTop);
        ft.commit();

        // Create new fragment and transaction


/*        Fragment newFragment = new login();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.contentFragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();*/
        what_fragment=0;
    }

    public void secondFragment(){

 /*       Fragment newFragment = new signup();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.contentFragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();*/


        FragmentManager fm = getSupportFragmentManager();
        signup fragTop = new signup();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contentFragment, fragTop);
        ft.commit();
        what_fragment=1;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            Intent a = new Intent(this, menu.class);
            //   startActivity(a);
            startActivityForResult(a, 0);
        }
        return super.onOptionsItemSelected(item);
    }


    public void customerLogin(View view) {
        Intent a = new Intent(this, LoginActivity.class);
        startActivity(a);
    }

    public void changeLayout(View view) {

        if (what_fragment==1) {
            firstFragment();
        }
        else {
            secondFragment();
        }


    }

    public void register(View view) {


    }
}
