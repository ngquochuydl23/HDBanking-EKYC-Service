package com.socialv2.ewallet.ui.facialRecognition;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.register.ConfirmInformationActivity;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

public class IntroduceFacialRecognitionActivity extends BaseActivity {

    private Button mGettingStartedButton;

    public IntroduceFacialRecognitionActivity() {
        super(R.layout.activity_introduct_facial_recognition);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGettingStartedButton = findViewById(R.id.gettingStartedButton);
        initView();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));

        mGettingStartedButton.setOnClickListener(v -> {
            NavigateUtil.navigateTo(this, FacialRecognitionActivity.class);

        });
    }
}