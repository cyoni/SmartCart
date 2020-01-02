package com.example.smartcart;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class controller {



    public static void toast(Context c, String msg){
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }

    public static HashMap<String, item> convertToHashmap(ArrayList<item> list) {
        HashMap<String, item> newHashMap = new HashMap<>();

        for (int i=0; i< list.size(); i++){
            newHashMap.put(list.get(i).getName() , list.get(i));
        }
        return newHashMap;

    }

    public static ArrayList<item>  convertToArraylist(HashMap<String, item> oldList) {
        ArrayList<item> list = new ArrayList<>();
        for (Map.Entry<String, item> entry : oldList.entrySet()) {
            list.add(entry.getValue());
        }

        return list;
    }
}
