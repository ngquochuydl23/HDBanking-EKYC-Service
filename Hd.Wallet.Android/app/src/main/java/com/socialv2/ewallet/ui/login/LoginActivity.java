package com.socialv2.ewallet.ui.login;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.register.WelcomeActivity;
import com.socialv2.ewallet.utils.NavigateUtil;

public class LoginActivity extends AppCompatActivity {

    private Button mContinue  , mSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mContinue = findViewById(R.id.continueButton);
        mSignIn = findViewById(R.id.signinButton);


        initView();
    }

    private void initView() {
        mContinue.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, LoginPasswordActivity.class);
        });

        mSignIn.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, WelcomeActivity.class);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}