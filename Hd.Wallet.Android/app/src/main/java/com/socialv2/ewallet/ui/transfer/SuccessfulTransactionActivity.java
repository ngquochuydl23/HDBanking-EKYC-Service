package com.socialv2.ewallet.ui.transfer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

public class SuccessfulTransactionActivity extends BaseActivity {

    private Button mTransferButton;

    public SuccessfulTransactionActivity() {
        super(R.layout.activity_successful_transaction);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTransferButton = findViewById(R.id.transferButton);

        initView();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));

        mTransferButton.setOnClickListener(view -> {
            NavigateUtil.navigateToHomeClearState(this);
        });
    }
}