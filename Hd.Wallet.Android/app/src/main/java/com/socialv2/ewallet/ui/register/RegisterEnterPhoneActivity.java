package com.socialv2.ewallet.ui.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.sharedReferences.KeyValueSharedPreferences;
import com.socialv2.ewallet.utils.DpToPx;
import com.socialv2.ewallet.utils.NavigateUtil;

import java.util.regex.Pattern;

public class RegisterEnterPhoneActivity extends AppCompatActivity {

    private static final Pattern VIETNAM_PHONE_PATTERN = Pattern.compile("^0[3|5|7|8|9]\\d{8}$");

    private Button mContinueButton;
    private TextInputEditText mEditTextPhoneNumber;
    private TextInputLayout mPhoneNumberTextInputLayout;
    private TextView mTextConsentTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_enter_phone);

        mContinueButton = findViewById(R.id.continueButton);
        mEditTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        mPhoneNumberTextInputLayout = findViewById(R.id.phoneNumberTextInputLayout);
        mTextConsentTextView = findViewById(R.id.textConsentTextView);

        initView();
        initPhoneNumberValidation();
    }

    private void initView() {
        setMarginTopTextConsent(0);
        mEditTextPhoneNumber.requestFocus();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mContinueButton.setOnClickListener(view -> {
            String phoneNumber = mEditTextPhoneNumber
                    .getText()
                    .toString();

            if (isValidVietnamPhoneNumber(phoneNumber)) {
                NavigateUtil.navigateTo(this, RegisterCheckOtpActivity.class);

                new KeyValueSharedPreferences(this, "PhoneNumberRegister")
                        .setData(phoneNumber);

            } else {
                Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidVietnamPhoneNumber(String phoneNumber) {
        return VIETNAM_PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    private void initPhoneNumberValidation() {
        mEditTextPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String phoneNumber = charSequence.toString();
                if (!isValidVietnamPhoneNumber(phoneNumber)) {
                    mPhoneNumberTextInputLayout.setError("Số điện thoại không hợp lệ");
                    setMarginTopTextConsent(10);
                } else {
                    mPhoneNumberTextInputLayout.setError(null);
                    setMarginTopTextConsent(0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No action needed
            }
        });
    }

    private void setMarginTopTextConsent(int dp) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mTextConsentTextView.getLayoutParams();
        layoutParams.setMargins(0, DpToPx.convert(this, dp),0,0);
        mTextConsentTextView.setLayoutParams(layoutParams);
    }
}
