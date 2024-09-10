package com.socialv2.ewallet;

import android.content.res.Configuration;

import com.socialv2.ewallet.utils.ThemeUi;


public class Application extends android.app.Application {

    private ThemeUi mThemeUi;


    @Override
    public void onCreate() {
        super.onCreate();

        mThemeUi = new ThemeUi(getApplicationContext());
        initApplication();
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
}
