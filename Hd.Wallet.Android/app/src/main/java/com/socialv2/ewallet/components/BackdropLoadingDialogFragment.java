package com.socialv2.ewallet.components;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.socialv2.ewallet.R;

import javax.annotation.Nullable;


public class BackdropLoadingDialogFragment extends DialogFragment {

    private final String TAG = BackdropLoadingDialogFragment.class.getName();

    private Boolean loading = false;
    private FragmentManager mFragmentManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(@androidx.annotation.Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(false); // Prevent cancellation by back button
        dialog.setCanceledOnTouchOutside(false); // Prevent cancellation by touching outside
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_loading_backdrop, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TransparentDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        }
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        if (mFragmentManager == null) {
            Log.e(TAG, "FragmentManager is not set");
            return;
        }

        if (loading) {
            Log.d(TAG, "Showing loading dialog");
            if (!isAdded()) {
                show(mFragmentManager, getClass().getName());
            }
        } else {
            Log.d(TAG, "Dismissing loading dialog");
            if (isAdded() && getDialog() != null && getDialog().isShowing()) {
                dismiss();
            } else {
                Log.d(TAG, "Dialog is not added or not showing");
                //dismiss();
            }
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public Boolean isLoading() {
        return loading;
    }

    @Override
    public void onResume() {
        super.onResume();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setOnKeyListener((dialogInterface, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            });
        }
    }
}
