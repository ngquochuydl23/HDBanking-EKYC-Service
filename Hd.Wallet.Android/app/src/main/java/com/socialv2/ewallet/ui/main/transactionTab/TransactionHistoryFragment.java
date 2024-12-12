package com.socialv2.ewallet.ui.main.transactionTab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.socialv2.ewallet.BaseFragment;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.transactions.TransactionDto;
import com.socialv2.ewallet.https.api.transactionHttp.IHttpTransaction;
import com.socialv2.ewallet.https.api.transactionHttp.ITransactionService;
import com.socialv2.ewallet.https.api.transactionHttp.TransactionHttpImpl;
import com.socialv2.ewallet.ui.statistic.StatisticActivity;
import com.socialv2.ewallet.utils.NavigateUtil;

import java.util.List;

public class TransactionHistoryFragment extends BaseFragment {

    private ITransactionService mTransactionService;

    private TabLayout mHistoryTabLayout;
    private View mStatisticButton;
    private RecyclerView mTransactionRecyclerView;


    private TransactionAdapter mTransactionAdapter;

    public TransactionHistoryFragment() {
        super(R.layout.fragment_historytrans);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTransactionService = new TransactionHttpImpl(getContext());
        mTransactionAdapter = new TransactionAdapter();

        mHistoryTabLayout = view.findViewById(R.id.historyTabLayout);
        mStatisticButton = view.findViewById(R.id.statisticButton);
        mTransactionRecyclerView = view.findViewById(R.id.transactionRecyclerView);

        initView();
        getTransactions(null, null, null, null, null);
    }

    private void initView() {
        mTransactionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTransactionRecyclerView.setAdapter(mTransactionAdapter);


        mStatisticButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getContext(), StatisticActivity.class);
        });

        mHistoryTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @SuppressLint("CheckResult")
    private void getTransactions(String transactionType,
                                 String transactionStatus,
                                 String transactionDateMin,
                                 String transactionDateMax,
                                 Double amountIn) {
        mTransactionService.getTransactions(
                        transactionType,
                        transactionStatus,
                        transactionDateMin,
                        transactionDateMax,
                        amountIn,
                        5,
                        0
                )
                .subscribe(response -> {
                    mTransactionAdapter.setItems(response.getResult());
                    //groupTransactionsByMonth(response.getResult());
                }, throwable -> {
                }, () -> {

                });
    }

    private List<Object> groupTransactionsByMonth(List<TransactionDto> transactions) {
        return null;
    }
}
