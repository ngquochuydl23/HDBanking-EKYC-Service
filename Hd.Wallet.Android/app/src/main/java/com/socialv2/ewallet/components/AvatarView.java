package com.socialv2.ewallet.components;

import android.content.Context;
import android.util.AttributeSet;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.utils.DpToPx;
import com.socialv2.ewallet.utils.FetchImageUrl;

import de.hdodenhof.circleimageview.CircleImageView;

public class AvatarView extends CircleImageView {

    public AvatarView(Context context) {
        super(context);

        initView();
    }

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView();
    }

    private void initView() {
        setPadding(10, 10, 10, 10);
        setBackground(getResources().getDrawable(R.drawable.drawable_filled_circle));
        setBackgroundTintList(getResources().getColorStateList(R.color.backgroundColor));
    }

    public void setSrc(String url) {
        FetchImageUrl.read(AvatarView.this, url);

        setPadding(0, 0, 0, 0);
        invalidate();
    }

    public void setSrcWithGender(String url, Boolean gender) {
        FetchImageUrl.read(AvatarView.this, url);

        setPadding(0, 0, 0, 0);
        invalidate();
    }

    public void setSrcAsGroup(String url) {
        if (url == null || url.isEmpty()) {
            int padding = DpToPx.convert(getContext(), 10f);
            setPadding(padding, padding, padding, padding);
        } else {
            setPadding(0, 0, 0, 0);
        }

        FetchImageUrl.read(AvatarView.this, url, R.drawable.img_groupchat_no_thumbnail);

        invalidate();
    }
}
