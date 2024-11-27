package com.socialv2.ewallet.components;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.socialv2.ewallet.BaseDialog;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.dev.WriteNfcActivity;

public class OkDialog extends BaseDialog {

    private Button mUnderstoodButton;
    private UnderstoodListener mUnderstoodListener;

    public OkDialog(Context context) {
        super(context, R.layout.dialog_ok);
    }


    @Override
    protected void onDialogCreated(View view) {
        mUnderstoodButton = view.findViewById(R.id.understoodButton);
        mUnderstoodButton.setOnClickListener(x -> {
            dismiss();

            if (mUnderstoodListener != null) {
                mUnderstoodListener.onClick();
            }
        });
    }

    public static BaseDialog makeDialog(AppCompatActivity atc, String title, String description) {
        return new OkDialog(atc);
    }

    public static BaseDialog makeDialog(Context context, String title, String description) {
        return new OkDialog(context);
    }

    public void setUnderstoodListener(UnderstoodListener understoodListener) {
        mUnderstoodListener = understoodListener;
    }

    @FunctionalInterface
    public interface UnderstoodListener {
        public void onClick();
    }
}
