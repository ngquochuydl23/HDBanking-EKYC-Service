package com.socialv2.ewallet.ui.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.Validator;
import com.socialv2.ewallet.R;

public class SignUpAccountActivity extends AppCompatActivity  {




    private CheckBox termsCheckBox, privacyCheckBox;
    private TextInputLayout phoneInputLayout, passwordInputLayout, confirmPasswordInputLayout;
    private EditText phoneEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_account);

        // Ánh xạ các thành phần
        termsCheckBox = findViewById(R.id.termsCheckBox);
        privacyCheckBox = findViewById(R.id.privacyCheckBox);

        phoneInputLayout = findViewById(R.id.phoneInputLayout);
        phoneEditText = findViewById(R.id.phoneEditText);

        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        passwordEditText = findViewById(R.id.passwordEditText);

        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(v -> {
            Toast.makeText(SignUpAccountActivity.this, "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
        });

        initView();

    }
    private void initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}