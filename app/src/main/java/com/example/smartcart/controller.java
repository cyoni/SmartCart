package com.example.smartcart;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class controller {
    private FirebaseAuth mAuth;

    public  controller(){}

    public boolean isCustomerLoggedIn(){

        return false;
    }

    public String getCustomerInfo(){
        return "coming soon";
    }



    public static void toast(Context c, String msg){
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }
}
