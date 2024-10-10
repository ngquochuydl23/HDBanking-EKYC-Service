package com.socialv2.ewallet.ui.addCardOrAccount;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

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
import com.socialv2.ewallet.dtos.banks.BankAppDto;
import com.socialv2.ewallet.utils.BankUtils;
import com.socialv2.ewallet.utils.WindowUtils;

import java.util.List;

public class AddCardOrAccountActivity extends BaseActivity {

    private final String TAG = AddCardOrAccountActivity.class.getName();

    private BankAppAdapter mBankAppAdapter;
    private RecyclerView mBankAppRecyclerView;

    public AddCardOrAccountActivity() {
        super(R.layout.activity_add_card_or_account);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBankAppAdapter = new BankAppAdapter();
        mBankAppRecyclerView = findViewById(R.id.bankAppRecyclerView);
        initView();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));

        mBankAppRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBankAppRecyclerView.setAdapter(mBankAppAdapter);
    }

    private void getInstallBanks() {
        BankUtils bankUtils = new BankUtils(this);
        List<BankAppDto> bankApps = bankUtils.getOtherBankInstalled();

        mBankAppAdapter.setItems(bankApps);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInstallBanks();
    }
}