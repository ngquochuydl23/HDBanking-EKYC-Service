package com.socialv2.ewallet.ui.mainHome;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.customAdapter.FragmentMainHomeAdapter;

public class MainHomeActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private ViewPager2 mViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_home);

        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mViewPager2 = findViewById(R.id.view_pager_2);

        FragmentMainHomeAdapter fragmentMainHomeAdapter = new FragmentMainHomeAdapter(this);
        mViewPager2.setAdapter(fragmentMainHomeAdapter);

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_home) {
                    mViewPager2.setCurrentItem(0);
                } else if (id == R.id.bottom_transactionhistory) {
                    mViewPager2.setCurrentItem(1);
                } else if (id == R.id.bottom_scanqr) {
                    startActivity(new Intent(MainHomeActivity.this, QRPaymentActivity.class));
                } else if (id == R.id.bottom_chat) {
                    mViewPager2.setCurrentItem(3);
                } else if (id == R.id.bottom_profile) {
                    mViewPager2.setCurrentItem(4);
                }
                return true;
            }
        });

        initview();
    }

    private void initview() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            mBottomNavigationView.setPadding(0, 0, 0, systemBars.bottom);
            mViewPager2.setPadding(0, 0, 0, systemBars.bottom);
            return insets;
        });

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.bottom_transactionhistory).setChecked(true);
                        break;
                    case 2:

                        mBottomNavigationView.getMenu().findItem(R.id.bottom_scanqr).setChecked(true);
                        break;
                    case 3:
                        mBottomNavigationView.getMenu().findItem(R.id.bottom_chat).setChecked(true);
                        break;
                    case 4:
                        mBottomNavigationView.getMenu().findItem(R.id.bottom_profile).setChecked(true);
                        break;
                }
            }
        });
    }
}