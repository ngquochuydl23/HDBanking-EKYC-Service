package com.socialv2.ewallet.ui.main;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.users.UserDto;
import com.socialv2.ewallet.https.api.userHttp.IUserService;
import com.socialv2.ewallet.https.api.userHttp.UserHttpImpl;
import com.socialv2.ewallet.singleton.UserSingleton;
import com.socialv2.ewallet.ui.main.accountTab.AccountTabFragment;
import com.socialv2.ewallet.ui.main.homeTab.HomeFragment;
import com.socialv2.ewallet.ui.main.moreTab.ProfileFragment;
import com.socialv2.ewallet.ui.main.transactionTab.TransactionHistoryFragment;

public class MainHomeActivity extends BaseActivity {

    private static final String TAG = MainHomeActivity.class.getName();

    private BottomNavigationView mBottomNavigationView;
    private IUserService mUserService;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Boolean doubleBackToExitPressedOnce;


    private HomeFragment mHomeFragment;
    private AccountTabFragment mAccountFragment;
    private TransactionHistoryFragment mTransactionHistoryFragment;
    private ProfileFragment mProfileFragment;


    public MainHomeActivity() {
        super(R.layout.activity_main_home);

        doubleBackToExitPressedOnce = true;
        mHomeFragment = new HomeFragment();
        mAccountFragment = new AccountTabFragment();
        mProfileFragment = new ProfileFragment();
        mTransactionHistoryFragment = new TransactionHistoryFragment();


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserService = new UserHttpImpl(this);

        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mFragmentManager = getSupportFragmentManager();

        initView();
        getUserProfile();
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
                    case R.id.accountTab:
                        navigateFragment(mAccountFragment);
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

    @SuppressLint("CheckResult")
    private void getUserProfile() {
        mUserService
                .getUserInfo()
                .subscribe(response -> {
                    UserDto user = response.getResult();
                    UserSingleton
                            .getInstance()
                            .setData(user);

                    Log.i(TAG, user.toString());
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }
}
