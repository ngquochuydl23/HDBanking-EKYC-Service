package com.socialv2.ewallet.ui.transfer;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.ui.main.homeTab.RecentlyTransferDestAdapter;
import com.socialv2.ewallet.ui.qr.QrTransferActivity;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

import java.util.ArrayList;
import java.util.List;

public class MenuTransferActivity extends BaseActivity {

    private RecyclerView mRecentlyTransferDestRecyclerView;
    private RecentlyTransferDestAdapter mRecentlyTransferDestAdapter;
    private View mQrTransferButton;
    private View mBankingTransferButton;
    private View mInternalTransferButton;

    private FindFriendBottomSheet mFindFriendBottomSheet;

    public MenuTransferActivity() {
        super(R.layout.activity_menu_transfer);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecentlyTransferDestAdapter = new RecentlyTransferDestAdapter(true);
        mFindFriendBottomSheet = new FindFriendBottomSheet();


        mInternalTransferButton = findViewById(R.id.internalTransferButton);
        mRecentlyTransferDestRecyclerView = findViewById(R.id.recentlyTransferDestRecyclerView);
        mQrTransferButton = findViewById(R.id.qrTransferButton);
        mBankingTransferButton = findViewById(R.id.bankingTransferButton);

        initView();
        getRecentlyTransferDest();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        mRecentlyTransferDestRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecentlyTransferDestRecyclerView.setAdapter(mRecentlyTransferDestAdapter);

        mInternalTransferButton.setOnClickListener(view -> {
            mFindFriendBottomSheet.show(getSupportFragmentManager(), mFindFriendBottomSheet.getTag());
        });

        mQrTransferButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, QrTransferActivity.class);
        });

        mBankingTransferButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, FindBankActivity.class);
        });


    }

    private void getRecentlyTransferDest() {
        List<AccountDto> accounts = new ArrayList<>();

        AccountDto bankAccount = new AccountDto();
        bankAccount.setBankLinking(true);


        AccountDto walletAccount = new AccountDto();
        walletAccount.setBankLinking(false);

        accounts.add(bankAccount);
        accounts.add(walletAccount);
        accounts.add(bankAccount);
        accounts.add(bankAccount);
        accounts.add(walletAccount);
        mRecentlyTransferDestAdapter.setItems(accounts);
    }
}