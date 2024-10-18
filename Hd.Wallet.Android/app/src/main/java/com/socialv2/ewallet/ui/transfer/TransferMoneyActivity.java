package com.socialv2.ewallet.ui.transfer;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

public class TransferMoneyActivity extends BaseActivity {

    private final String TAG = TransferMoneyActivity.class.getName();

    private Button mTransferButton;

    public TransferMoneyActivity() {
        super(R.layout.activity_transfer_money);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTransferButton = findViewById(R.id.transferButton);


        initView();

        getAccountResult();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        mTransferButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, SuccessfulTransactionActivity.class);
        });
    }

    private void getAccountResult() {
        String json = "{ \"id\": \"ffeba890-30cb-49ee-9dda-a2209d750c10\", \"userId\": \"9565dc9b-0af9-4793-81e4-8f3615612785\", \"isBankLinking\": false, \"walletBalance\": 1000000, \"linkedAccountId\": null, \"transactionLimit\": 10000000, \"accountType\": 0, \"accountBank\": { \"bankName\": \"HD_WALLET_MBBANK\", \"bankAccountId\": \"0868684961\", \"bankOwnerName\": \"NGUYEN QUOC HUY\", \"idCardNo\": \"068203012123\" }, \"user\": null, \"isBlocked\": false, \"isUnlinked\": false, \"isDeleted\": false, \"createdAt\": \"2024-09-30T14:36:32.792995\", \"lastUpdated\": \"0001-01-01T00:00:00\" }";
        AccountDto account = new Gson().fromJson(json, AccountDto.class);

        Log.i(TAG, account.toString());
    }


}