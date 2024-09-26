package com.socialv2.ewallet.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final Pattern VIETNAM_PHONE_PATTERN = Pattern.compile("^0[3|5|7|8|9]\\d{8}$");

    private Button mContinue, mSignIn;
    private TextInputEditText mEditTextPhoneNumber;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mContinue = findViewById(R.id.continueButtonLogin);
        mSignIn = findViewById(R.id.signinButton);
        mEditTextPhoneNumber = findViewById(R.id.editTextPhoneNumberLogin);

        mSignIn.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, WelcomeActivity.class);
        });

        getPhoneNumber();
        initView();
    }

    private void getPhoneNumber() {
        mContinue.setOnClickListener(view -> {
            String phoneNumber = mEditTextPhoneNumber.getText().toString();

            if (isValidVietnamPhoneNumber(phoneNumber)) {
                Intent intent = new Intent(LoginActivity.this, LoginPasswordActivity.class);
                intent.putExtra("phone_number_login", phoneNumber); // Pass the phone number
                startActivity(intent);

                new KeyValueSharedPreferences(this, "PhoneNumberLogin").setData(phoneNumber);
            } else {
                Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidVietnamPhoneNumber(String phoneNumber) {
        return VIETNAM_PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    private void initView() {

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}