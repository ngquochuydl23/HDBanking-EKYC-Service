package com.socialv2.ewallet.ui.facialRecognition;

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
import com.socialv2.ewallet.ui.idCardTaken.IdCardFrameOverlayView;

import java.util.concurrent.CompletableFuture;

public class FacialRecognitionOverlayView extends FrameLayout {

    private Context context;
    private Button mCameraButton;
    private View mOverlayView;
    private SoundPool mSoundPool;

    private int soundId;



    private OnButtonCameraPressListener onButtonCameraPressListener;

    public FacialRecognitionOverlayView(Context context) {
        super(context);
    }

    public FacialRecognitionOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public FacialRecognitionOverlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context);
    }

    private void init(Context context) {
        this.context = context;

        inflate(context, R.layout.layout_face_overlay, this);

        mCameraButton = findViewById(R.id.cameraButton);
        mOverlayView = findViewById(R.id.overlayView);

        setupSoundEffect();

        mCameraButton.setOnClickListener(view -> {
            mSoundPool.play(soundId, 0.5f, 0.5f, 0, 0, 1);
            if (onButtonCameraPressListener != null) {
                onButtonCameraPressListener.onPress();
            }
        });

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

    private void setupSoundEffect() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        mSoundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        soundId = mSoundPool.load(getContext(), R.raw.taken_picture_sound_effect, 1);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mSoundPool != null) {
            mSoundPool.release();
            mSoundPool = null;
        }
    }

    public void setOnButtonCameraPressListener(OnButtonCameraPressListener onButtonCameraPressListener) {
        this.onButtonCameraPressListener = onButtonCameraPressListener;
    }

    @FunctionalInterface
    public interface OnButtonCameraPressListener {
        void onPress();
    }
}