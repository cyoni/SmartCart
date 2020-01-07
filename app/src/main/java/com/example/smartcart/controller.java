package com.example.smartcart;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

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
