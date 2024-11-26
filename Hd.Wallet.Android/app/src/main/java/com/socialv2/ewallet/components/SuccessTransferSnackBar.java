package com.socialv2.ewallet.components;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.utils.DpToPx;

public class SuccessTransferSnackBar {

    private Context mContext;
    private Snackbar mSnackbar;

    public SuccessTransferSnackBar(Context context, View parentView, int duration) {
        mContext = context;
        mSnackbar = Snackbar.make(context, parentView, "OK", duration);
    }

    public static SuccessTransferSnackBar make(Context context, int duration) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            View parentView = activity.findViewById(R.id.main);
            return new SuccessTransferSnackBar(context, parentView, duration)
                    .setGravity(Gravity.TOP)
                    .setContentView();
        }

        return null;
    }

    public static SuccessTransferSnackBar make(Context context, View parentView, int duration) {
        return new SuccessTransferSnackBar(context, parentView, duration)
                .setGravity(Gravity.TOP)
                .setContentView();
    }

    public SuccessTransferSnackBar setGravity(int gravity) {
        if (mSnackbar != null) {
            View snackbarView = mSnackbar.getView();

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
            params.gravity = gravity;
            params.setMargins(
                    DpToPx.convert(mContext, 8),
                    DpToPx.convert(mContext, 60),
                    DpToPx.convert(mContext, 8),
                    DpToPx.convert(mContext, 10));
            snackbarView.setLayoutParams(params);

        }
        return this;
    }

    public SuccessTransferSnackBar show() {
        if (mSnackbar != null) {
            mSnackbar.show();
        }

        return this;
    }

    @SuppressLint("RestrictedApi")
    public SuccessTransferSnackBar setContentView() {

        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) mSnackbar.getView();
        snackbarLayout.setBackgroundColor(Color.TRANSPARENT);
        snackbarLayout.setElevation(20f);


        TextView defaultTextView = snackbarLayout.findViewById(com.google.android.material.R.id.snackbar_text);
        defaultTextView.setVisibility(View.INVISIBLE);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View customView = inflater.inflate(R.layout.snackbar_success_transfer, null);

//        TextView customTextView = customView.findViewById(R.id.snackbar_text);
//        customTextView.setText("Giao dịch thành công!");


        snackbarLayout.addView(customView, 0);
        return this;
    }
}
