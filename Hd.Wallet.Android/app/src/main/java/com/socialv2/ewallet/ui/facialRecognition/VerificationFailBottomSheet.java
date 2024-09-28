package com.socialv2.ewallet.ui.facialRecognition;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.socialv2.ewallet.BaseBottomSheetDialog;
import com.socialv2.ewallet.R;

public class VerificationFailBottomSheet extends BaseBottomSheetDialog {

    private RetryButtonClick mRetryButtonClick;
    private View mRetryButton;

    public VerificationFailBottomSheet() {
        super(R.layout.bottomsheet_verification_failed);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRetryButton = view.findViewById(R.id.retryButton);
        mRetryButton.setOnClickListener(v -> {
            mRetryButtonClick.onClick();
        });
    }

    @FunctionalInterface
    public interface RetryButtonClick {
        void onClick();
    }

    public void setRetryButtonClick(RetryButtonClick retryButtonClick) {
        mRetryButtonClick = retryButtonClick;
    }
}
