package com.socialv2.ewallet.ui.transfer;

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

public class FindBankActivity extends BaseActivity {

    public FindBankActivity() {
        super(R.layout.activity_find_bank);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
    }
}