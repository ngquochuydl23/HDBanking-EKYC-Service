package com.socialv2.ewallet.components;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.chaos.view.PinView;
import com.socialv2.ewallet.BaseBottomSheetDialog;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.register.WelcomeActivity;
import com.socialv2.ewallet.utils.DpToPx;
import com.socialv2.ewallet.utils.NavigateUtil;

public class AskUserPinBottomSheet extends BaseBottomSheetDialog {

    private final int N_PIN_ITEMS = 6;

    private PinView mOtpTextView;
    private OnCompletePin mListener;
    private OnShowListener mOnShowListener;
    private TextView mErrorTextView;

    public AskUserPinBottomSheet() {
        super(R.layout.bottomsheet_ask_user_pin);
    }

    @Override
    public int getTheme() {
        return R.style.PinBottomSheetDialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(true);

        mOtpTextView = view.findViewById(R.id.otpTextView);
        mErrorTextView = view.findViewById(R.id.errorTextView);
        initView();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void initView() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mOtpTextView.requestFocus();
        mOtpTextView.setError("");
        mOtpTextView.setLineColor(getContext().getColor(R.color.borderWidthColor));
        Display display = requireActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - (2 * DpToPx.convert(getContext(), 15));
        int totalSpacing = width - (DpToPx.convert(getContext(), 48) * N_PIN_ITEMS);

        mOtpTextView.setItemSpacing(totalSpacing / (N_PIN_ITEMS - 1));

    }

    public AskUserPinBottomSheet setOnCompletePin(OnCompletePin listener) {
        mListener = listener;
        return this;
    }

    @Override
    public void onResume() {
        super.onResume();
        mOtpTextView.requestFocus();
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setPinIncorrect() {
        mOtpTextView.requestFocus();
        mOtpTextView.setText(null);
        mOtpTextView.setError(null);
        mOtpTextView.setLineColor(
                ResourcesCompat.getColorStateList(
                        getResources(),
                        R.color.error,
                        requireActivity().getTheme()));
        mErrorTextView.setText("Mã PIN của bạn không đúng");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mOtpTextView.setError(null);
        mOtpTextView.setText("");
        mOtpTextView.requestFocus();
        mOtpTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mErrorTextView.setVisibility(View.INVISIBLE);
                if (s.length() == N_PIN_ITEMS) {
                    new Handler().postDelayed(() -> {
                        if (mListener != null) {
                            hideKeyboard(mOtpTextView);
                            mListener.onCompleted(mOtpTextView.getText().toString());
                        }
                    }, 500);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        if (mOnShowListener != null) {
            mOnShowListener.onShow();
        }
    }

    public void setOnShowListener(OnShowListener listener) {
        mOnShowListener = listener;
    }

    @FunctionalInterface
    public interface OnCompletePin {
        void onCompleted(String pin);
    }

    @FunctionalInterface
    public interface OnShowListener {
        void onShow();
    }
}