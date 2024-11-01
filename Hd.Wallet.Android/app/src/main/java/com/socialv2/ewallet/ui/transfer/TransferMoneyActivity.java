package com.socialv2.ewallet.ui.transfer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AskUserPinBottomSheet;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.components.BackdropLoadingDialogFragment;
import com.socialv2.ewallet.components.HdWalletToolbar;
import com.socialv2.ewallet.dtos.CitizenAccountBankDto;
import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.accounts.AccountBankDto;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.accounts.RequestLinkingAccount;
import com.socialv2.ewallet.dtos.transactions.TransactionDto;
import com.socialv2.ewallet.dtos.transfers.RequestBankTransferDto;
import com.socialv2.ewallet.https.api.accountHttp.AccountHttpImpl;
import com.socialv2.ewallet.https.api.accountHttp.IAccountService;
import com.socialv2.ewallet.https.api.transferHttp.TransferHttpImpl;
import com.socialv2.ewallet.https.api.transferHttp.ITransferService;
import com.socialv2.ewallet.ui.addCardOrAccount.AddLinkingBankActivity;
import com.socialv2.ewallet.ui.addCardOrAccount.LinkingBankSuccessfullyActivity;
import com.socialv2.ewallet.utils.BankingResourceLogo;
import com.socialv2.ewallet.utils.FetchImageUrl;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.ParseHttpError;
import com.socialv2.ewallet.utils.UpperCaseOwnerName;
import com.socialv2.ewallet.utils.VietnameseConcurrency;
import com.socialv2.ewallet.utils.WindowUtils;

public class TransferMoneyActivity extends BaseActivity {

    private final String TAG = TransferMoneyActivity.class.getName();

    private IAccountService mAccountService;
    private ITransferService mTransferService;

    private EditText mAmountMoneyEditText;
    private Button mTransferButton;
    private View mSelectSourceButton;
    private TextView mSrcAccountNoBank;
    private TextView mBalanceTextView;
    private TextView mDestFullNameTextView;
    private TextView mDestAccountNoTextView;
    private TextView mBankNameTextView;
    private AvatarView mLogoBankAvatarView;
    private AvatarView mDestLogoImageView;
    private EditText mTransferContentEditText;

    private SelectSourceBottomSheet mSelectSourceBottomSheet;
    private BackdropLoadingDialogFragment mLoadingBackdropDialog;
    private AskUserPinBottomSheet mAskUserPinBottomSheet;

    private AccountDto mSourceAccount;
    private boolean mUseLinkingBank = false;

    public TransferMoneyActivity() {
        super(R.layout.activity_transfer_money);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAccountService = new AccountHttpImpl(this);
        mTransferService = new TransferHttpImpl(this);

        mTransferButton = findViewById(R.id.transferButton);
        mSelectSourceButton = findViewById(R.id.selectSourceButton);
        mAmountMoneyEditText = findViewById(R.id.amountMoneyEditText);
        mSrcAccountNoBank = findViewById(R.id.srcAccountNoBank);
        mBalanceTextView = findViewById(R.id.balanceTextView);
        mLogoBankAvatarView = findViewById(R.id.logoBankAvatarView);
        mTransferContentEditText = findViewById(R.id.transferContentEditText);
        mDestFullNameTextView = findViewById(R.id.destFullNameTextView);
        mDestAccountNoTextView = findViewById(R.id.destAccountNoTextView);
        mBankNameTextView = findViewById(R.id.bankNameTextView);
        mDestLogoImageView = findViewById(R.id.destLogoImageView);

        mSelectSourceBottomSheet = new SelectSourceBottomSheet();

        mLoadingBackdropDialog = new BackdropLoadingDialogFragment();
        mLoadingBackdropDialog.setFragmentManager(getSupportFragmentManager());

        initView();

        getPrimaryBalance();
        getDestAccountResult();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));

        mAmountMoneyEditText.requestFocus();
        mTransferButton.setOnClickListener(view -> {
            getBottomSheet();
            mAskUserPinBottomSheet.show(getSupportFragmentManager(), AskUserPinBottomSheet.class.getName());
        });

        mSelectSourceButton.setOnClickListener(view -> {
            mSelectSourceBottomSheet.show(getSupportFragmentManager(), mSelectSourceBottomSheet.getTag());
        });

        mSelectSourceBottomSheet.setOnSelectSourceBank(this::onSelectedSource);
        mAmountMoneyEditText.addTextChangedListener(new TextWatcher() {

            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {


                if (!s.toString().equals(current)) {
                    mAmountMoneyEditText.removeTextChangedListener(this);

                    // Chuyển text từ EditText thành giá trị double
                    double parsedValue = VietnameseConcurrency.parseToDouble(s.toString());

                    // Định dạng lại và cập nhật Text
                    String formatted = VietnameseConcurrency.formatWithoutSymbol(parsedValue);
                    current = formatted;

                    // Cập nhật lại EditText với văn bản đã định dạng
                    mAmountMoneyEditText.setText(formatted);
                    mAmountMoneyEditText.setSelection(formatted.length());

                    mAmountMoneyEditText.addTextChangedListener(this);
                }

            }
        });
    }

    private void getDestAccountResult() {
        Intent intent = getIntent();
        if (intent != null
                && intent.hasExtra("TransferTo")
                && intent.hasExtra("Type")) {


            String type = intent.getStringExtra("Type");
            String transferTo = intent.getStringExtra("TransferTo");

            if (type.equals("InternalTransfer")
                    && transferTo != null
                    && !transferTo.isEmpty()) {
                bindInternalTransfer(transferTo, intent);

            } else if (type.equals("BankTransfer")
                    && transferTo != null
                    && !transferTo.isEmpty()) {

                Log.d(TAG, "Type -> BankTransfer");
                bindBankTransfer(transferTo, intent);
            }
        }
    }

    private void bindInternalTransfer(String transferTo, Intent extraIntents) { }

    private void bindBankTransfer(String transferTo, Intent extraIntents ) {
        if (transferTo.equals("Bank") && extraIntents.hasExtra("CitizenAccount")) {
            CitizenAccountBankDto citizenAccountBank = new Gson()
                    .fromJson(extraIntents.getStringExtra("CitizenAccount"), CitizenAccountBankDto.class);

            FetchImageUrl.read(mDestLogoImageView, BankingResourceLogo.getLogo(citizenAccountBank
                    .getBank()
                    .getLogoApp()));

            mDestFullNameTextView.setText(UpperCaseOwnerName.apply(citizenAccountBank.getOwnerName()));
            mDestAccountNoTextView.setText(citizenAccountBank.getAccountNo());
            mBankNameTextView.setText(citizenAccountBank.getBankName());
            return;
        }
    }

    private void onSelectedSource(AccountDto account) {
        mSelectSourceBottomSheet.dismiss();
        Log.d(TAG, account.toString());

        mSourceAccount = account;
        mSrcAccountNoBank.setText(mSourceAccount.getAccountBank().getBankAccountId());

        if (mSourceAccount.getBankLinking()) {
            mUseLinkingBank = true;
            mBalanceTextView.setVisibility(View.VISIBLE);
            FetchImageUrl.read(mLogoBankAvatarView, BankingResourceLogo.getLogo(mSourceAccount
                    .getAccountBank()
                    .getLogoUrl()));

            mBalanceTextView.setText("Tài khoản liên kết");
        } else {
            mUseLinkingBank = false;
            mBalanceTextView.setVisibility(View.VISIBLE);
            mBalanceTextView.setText("Số dư " + VietnameseConcurrency.format(mSourceAccount.getWalletBalance()));
            mLogoBankAvatarView.setImageDrawable(getDrawable(R.drawable.ic_src_wallet));
        }
    }

    @SuppressLint("CheckResult")
    private void getPrimaryBalance() {
        mUseLinkingBank = false;
        mAccountService.getPrimaryAccount()
                .subscribe(response -> {

                    mSourceAccount = response.getResult();
                    onSelectedSource(mSourceAccount);
                    mTransferContentEditText.setText(mSourceAccount
                            .getAccountBank()
                            .getBankOwnerName() + " chuyen khoan");

                    Log.i(TAG, mSourceAccount.toString());
                }, throwable -> {

                }, () -> {

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

                transfer(pin);
            });
        }
    }


    @SuppressLint("CheckResult")
    private void transfer(String pin) {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TransferTo")) {
            String transferTo = intent.getStringExtra("TransferTo");

            if (transferTo.equals("Bank") && intent.hasExtra("CitizenAccount")) {
                CitizenAccountBankDto citizenAccountBank = new Gson()
                        .fromJson(intent.getStringExtra("CitizenAccount"), CitizenAccountBankDto.class);

                double amount = VietnameseConcurrency.parseToDouble(mAmountMoneyEditText
                        .getText()
                        .toString());

                String transferContent = mTransferContentEditText
                        .getText()
                        .toString();

                mTransferService.bankTransfer(pin, new RequestBankTransferDto(
                                mSourceAccount.getId(),
                                citizenAccountBank.getBin(),
                                citizenAccountBank.getAccountNo(),
                                transferContent,
                                amount,
                                mUseLinkingBank))
                        .subscribe(response -> {
                                    mLoadingBackdropDialog.setLoading(false);

                                    Log.d(TAG, response.getResult().toString());
                                    TransactionDto transaction = response.getResult();
                                    String transactionJson = new Gson()
                                            .toJson(transaction);

                                    Intent forwardIntent = new Intent(this, SuccessfulTransactionActivity.class);
                                    forwardIntent.putExtra("TransactionResult", transactionJson);
                                    forwardIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
                                    startActivity(forwardIntent);
                                    finish();
                                },
                                throwable -> {
                                    mLoadingBackdropDialog.setLoading(false);

                                    if (throwable instanceof JsonSyntaxException) {
                                        throwable.printStackTrace();
                                        return;
                                    }


                                    int statusCode = ParseHttpError.getStatusCode(throwable);
                                    if (statusCode == 400 || statusCode == 500) {
                                        HttpResponseDto<?> errorBody = ParseHttpError.parse(throwable);
                                        String errMsg = errorBody.getError();
                                        Log.e(TAG, errMsg, throwable);


                                        if (errMsg.equals("Pin is incorrect")) {
                                            getBottomSheet();

                                            mAskUserPinBottomSheet.setOnShowListener(() -> {
                                                mAskUserPinBottomSheet.setPinIncorrect();
                                            });

                                            mAskUserPinBottomSheet.show(getSupportFragmentManager(), AskUserPinBottomSheet.class.getName());

                                        }
                                    }
                                },
                                () -> {
                                    mLoadingBackdropDialog.setLoading(false);
                                });


            } else if (transferTo.equals("Internal") && intent.hasExtra("Account")) {


                NavigateUtil.navigateTo(this, SuccessfulTransactionActivity.class);
            }
        }


    }
}
