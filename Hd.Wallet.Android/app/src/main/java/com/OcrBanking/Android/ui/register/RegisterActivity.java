package com.OcrBanking.Android.ui.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.OcrBanking.Android.R;

public class RegisterActivity extends AppCompatActivity {
    private CardView mSignUpCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        mSignUpCardView = findViewById(R.id.signUpCardView);


        mSignUpCardView.setOnClickListener(v -> {

            Intent intent = new Intent(RegisterActivity.this, OtpScreen.class);
            startActivity(intent); // Chuyển sang trang khác


        });



    }
}
