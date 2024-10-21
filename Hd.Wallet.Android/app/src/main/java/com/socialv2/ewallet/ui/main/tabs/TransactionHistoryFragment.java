package com.socialv2.ewallet.ui.main.tabs;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.socialv2.ewallet.BaseFragment;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.main.tabs.tabLayoutHistory.TabarHistoryViewPager;
import com.socialv2.ewallet.ui.statistic.StatisticActivity;
import com.socialv2.ewallet.utils.NavigateUtil;

public class TransactionHistoryFragment extends BaseFragment {

    private TabLayout mHistoryTabLayout;
    private View mStatisticButton;
    public TransactionHistoryFragment() {
        super(R.layout.fragment_historytrans);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHistoryTabLayout = view.findViewById(R.id.historyTabLayout);
        mStatisticButton = view.findViewById(R.id.statisticButton);
        initView();
    }

    private void initView() {

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
}
