package com.socialv2.ewallet.ui.fragment.fragmentQR;

import com.journeyapps.barcodescanner.CaptureActivity;
import android.content.pm.ActivityInfo;

public class CustomCaptureActivity extends CaptureActivity {
    @Override
    public void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
