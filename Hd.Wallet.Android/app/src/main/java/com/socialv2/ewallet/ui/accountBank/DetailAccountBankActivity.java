package com.socialv2.ewallet.ui.accountBank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AskUserPinBottomSheet;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.components.BackdropLoadingDialogFragment;
import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.accounts.AccountBankDto;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.https.api.accountHttp.AccountHttpImpl;
import com.socialv2.ewallet.https.api.accountHttp.IAccountService;
import com.socialv2.ewallet.https.api.transferHttp.TransferHttpImpl;
import com.socialv2.ewallet.https.api.transferHttp.ITransferService;
import com.socialv2.ewallet.utils.BankingResourceLogo;
import com.socialv2.ewallet.utils.FetchImageUrl;
import com.socialv2.ewallet.utils.ParseHttpError;
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
    private AskUserPinBottomSheet mAskUserPinBottomSheet;
    private BackdropLoadingDialogFragment mLoadingBackdropDialog;

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
        mLoadingBackdropDialog = new BackdropLoadingDialogFragment();
        mLoadingBackdropDialog.setFragmentManager(getSupportFragmentManager());

        initView();
        getAccountId();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        mUnlinkBankAccountButton.setOnClickListener(view -> {
            getBottomSheet();
            mAskUserPinBottomSheet.show(getSupportFragmentManager(), AskUserPinBottomSheet.class.getName());
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
    private void unlink(String pin) {
        mAccountService
                .unlinkAccount(pin, mAccountId)
                .subscribe(response -> {
                    Log.i(TAG, "Unlink successfully");

                    mLoadingBackdropDialog.setLoading(false);
                    AccountBankDto bankAccount = response
                            .getResult()
                            .getAccountBank();

                    Log.i(TAG, bankAccount.toString());

//                    Intent intent = new Intent(AddLinkingBankActivity.this, LinkingBankSuccessfullyActivity.class);
//                    String json = new Gson().toJson(bankAccount);
//
//                    intent.putExtra("AccountBank-json", json);
//
//                    startActivity(intent);
//                    finish();
                }, throwable -> {
                    mLoadingBackdropDialog.setLoading(false);

                    Log.e(TAG, throwable.getMessage());

                    int statusCode = ParseHttpError.getStatusCode(throwable);
                    if (statusCode == 400) {
                        HttpResponseDto<?> errorBody = ParseHttpError.parse(throwable);
                        String errMsg = errorBody.getError();

                        if (errMsg.equals("Pin is incorrect")) {
                            getBottomSheet();

                            mAskUserPinBottomSheet.setOnShowListener(() -> {
                                mAskUserPinBottomSheet.setPinIncorrect();
                            });

                            mAskUserPinBottomSheet.show(getSupportFragmentManager(), AskUserPinBottomSheet.class.getName());

                        }
                    }
                }, () -> {
                    mLoadingBackdropDialog.setLoading(false);
                });
    }

    @SuppressLint("CheckResult")
    private void getBottomSheet() {
        if (mAskUserPinBottomSheet == null) {
            mAskUserPinBottomSheet = new AskUserPinBottomSheet();
            mAskUserPinBottomSheet.setOnCompletePin(pin -> {
                Log.i(TAG, pin);

                hideKeyboard();
                mAskUserPinBottomSheet.dismiss();
                mLoadingBackdropDialog.setLoading(true);

                unlink(pin);
            });
        }
    }
}