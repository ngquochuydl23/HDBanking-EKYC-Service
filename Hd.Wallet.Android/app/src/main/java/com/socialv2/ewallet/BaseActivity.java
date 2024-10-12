package com.socialv2.ewallet;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {



    private int viewResource;
    private String title;

    public BaseActivity(int viewResource) {
        this.viewResource = viewResource;
    }


    public int getViewResource() {
        return viewResource;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInitalBeforeInflatingView();
        setContentView(viewResource);

    }

    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected void onInitalBeforeInflatingView() {
        EdgeToEdge.enable(this);
    }
}