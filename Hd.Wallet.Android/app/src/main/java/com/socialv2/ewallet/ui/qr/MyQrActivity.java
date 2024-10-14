package com.socialv2.ewallet.ui.qr;

import static com.socialv2.ewallet.ui.qr.QrStyleCustomizeKt.getQrStyle;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.alexzhirkevich.customqrgenerator.QrData;
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawableKt;
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.HdWalletToolbar;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.users.UserDto;

public class MyQrActivity extends BaseActivity {

    private static final String TAG = MyQrActivity.class.getName();

    private ImageView mQrCodeImageView;
    private Button mShareButton;
    private Button mSaveButton;
    private HdWalletToolbar mToolbar;


    private Drawable mQrDrawable;

    public MyQrActivity() {
        super(R.layout.activity_my_qr);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mQrCodeImageView = findViewById(R.id.qrCodeImageView);
        mShareButton = findViewById(R.id.shareButton);
        mSaveButton = findViewById(R.id.saveButton);
        mToolbar = findViewById(R.id.toolbar);

        initView();

        AccountDto account = new AccountDto();
        account.setId("ffeba890-30cb-49ee-9dda-a2209d750c10");

        UserDto user = new UserDto();
        user.setAvatar("https://avatars.githubusercontent.com/u/36536025?v=4");

        generateQRCode(account, user, new MyQrActivity.OnQrCodeGeneratedListener() {
            @Override
            public void onQrCodeGenerated(Drawable qrCodeDrawable) {
                mQrDrawable = qrCodeDrawable;
                mQrCodeImageView.setImageDrawable(mQrDrawable);
            }
        });
    }

    private void initView() {
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_back_navigation_white);

        if (mToolbar != null) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();
            params.setMargins(params.leftMargin, getStatusBarHeight(), params.rightMargin, params.bottomMargin);
            mToolbar.setLayoutParams(params);
        }


        mShareButton.setOnClickListener(view -> {

        });

        mSaveButton.setOnClickListener(view -> {
            saveQrPicture();
        });
    }

    private void generateQRCode(
            AccountDto account,
            UserDto user,
            MyQrActivity.OnQrCodeGeneratedListener listener) {

        Glide.with(this)
                .load(user.getAvatar())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(
                            @NonNull Drawable resource,
                            @Nullable Transition<? super Drawable> transition) {

                        QrData data = new QrData.Text(account.getId());
                        Drawable qrCodeDrawable = QrCodeDrawableKt.QrCodeDrawable(
                                data,
                                getQrStyle(MyQrActivity.this, resource),
                                null);


                        if (listener != null) {
                            listener.onQrCodeGenerated(qrCodeDrawable);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    private void saveQrPicture() {

    }

    public interface OnQrCodeGeneratedListener {
        void onQrCodeGenerated(Drawable qrCodeDrawable);
    }
}