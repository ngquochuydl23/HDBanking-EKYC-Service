package com.socialv2.ewallet.ui.main.homeTab;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toolbar;

import com.socialv2.ewallet.R;

public class HomeTabToolbar extends Toolbar {
    private Context mContext;


    public HomeTabToolbar(Context context) {
        super(context);
    }

    public HomeTabToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        init(context);
    }

    public HomeTabToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        init(context);
    }

    public HomeTabToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mContext = context;
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.layout_homtab_toolbar, this);

        //setNavigationIcon(R.drawable.ic_back_navigation);
        //setContentInsetStartWithNavigation(0);
        //setTitleTextAppearance(mContext, R.style.OCRMainToolbar_TitleTextAppearance);

//        setNavigationOnClickListener(v -> {
//            onBackClick();
//        });
    }

    public void onBackClick() {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            activity.finish();
            // You can safely use 'activity' as an Activity now
        }
    }
}
