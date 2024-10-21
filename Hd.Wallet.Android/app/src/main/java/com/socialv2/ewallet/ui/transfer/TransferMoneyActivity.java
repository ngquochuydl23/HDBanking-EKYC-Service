package com.socialv2.ewallet.ui.transfer;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.components.HdWalletToolbar;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.https.api.accountHttp.AccountHttpImpl;
import com.socialv2.ewallet.https.api.accountHttp.IAccountService;
import com.socialv2.ewallet.utils.BankingResourceLogo;
import com.socialv2.ewallet.utils.FetchImageUrl;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.VietnameseConcurrency;
import com.socialv2.ewallet.utils.WindowUtils;

public class TransferMoneyActivity extends BaseActivity {

    private final String TAG = TransferMoneyActivity.class.getName();

    private IAccountService mAccountService;
    private EditText mAmountMoneyEditText;
    private Button mTransferButton;
    private View mSelectSourceButton;
    private SelectSourceBottomSheet mSelectSourceBottomSheet;
    private HdWalletToolbar mToolbar;
    private TextView mSrcAccountNoBank;
    private TextView mBalanceTextView;
    private AvatarView mLogoBankAvatarView;
    private EditText mTransferContentEditText;

    private AccountDto mSourceAccount;
    private AccountDto mDestAccount;

    public TransferMoneyActivity() {
        super(R.layout.activity_transfer_money);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAccountService = new AccountHttpImpl(this);

        mTransferButton = findViewById(R.id.transferButton);
        mSelectSourceButton = findViewById(R.id.selectSourceButton);
        mAmountMoneyEditText = findViewById(R.id.amountMoneyEditText);
        mSrcAccountNoBank = findViewById(R.id.srcAccountNoBank);
        mToolbar = findViewById(R.id.toolbar);
        mBalanceTextView = findViewById(R.id.balanceTextView);
        mLogoBankAvatarView = findViewById(R.id.logoBankAvatarView);
        mTransferContentEditText= findViewById(R.id.transferContentEditText);

        mSelectSourceBottomSheet = new SelectSourceBottomSheet();
        initView();

        getPrimaryBalance();
        getDestAccountResult();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));

        mAmountMoneyEditText.requestFocus();
        mTransferButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, SuccessfulTransactionActivity.class);
        });

        mSelectSourceButton.setOnClickListener(view -> {
            mSelectSourceBottomSheet.show(getSupportFragmentManager(), mSelectSourceBottomSheet.getTag());
        });

        mSelectSourceBottomSheet.setOnSelectSourceBank(this::onSelectedSource);
    }

    private void getDestAccountResult() {
        String json = "{ \"id\": \"ffeba890-30cb-49ee-9dda-a2209d750c10\", \"userId\": \"9565dc9b-0af9-4793-81e4-8f3615612785\", \"isBankLinking\": false, \"walletBalance\": 1000000, \"linkedAccountId\": null, \"transactionLimit\": 10000000, \"accountType\": 0, \"accountBank\": { \"bankName\": \"HD_WALLET_MBBANK\", \"bankAccountId\": \"0868684961\", \"bankOwnerName\": \"NGUYEN QUOC HUY\", \"idCardNo\": \"068203012123\" }, \"user\": null, \"isBlocked\": false, \"isUnlinked\": false, \"isDeleted\": false, \"createdAt\": \"2024-09-30T14:36:32.792995\", \"lastUpdated\": \"0001-01-01T00:00:00\" }";
        mDestAccount = new Gson()
                .fromJson(json, AccountDto.class);

        //Log.i(TAG, account.toString());
    }

    private void onSelectedSource(AccountDto account) {
        mSelectSourceBottomSheet.dismiss();
        Log.i(TAG, account.toString());

        mSourceAccount = account;
        mSrcAccountNoBank.setText(mSourceAccount.getAccountBank().getBankAccountId());

        if (mSourceAccount.getBankLinking()) {
            mBalanceTextView.setVisibility(View.VISIBLE);
            FetchImageUrl.read(mLogoBankAvatarView, BankingResourceLogo.getLogo(mSourceAccount
                    .getAccountBank()
                    .getLogoUrl()));

            mBalanceTextView.setText("Tài khoản liên kết");
        } else {

            mBalanceTextView.setVisibility(View.VISIBLE);
            mBalanceTextView.setText("Số dư " + VietnameseConcurrency.format(mSourceAccount.getWalletBalance()));
            mLogoBankAvatarView.setImageDrawable(getDrawable(R.drawable.ic_src_wallet));
        }
    }

    @SuppressLint("CheckResult")
    private void getPrimaryBalance() {
        mAccountService.getPrimaryAccount()
                .subscribe(response -> {
                    AccountDto primaryAccount = response.getResult();
                    onSelectedSource(primaryAccount);
                    mTransferContentEditText.setText(primaryAccount
                            .getAccountBank()
                            .getBankOwnerName() + " chuyen khoan");

                    Log.i(TAG, primaryAccount.toString());
                }, throwable -> {

                }, () -> {

                });
    }
}