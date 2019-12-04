package com.example.smartcart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.smartcart.login_signup.login;
import com.example.smartcart.login_signup.signup;

public class LoginActivity extends AppCompatActivity {

    int what_fragment=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        firstFragment();
    }

    public void firstFragment(){
        FragmentManager fm = getSupportFragmentManager();
        login fragTop = new login();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contentFragment, fragTop);
        ft.commit();

        what_fragment=0;
    }

    public void secondFragment(){
        FragmentManager fm = getSupportFragmentManager();
        signup fragTop = new signup();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contentFragment, fragTop);
        ft.commit();
        what_fragment=1;
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


}
