package com.customnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    // checking for type intent filter
                    if (intent.getAction().equals(InterfaceConsts.REGISTRATION_COMPLETE)) {
                        // gcm successfully registered now subscribe to `global` topic to receive app wide notifications
                    } else if (intent.getAction().equals(InterfaceConsts.PUSH_NOTIFICATION)) {
                        // new push notification is received
                        // String message = intent.getStringExtra("message");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            // register GCM registration complete receiver
            LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(InterfaceConsts.REGISTRATION_COMPLETE));
            // register new push message receiver by doing this, the activity will be notified each time a new message arrives
            LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(InterfaceConsts.PUSH_NOTIFICATION));
            // clear the notification area when the app is opened
            NotificationUtils.clearNotifications(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }
}
