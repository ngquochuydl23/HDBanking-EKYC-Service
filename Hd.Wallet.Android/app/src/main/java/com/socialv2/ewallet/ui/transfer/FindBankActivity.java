package com.socialv2.ewallet.ui.transfer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.https.api.bankHttp.BankingResourceHttpImpl;
import com.socialv2.ewallet.https.api.bankHttp.IBankingResourceService;
import com.socialv2.ewallet.ui.addCardOrAccount.AddLinkingBankActivity;
import com.socialv2.ewallet.utils.WindowUtils;

public class FindBankActivity extends BaseActivity {

    private final String TAG = FindBankActivity.class.getName();

    private RecyclerView mBankRecyclerView;
    private SelectBankAdapter mSelectBankAdapter;
    private ProgressBar mProgressBarView;

    private IBankingResourceService mBankingResourceService;

    public FindBankActivity() {
        super(R.layout.activity_find_bank);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBankingResourceService = new BankingResourceHttpImpl(this);
        mSelectBankAdapter = new SelectBankAdapter();

        mProgressBarView = findViewById(R.id.progressBarView);
        mBankRecyclerView = findViewById(R.id.bankRecyclerView);
        initView();
        getBanks();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));

        mBankRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBankRecyclerView.setAdapter(mSelectBankAdapter);
    }

    @SuppressLint("CheckResult")
    private void getBanks() {
        mProgressBarView.setVisibility(View.VISIBLE);
        mBankingResourceService
                .getTopBanks()
                .subscribe(response -> {
                    mSelectBankAdapter.setItems(response.getResult());
                    Log.i(TAG, response.getResult().toString());
                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {
                    mProgressBarView.setVisibility(View.GONE);
                });
    }
}