package com.socialv2.ewallet.ui.login;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.socialv2.ewallet.BaseBottomSheetDialog;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.register.WelcomeActivity;
import com.socialv2.ewallet.utils.NavigateUtil;

public class UserNotFoundBottomSheet extends BaseBottomSheetDialog {

    private String phoneNumber;

    private Button mSkipButton;
    private Button mSignUpButton;
    private TextView mErrorMsgTextView;

    public UserNotFoundBottomSheet() {
        super(R.layout.bottomsheet_user_not_found);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mSkipButton = view.findViewById(R.id.skipButton);
        mSignUpButton = view.findViewById(R.id.signUpButton);

        mErrorMsgTextView = view.findViewById(R.id.errorMsgTextView);
        setErrorMsg(phoneNumber);

        mSkipButton.setOnClickListener(v -> {
            dismiss();
        });

        mSignUpButton.setOnClickListener(v -> {
            NavigateUtil.navigateTo(getContext(), WelcomeActivity.class);
            ((Activity) getContext()).finish();
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mErrorMsgTextView != null) {
            mErrorMsgTextView.setText("");
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void setErrorMsg(String phoneNumber) {
        String text = "Số điện thoại " + phoneNumber + " chưa đăng ký dịch vụ ví HDWallet. Vui lòng bấm 'Đăng ký' để mở tài khoản trực tuyến và Đăng ký sử dụng ứng dụng ngay.";
        SpannableString spannableString = new SpannableString(text);
        int start = text.indexOf(phoneNumber);
        int end = start + phoneNumber.length();

        if (start >= 0) {
            // Set color for the phone number
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        mErrorMsgTextView.setText(spannableString);
    }
}
