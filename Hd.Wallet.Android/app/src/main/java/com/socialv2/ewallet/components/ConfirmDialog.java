package com.socialv2.ewallet.components;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.socialv2.ewallet.BaseDialog;
import com.socialv2.ewallet.R;

public class ConfirmDialog extends BaseDialog {

    private Button mOkButton;
    private Button mCancelButton;
    private UnderstoodListener mUnderstoodListener;

    public ConfirmDialog(Context context) {
        super(context, R.layout.dialog_confirm);
    }


    @Override
    protected void onDialogCreated(View view) {
        mOkButton = view.findViewById(R.id.okButton);
        mCancelButton = view.findViewById(R.id.cancelButton);


        mOkButton.setOnClickListener(x -> {
            dismiss();
        });

        mCancelButton.setOnClickListener(x -> {

        });
    }

    public ConfirmDialog setTitle(String title) {
        return this;
    }

    public ConfirmDialog setDescription(String title) {
        return this;
    }

    public static BaseDialog makeDialog(AppCompatActivity atc, String title, String description) {
        return new ConfirmDialog(atc);
    }

    public static BaseDialog makeDialog(Context context, String title, String description) {
        return new ConfirmDialog(context);
    }

    public void setUnderstoodListener(UnderstoodListener understoodListener) {
        mUnderstoodListener = understoodListener;
    }

    @FunctionalInterface
    public interface UnderstoodListener {
        public void onClick();
    }
}
