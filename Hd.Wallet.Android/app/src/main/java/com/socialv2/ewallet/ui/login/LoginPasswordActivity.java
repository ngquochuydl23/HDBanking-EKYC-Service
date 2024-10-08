package com.socialv2.ewallet.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.utils.NavigateUtil;

public class LoginPasswordActivity extends AppCompatActivity {

    private Button mContinue;
    private TextView mHiddenPhoneNumberTextView, tvChangeSdt;
    private Button mChangePhoneNumberButton;
    private View mForgotPasswordTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_password);

        mContinue = findViewById(R.id.continueButton);
        mForgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        mChangePhoneNumberButton = findViewById(R.id.changePhoneNumberButton);

        mHiddenPhoneNumberTextView = findViewById(R.id.hiddenPhoneNumberTextView);

        navigateunti();
        getPhoneNumber();
        initview();
    }

    private void getPhoneNumber() {
        String phoneNumber = getIntent().getStringExtra("phone_number_login");
        if (phoneNumber != null) {
            // Mask the first 6 digits with asterisks
            String maskedPhoneNumber = phoneNumber.replaceAll("\\d(?=\\d{3})", "*");
            mHiddenPhoneNumberTextView.setText(maskedPhoneNumber); // Set the masked phone number to TextView

        }
    }

    private void initview() {

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void navigateunti() {

        mContinue.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, com.socialv2.ewallet.ui.home.MainHomeActivity.class);
        });

        mForgotPasswordTextView.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, ResetPasswordActivity.class);
        });

        mChangePhoneNumberButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, LoginActivity.class);
        });
    }
}