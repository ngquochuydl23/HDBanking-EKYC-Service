package com.socialv2.ewallet.components;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.socialv2.ewallet.BaseDialog;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.dev.WriteNfcActivity;

public class OkDialog extends BaseDialog {

    public OkDialog(Context context) {
        super(context, R.layout.bottomsheet_ask_user_pin);
    }


    @Override
    protected void onDialogCreated(View view) {

    }

    public static BaseDialog makeDialog(AppCompatActivity atc, String title, String description) {
        return new OkDialog(atc);
    }

    public static BaseDialog makeDialog(Context context, String title, String description) {
        return new OkDialog(context);
    }
}
