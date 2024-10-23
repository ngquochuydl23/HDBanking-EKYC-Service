package com.socialv2.ewallet.ui.main.moreTab;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseFragment;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.dtos.MenuAppDto;
import com.socialv2.ewallet.singleton.UserSingleton;
import com.socialv2.ewallet.ui.nfcScan.IdCardNfcScanActivity;
import com.socialv2.ewallet.ui.profile.ProfileActivity;
import com.socialv2.ewallet.utils.NavigateUtil;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends BaseFragment {

    private static final String TAG = ProfileFragment.class.getName();

    private RecyclerView mMenuAppRecyclerView;
    private MenuAppAdapter mMenuAppAdapter;
    private Button mLogOutButton;
    private AvatarView mAvatarImageView;
    private TextView mFullnameTextView;
    private TextView mPhoneNumberTextView;
    private View mUserInfoContainerView;
    private View mProvideIdCardViaNFCView;
    private NfcAdapter mNFCAdapter;
    private Button mUpdateButton;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNFCAdapter = NfcAdapter.getDefaultAdapter(getContext());
        mMenuAppAdapter = new MenuAppAdapter();

        mUserInfoContainerView = view.findViewById(R.id.userInfoContainerView);
        mMenuAppRecyclerView = view.findViewById(R.id.menuAppRecyclerView);
        mLogOutButton = view.findViewById(R.id.logOutButton);
        mAvatarImageView = view.findViewById(R.id.avatarImageView);
        mFullnameTextView = view.findViewById(R.id.fullnameTextView);
        mPhoneNumberTextView = view.findViewById(R.id.phoneNumberTextView);
        mProvideIdCardViaNFCView = view.findViewById(R.id.provideIdCardViaNFCView);
        mUpdateButton = view.findViewById(R.id.updateButton);


        initView();
        checkNFCSupport();
        observeUserData();
    }

    private void initView() {
        List<MenuAppDto> menuAppItems = new ArrayList<>();
        menuAppItems.add(new MenuAppDto("Tài khoản", "Xác minh", R.drawable.icon_profile_unactive, 0, null));
        menuAppItems.add(new MenuAppDto("Quyền riêng tư", null, R.drawable.ic_privacy, 0, null));
        menuAppItems.add(new MenuAppDto("Thông báo", null, R.drawable.ic_menu_notification, 0, null));
        menuAppItems.add(new MenuAppDto("Ngôn ngữ", "Hệ thống mặc định", R.drawable.ic_language, 0, null));

        mMenuAppRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMenuAppRecyclerView.setAdapter(mMenuAppAdapter);
        mMenuAppAdapter.setItems(menuAppItems);
        mUserInfoContainerView.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getContext(), ProfileActivity.class);
        });

        mLogOutButton.setOnClickListener(view -> {

        });

        mUpdateButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getContext(), IdCardNfcScanActivity.class);
        });
    }

    private void observeUserData() {
        UserSingleton.getInstance()
                .getData()
                .observe(getViewLifecycleOwner(), user -> {

                    mAvatarImageView.setSrcWithGender(user.getAvatar(), true);
                    mFullnameTextView.setText(user.getFullName());
                    mPhoneNumberTextView.setText(user.getPhoneNumber());
                });
    }

    private void checkNFCSupport() {
        if (mNFCAdapter == null) {
            Log.w(TAG, "NFS is not supported in this device.");

            //mProvideIdCardViaNFCView.setVisibility(View.GONE);
            return;
        }

        Log.i(TAG, "NFS is supported in this device.");
        mProvideIdCardViaNFCView.setVisibility(View.VISIBLE);
    }
}
