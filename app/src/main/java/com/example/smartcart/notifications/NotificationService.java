package com.example.smartcart.notifications;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {


        @Override
        public int onStartCommand(Intent intent, int flags, int startId){
            // START YOUR TASKS
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public void onDestroy() {
            // STOP YOUR TASKS
            super.onDestroy();
        }

        @Override
        public IBinder onBind(Intent intent){
            return null;
        }
}
