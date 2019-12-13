package com.example.smartcart;

import android.content.Context;
import android.widget.Toast;


public class controller {



    public static void toast(Context c, String msg){
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }
}
