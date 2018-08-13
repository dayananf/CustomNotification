package com.customnotification;

public class InterfaceConsts {

    String versionName = BuildConfig.VERSION_NAME;
    int versionCode = BuildConfig.VERSION_CODE;
    int REQUEST_TIMEOUT_MS = 600000;
    int SPLASH_SHOW_TIME = 3000;

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "notificationcheck";


    public static final String CHANNEL_ID = "notify_channel_01";
    public static final String CHANNEL_NAME = "Firebase Notification";
    public static final String CHANNEL_DESCRIPTION = "show notification";

}
