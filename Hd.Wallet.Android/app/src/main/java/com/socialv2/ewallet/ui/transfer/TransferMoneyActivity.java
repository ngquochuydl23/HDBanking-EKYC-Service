package com.socialv2.ewallet.ui.transfer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
import com.socialv2.ewallet.dtos.CitizenAccountBankDto;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.banks.BankDto;
import com.socialv2.ewallet.https.api.accountHttp.AccountHttpImpl;
import com.socialv2.ewallet.https.api.accountHttp.IAccountService;
import com.socialv2.ewallet.utils.BankingResourceLogo;
import com.socialv2.ewallet.utils.FetchImageUrl;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.UpperCaseOwnerName;
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
    private TextView mDestFullNameTextView;
    private TextView mDestAccountNoTextView;
    private TextView mBankNameTextView;
    private AvatarView mLogoBankAvatarView;
    private AvatarView mDestLogoImageView;
    private EditText mTransferContentEditText;

    private AccountDto mSourceAccount;

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
        mTransferContentEditText = findViewById(R.id.transferContentEditText);
        mDestFullNameTextView = findViewById(R.id.destFullNameTextView);
        mDestAccountNoTextView = findViewById(R.id.destAccountNoTextView);
        mBankNameTextView = findViewById(R.id.bankNameTextView);
        mDestLogoImageView = findViewById(R.id.destLogoImageView);

        mSelectSourceBottomSheet = new SelectSourceBottomSheet();
        initView();

        getPrimaryBalance();
        getDestAccountResult();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));

        mAmountMoneyEditText.requestFocus();
        mTransferButton.setOnClickListener(view -> {
            transfer();
        });

        mSelectSourceButton.setOnClickListener(view -> {
            mSelectSourceBottomSheet.show(getSupportFragmentManager(), mSelectSourceBottomSheet.getTag());
        });

        mSelectSourceBottomSheet.setOnSelectSourceBank(this::onSelectedSource);

    }

    private void getDestAccountResult() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TransferTo")) {
            String transferTo = intent.getStringExtra("TransferTo");


            if (transferTo.equals("Bank") && intent.hasExtra("CitizenAccount")) {
                CitizenAccountBankDto citizenAccountBank = new Gson()
                        .fromJson(intent.getStringExtra("CitizenAccount"), CitizenAccountBankDto.class);

                FetchImageUrl.read(mDestLogoImageView, BankingResourceLogo.getLogo(citizenAccountBank
                        .getBank()
                        .getLogoApp()));

                mDestFullNameTextView.setText(UpperCaseOwnerName.apply(citizenAccountBank.getOwnerName()));
                mDestAccountNoTextView.setText(citizenAccountBank.getAccountNo());
                mBankNameTextView.setText(citizenAccountBank.getBankName());
                return;
            }


        }
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

    private void transfer() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TransferTo")) {
            String transferTo = intent.getStringExtra("TransferTo");

            if (transferTo.equals("Bank") && intent.hasExtra("CitizenAccount")) {
                CitizenAccountBankDto citizenAccountBank = new Gson()
                        .fromJson(intent.getStringExtra("CitizenAccount"), CitizenAccountBankDto.class);

                NavigateUtil.navigateTo(this, SuccessfulTransactionActivity.class);
            } else if (transferTo.equals("Internal") && intent.hasExtra("Account")) {


                NavigateUtil.navigateTo(this, SuccessfulTransactionActivity.class);
            }
        }


    }
}
