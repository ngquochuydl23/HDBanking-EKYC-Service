package com.socialv2.ewallet;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;

import com.afollestad.materialdialogs.MaterialDialog;

public abstract class BaseDialog {

    private Context mContext;
    private MaterialDialog mMaterialDialog;
    private DialogStyle mDialogStyle;
    private View mView;

    public BaseDialog(Context context, @LayoutRes int layout) {
        mContext = context;
        mView = LayoutInflater
                .from(context)
                .inflate(layout, null);

        mMaterialDialog = new MaterialDialog(mContext, MaterialDialog.getDEFAULT_BEHAVIOR());
        mMaterialDialog.setContentView(mView);
        onDialogCreated(mView);
    }

    public void show() {
        if (!mMaterialDialog.isShowing()) {
            mMaterialDialog.show();
        }
    }

    public void dismiss() {
        if (mMaterialDialog.isShowing()) {
            mMaterialDialog.dismiss();
        }
    }

    protected abstract void onDialogCreated(View view);

    public BaseDialog setupStyle(DialogStyle style) {
        mDialogStyle = style != null ? style : new DialogStyle();

        mMaterialDialog.cornerRadius(mDialogStyle.getBorderRadius(), null);
        return this;
    }

    public class DialogStyle {

        private float borderRadius;


        public DialogStyle() {
            this.borderRadius = 5f;
        }

        public DialogStyle(float borderRadius) {
            this.borderRadius = borderRadius;
        }

        public void setBorderRadius(float borderRadius) {
            this.borderRadius = borderRadius;
        }

        public float getBorderRadius() {
            return borderRadius;
        }
    }
}
