package com.socialv2.ewallet.ui.customAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.socialv2.ewallet.ui.fragment.fragmentHome.ChatFragment;
import com.socialv2.ewallet.ui.fragment.fragmentHome.EmptyFragment;
import com.socialv2.ewallet.ui.fragment.fragmentHome.HomeFragment;
import com.socialv2.ewallet.ui.fragment.fragmentHome.ProfileFragment;
import com.socialv2.ewallet.ui.fragment.fragmentHome.TransactionHistoryFragment;

public class FragmentMainHomeAdapter  extends FragmentStateAdapter {

    public FragmentMainHomeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new TransactionHistoryFragment();
            case 2:
                return new EmptyFragment();
            case 3:
                return new ChatFragment();
            case 4:
                return new ProfileFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
