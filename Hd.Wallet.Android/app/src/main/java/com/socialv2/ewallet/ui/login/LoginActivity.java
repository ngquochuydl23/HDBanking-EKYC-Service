package com.socialv2.ewallet.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.HttpException;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.BackdropLoadingDialogFragment;
import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.https.api.userHttp.IUserService;
import com.socialv2.ewallet.https.api.userHttp.UserHttpImpl;
import com.socialv2.ewallet.sharedReferences.KeyValueSharedPreferences;
import com.socialv2.ewallet.ui.register.RegisterCheckOtpActivity;
import com.socialv2.ewallet.ui.register.RegisterEnterPhoneActivity;
import com.socialv2.ewallet.ui.register.SignUpAccountActivity;
import com.socialv2.ewallet.ui.register.WelcomeActivity;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.ParseHttpError;
import com.socialv2.ewallet.utils.WindowUtils;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity implements TextWatcher {

    private static final String TAG = LoginActivity.class.getName();
    private static final Pattern VIETNAM_PHONE_PATTERN = Pattern.compile("^0[3|5|7|8|9]\\d{8}$");

    private Button mContinueButton, mSignInButton;
    private TextInputEditText mEditTextPhoneNumber;
    private IUserService mUserService;
    private BackdropLoadingDialogFragment mLoadingBackdropDialog;
    private UserNotFoundBottomSheet mUserNotFoundBottomSheet;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mUserService = new UserHttpImpl(this);
        mUserNotFoundBottomSheet = new UserNotFoundBottomSheet();
        mContinueButton = findViewById(R.id.continueButtonLogin);
        mSignInButton = findViewById(R.id.signinButton);
        mEditTextPhoneNumber = findViewById(R.id.editTextPhoneNumberLogin);

        mLoadingBackdropDialog = new BackdropLoadingDialogFragment();
        mLoadingBackdropDialog.setFragmentManager(getSupportFragmentManager());

        initView();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        mLoadingBackdropDialog.setLoading(false);
        mContinueButton.setEnabled(false);

        mEditTextPhoneNumber.requestFocus();
        mEditTextPhoneNumber.addTextChangedListener(this);
        mContinueButton.setOnClickListener(view -> {
            onContinue();
        });

        mSignInButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, WelcomeActivity.class);
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String phoneNumber = mEditTextPhoneNumber
                .getText()
                .toString();

        if (phoneNumber.length() > 0 && VIETNAM_PHONE_PATTERN.matcher(phoneNumber).matches()) {
            mContinueButton.setEnabled(true);
        } else {
            mContinueButton.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @SuppressLint("CheckResult")
    private void onContinue() {
        String phoneNumber = mEditTextPhoneNumber
                .getText()
                .toString();

        if (VIETNAM_PHONE_PATTERN.matcher(phoneNumber).matches()) {
            mLoadingBackdropDialog.setLoading(true);

            mUserService.getUserByPhone(phoneNumber)
                    .subscribe(response -> {
                        mLoadingBackdropDialog.setLoading(false);

                        Log.i(TAG, response.toString());

                        new KeyValueSharedPreferences(this,"PhoneNumberLogin")
                                .setData(phoneNumber);
                        Intent intent = new Intent(LoginActivity.this, LoginPasswordActivity.class);
                        intent.putExtra("PhoneNumberLogin", phoneNumber); // Pass the phone number
                        startActivity(intent);
                        finish();
                    }, throwable -> {
                        int statusCode = ParseHttpError.getStatusCode(throwable);
                        HttpResponseDto<?> errorBody = ParseHttpError.parse(throwable);

                        if (errorBody != null) {
                            Log.e(TAG, errorBody.getError());
                        }

                        if (statusCode == 400) {
                            mLoadingBackdropDialog.setLoading(false);
                            mUserNotFoundBottomSheet.setPhoneNumber(phoneNumber);
                            mUserNotFoundBottomSheet.show(getSupportFragmentManager(), mUserNotFoundBottomSheet.getTag());
                        }

                        throwable.printStackTrace();
                    }, () -> {
                        mLoadingBackdropDialog.setLoading(false);
                    });
//
        } else {
            Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
        }
    }
}