package com.socialv2.ewallet.ui.home;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.home.tabs.HomeFragment;
import com.socialv2.ewallet.ui.home.tabs.ProfileFragment;
import com.socialv2.ewallet.ui.home.tabs.TransactionHistoryFragment;

public class MainHomeActivity extends BaseActivity {

    private BottomNavigationView mBottomNavigationView;
    private Toolbar mToolbar;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Boolean doubleBackToExitPressedOnce;


    private HomeFragment mHomeFragment;
    private TransactionHistoryFragment mTransactionHistoryFragment;
    private ProfileFragment mProfileFragment;


    public MainHomeActivity() {
        super(R.layout.activity_main_home);

        doubleBackToExitPressedOnce = true;
        mHomeFragment = new HomeFragment();
        mProfileFragment = new ProfileFragment();
        mTransactionHistoryFragment = new TransactionHistoryFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolbar = findViewById(R.id.toolbar);
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mFragmentManager = getSupportFragmentManager();

        initView();
    }

    private void initView() {
        navigateFragment(mHomeFragment);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeTab:
                        navigateFragment(mHomeFragment);
                        return true;
                    case R.id.transactionTab:
                        navigateFragment(mTransactionHistoryFragment);
                        return true;
                    case R.id.profileTab:
                        navigateFragment(mProfileFragment);
                        return true;
                }
                return false;
            }
        });
    }


    private void navigateFragment(Fragment fragment) {
        doubleBackToExitPressedOnce = (fragment == mHomeFragment);
        mFragmentTransaction = mFragmentManager.beginTransaction();

        String fragmentTag = fragment.getClass().getName();
        Fragment current = mFragmentManager.getPrimaryNavigationFragment();
        Fragment temp = mFragmentManager.findFragmentByTag(fragmentTag);

        if (current != null) {
            mFragmentTransaction.hide(current);
        }

        if (temp == null) {
            temp = fragment;
            mFragmentTransaction.add(R.id.fragmentContainer, temp, fragmentTag);
        } else mFragmentTransaction.show(temp);

        mFragmentTransaction.setPrimaryNavigationFragment(temp);
        mFragmentTransaction.setReorderingAllowed(true);
        mFragmentTransaction.commitNowAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        mBottomNavigationView.setSelectedItemId(R.id.homeTab);
        navigateFragment(mHomeFragment);
    }
}
