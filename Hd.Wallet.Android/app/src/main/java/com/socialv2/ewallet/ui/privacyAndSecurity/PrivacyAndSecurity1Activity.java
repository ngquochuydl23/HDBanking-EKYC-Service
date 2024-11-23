package com.socialv2.ewallet.ui.privacyAndSecurity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.MenuAppDto;
import com.socialv2.ewallet.ui.dev.WriteNfcActivity;
import com.socialv2.ewallet.ui.main.moreTab.MenuAppAdapter;
import com.socialv2.ewallet.ui.settings.notifications.NotificationSettingActivity;

import java.util.ArrayList;
import java.util.List;

public class PrivacyAndSecurity1Activity extends AppCompatActivity {
    private RecyclerView mMenuAppRecyclerView;
    private MenuAppAdapter mMenuAppAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_and_security1);
        EdgeToEdge.enable(this);
        mMenuAppAdapter = new MenuAppAdapter();
        mMenuAppRecyclerView = findViewById(R.id.menuAppRecyclerView);
        initView();
    }

    private void initView() {
        List<MenuAppDto> menuAppItems = new ArrayList<>();
        menuAppItems.add(new MenuAppDto("Đổi mật khẩu", null, R.drawable.icon_profile_unactive, 0, ChangePasswordActivity.class));
        menuAppItems.add(new MenuAppDto("Quên mã pin", null, R.drawable.ic_privacy, 0, ChangePincodeActivity.class));

        mMenuAppRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMenuAppRecyclerView.setAdapter(mMenuAppAdapter);
        mMenuAppAdapter.setItems(menuAppItems);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}