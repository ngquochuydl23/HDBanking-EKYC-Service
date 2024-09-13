package com.socialv2.ewallet.ui.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.textfield.TextInputEditText;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.utils.NavigateUtil;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private Button mContinueButton;
    private TextInputEditText editTextPhoneNumber;

    // Regex to validate Vietnamese phone numbers
    private static final Pattern VIETNAM_PHONE_PATTERN = Pattern.compile("^0[3|5|7|8|9]\\d{8}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        mContinueButton = findViewById(R.id.continueButton);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        initView();
        initPhoneNumberValidation();
    }

    private void initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mContinueButton.setOnClickListener(view -> {
            String phoneNumber = editTextPhoneNumber.getText().toString();
            if (isValidVietnamPhoneNumber(phoneNumber)) {
                NavigateUtil.navigateTo(this, GettingTakenIdCardActivity.class);
            } else {
                Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidVietnamPhoneNumber(String phoneNumber) {
        return VIETNAM_PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    private void initPhoneNumberValidation() {
        editTextPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Validate phone number as user types
                String phoneNumber = charSequence.toString();
                if (!isValidVietnamPhoneNumber(phoneNumber)) {
                    editTextPhoneNumber.setError("Số điện thoại không hợp lệ");
                } else {
                    editTextPhoneNumber.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No action needed
            }
        });
    }
}
