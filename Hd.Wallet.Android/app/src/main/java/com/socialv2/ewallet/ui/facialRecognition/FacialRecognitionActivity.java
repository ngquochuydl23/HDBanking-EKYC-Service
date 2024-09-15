package com.socialv2.ewallet.ui.facialRecognition;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.utils.WindowUtils;

public class FacialRecognitionActivity extends BaseActivity {

    private Toolbar mToolbar;
    public FacialRecognitionActivity() {
        super(R.layout.activity_facial_recognition);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowUtils.applyPadding(findViewById(R.id.main));

        mToolbar = findViewById(R.id.toolbar);

        View overlayFaceView = findViewById(R.id.overlayFaceView);
        ViewGroup.MarginLayoutParams overlayLayoutParams = (ViewGroup.MarginLayoutParams) overlayFaceView.getLayoutParams();
        ViewGroup.MarginLayoutParams toolbarLayoutParams = (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            overlayLayoutParams.setMargins(0, 0, 0, systemBars.bottom);

            mToolbar.setLayoutParams(toolbarLayoutParams);
            overlayFaceView.setLayoutParams(overlayLayoutParams);
            return insets;
        });
    }
}