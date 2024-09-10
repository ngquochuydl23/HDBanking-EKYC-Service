package com.socialv2.ewallet.components;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toolbar;

import com.socialv2.ewallet.R;


public class HdWalletToolbar extends Toolbar {

    private Context mContext;


    public HdWalletToolbar(Context context) {
        super(context);
    }

    public HdWalletToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        init();
    }

    public HdWalletToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        init();
    }

    public HdWalletToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mContext = context;
        init();
    }

    public void init() {
        setNavigationIcon(R.drawable.ic_back_navigation);
        setContentInsetStartWithNavigation(0);
        setTitleTextAppearance(mContext, R.style.OCRMainToolbar_TitleTextAppearance);

        setOnClickListener(v -> {
            onBackClick();
        });
    }

    public void onBackClick() {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            activity.finish();
            // You can safely use 'activity' as an Activity now
        }
    }
}
