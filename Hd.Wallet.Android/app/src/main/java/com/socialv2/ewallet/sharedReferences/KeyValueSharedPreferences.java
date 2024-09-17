package com.socialv2.ewallet.sharedReferences;

import android.content.Context;

import com.socialv2.ewallet.BaseSharedPreferences;

public class KeyValueSharedPreferences extends BaseSharedPreferences<String> {

    public KeyValueSharedPreferences(Context context, String key) {
        super(context, key, String.class);
    }
}
