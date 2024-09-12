package com.socialv2.ewallet.ui.idCardTaken;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.socialv2.ewallet.BaseBottomSheetDialog;
import com.socialv2.ewallet.R;

public class UserCheckIdCardBottomSheet extends BaseBottomSheetDialog {

    private ImageView mUserIdCardImageView;
    private Bitmap mBitmap;
    public UserCheckIdCardBottomSheet() {
        super(R.layout.bottomsheet_user_check_idcard);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUserIdCardImageView = view.findViewById(R.id.userIdCardImageView);
        mUserIdCardImageView.setImageBitmap(mBitmap);
    }

    public void setIdCard(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }
}
