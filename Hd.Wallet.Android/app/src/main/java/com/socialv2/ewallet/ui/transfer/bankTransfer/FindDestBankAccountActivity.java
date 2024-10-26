package com.socialv2.ewallet.ui.transfer.bankTransfer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.socialv2.ewallet.components.AskUserPinBottomSheet;
import com.socialv2.ewallet.dtos.CitizenAccountBankDto;
import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.https.api.bankHttp.BankingResourceHttpImpl;
import com.socialv2.ewallet.https.api.bankHttp.IBankingResourceService;
import com.socialv2.ewallet.ui.transfer.TransferMoneyActivity;
import com.socialv2.ewallet.utils.ParseHttpError;
import com.socialv2.ewallet.utils.UpperCaseOwnerName;
import com.socialv2.ewallet.utils.WindowUtils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class FindDestBankAccountActivity extends BaseActivity {

    private static final String TAG = FindDestBankAccountActivity.class.getName();

    private IBankingResourceService mBankingResourceService;

    private Button mContinueButton;
    private TextView mShortNameTextView;
    private TextView mOwnerNameTextView;
    private EditText mAccountBankNoEditText;
    private View mLoadingView;
    private View mResultView;
    private String mBin;
    private CitizenAccountBankDto mCitizenAccountBankDto;

    public FindDestBankAccountActivity() {
        super(R.layout.activity_find_dest_bank_account);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBankingResourceService = new BankingResourceHttpImpl(this);

        mContinueButton = findViewById(R.id.continueButton);
        mOwnerNameTextView = findViewById(R.id.ownerNameTextView);
        mShortNameTextView = findViewById(R.id.shortNameTextView);
        mAccountBankNoEditText = findViewById(R.id.accountBankNoEditText);
        mLoadingView = findViewById(R.id.loadingView);
        mResultView = findViewById(R.id.resultView);

        initView();
        getBin();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        mLoadingView.setVisibility(View.GONE);
        mResultView.setVisibility(View.GONE);
        mContinueButton.setEnabled(false);

        mAccountBankNoEditText.setEnabled(true);
        mAccountBankNoEditText.requestFocus();
        mAccountBankNoEditText.addTextChangedListener(new TextWatcher() {

            private Handler handler = new Handler();
            private Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    String accountBankNo = mAccountBankNoEditText
                            .getText()
                            .toString();
                    getDestBank(mBin, accountBankNo);
                }
            };

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String accountBankNo = mAccountBankNoEditText
                        .getText()
                        .toString();
                if (checkValidAccountNo(accountBankNo)) {
                    handler.postDelayed(runnable, 200);
                }
            }
        });

        mAccountBankNoEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mAccountBankNoEditText.clearFocus();
                    String accountBankNo = mAccountBankNoEditText
                            .getText()
                            .toString();
                    getDestBank(mBin, accountBankNo);
                }
                return false;
            }
        });

        mContinueButton.setOnClickListener(view -> {
            String json = new Gson()
                    .toJson(mCitizenAccountBankDto);

            Intent intent = new Intent(this, TransferMoneyActivity.class);

            intent.putExtra("TransferTo", "Bank");
            intent.putExtra("CitizenAccount", json);

            startActivity(intent);
            //finish();
        });
    }

    private void getBin() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Bin") && intent.hasExtra("ShortName")) {
            String shortName = intent.getStringExtra("ShortName");

            mBin = intent.getStringExtra("Bin");

            String title = "Bạn muốn chuyển khoản tới " + shortName;
            SpannableString spannable = new SpannableString(title);

            assert shortName != null;
            int start = title.indexOf(shortName);
            int end = start + shortName.length();
            int color = getResources().getColor(R.color.secondaryColor, getTheme());
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);

            spannable.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            mShortNameTextView.setText(spannable);
        }
    }

    private boolean checkValidAccountNo(String accountBankNo) {
        return Pattern.matches("^[1-9][0-9]{10}$", accountBankNo);
    }

    @SuppressLint("CheckResult")
    private void getDestBank(String bin, String accountBankNo) {
        Log.d(TAG, String.format("getDestBank - %s - %s", bin, accountBankNo));

        mAccountBankNoEditText.setEnabled(false);
        mAccountBankNoEditText.clearFocus();
        mLoadingView.setVisibility(View.VISIBLE);

        mBankingResourceService
                .getCitizenAccountBank(bin, accountBankNo)
                .subscribe(response -> {
                            mCitizenAccountBankDto = response.getResult();

                            Log.d(TAG, mCitizenAccountBankDto.toString());
                            mContinueButton.setEnabled(true);
                            mResultView.setVisibility(View.VISIBLE);
                            mLoadingView.setVisibility(View.GONE);
                            mAccountBankNoEditText.setEnabled(true);

                            mOwnerNameTextView.setText(UpperCaseOwnerName.apply(mCitizenAccountBankDto.getOwnerName()));
                        },
                        throwable -> {
                            mCitizenAccountBankDto = null;

                            mResultView.setVisibility(View.GONE);
                            mLoadingView.setVisibility(View.GONE);
                            mAccountBankNoEditText.setEnabled(true);
                            mAccountBankNoEditText.requestFocus();
                            mContinueButton.setEnabled(false);
                            int statusCode = ParseHttpError.getStatusCode(throwable);
                            if (statusCode == 400 || statusCode == 500) {
                                HttpResponseDto<?> errorBody = ParseHttpError.parse(throwable);
                                String errMsg = errorBody.getError();

                                Log.e(TAG, errMsg, throwable);
                                if (errMsg.equals("Citizen Account not found.")) {

                                }
                            } else {

                            }
                        });
    }
}