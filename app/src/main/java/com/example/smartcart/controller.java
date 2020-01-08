package com.example.smartcart;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class controller {
    Context context;

    public static void toast(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }

    public static HashMap<String, item> convertToHashmap(ArrayList<item> list) {
        HashMap<String, item> newHashMap = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            newHashMap.put(list.get(i).getName(), list.get(i));
        }
        return newHashMap;

    }

    public static ArrayList<item> convertToArraylist(HashMap<String, item> oldList) {
        ArrayList<item> list = new ArrayList<>();
        for (Map.Entry<String, item> entry : oldList.entrySet()) {
            list.add(entry.getValue());
        }

        return list;
    }


    // This is the Notification Channel ID. More about this in the next section
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";

    public static String makeUpperLetter(String str) {
        if (str.length() != 0) {
            str = str.toLowerCase();
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return "";
    }

    public static String splitCart(ArrayList<item> list) {
        String items = "";
        for (int i=0; i<list.size(); i++){
            item tmp = list.get(i);
            items += tmp.getCategory() + "," + tmp.getName() + "," + tmp.getMyQuantity() + "," + tmp.getPrice() + "," + tmp.getMyQuantity()*tmp.getPrice() + ";";
        }
        return items;
    }

    public static void updateCartOnline(HashMap<String, item> myCart) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("cart").child(mAuth.getCurrentUser().getUid()).setValue(splitCart(controller.convertToArraylist(myCart)));
        }
    }


    //Notification Channel ID passed as a parameter here will be ignored for all the Android versions below 8.0
    public void ccc() {
/*        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle("This is heading");
        builder.setContentText("This is description");
        builder.setSmallIcon(R.drawable.icon);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
        Notification notification = builder.build();*/
    }
}
