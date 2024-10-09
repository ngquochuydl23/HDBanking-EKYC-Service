package com.socialv2.ewallet.ui.main.homeTab;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.socialv2.ewallet.BaseFragment;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.profile.ProfileActivity;
import com.socialv2.ewallet.ui.transfer.TransferMoneyActivity;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.VietnameseConcurrency;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class HomeFragment extends BaseFragment {

    private View mHomeTabToolbarContainer;
    private View mFundButton;
    private View mTransferButton;
    private View mWithdrawalButton;
    private View mAvatarView;
    private TextView mBalanceTextView;
    private Toolbar mHomeTabToolbar;
    private View mToggleVisibilityButton;

    private boolean balanceVisible;
    private double balance;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHomeTabToolbarContainer = view.findViewById(R.id.homeTabToolbarContainer);
        mAvatarView = view.findViewById(R.id.avatarView);
        mFundButton = view.findViewById(R.id.fundButton);
        mTransferButton = view.findViewById(R.id.transferButton);
        mWithdrawalButton = view.findViewById(R.id.withdrawalButton);
        mBalanceTextView = view.findViewById(R.id.balanceTextView);
        mHomeTabToolbar = view.findViewById(R.id.homeTabToolbar);
        mToggleVisibilityButton = view.findViewById(R.id.toggleVisibilityButton);

        balanceVisible = false;

        initView();
    }

    @Override
    public void onResume() {
        super.onResume();

        getBalance();
        balanceVisible = false;
    }

    private void initView() {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mHomeTabToolbar);
        getActivity().setTitle("");

        mToggleVisibilityButton.setBackgroundResource(R.drawable.ic_visibility_off_balance);

        int statusBarHeight = getStatusBarHeight();

        // Set the top margin to be equal to the status bar height
        if (mHomeTabToolbarContainer != null) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mHomeTabToolbarContainer.getLayoutParams();
            params.setMargins(params.leftMargin, statusBarHeight, params.rightMargin, params.bottomMargin);
            mHomeTabToolbarContainer.setLayoutParams(params);
        }

        mFundButton.setOnClickListener(view -> {

        });

        mTransferButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getContext(), TransferMoneyActivity.class);
        });

        mToggleVisibilityButton.setOnClickListener(view -> {
            balanceVisible = !balanceVisible;

            mToggleVisibilityButton.setBackgroundResource(balanceVisible ? R.drawable.ic_invisibility_off_balance: R.drawable.ic_visibility_off_balance);
            mBalanceTextView.setText(balanceVisible ? VietnameseConcurrency.format(100000) : "*** **");
        });

        mFundButton.setOnClickListener(view -> {
        });

        mAvatarView.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getContext(), ProfileActivity.class);
        });
    }

    private void getBalance() {


        mBalanceTextView.setText(VietnameseConcurrency.format(1000000));
        mBalanceTextView.setText("*** **");
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.your_menu_xml, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                // Handle settings action
//                return true;
//            case R.id.action_search:
//                // Handle search action
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
