package com.socialv2.ewallet.ui.qr;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.socialv2.ewallet.BaseBottomSheetDialog;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.facialRecognition.VerificationFailBottomSheet;

public class ScanFailedBottomSheet extends BaseBottomSheetDialog {

    public static final String TAG = ScanFailedBottomSheet.class.getName();

    private VerificationFailBottomSheet.RetryButtonClick mRetryButtonClick;
    private TextView mTitleTextView;
    private TextView mSubtitleTextView;
    private View mRetryButton;


    private String mTitle;
    private String mSubtitle;

    private OnRetryListener listener;

    public ScanFailedBottomSheet(String title, String subtitle) {
        super(R.layout.bottomsheet_verification_failed);
        mTitle = title;
        mSubtitle = subtitle;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitleTextView = view.findViewById(R.id.titleTextView);
        mSubtitleTextView = view.findViewById(R.id.subtitleTextView);

        mRetryButton = view.findViewById(R.id.retryButton);
        
        mTitleTextView.setText(mTitle);
        mSubtitleTextView.setText(mSubtitle);
        mRetryButton.setOnClickListener(v -> {
            dismiss();
            if (listener != null){
                listener.onRetryClick();
            }
        });
    }

    public void setRetryClickListener(OnRetryListener listener) {
        this.listener = listener;
    }

    public interface OnRetryListener {
        void onRetryClick();
    }
}