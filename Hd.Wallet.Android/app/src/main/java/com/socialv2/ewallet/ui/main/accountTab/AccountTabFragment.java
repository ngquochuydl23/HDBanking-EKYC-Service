package com.socialv2.ewallet.ui.main.accountTab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.socialv2.ewallet.BaseFragment;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.ui.addCardOrAccount.AddCardOrAccountActivity;
import com.socialv2.ewallet.utils.NavigateUtil;

import java.util.ArrayList;
import java.util.List;


public class AccountTabFragment extends BaseFragment {

    private RecyclerView mAccountCardRecyclerView;
    private AccountCardAdapter mAccountCardAdapter;
    private View mAddLinkingAccountButton;

    public AccountTabFragment() {
        super(R.layout.fragment_account_tab);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAccountCardAdapter = new AccountCardAdapter();

        mAccountCardRecyclerView = view.findViewById(R.id.accountCardRecyclerView);
        mAddLinkingAccountButton = view.findViewById(R.id.addLinkingAccountButton);

        initView();
        getAccounts();
    }

    private void initView() {
        mAccountCardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAccountCardRecyclerView.setAdapter(mAccountCardAdapter);

        mAddLinkingAccountButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getContext(), AddCardOrAccountActivity.class);
        });
    }

    private void getAccounts() {
        List<AccountDto> accounts = new ArrayList<>();
        accounts.add(new AccountDto());
        accounts.add(new AccountDto());
        accounts.add(new AccountDto());

        mAccountCardAdapter.setItems(accounts);
    }
}