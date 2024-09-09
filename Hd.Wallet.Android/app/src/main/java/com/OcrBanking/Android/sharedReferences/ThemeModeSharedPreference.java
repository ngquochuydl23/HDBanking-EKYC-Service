package com.OcrBanking.Android.sharedReferences;

import android.content.Context;

import com.OcrBanking.Android.BaseSharedPreferences;

public class ThemeModeSharedPreference extends BaseSharedPreferences<String> {

    private static final String KeyName = "WeChat.ThemeMode";

    public ThemeModeSharedPreference(Context context) {
        super(context, KeyName, String.class);
    }
}