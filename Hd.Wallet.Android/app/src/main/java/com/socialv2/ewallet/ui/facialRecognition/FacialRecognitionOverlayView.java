package com.socialv2.ewallet.ui.facialRecognition;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.idCardTaken.IdCardFrameOverlayView;

import java.util.concurrent.CompletableFuture;

public class FacialRecognitionOverlayView extends FrameLayout {

    private Context context;
    private Button mCameraButton;
    private View mOverlayView;
    private View mOverlaySuccessView;
    private View mSuccessImageView;
    private View mOverlayVerifyingView;
    private SoundPool mSoundPool;
    private View mOverlayErrorView;
    private View mErrorImageView;

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
        mOverlayVerifyingView = findViewById(R.id.overlayVerifyingView);
        mOverlaySuccessView = findViewById(R.id.overlaySuccessView);
        mSuccessImageView = findViewById(R.id.successImageView);
        mOverlayErrorView = findViewById(R.id.overlayErrorView);
        mErrorImageView = findViewById(R.id.errorImageView);

        setGettingStarted();
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

    public void setGettingStarted() {
        mOverlayView.setVisibility(View.VISIBLE);

        // error view
        mOverlayErrorView.setVisibility(View.GONE);
        mErrorImageView.setVisibility(View.GONE);

        // hide verifying
        mOverlayVerifyingView.setVisibility(View.GONE);

        // hide success
        mOverlaySuccessView.setVisibility(View.GONE);
        mSuccessImageView.setVisibility(View.GONE);
    }

    public void setIsSuccess() {
        mOverlayView.setVisibility(View.INVISIBLE);

        // hide verifying
        mOverlayVerifyingView.setVisibility(View.GONE);

        // error view
        mOverlayErrorView.setVisibility(View.GONE);
        mErrorImageView.setVisibility(View.GONE);


        mOverlaySuccessView.setVisibility(View.VISIBLE);
        mSuccessImageView.setVisibility(View.VISIBLE);
    }

    public void setIsError() {
        mOverlayView.setVisibility(View.INVISIBLE);

        // error view
        mOverlayErrorView.setVisibility(View.VISIBLE);
        mErrorImageView.setVisibility(View.VISIBLE);

        // hide verifying
        mOverlayVerifyingView.setVisibility(View.GONE);

        // hide success
        mSuccessImageView.setVisibility(View.GONE);
        mOverlaySuccessView.setVisibility(View.GONE);
    }

    public void setIsVerifying() {
        mOverlayVerifyingView.setVisibility(View.VISIBLE);
        mOverlayView.setVisibility(View.INVISIBLE);
        mOverlaySuccessView.setVisibility(View.GONE);
        mSuccessImageView.setVisibility(View.GONE);
        // error view
        mOverlayErrorView.setVisibility(View.GONE);
        mErrorImageView.setVisibility(View.GONE);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(findViewById(R.id.faceVerifyingView), "rotation", 0f, 360f);
        rotation.setDuration(700);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.start();
    }

    public void setVisibleButton(boolean visible) {
        mCameraButton.setVisibility(visible ? View.VISIBLE :View.GONE);
    }

    @FunctionalInterface
    public interface OnButtonCameraPressListener {
        void onPress();
    }
}