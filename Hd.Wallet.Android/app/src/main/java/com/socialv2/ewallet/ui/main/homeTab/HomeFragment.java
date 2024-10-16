package com.socialv2.ewallet.ui.main.homeTab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.socialv2.ewallet.BaseFragment;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.users.UserDto;
import com.socialv2.ewallet.ui.contacts.ContactActivity;
import com.socialv2.ewallet.ui.main.NotificationActivity;
import com.socialv2.ewallet.ui.profile.ProfileActivity;
import com.socialv2.ewallet.ui.qr.MyQrActivity;
import com.socialv2.ewallet.ui.qr.QrTransferActivity;
import com.socialv2.ewallet.ui.transfer.TransferMoneyActivity;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.VietnameseConcurrency;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private View mHomeTabToolbarContainer;
    private View mFundButton;
    private View mTransferButton;
    private View mWithdrawalButton;
    private View mQrButton;
    private View mAvatarView;
    private TextView mBalanceTextView;
    private Toolbar mHomeTabToolbar;
    private View mToggleVisibilityButton;
    private RecyclerView mRecentlyContactRecyclerView;
    private ContactRecentlyAdapter mContactRecentlyAdapter;
    private View mSeeMoreContactButton;

    private boolean balanceVisible;
    private double balance;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContactRecentlyAdapter = new ContactRecentlyAdapter();

        mHomeTabToolbarContainer = view.findViewById(R.id.homeTabToolbarContainer);
        mAvatarView = view.findViewById(R.id.avatarView);
        mFundButton = view.findViewById(R.id.fundButton);
        mTransferButton = view.findViewById(R.id.transferButton);
        mWithdrawalButton = view.findViewById(R.id.withdrawalButton);
        mBalanceTextView = view.findViewById(R.id.balanceTextView);
        mHomeTabToolbar = view.findViewById(R.id.homeTabToolbar);
        mToggleVisibilityButton = view.findViewById(R.id.toggleVisibilityButton);
        mRecentlyContactRecyclerView = view.findViewById(R.id.recentlyContactRecyclerView);
        mSeeMoreContactButton = view.findViewById(R.id.seeMoreContactButton);
        mQrButton = view.findViewById(R.id.qrButton);

        balanceVisible = false;

        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        balanceVisible = false;

        getBalance();
        getRecentlyContacts();
    }

    private void initView() {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mHomeTabToolbar);
        getActivity().setTitle("");

        mRecentlyContactRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecentlyContactRecyclerView.setAdapter(mContactRecentlyAdapter);

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
            mBalanceTextView.setText(balanceVisible ? VietnameseConcurrency.format(balance) : "*** **");
        });

        mAvatarView.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getContext(), ProfileActivity.class);
        });

        mSeeMoreContactButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getContext(), ContactActivity.class);
        });

        mQrButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getContext(), QrTransferActivity.class);
        });
    }

    private void getBalance() {

        balance = 1000000;
        mBalanceTextView.setText(VietnameseConcurrency.format(balance));
        mBalanceTextView.setText("*** **");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_hometab_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification:
                NavigateUtil.navigateTo(getContext(), NotificationActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getRecentlyContacts() {
        List<UserDto> contacts = new ArrayList<UserDto>();
        contacts.add(new UserDto());
        contacts.add(new UserDto());
        contacts.add(new UserDto());
        contacts.add(new UserDto());
        contacts.add(new UserDto());
        contacts.add(new UserDto());
        contacts.add(new UserDto());

        mContactRecentlyAdapter.setItems(contacts);
    }
}
