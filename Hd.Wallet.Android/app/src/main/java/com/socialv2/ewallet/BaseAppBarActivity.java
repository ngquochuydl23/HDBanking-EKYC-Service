package com.socialv2.ewallet;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.socialv2.ewallet.components.HdWalletToolbar;


public abstract class BaseAppBarActivity extends BaseActivity {
    private HdWalletToolbar mWeChatToolbar;
    private String mTitle;

    public BaseAppBarActivity(int viewResource, String title) {
        super(viewResource);

        mTitle = title;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

//        mWeChatToolbar = findViewById(R.id.toolbar);
//        mWeChatToolbar.setTitle(mTitle);
//        mWeChatToolbar.setNavigationOnClickListener(view -> {
//            onNavigationBack();
//        });
    }

    public void setTitle(String title) {
        mTitle = title;
        mWeChatToolbar.setTitle(mTitle);
    }

    protected void onNavigationBack() {
        finish();
    }
}
