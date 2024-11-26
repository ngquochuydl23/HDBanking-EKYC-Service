package com.socialv2.ewallet.ui.login;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.OkDialog;
import com.socialv2.ewallet.components.SuccessTransferSnackBar;
import com.socialv2.ewallet.dtos.users.RequestResetPasswordDto;
import com.socialv2.ewallet.utils.WindowUtils;

public class ResetPasswordActivity extends BaseActivity {

    private Button mContinueButton;
    private EditText mFullNameEditText;
    private EditText mIdCardNoEditText;
    private EditText mPhoneNumberEditText;

    public ResetPasswordActivity() {
        super(R.layout.activity_reset_password);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContinueButton = findViewById(R.id.continueButton);
        mFullNameEditText = findViewById(R.id.fullNameEditText);
        mIdCardNoEditText = findViewById(R.id.idCardNoEditText);
        mPhoneNumberEditText = findViewById(R.id.phoneNumberEditText);

        initView();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));


        mContinueButton.setOnClickListener(view -> {
            requestResetPassword();
        });
    }

    private void requestResetPassword() {
        getFieldsData();
//        OkDialog.makeDialog(this, "", "")
//                .show();

        SuccessTransferSnackBar
                .make(this, 8000)
                .show();
    }

    private RequestResetPasswordDto getFieldsData() {
        String fullName = mFullNameEditText
                .getText()
                .toString()
                .trim();

        String idCardNo = mIdCardNoEditText
                .getText()
                .toString()
                .trim();

        String phoneNumber = mPhoneNumberEditText
                .getText()
                .toString()
                .trim();

        return new RequestResetPasswordDto(fullName, idCardNo, phoneNumber);
    }
}

