package com.socialv2.ewallet.ui.customAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.socialv2.ewallet.ui.fragment.fragmentHome.HomeFragment;
import com.socialv2.ewallet.ui.fragment.fragmentHome.TransactionHistoryFragment;
import com.socialv2.ewallet.ui.fragment.fragmentQR.GenQRFragment;
import com.socialv2.ewallet.ui.fragment.fragmentQR.ScanQRFragment;

public class FragmentQrAdapter extends FragmentStateAdapter {

    public FragmentQrAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new GenQRFragment();
            case 1:
                return new ScanQRFragment();
            default:
                return new GenQRFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
