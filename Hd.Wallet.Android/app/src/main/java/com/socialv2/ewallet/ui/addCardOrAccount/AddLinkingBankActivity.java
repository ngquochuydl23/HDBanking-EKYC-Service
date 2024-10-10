package com.socialv2.ewallet.ui.addCardOrAccount;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.HdWalletToolbar;
import com.socialv2.ewallet.utils.WindowUtils;

public class AddLinkingBankActivity extends BaseActivity {

    private HdWalletToolbar mToolbar;

    public AddLinkingBankActivity() {
        super(R.layout.activity_add_linking_bank);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolbar = findViewById(R.id.toolbar);

        initView();
        getAppInfo();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
    }

    private void getAppInfo() {
        mToolbar.setTitle("MB Bank");
    }
}