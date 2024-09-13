package com.socialv2.ewallet.ui.idCardTaken;

import android.content.Context;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.socialv2.ewallet.R;

import java.util.concurrent.CompletableFuture;

public class IdCardFrameOverlayView extends FrameLayout {

    private Context context;
    private Button mCameraButton;
    private View mOverlayView;

    private TextView mTitleTextView;
    private TextView mStepTextView;
    private SoundPool mSoundPool;

    private int soundId;

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

        mTitleTextView = findViewById(R.id.titleTextView);
        mStepTextView = findViewById(R.id.stepTextView);

        setupSoundEffect();

        mCameraButton.setOnClickListener(view -> {
            mSoundPool.play(soundId, 0.5f, 0.5f, 0, 0, 1);
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

    public void setStep(int step) {
        if (step == 1) {
            mStepTextView.setText("1/2");
            mTitleTextView.setText("Quý khách vui lòng chụp ảnh mặt trước của CMND/CCCD trong khung hình này.");
        } else {
            mStepTextView.setText("2/2");
            mTitleTextView.setText("Quý khách vui lòng chụp ảnh mặt sau của CMND/CCCD trong khung hình này.");
        }
    }

    private void setupSoundEffect() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        mSoundPool = new SoundPool.Builder()
                .setMaxStreams(1) // Maximum number of sounds that can be played at once
                .setAudioAttributes(audioAttributes)
                .build();

        soundId = mSoundPool.load(getContext(), R.raw.taken_picture_sound_effect, 1);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // Release SoundPool resources
        if (mSoundPool != null) {
            mSoundPool.release();
            mSoundPool = null;
        }
    }

    @FunctionalInterface
    public interface OnButtonCameraPressListener {
        void onPress();
    }
}
