package com.socialv2.ewallet.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.sharedReferences.KeyValueSharedPreferences;
import com.socialv2.ewallet.ui.register.RegisterCheckOtpActivity;
import com.socialv2.ewallet.ui.register.RegisterEnterPhoneActivity;
import com.socialv2.ewallet.ui.register.WelcomeActivity;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements TextWatcher {

    private static final Pattern VIETNAM_PHONE_PATTERN = Pattern.compile("^0[3|5|7|8|9]\\d{8}$");

    private Button mContinueButton, mSignInButton;
    private TextInputEditText mEditTextPhoneNumber;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mContinueButton = findViewById(R.id.continueButtonLogin);
        mSignInButton = findViewById(R.id.signinButton);
        mEditTextPhoneNumber = findViewById(R.id.editTextPhoneNumberLogin);

        mSignInButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, WelcomeActivity.class);
        });

        initView();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        mContinueButton.setEnabled(false);
        mEditTextPhoneNumber.addTextChangedListener(this);
        mContinueButton.setOnClickListener(view -> {
            String phoneNumber = mEditTextPhoneNumber
                    .getText()
                    .toString();

            if (VIETNAM_PHONE_PATTERN.matcher(phoneNumber).matches()) {
                Intent intent = new Intent(LoginActivity.this, LoginPasswordActivity.class);
                intent.putExtra("phone_number_login", phoneNumber); // Pass the phone number
                startActivity(intent);

                new KeyValueSharedPreferences(this, "PhoneNumberLogin").setData(phoneNumber);
            } else {
                Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
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
}