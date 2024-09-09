package com.OcrBanking.Android.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.OcrBanking.Android.R;
import com.bumptech.glide.Glide;


public class FetchImageUrl {
    @SuppressLint("UseCompatLoadingForDrawables")
    public static void read(ImageView imageView, String url, Integer errorResourceId) {
        Context context = imageView.getContext();

        Glide.with(context)
                .load(context.getString(R.string.storage_host) + url)
                .error(errorResourceId == -1 ? null : context.getDrawable(errorResourceId))
                .into(imageView);
    }

    public static void read(ImageView imageView, String url) {
        read(imageView, url, -1);
    }

    public static void readUrlAsAvatar(ImageView imageView, String url, Boolean gender) {
        read(imageView, url, R.drawable.default_avatar_men);
    }
}
