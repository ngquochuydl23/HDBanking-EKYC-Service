package com.socialv2.ewallet.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        WindowUtils.applyPadding(findViewById(R.id.main));

        NavigateUtil.navigateTo(this, WelcomeActivity.class);

        // chia flow ra chỗ này

    }
}