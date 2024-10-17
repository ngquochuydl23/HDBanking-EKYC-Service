package com.socialv2.ewallet.ui.addCardOrAccount;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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
import com.socialv2.ewallet.components.BackdropLoadingDialogFragment;
import com.socialv2.ewallet.components.HdWalletToolbar;
import com.socialv2.ewallet.dtos.accounts.AccountBankDto;
import com.socialv2.ewallet.dtos.accounts.RequestLinkingAccount;
import com.socialv2.ewallet.dtos.banks.BankDto;
import com.socialv2.ewallet.dtos.users.UserDto;
import com.socialv2.ewallet.https.api.accountHttp.AccountHttpImpl;
import com.socialv2.ewallet.https.api.accountHttp.IAccountService;
import com.socialv2.ewallet.https.api.bankHttp.BankingResourceHttpImpl;
import com.socialv2.ewallet.https.api.bankHttp.IBankingResourceService;
import com.socialv2.ewallet.singleton.UserSingleton;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

import java.text.Normalizer;
import java.util.regex.Pattern;

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
            addLinkingAccount();
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
                String ownerName = Normalizer.normalize(user.getFullName(), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String result = pattern.matcher(ownerName).replaceAll("");
                ownerName = result.toUpperCase();

                mOwnerEditText.setText(ownerName);
                mIdCardNoEditText.setText(user.getIdCardNo());
            }
        }
    }

    private void getUserInfo() {

    }

    private void getIdCard() {

    }

    @SuppressLint("CheckResult")
    private void addLinkingAccount() {
        String bankAccountId = mAccountNoEditText
                .getText()
                .toString();

        String bankOwnerName = mOwnerEditText
                .getText()
                .toString();

        String idCardNo = mIdCardNoEditText
                .getText()
                .toString();

        if (!bankAccountId.isEmpty() && !bankOwnerName.isEmpty() && !idCardNo.isEmpty()) {
            mLoadingBackdropDialog.setLoading(true);
            mAccountService
                    .addLinkingAccount(new RequestLinkingAccount(
                            mBank.getBin(),
                            bankAccountId,
                            bankOwnerName,
                            idCardNo
                    ))
                    .subscribe(response -> {
                                mLoadingBackdropDialog.setLoading(false);
                                AccountBankDto bankAccount = response
                                        .getResult()
                                        .getAccountBank();

                                Intent intent = new Intent(this, LinkingBankSuccessfullyActivity.class);
                                String json = new Gson().toJson(bankAccount);

                                intent.putExtra("AccountBank-json", json);

                                startActivity(intent);
                                finish();
                            },
                            throwable -> {
                                throwable.printStackTrace();
                                mLoadingBackdropDialog.setLoading(false);
                            },
                            () -> {
                                mLoadingBackdropDialog.setLoading(false);
                            });
        }
    }
}