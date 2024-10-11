package com.socialv2.ewallet.ui.qr;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;

public class QrMenuActivity extends BaseActivity {

    private BottomNavigationView mBottomNavigationQrView;
    private ViewPager2 mChildTabQrViewPager;


    public QrMenuActivity() {
        super(R.layout.activity_qr_acitvity);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mChildTabQrViewPager = findViewById(R.id.childTabQrViewPager);
        mBottomNavigationQrView = findViewById(R.id.qrBottomNavigationView);
        mChildTabQrViewPager.setAdapter(new QRPagerAdapter(this));
        mBottomNavigationQrView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_genqr) {
                    mChildTabQrViewPager.setCurrentItem(0);
                } else if (id == R.id.bottom_scanqr) {
                    mChildTabQrViewPager.setCurrentItem(1);
                }
                return true;
            }
        });

        mChildTabQrViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        mBottomNavigationQrView.getMenu().findItem(R.id.bottom_genqr).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationQrView.getMenu().findItem(R.id.bottom_scanqr).setChecked(true);
                        break;
                }
            }
        });
    }
}