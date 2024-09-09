package com.OcrBanking.Android.sharedReferences;

import android.content.Context;

import com.OcrBanking.Android.BaseSharedPreferences;

public class SaveTokenSharedPreference extends BaseSharedPreferences<String> {

    private static final String KeyName = "accessToken";

    public SaveTokenSharedPreference(Context context) {
        super(context, KeyName, String.class);
    }
}
