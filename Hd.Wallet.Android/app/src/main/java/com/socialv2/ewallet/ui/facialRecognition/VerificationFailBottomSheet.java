package com.socialv2.ewallet.ui.idCardTaken;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.socialv2.ewallet.BaseBottomSheetDialog;
import com.socialv2.ewallet.R;

public class UserCheckIdCardBottomSheet extends BaseBottomSheetDialog {

    private ImageView mUserIdCardImageView;
    private Bitmap mBitmap;

    private ContinueButtonClick mContinueButtonClick;
    private RetryButtonClick mRetryButtonClick;

    private View mContinueButton;
    private View mRetryButton;


    public UserCheckIdCardBottomSheet() {
        super(R.layout.bottomsheet_user_check_idcard);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUserIdCardImageView = view.findViewById(R.id.userIdCardImageView);
        mUserIdCardImageView.setImageBitmap(mBitmap);

        mContinueButton = view.findViewById(R.id.continueButton);
        mRetryButton = view.findViewById(R.id.retryButton);

        mContinueButton.setOnClickListener(v -> {
            mContinueButtonClick.onContinue(mBitmap);
        });

        mRetryButton.setOnClickListener(v -> {
            mRetryButtonClick.onClick();
        });


    }

    public void setIdCard(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }


    @FunctionalInterface
    public interface ContinueButtonClick {
        void onContinue(Bitmap bitmap);
    }

    @FunctionalInterface
    public interface RetryButtonClick {
        void onClick();
    }

    public void setContinueButtonClick(ContinueButtonClick continueButtonClick) {
        mContinueButtonClick = continueButtonClick;
    }

    public void setRetryButtonClick(RetryButtonClick retryButtonClick) {
        mRetryButtonClick = retryButtonClick;
    }

    public void clearState() {
        mBitmap = null;
    }
}
