package com.socialv2.ewallet.ui.register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.sharedReferences.KeyValueSharedPreferences;
import com.socialv2.ewallet.ui.login.LoginActivity;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

public class RegistrationSuccessfulActivity extends AppCompatActivity {

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration_successful);

        loginButton = findViewById(R.id.loginButton);

        initView();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        loginButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, LoginActivity.class);
        });
    }
}
