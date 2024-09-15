package com.socialv2.ewallet.ui;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.facialRecognition.IntroduceFacialRecognitionActivity;
import com.socialv2.ewallet.ui.login.LoginActivity;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

public class Main1Activity extends AppCompatActivity {
    private Button mLogIn, mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        WindowUtils.applyPadding(findViewById(R.id.main));


        mLogIn = findViewById(R.id.btn_login);
        mSignUp = findViewById(R.id.btn_signup);

        setupListeners();
    }


    private void setupListeners() {
        mLogIn.setOnClickListener(v -> navigateToLoginActivity());
        mSignUp.setOnClickListener(v -> navigateToSignUpActivity());
    }

    private void navigateToSignUpActivity() {
        NavigateUtil.navigateTo(this, IntroduceFacialRecognitionActivity.class); // Chuyển hướng đến màn hình đăng ký
    }

    private void navigateToLoginActivity() {
        NavigateUtil.navigateTo(this, LoginActivity.class); // Chuyển hướng đến màn hình đăng nhập
    }
}
