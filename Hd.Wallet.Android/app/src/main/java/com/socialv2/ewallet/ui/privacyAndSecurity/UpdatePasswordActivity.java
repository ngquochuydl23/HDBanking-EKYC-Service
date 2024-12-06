package com.socialv2.ewallet.ui.privacyAndSecurity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.utils.WindowUtils;

public class UpdatePasswordActivity extends BaseActivity {

    public UpdatePasswordActivity() {
        super(R.layout.activity_update_password);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.applyPadding(findViewById(R.id.main));

    }



}