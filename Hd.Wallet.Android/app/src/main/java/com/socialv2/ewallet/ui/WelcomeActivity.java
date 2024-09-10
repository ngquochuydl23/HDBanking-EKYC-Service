package com.socialv2.ewallet.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.register.RegisterActivity;
import com.socialv2.ewallet.utils.NavigateUtil;


public class WelcomeActivity extends BaseActivity {
    private CardView mSignUpCardView;

    public WelcomeActivity() {
        super(R.layout.activity_welcome);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSignUpCardView = findViewById(R.id.signUpCardView);

        initView();
    }

    private void initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mSignUpCardView.setOnClickListener(v -> {

            NavigateUtil.navigateTo(this, RegisterActivity.class);

        });
    }
}
