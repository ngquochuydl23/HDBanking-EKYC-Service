package com.socialv2.ewallet.ui.register;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.idCardTaken.IdCardFrameOverlayView;
import com.socialv2.ewallet.ui.idCardTaken.IdCardTakenAcitivity;
import com.socialv2.ewallet.utils.NavigateUtil;


public class GettingTakenIdCardActivity extends AppCompatActivity {

    private Button mContinueButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_getting_take_idcard);
        mContinueButton = findViewById(R.id.btn_continue);
        initView();
    }

    private void initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mContinueButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, IdCardTakenAcitivity.class);
        });
    }
}