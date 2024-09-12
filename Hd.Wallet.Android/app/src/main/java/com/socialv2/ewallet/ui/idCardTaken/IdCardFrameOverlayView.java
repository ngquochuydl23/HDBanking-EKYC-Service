package com.socialv2.ewallet.ui.idCardTaken;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.socialv2.ewallet.R;

import java.util.concurrent.CompletableFuture;

public class IdCardFrameOverlayView extends FrameLayout {

    private Context context;
    private Button mCameraButton;
    private View mOverlayView;
    private OnButtonCameraPressListener onButtonCameraPressListener;

    public IdCardFrameOverlayView(Context context) {
        super(context);
    }

    public IdCardFrameOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public IdCardFrameOverlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context);
    }

    private void init(Context context) {
        this.context = context;

        inflate(context, R.layout.layout_idcard_overlay_camera, this);

        mOverlayView = findViewById(R.id.overlayView);
        mCameraButton = findViewById(R.id.cameraButton);
        mCameraButton.setOnClickListener(view -> {
            if (onButtonCameraPressListener != null) {
                onButtonCameraPressListener.onPress();
            }
        });
    }

    public void setOnButtonCameraPressListener(OnButtonCameraPressListener onButtonCameraPressListener) {
        this.onButtonCameraPressListener = onButtonCameraPressListener;
    }

    public CompletableFuture<Rect> getIdCardRectAsync() {
        CompletableFuture<Rect> future = new CompletableFuture<>();

        mOverlayView.post(() -> {
            try {
                int[] location = new int[2];
                mOverlayView.getLocationOnScreen(location);

                int x = location[0];
                int y = location[1];
                int width = mOverlayView.getWidth();
                int height = mOverlayView.getHeight();

                // Create a Rect to store the view's location and dimensions
                Rect rect = new Rect(x, y, x + width, y + height);
                future.complete(rect);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    @FunctionalInterface
    public interface OnButtonCameraPressListener {
        void onPress();
    }
}
