package com.socialv2.ewallet.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.socialv2.ewallet.R;


public class BackdropLoadingDialog extends AlertDialog.Builder {
    private AlertDialog dialog;

    public BackdropLoadingDialog(Context context) {
        super(context, R.style.TransparentDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_loading_backdrop, null); // Use your actual layout resource here
        setView(view);

        dialog = create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT); // Fullscreen layout
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        }
    }

    public void setLoading(Boolean loading) {
        if (loading) {
            dialog.show();
        } else {
            dialog.dismiss();
        }

    }
}
