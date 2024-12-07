package com.socialv2.ewallet.ui.privacyAndSecurity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.chaos.view.PinView;
import com.socialv2.ewallet.R;

public class UpdatePinActivity extends AppCompatActivity {

    private PinView otpTextView, otpTextView1, otpTextView2;
    private TextView errorTextView, errorTextView1;
    private View continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pin);


        otpTextView = findViewById(R.id.otpTextView);
        otpTextView1 = findViewById(R.id.otpTextView1);
        otpTextView2 = findViewById(R.id.otpTextView2);
        errorTextView = findViewById(R.id.errorTextView);
        errorTextView1 = findViewById(R.id.errorTextView1);
        continueButton = findViewById(R.id.continueButton);


        clearErrorsOnInput();

        continueButton.setOnClickListener(v -> handleContinue());
    }

    private void clearErrorsOnInput() {
        otpTextView.setOnClickListener(v -> errorTextView.setVisibility(View.GONE));
        otpTextView1.setOnClickListener(v -> errorTextView1.setVisibility(View.GONE));
        otpTextView2.setOnClickListener(v -> errorTextView1.setVisibility(View.GONE));
    }

    private void handleContinue() {
        String oldPin = otpTextView.getText().toString();
        String newPin = otpTextView1.getText().toString();
        String confirmPin = otpTextView2.getText().toString();

        // Kiểm tra mã PIN cũ
        if (TextUtils.isEmpty(oldPin) || oldPin.length() < 6) {
            errorTextView.setText("Mã PIN cũ không hợp lệ.");
            errorTextView.setVisibility(View.VISIBLE);
            return;
        }

        // Kiểm tra mã PIN mới và xác nhận
        if (TextUtils.isEmpty(newPin) || newPin.length() < 6) {
            errorTextView1.setText("Mã PIN mới không hợp lệ.");
            errorTextView1.setVisibility(View.VISIBLE);
            return;
        }
        if (!newPin.equals(confirmPin)) {
            errorTextView1.setText("Mã PIN xác nhận không khớp.");
            errorTextView1.setVisibility(View.VISIBLE);
            return;
        }

        // Nếu tất cả đều hợp lệ
        Toast.makeText(this, "Cập nhật mã PIN thành công!", Toast.LENGTH_SHORT).show();
        finish(); // Kết thúc Activity sau khi xử lý thành công
    }
}
