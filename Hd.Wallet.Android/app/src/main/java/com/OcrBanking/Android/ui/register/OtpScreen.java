package com.OcrBanking.Android.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.OcrBanking.Android.R;
import com.google.android.material.textfield.TextInputEditText;

public class OtpScreen extends AppCompatActivity {

    private Button mButtonContinue;
    private TextInputEditText mEditTextPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_optscreen);

        // Initialize views
        mButtonContinue = findViewById(R.id.mButtonContinue);
        mEditTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        // Disable button by default
        mButtonContinue.setEnabled(false);

        mButtonContinue.setOnClickListener(v -> {
            Intent intent = new Intent(OtpScreen.this, OcrVerifyScreen.class);
            startActivity(intent); // Chuyển sang trang khác

        });



        // Add TextWatcher to EditText
        mEditTextPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Enable/Disable button based on input
                mButtonContinue.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }
}
