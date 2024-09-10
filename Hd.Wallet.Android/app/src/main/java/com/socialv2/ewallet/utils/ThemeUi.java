package com.socialv2.ewallet.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.sharedReferences.ThemeModeSharedPreference;


public class ThemeUi {
    public static final String DARK_MODE = "dark";
    public static final String LIGHT_MODE = "light";
    private Context mContext;
    private ThemeModeSharedPreference mThemeModeSharedPreference;

    public ThemeUi(Context context) {
        mContext = context;
        mThemeModeSharedPreference = new ThemeModeSharedPreference(context);
    }

    public void init() {
        String wechatThemeModeUI = mThemeModeSharedPreference.getData();

        if (wechatThemeModeUI == null || wechatThemeModeUI.isEmpty()) {
            turnToLightMode();
            return;
        }

        if (wechatThemeModeUI.equals(DARK_MODE)) {
            turnToDarkMode();
            return;
        }

        turnToLightMode();
    }

    public void turnToLightMode() {
        mThemeModeSharedPreference.setData(LIGHT_MODE);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mContext.setTheme(R.style.Theme_OCRBanking_MobileApp);
    }

    public void turnToDarkMode() {
        mThemeModeSharedPreference.setData(DARK_MODE);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        mContext.setTheme(R.style.Theme_OCRBanking_MobileApp);
    }
}
