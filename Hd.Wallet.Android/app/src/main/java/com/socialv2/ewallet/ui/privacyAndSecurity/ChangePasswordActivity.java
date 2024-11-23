package com.socialv2.ewallet.ui.privacyAndSecurity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.socialv2.ewallet.R;

import java.util.List;


public class ChangePasswordActivity extends AppCompatActivity implements Validator.ValidationListener {

    private TextInputLayout oldPasswordTextInputLayout;
    private TextInputLayout newPasswordTextInputLayout;
    private TextInputLayout confirmNewPasswordTextInputLayout;

    @NotEmpty(message = "Vui lòng nhập mật khẩu cũ")
    private TextInputEditText oldPasswordEditText;

    @NotEmpty(message = "Vui lòng nhập mật khẩu mới")
//    @Length(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private TextInputEditText newPasswordEditText;

    @NotEmpty(message = "Vui lòng xác nhận mật khẩu mới")
    private TextInputEditText confirmNewPasswordEditText;

    private Button changePasswordButton;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        initViews();


        validator = new Validator(this);
        validator.setValidationListener(this);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setupListeners();
    }

    private void initViews() {
        oldPasswordTextInputLayout = findViewById(R.id.oldPasswordTextInputLayout);
        newPasswordTextInputLayout = findViewById(R.id.newPasswordTextInputLayout);
        confirmNewPasswordTextInputLayout = findViewById(R.id.confirmNewPasswordTextInputLayout);

        oldPasswordEditText = findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmNewPasswordEditText = findViewById(R.id.confirmNewPasswordEditText);

        changePasswordButton = findViewById(R.id.changePasswordButton);
    }

    private void setupListeners() {
        changePasswordButton.setOnClickListener(view -> {
            validator.validate();
        });
    }

    // Khi kiểm tra hợp lệ thành công
    @Override
    public void onValidationSucceeded() {
        String oldPassword = oldPasswordEditText.getText().toString();
        String newPassword = newPasswordEditText.getText().toString();
        String confirmNewPassword = confirmNewPasswordEditText.getText().toString();


        if (!newPassword.equals(confirmNewPassword)) {
            confirmNewPasswordTextInputLayout.setError("Mật khẩu không khớp");
        } else {
            confirmNewPasswordTextInputLayout.setError(null);
            handleChangePassword(oldPassword, newPassword);
        }
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);


            if (view == oldPasswordEditText) {
                oldPasswordTextInputLayout.setError(message);
            } else if (view == newPasswordEditText) {
                newPasswordTextInputLayout.setError(message);
            } else if (view == confirmNewPasswordEditText) {
                confirmNewPasswordTextInputLayout.setError(message);
            }
        }
    }


    private void handleChangePassword(String oldPassword, String newPassword) {
        showToast("Đổi mật khẩu thành công!");
    }

    private void showToast(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }
}
