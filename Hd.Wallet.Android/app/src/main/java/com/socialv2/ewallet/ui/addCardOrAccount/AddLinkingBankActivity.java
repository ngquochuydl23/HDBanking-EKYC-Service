package com.socialv2.ewallet.ui.addCardOrAccount;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AskUserPinBottomSheet;
import com.socialv2.ewallet.components.BackdropLoadingDialogFragment;
import com.socialv2.ewallet.components.HdWalletToolbar;
import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.accounts.AccountBankDto;
import com.socialv2.ewallet.dtos.accounts.RequestLinkingAccount;
import com.socialv2.ewallet.dtos.banks.BankDto;
import com.socialv2.ewallet.dtos.users.UserDto;
import com.socialv2.ewallet.https.api.accountHttp.AccountHttpImpl;
import com.socialv2.ewallet.https.api.accountHttp.IAccountService;
import com.socialv2.ewallet.https.api.transferHttp.TransferHttpImpl;
import com.socialv2.ewallet.https.api.transferHttp.ITransferService;
import com.socialv2.ewallet.https.api.bankHttp.BankingResourceHttpImpl;
import com.socialv2.ewallet.https.api.bankHttp.IBankingResourceService;
import com.socialv2.ewallet.singleton.UserSingleton;
import com.socialv2.ewallet.utils.ParseHttpError;
import com.socialv2.ewallet.utils.UpperCaseOwnerName;
import com.socialv2.ewallet.utils.WindowUtils;

public class AddLinkingBankActivity extends BaseActivity {

    private final String TAG = AddLinkingBankActivity.class.getName();

    private IBankingResourceService mBankingResourceService;
    private IAccountService mAccountService;
    private BackdropLoadingDialogFragment mLoadingBackdropDialog;

    private HdWalletToolbar mToolbar;
    private EditText mAccountNoEditText;
    private EditText mOwnerEditText;
    private EditText mIdCardNoEditText;
    private Button mLinkAccountButton;
    private TextView mLinkingConditionTextView;
    private AskUserPinBottomSheet mAskUserPinBottomSheet;
    private BankDto mBank;

    public AddLinkingBankActivity() {
        super(R.layout.activity_add_linking_bank);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolbar = findViewById(R.id.toolbar);
        mAccountNoEditText = findViewById(R.id.accountNoEditText);
        mOwnerEditText = findViewById(R.id.ownerEditText);
        mIdCardNoEditText = findViewById(R.id.idCardNoEditText);
        mLinkAccountButton = findViewById(R.id.linkAccountButton);
        mLinkingConditionTextView = findViewById(R.id.linkingConditionTextView);

        mBankingResourceService = new BankingResourceHttpImpl(this);
        mAccountService = new AccountHttpImpl(this);

        mLoadingBackdropDialog = new BackdropLoadingDialogFragment();
        mLoadingBackdropDialog.setFragmentManager(getSupportFragmentManager());

        initView();
        getBin();
        getUserInfo();
        getIdCard();
    }

    private void initView() {
        mLoadingBackdropDialog.setLoading(false);
        WindowUtils.applyPadding(findViewById(R.id.main));
        mLinkAccountButton.setOnClickListener(view -> {
            getBottomSheet();
            mAskUserPinBottomSheet.show(getSupportFragmentManager(), AskUserPinBottomSheet.class.getName());
        });

        SpannableString spannable = new SpannableString(
                "Đã đăng ký dịch vụ Ngân hàng điện tử tại ngân hàng. Đăng ký ngay bằng thẻ ATM");
        String conditions = "• Đã đăng ký dịch vụ Ngân hàng điện tử tại ngân hàng. Đăng ký ngay bằng thẻ ATM\n" +
                "• Số dư tài khoản ngân hàng > 50.000đ\n" +
                "• Số điện thoại đăng ký tại Bản Việt phải là 070\n" +
                "• Số CMND/CCCD/Hộ chiếu trùng khớp với thông tin đã đăng ký tại ngân hàng";

        mLinkingConditionTextView.setText(conditions);
        mLinkingConditionTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @SuppressLint("CheckResult")
    private void getBin() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Bin")) {
            String bin = intent.getStringExtra("Bin");

            Log.d(TAG, "Bin value: " + bin);

            mBankingResourceService.getBankByBin(bin)
                    .subscribe(response -> {
                        mBank = response.getResult();
                        mToolbar.setTitle(mBank.getShortName());
                        Log.i(TAG, mBank.toString());
                    }, throwable -> {
                        throwable.printStackTrace();
                    });

            UserDto user = UserSingleton
                    .getInstance()
                    .getData()
                    .getValue();

            if (user != null) {

                mOwnerEditText.setText(UpperCaseOwnerName.apply(user.getFullName()));
                mIdCardNoEditText.setText(user.getIdCardNo());
            }
        }
    }

    private void getUserInfo() {

    }

    private void getIdCard() {

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

                onCompletePin(pin);
            });
        }
    }

    @SuppressLint("CheckResult")
    private void onCompletePin(String pin) {
        String bankAccountId = mAccountNoEditText
                .getText()
                .toString();

        String bankOwnerName = mOwnerEditText
                .getText()
                .toString();

        String idCardNo = mIdCardNoEditText
                .getText()
                .toString();
        if (bankAccountId.isEmpty() || idCardNo.isEmpty() || pin.isEmpty()) {
            return;
        }
        mAccountService
                .addLinkingAccount(pin, new RequestLinkingAccount(
                        mBank.getBin(),
                        bankAccountId,
                        idCardNo
                ))
                .subscribe(response -> {
                            mLoadingBackdropDialog.setLoading(false);
                            AccountBankDto bankAccount = response
                                    .getResult()
                                    .getAccountBank();

                            Intent intent = new Intent(AddLinkingBankActivity.this, LinkingBankSuccessfullyActivity.class);
                            String json = new Gson().toJson(bankAccount);

                            intent.putExtra("AccountBank-json", json);

                            startActivity(intent);
                            finish();
                        },
                        throwable -> {
                            mLoadingBackdropDialog.setLoading(false);


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

                                } else if (errMsg.equals("AccountBank not found")) {

                                } else if (errMsg.equals("Provided IdCard is not the same of bank account")) {

                                } else if (errMsg.equals("IdCardNo of account and yours is not the same")) {

                                } else if (errMsg.equals("Your bank account status is not active")) {

                                } else if (errMsg.equals("This account bank is already linked")) {

                                } else {

                                }
                            }


                        },
                        () -> {
                            mLoadingBackdropDialog.setLoading(false);
                        });
    }
}