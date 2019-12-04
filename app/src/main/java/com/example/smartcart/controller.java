package com.example.smartcart;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/*
this class contains static algorithms
*/
public class controller {




    public static void toast(Context c, String msg){
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }
}
