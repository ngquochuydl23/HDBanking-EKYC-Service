package com.socialv2.ewallet.ui.home.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.socialv2.ewallet.BaseFragment;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.home.tabs.tabLayoutHistory.TabarHistoryViewPager;

public class TransactionHistoryFragment extends BaseFragment {

    private TabLayout mTabLayoutHistory;
    private ViewPager mViewPager;

    public TransactionHistoryFragment() {
        super(R.layout.fragment_historytrans);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo TabLayout và ViewPager
        mTabLayoutHistory = view.findViewById(R.id.tab_layoutHistory);
        mViewPager = view.findViewById(R.id.viewpagerHistory);

        // Thiết lập ViewPager với Adapter
        TabarHistoryViewPager tabarHistoryViewPager = new TabarHistoryViewPager(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(tabarHistoryViewPager);

        // Liên kết TabLayout với ViewPager
        mTabLayoutHistory.setupWithViewPager(mViewPager);
    }
}
