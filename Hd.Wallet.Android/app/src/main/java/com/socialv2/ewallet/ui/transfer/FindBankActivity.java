package com.socialv2.ewallet.ui.transfer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.banks.BankDto;
import com.socialv2.ewallet.https.api.bankHttp.BankingResourceHttpImpl;
import com.socialv2.ewallet.https.api.bankHttp.IBankingResourceService;
import com.socialv2.ewallet.ui.addCardOrAccount.AddLinkingBankActivity;
import com.socialv2.ewallet.utils.WindowUtils;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class FindBankActivity extends BaseActivity {

    private final String TAG = FindBankActivity.class.getName();

    private RecyclerView mBankRecyclerView;
    private SelectBankAdapter mSelectBankAdapter;
    private ProgressBar mProgressBarView;
    private EditText mSearchEditText;

    private IBankingResourceService mBankingResourceService;

    public FindBankActivity() {
        super(R.layout.activity_find_bank);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBankingResourceService = new BankingResourceHttpImpl(this);
        mSelectBankAdapter = new SelectBankAdapter();
        mSearchEditText = findViewById(R.id.searchEditText);
        mProgressBarView = findViewById(R.id.progressBarView);
        mBankRecyclerView = findViewById(R.id.bankRecyclerView);
        initView();
        getBanks(null);
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));

        mBankRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBankRecyclerView.setAdapter(mSelectBankAdapter);
        mSearchEditText.requestFocus();
        mSearchEditText.addTextChangedListener(new TextWatcher() {

            private Handler handler = new Handler();
            private Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    String search = mSearchEditText
                            .getText()
                            .toString();
                    Log.d(TAG, "User has stopped typing: " + search);
                    getBanks(search);
                }
            };

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                handler.postDelayed(runnable, 200);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void getBanks(String search) {
        mProgressBarView.setVisibility(View.VISIBLE);

        Observable<HttpResponseDto<List<BankDto>>> getBanksObservable;
        if (search == null || search.isEmpty()) {
            getBanksObservable = mBankingResourceService.getTopBanks();
        } else {
            getBanksObservable = mBankingResourceService.getBanks(search);
        }
        getBanksObservable
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