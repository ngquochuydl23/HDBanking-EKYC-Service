package com.socialv2.ewallet.ui.addCardOrAccount;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.https.api.bankHttp.BankingResourceHttpImpl;
import com.socialv2.ewallet.https.api.bankHttp.IBankingResourceService;
import com.socialv2.ewallet.utils.BankUtils;
import com.socialv2.ewallet.utils.WindowUtils;

import java.util.List;

public class AddCardOrAccountActivity extends BaseActivity {

    private final String TAG = AddCardOrAccountActivity.class.getName();

    private ProgressBar mProgressBarView;
    private IBankingResourceService mBankingResourceService;
    private BankAppAdapter mBankAppAdapter;
    private RecyclerView mBankAppRecyclerView;

    public AddCardOrAccountActivity() {
        super(R.layout.activity_add_card_or_account);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBankingResourceService = new BankingResourceHttpImpl(this);

        mBankAppAdapter = new BankAppAdapter();
        mBankAppRecyclerView = findViewById(R.id.bankAppRecyclerView);
        mProgressBarView = findViewById(R.id.progressBarView);
        initView();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        mProgressBarView.setVisibility(View.GONE);
        mBankAppRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBankAppRecyclerView.setAdapter(mBankAppAdapter);
    }

    @SuppressLint("CheckResult")
    private void getInstallBanks() {
        mProgressBarView.setVisibility(View.VISIBLE);
        mBankingResourceService
                .getBanks()
                .subscribe(response -> {
                    mBankAppAdapter.setItems(response.getResult());
                    Log.i(TAG, response.getResult().toString());
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {
                    mProgressBarView.setVisibility(View.GONE);
                });


        //mBankAppAdapter.setItems(bankApps);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInstallBanks();
    }
}