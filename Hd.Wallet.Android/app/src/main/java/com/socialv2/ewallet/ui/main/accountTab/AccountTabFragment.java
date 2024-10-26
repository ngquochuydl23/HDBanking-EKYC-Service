package com.socialv2.ewallet.ui.main.accountTab;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.socialv2.ewallet.BaseFragment;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.https.api.accountHttp.AccountHttpImpl;
import com.socialv2.ewallet.https.api.accountHttp.IAccountService;
import com.socialv2.ewallet.https.api.transferHttp.TransferHttpImpl;
import com.socialv2.ewallet.https.api.transferHttp.ITransferService;
import com.socialv2.ewallet.ui.addCardOrAccount.AddCardOrAccountActivity;
import com.socialv2.ewallet.utils.NavigateUtil;


public class AccountTabFragment extends BaseFragment {

    private final String TAG = AccountTabFragment.class.getName();

    private RecyclerView mAccountCardRecyclerView;
    private AccountCardAdapter mInstalledCardAdapter;
    private View mAddLinkingAccountButton;
    private IAccountService mAccountService;

    public AccountTabFragment() {
        super(R.layout.fragment_account_tab);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAccountService = new AccountHttpImpl(getContext());
        mInstalledCardAdapter = new AccountCardAdapter();

        mAccountCardRecyclerView = view.findViewById(R.id.accountCardRecyclerView);
        mAddLinkingAccountButton = view.findViewById(R.id.addLinkingAccountButton);

        initView();
        ///getAccounts();
    }

    private void initView() {
        mAccountCardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAccountCardRecyclerView.setAdapter(mInstalledCardAdapter);

        mAddLinkingAccountButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getContext(), AddCardOrAccountActivity.class);
        });
    }

    @SuppressLint("CheckResult")
    private void getAccounts() {

        mAccountService
                .getAccounts()
                .subscribe(response -> {
                    Log.i(TAG, response.toString());
                    mInstalledCardAdapter.setItems(response.getResult());
                },throwable -> {
                    throwable.printStackTrace();
                },() -> {

                });

    }

    @Override
    public void onResume() {
        super.onResume();
        getAccounts();
    }
}