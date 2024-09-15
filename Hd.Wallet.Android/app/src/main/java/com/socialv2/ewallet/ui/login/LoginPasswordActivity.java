package com.socialv2.ewallet.ui.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.MainHome.MainHomeActivity;
import com.socialv2.ewallet.ui.register.DetailedAddressAtivity;
import com.socialv2.ewallet.utils.NavigateUtil;

public class LoginPasswordActivity extends AppCompatActivity {

    private Button mContinue, mForgetPassword;
    private TextView tvForgetPassword, tvChangeSdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_password);

        mContinue = findViewById(R.id.continueButton);
        tvForgetPassword = findViewById(R.id.textviewForgetPassword);
        tvChangeSdt = findViewById(R.id.textviewChangeSDT);

        navigateunti();
        initview();
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
            NavigateUtil.navigateTo(this, MainHomeActivity.class);
        });

        tvForgetPassword.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, ResetPasswordActivity.class);
        });

        tvChangeSdt.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, LoginActivity.class);
        });
    }
}