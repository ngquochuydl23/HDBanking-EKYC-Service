package com.OcrBanking.Android.ui.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.OcrBanking.Android.R;

public class MainActivity extends AppCompatActivity {
    private CardView mCardViewSignUP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        mCardViewSignUP = findViewById(R.id.cardViewSignUP);


        mCardViewSignUP.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, OtpScreen.class);
            startActivity(intent); // Chuyển sang trang khác


        });



    }
}
