package com.socialv2.ewallet.components;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.socialv2.ewallet.BaseDialog;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.dev.WriteNfcActivity;

public class OkDialog extends BaseDialog {

    private Button mUnderstoodButton;
    private UnderstoodListener mUnderstoodListener;
    private String title;
    private String description;

    public OkDialog(Context context, String title, String description) {
        super(context, R.layout.dialog_ok);

        this.title = title;
        this.description = description;
        bindToDialog();
    }

    private void bindToDialog() {
        ((TextView) getView().findViewById(R.id.titleOkDialogTextView)).setText(title);
        ((TextView) getView().findViewById(R.id.descriptionOkDialogTextView)).setText(description);
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

        ((TextView) view.findViewById(R.id.titleOkDialogTextView)).setText(title);
        ((TextView) view.findViewById(R.id.descriptionOkDialogTextView)).setText(description);
    }

    public static BaseDialog makeDialog(AppCompatActivity atc, String title, String description) {
        return new OkDialog(atc, title, description);
    }

    public static BaseDialog makeDialog(Context context, String title, String description) {
        return new OkDialog(context , title, description);
    }

    public void setUnderstoodListener(UnderstoodListener understoodListener) {
        mUnderstoodListener = understoodListener;
    }

    @FunctionalInterface
    public interface UnderstoodListener {
        public void onClick();
    }
}
