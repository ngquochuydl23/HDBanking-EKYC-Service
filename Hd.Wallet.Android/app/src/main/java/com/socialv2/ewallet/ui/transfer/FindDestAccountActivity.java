package com.socialv2.ewallet.ui.transfer;

import android.os.Bundle;

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
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.ui.main.homeTab.RecentlyTransferDestAdapter;
import com.socialv2.ewallet.utils.WindowUtils;

import java.util.ArrayList;
import java.util.List;

public class FindDestAccountActivity extends BaseActivity {

    private RecyclerView mRecentlyTransferDestRecyclerView;
    private RecentlyTransferDestAdapter mRecentlyTransferDestAdapter;

    public FindDestAccountActivity() {
        super(R.layout.activity_find_dest_account);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecentlyTransferDestRecyclerView = findViewById(R.id.recentlyTransferDestRecyclerView);
        mRecentlyTransferDestAdapter = new RecentlyTransferDestAdapter(true);
        initView();
        getRecentlyTransferDest();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        mRecentlyTransferDestRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecentlyTransferDestRecyclerView.setAdapter(mRecentlyTransferDestAdapter);
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