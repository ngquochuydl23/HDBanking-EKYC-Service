package com.socialv2.ewallet.ui.transfer;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.transactions.TransactionAccountBankDto;
import com.socialv2.ewallet.dtos.transactions.TransactionDto;
import com.socialv2.ewallet.utils.DateFormatter;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.VietnameseConcurrency;
import com.socialv2.ewallet.utils.WindowUtils;

public class SuccessfulTransactionActivity extends BaseActivity {


    private static final String TAG = SuccessfulTransactionActivity.class.getName();

    private Button mCompleteButton;
    private TextView mAmountTextView;
    private TextView mDestOwnerNameTextView;
    private TextView mDestBankNameTextView;
    private TextView mDestAccountNoTextView;
    private TextView mTransferContentTextView;
    private TextView mTransactionIdTextView;
    private TextView mTransactionDateTextView;

    private LottieAnimationView mLottieAnimationView;

    public SuccessfulTransactionActivity() {
        super(R.layout.activity_successful_transaction);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCompleteButton = findViewById(R.id.completeButton);
        mAmountTextView = findViewById(R.id.amountTextView);
        mDestOwnerNameTextView = findViewById(R.id.destOwnerNameTextView);
        mDestBankNameTextView = findViewById(R.id.destBankNameTextView);
        mDestAccountNoTextView = findViewById(R.id.destAccountNoTextView);
        mTransferContentTextView = findViewById(R.id.transferContentTextView);
        mTransactionIdTextView = findViewById(R.id.transactionIdTextView);
        mTransactionDateTextView = findViewById(R.id.transactionDateTextView);
        mLottieAnimationView = findViewById(R.id.lottieAnimationView);

        initView();
        getResultTransfer();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));

        mCompleteButton.setOnClickListener(view -> {
            NavigateUtil.navigateToHomeClearState(this);
        });
    }

    private void getResultTransfer() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TransactionResult")) {
            String json = intent.getStringExtra("TransactionResult");
            TransactionDto transactionResult = new Gson()
                    .fromJson(json, TransactionDto.class);
            TransactionAccountBankDto destAccountBank = transactionResult.getDestAccount();

            if (transactionResult == null) {
                Log.w(TAG, "transactionResult is null");
                return;
            }

            mAmountTextView.setText(VietnameseConcurrency.formatWithoutSymbol(transactionResult.getAmount()));
            mDestOwnerNameTextView.setText("Tới " + destAccountBank.getOwnerName());
            mDestBankNameTextView.setText(destAccountBank.getBankName());
            mDestAccountNoTextView.setText(destAccountBank.getAccountNo());
            mTransferContentTextView.setText(transactionResult.getTransferContent());
            mTransactionIdTextView.setText(transactionResult.getId());
            mTransactionDateTextView.setText(DateFormatter.formatToVietnameseDate(transactionResult.getTransactionDate()));

        }
    }
}