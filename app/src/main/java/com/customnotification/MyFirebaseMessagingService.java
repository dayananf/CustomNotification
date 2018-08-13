package com.customnotification;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM Service";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        try {
            if (remoteMessage == null)
                return;

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                handleNotification(remoteMessage.getNotification().getBody());
            }

            if (remoteMessage.getData().size() > 0) {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());

                String title = remoteMessage.getData().get("title");
                String message = remoteMessage.getData().get("message");
                String image = remoteMessage.getData().get("image");
                String tag = remoteMessage.getData().get("tag");

                try {
                    handleDataMessage(remoteMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
                handleNotification(remoteMessage.getNotification().getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //    private void handleDataMessage(JSONObject json) {
    private void handleDataMessage(RemoteMessage remoteMessage) {

        try {
            Intent pushNotification = null;
            Map<String, String> data = remoteMessage.getData();
            String title = data.get("title");
            String message = data.get("message");
            String imageUrl = data.get("image");
            String tag = data.get("tag");


            pushNotification = new Intent(InterfaceConsts.PUSH_NOTIFICATION);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // check for image attachment
            if ((imageUrl.equals("null") || imageUrl.equals("")) || imageUrl == null) {
                showNotificationMessage(getApplicationContext(), title, message, pushNotification);
            } else {
                // image is present, show notification with image
                showNotificationMessageWithBigImage(getApplicationContext(), title, message, pushNotification, imageUrl); // image is present, show notification with image
            }

//            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//                // app is in foreground, broadcast the push message
//                pushNotification = new Intent(InterfaceConsts.PUSH_NOTIFICATION);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//                if ((imageUrl.equals("null") || imageUrl.equals("")) || imageUrl == null) {
//                    showNotificationMessage(getApplicationContext(), title, message, pushNotification);
//                } else {
//                    // image is present, show notification with image
//                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, pushNotification, imageUrl); // image is present, show notification with image
//                }
//            } else {
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNotificationMessageWithBigImage(Context applicationContext, String title, String message, Intent resultIntent, String imageUrl) {
        try {
            NotificationUtils notificationUtils = new NotificationUtils(applicationContext);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            notificationUtils.showNotificationMessage(title, message, resultIntent, imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNotificationMessage(Context applicationContext, String title, String message, Intent resultIntent) {
        try {
            NotificationUtils notificationUtils = new NotificationUtils(applicationContext);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            notificationUtils.showNotificationMessage(title, message, resultIntent, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleNotification(String message) {

        try {
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(InterfaceConsts.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
