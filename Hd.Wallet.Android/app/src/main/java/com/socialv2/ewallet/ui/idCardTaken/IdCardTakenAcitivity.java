package com.socialv2.ewallet.ui.idCardTaken;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.common.util.concurrent.ListenableFuture;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.permissions.Permissions;
import com.socialv2.ewallet.ui.register.ConfirmInformationActivity;
import com.socialv2.ewallet.utils.CropImageUtils;
import com.socialv2.ewallet.utils.ImageToBitmap;
import com.socialv2.ewallet.utils.NavigateUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class IdCardTakenAcitivity extends AppCompatActivity {

    private final String TAG = IdCardTakenAcitivity.class.getName();

    private UserCheckIdCardBottomSheet mUserCheckIdCardBottomSheet;
    private IdCardFrameOverlayView mIdCardFrameOverlayView;
    private Toolbar mToolbar;
    private ImageCapture mImageCapture;
    private PreviewView mPreviewView;
    private Permissions appPermission;
    private CompletableFuture<Rect> rectFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_id_card_taken);

        mIdCardFrameOverlayView = findViewById(R.id.idCardFrameOverlayView);
        mToolbar = findViewById(R.id.toolbar);
        mPreviewView = findViewById(R.id.previewView);
        mUserCheckIdCardBottomSheet = new UserCheckIdCardBottomSheet();

        initView();
        requestPermissions();
    }

    private void initView() {
        ViewGroup.MarginLayoutParams toolbarLayoutParams = (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();
        ViewGroup.MarginLayoutParams overlayLayoutParams = (ViewGroup.MarginLayoutParams) mIdCardFrameOverlayView.getLayoutParams();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            toolbarLayoutParams.setMargins(0, systemBars.top, 0, 0);
            overlayLayoutParams.setMargins(0, 0, 0, systemBars.bottom);

            mToolbar.setLayoutParams(toolbarLayoutParams);
            mIdCardFrameOverlayView.setLayoutParams(overlayLayoutParams);
            return insets;
        });
        rectFuture = mIdCardFrameOverlayView.getIdCardRectAsync();

        mIdCardFrameOverlayView.setOnButtonCameraPressListener(new IdCardFrameOverlayView.OnButtonCameraPressListener() {
            @Override
            public void onPress() {
                captureImage();
            }
        });

        mUserCheckIdCardBottomSheet.setContinueButtonClick(new UserCheckIdCardBottomSheet.ContinueButtonClick() {
            @Override
            public void onContinue(Bitmap bitmap) {
                NavigateUtil.navigateTo(IdCardTakenAcitivity.this, ConfirmInformationActivity.class);
            }
        });

        mUserCheckIdCardBottomSheet.setRetryButtonClick(new UserCheckIdCardBottomSheet.RetryButtonClick() {
            @Override
            public void onClick() {

            }
        });
    }

    private void requestPermissions() {
        appPermission = new Permissions(this, new String[]{Manifest.permission.CAMERA});

        if (!appPermission.checkIsGranted()) {
            Log.d(TAG, "Camera permission is denied");
            appPermission.request();
        } else {
            startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == appPermission.PERMISSION_REQUEST_CODE) {
            startCamera();
        } else {
            Log.i(TAG, "Permission error");
        }
    }

    private void startCamera() {
        Log.i(TAG, "Start running camera");

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Log.i(TAG, "Bind preview camera");
        Preview preview = new Preview
                .Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .setTargetRotation(mPreviewView.getDisplay().getRotation())
                .build();

        mImageCapture = new ImageCapture
                .Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .setTargetRotation(mPreviewView
                        .getDisplay()
                        .getRotation())
                .build();

        CameraSelector cameraSelector = new CameraSelector
                .Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider());

        try {
            cameraProvider.unbindAll();
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, mImageCapture);
        } catch (Exception e) {
            Log.e(TAG, "CameraX - Use case binding failed", e);
        }
    }

    private void captureImage() {
        Log.i(TAG, "Capturing image");

        mImageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                Log.i(TAG, "Captured image");


                processImage(image);
                image.close();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                // Handle the error
                Log.e(TAG, "Image capture failed: " + exception.getMessage());
            }
        });
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private void processImage(@NonNull ImageProxy image) {
        //try {
        try {

            if (rectFuture.isDone()) {

                Rect rect = rectFuture.get();
                Bitmap bitmap = ImageToBitmap.convert(image);
                Bitmap croppedBitmap = CropImageUtils.crop(bitmap, mPreviewView, rect);

                mUserCheckIdCardBottomSheet.show(getSupportFragmentManager(), mUserCheckIdCardBottomSheet.getTag());
                mUserCheckIdCardBottomSheet.setIdCard(croppedBitmap);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}