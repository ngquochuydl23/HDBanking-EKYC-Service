package com.socialv2.ewallet.ui.accountBank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.https.api.accountHttp.AccountHttpImpl;
import com.socialv2.ewallet.https.api.accountHttp.IAccountService;
import com.socialv2.ewallet.utils.BankingResourceLogo;
import com.socialv2.ewallet.utils.FetchImageUrl;
import com.socialv2.ewallet.utils.WindowUtils;

public class DetailAccountBankActivity extends BaseActivity {

    private static final String TAG = DetailAccountBankActivity.class.getName();

    private TextView mCardNoTextView;
    private TextView mShortBankNameTextView;
    private ImageView mCardBackGroundImageView;
    private AvatarView mLogoImageView;
    private Button mUnlinkBankAccountButton;

    private IAccountService mAccountService;

    private String mAccountId;

    public DetailAccountBankActivity() {
        super(R.layout.activity_detail_account_bank);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCardNoTextView = findViewById(R.id.cardNoTextView);
        mShortBankNameTextView = findViewById(R.id.shortBankNameTextView);
        mCardBackGroundImageView = findViewById(R.id.cardBackGroundImageView);
        mLogoImageView = findViewById(R.id.logoImageView);
        mUnlinkBankAccountButton = findViewById(R.id.unlinkBankAccountButton);

        mAccountService = new AccountHttpImpl(this);

        initView();
        getAccountId();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        mUnlinkBankAccountButton.setOnClickListener(view -> {
            unlink();
        });
    }

    @SuppressLint("CheckResult")
    private void getAccountId() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("AccountId")) {
            mAccountId = intent.getStringExtra("AccountId");
            mAccountService
                    .getAccountById(mAccountId)
                    .subscribe(response -> {
                        AccountDto account = response.getResult();
                        Log.i(TAG, account.toString());

                        mShortBankNameTextView.setText(account
                                .getAccountBank()
                                .getBankFullName());

                        String accountNumber = account
                                .getAccountBank()
                                .getBankAccountId();

                        int maskLength = accountNumber.length() - 3;
                        String maskedPart = "*".repeat(maskLength);
                        String visiblePart = accountNumber.substring(maskLength);


                        mCardNoTextView.setText(maskedPart + visiblePart);

                        FetchImageUrl.read(mLogoImageView, BankingResourceLogo.getLogo(
                                account.getAccountBank()
                                        .getLogoUrl()));
                    }, throwable -> {
                        throwable.printStackTrace();
                    }, () -> {

                    });
        }
    }

    @SuppressLint("CheckResult")
    private void unlink() {
        mAccountService
                .unlinkAccount(mAccountId)
                .subscribe(responseDto -> {
                    Log.i(TAG, "Unlink successfully");
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });
    }
}