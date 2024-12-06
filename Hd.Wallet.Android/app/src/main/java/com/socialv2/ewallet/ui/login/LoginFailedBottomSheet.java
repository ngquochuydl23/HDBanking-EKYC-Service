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
import com.socialv2.ewallet.components.BackdropLoadingDialogFragment;
import com.socialv2.ewallet.ui.register.WelcomeActivity;
import com.socialv2.ewallet.utils.NavigateUtil;

public class LoginFailedBottomSheet extends BaseBottomSheetDialog {

    private String phoneNumber;
    private Button mRetryButton;
    private TextView mErrorMsgTextView;

    public LoginFailedBottomSheet() {
        super(R.layout.bottomsheet_login_failed);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mRetryButton = view.findViewById(R.id.retryButton);

        mErrorMsgTextView = view.findViewById(R.id.errorMsgTextView);

        mRetryButton.setOnClickListener(v -> {
            dismiss();
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

    public static LoginFailedBottomSheet makeAndShow(FragmentManager fragmentManager, String errorMsg) {
        LoginFailedBottomSheet bottomSheet = new LoginFailedBottomSheet();
        bottomSheet.show(fragmentManager, bottomSheet.getTag());

        return bottomSheet;
    }

}
