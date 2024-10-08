package com.socialv2.ewallet.ui.main.tabs.tabLayoutHistory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabarHistoryViewPager extends FragmentStatePagerAdapter {
    public TabarHistoryViewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HistoryTransFilterAllFragment();
            case 1:
                return new HistoryTransFilterDepositFragment();
            case 2:
                return new HistoryTransFilterTransferMoneyFragment();
            case 3:
                return new HistoryTransFilterWithdrawMoneyFragment();
            case 4:
                return new HistoryTransFilterReceiveMoneyFragment();
            default:
                return new HistoryTransFilterAllFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";

        switch (position) {
            case 0:
                title = "Tất cả";
                break;  // Thêm break
            case 1:
                title = "Nạp tiền";
                break;  // Thêm break
            case 2:
                title = "Chuyển tiền";
                break;  // Thêm break
            case 3:
                title = "Rút tiền";  // Sửa tên tab cho đúng
                break;  // Thêm break
            case 4:
                title = "Nhận tiền";
                break;  // Thêm break
        }
        return title;
    }
}
