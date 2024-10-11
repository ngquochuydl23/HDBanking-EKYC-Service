package com.socialv2.ewallet.ui.main.moreTab;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseFragment;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.MenuAppDto;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends BaseFragment {

    private RecyclerView mMenuAppRecyclerView;
    private MenuAppAdapter mMenuAppAdapter;
    private Button mLogOutButton;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMenuAppAdapter = new MenuAppAdapter();

        mMenuAppRecyclerView = view.findViewById(R.id.menuAppRecyclerView);
        mLogOutButton = view.findViewById(R.id.logOutButton);

        initView();
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
//        mUserInfoContainerView.setOnClickListener(view -> {
//            NavigateUtil.navigateTo(getContext(), ProfileActivity.class);
//        });

        mLogOutButton.setOnClickListener(view -> {

        });
    }


}
