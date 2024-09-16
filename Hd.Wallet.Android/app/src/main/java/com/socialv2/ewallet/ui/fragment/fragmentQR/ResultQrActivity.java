package com.socialv2.ewallet.ui.fragment.fragmentQR;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.socialv2.ewallet.R;

public class ResultQrActivity extends AppCompatActivity {

    private TextView resultTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultqr);
        resultTextView = findViewById(R.id.resultTextView);

        // Nhận giá trị từ Intent
        String scannedValue = getIntent().getStringExtra("SCAN_RESULT");
        if (scannedValue != null) {
            resultTextView.setText("Scanned Value: " + scannedValue);
        }
    }
}
