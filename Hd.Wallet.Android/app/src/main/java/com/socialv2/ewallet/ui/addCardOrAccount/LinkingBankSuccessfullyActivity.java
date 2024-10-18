package com.socialv2.ewallet.ui.addCardOrAccount;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.accounts.AccountBankDto;
import com.socialv2.ewallet.ui.login.LoginPasswordActivity;
import com.socialv2.ewallet.ui.main.MainHomeActivity;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

public class LinkingBankSuccessfullyActivity extends BaseActivity {

    private final String TAG = LinkingBankSuccessfullyActivity.class.getName();


    private TextView mBankNameTextView;
    private TextView mAccountNoTextView;
    private TextView mOwnerTextView;

    private Button mHomeBackButton;

    public LinkingBankSuccessfullyActivity() {
        super(R.layout.activity_linking_bank_successfully);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBankNameTextView = findViewById(R.id.bankNameTextView);
        mAccountNoTextView = findViewById(R.id.accountNoTextView);
        mOwnerTextView = findViewById(R.id.ownerTextView);

        mHomeBackButton = findViewById(R.id.homeBackButton);
        initView();
        getCreatedAccount();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        mHomeBackButton.setOnClickListener(view -> {
            NavigateUtil.navigateToHomeClearState(this);
        });
    }

    private void getCreatedAccount() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("AccountBank-json")) {
            String json = intent.getStringExtra("AccountBank-json");
            AccountBankDto accountBank = new Gson().fromJson(json, AccountBankDto.class);
            Log.d(TAG, "AccountBank-json: " + accountBank.toString());


            mBankNameTextView.setText(accountBank.getBankFullName());
            mOwnerTextView.setText(accountBank.getBankOwnerName());

            String maskedAccNo = accountBank
                    .getBankAccountId()
                    .replaceAll("\\d(?=\\d{3})", "*");

            mAccountNoTextView.setText(maskedAccNo);

        }
    }
}