package com.socialv2.ewallet.sharedReferences;

import android.content.Context;

import com.socialv2.ewallet.BaseSharedPreferences;


public class ThemeModeSharedPreference extends BaseSharedPreferences<String> {

    private static final String KeyName = "WeChat.ThemeMode";

    public ThemeModeSharedPreference(Context context) {
        super(context, KeyName, String.class);
    }
}