package com.socialv2.ewallet;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.res.Configuration;
import android.os.Build;

import com.socialv2.ewallet.utils.ThemeUi;


public class Application extends android.app.Application {

    public static final String CHANNEL_ID = "pustnotification_id'";


    private ThemeUi mThemeUi;


    @Override
    public void onCreate() {
        super.onCreate();

        mThemeUi = new ThemeUi(getApplicationContext());

        initApplication();
        createChanelNotification();
    }

    private void initApplication() {

        mThemeUi.init();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private  void createChanelNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel  = new NotificationChannel(CHANNEL_ID ,  "pushnotification" ,
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager  manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
