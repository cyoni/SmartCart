package com.example.smartcart;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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

    public static void notification(Context context ,String title, String msg) {
        int notificationId = 123;
        final String CHANNEL_ID = "CHANNEL_ID";


        Intent intent = new Intent(context, welcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_alert)
                .setContentTitle(title)
                .setContentText(msg)
              //  .setStyle(new NotificationCompat.BigTextStyle()
              //      .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }

}
