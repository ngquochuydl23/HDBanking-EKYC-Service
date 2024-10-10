package com.socialv2.ewallet.ui.qr;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QRPagerAdapter extends FragmentStateAdapter {

    public QRPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new GenQRFragment(); // Fragment để tạo mã QR
            case 1:
                return new ScanQRFragment(); // Fragment để quét mã QR
            default:
                return new GenQRFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

