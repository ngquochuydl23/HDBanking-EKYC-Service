package com.socialv2.ewallet.ui.facialRecognition;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
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
import com.google.gson.Gson;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.BackdropLoadingDialogFragment;
import com.socialv2.ewallet.dtos.idCard.IdCardExtractDto;
import com.socialv2.ewallet.https.api.ekycHttp.EkycServiceImpl;
import com.socialv2.ewallet.https.api.ekycHttp.IEkycService;
import com.socialv2.ewallet.https.api.ekycHttp.IHttpEkyc;
import com.socialv2.ewallet.permissions.Permissions;
import com.socialv2.ewallet.sharedReferences.KeyValueSharedPreferences;
import com.socialv2.ewallet.utils.CropImageUtils;
import com.socialv2.ewallet.utils.ImageToBitmap;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FacialRecognitionActivity extends BaseActivity {

    private final String TAG = FacialRecognitionActivity.class.getName();

    private Toolbar mToolbar;
    private FacialRecognitionOverlayView mFacialFrameOverlayView;
    private Permissions appPermission;
    private BackdropLoadingDialogFragment mLoadingBackdropDialog;
    private ImageCapture mImageCapture;
    private PreviewView mPreviewView;
    private CompletableFuture<Rect> rectFuture;
    private ListenableFuture<ProcessCameraProvider> mCameraProviderListenableFuture;
    private IEkycService mEkycService;

    public FacialRecognitionActivity() {
        super(R.layout.activity_facial_recognition);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEkycService = new EkycServiceImpl(this);

        mLoadingBackdropDialog = new BackdropLoadingDialogFragment();
        mLoadingBackdropDialog.setFragmentManager(getSupportFragmentManager());

        mToolbar = findViewById(R.id.toolbar);
        mPreviewView = findViewById(R.id.previewView);
        mFacialFrameOverlayView = findViewById(R.id.facialFrameOverlayView);

        initView();
        requestPermissions();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");

        mLoadingBackdropDialog.setLoading(false);
        startCamera();
    }

    private void initView() {
        ViewGroup.MarginLayoutParams toolbarLayoutParams = (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();
        ViewGroup.MarginLayoutParams overlayLayoutParams = (ViewGroup.MarginLayoutParams) mFacialFrameOverlayView.getLayoutParams();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            toolbarLayoutParams.setMargins(0, systemBars.top, 0, 0);
            overlayLayoutParams.setMargins(0, 0, 0, systemBars.bottom);

            mToolbar.setLayoutParams(toolbarLayoutParams);
            mFacialFrameOverlayView.setLayoutParams(overlayLayoutParams);
            return insets;
        });

        mToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        rectFuture = mFacialFrameOverlayView.getIdCardRectAsync();

        mFacialFrameOverlayView.setOnButtonCameraPressListener(new FacialRecognitionOverlayView.OnButtonCameraPressListener() {
            @Override
            public void onPress() {
                captureImage();
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

        mCameraProviderListenableFuture = ProcessCameraProvider.getInstance(this);
        mCameraProviderListenableFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = mCameraProviderListenableFuture.get();
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
                .build();

        mImageCapture = new ImageCapture
                .Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
//                .setTargetRotation(mPreviewView
//                        .getDisplay()
//                        .getRotation())
                .build();

        CameraSelector cameraSelector = new CameraSelector
                .Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
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

                pauseCamera();
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

    private void pauseCamera() {
        mCameraProviderListenableFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = mCameraProviderListenableFuture.get();
                cameraProvider.unbindAll();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void resumeCamera() {
        mLoadingBackdropDialog.setLoading(true);
        mCameraProviderListenableFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = mCameraProviderListenableFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                mLoadingBackdropDialog.setLoading(false);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @Override
    public void onBackPressed() {
        if (mLoadingBackdropDialog != null && mLoadingBackdropDialog.isLoading()) {
            Log.d(TAG, "Backdrop loading dialog is visible, back press is ignored");
            // Do nothing to prevent the dialog from being dismissed
        } else {
            super.onBackPressed(); // Call the super method if the dialog is not visible
        }
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private void processImage(@NonNull ImageProxy image) {
        try {
            if (rectFuture.isDone()) {

                Rect rect = rectFuture.get();
                Bitmap bitmap = ImageToBitmap.convert(image);
                Bitmap croppedBitmap = CropImageUtils.crop(bitmap, mPreviewView, rect);

                Log.i(TAG, String.valueOf(croppedBitmap.getHeight()));
                Log.i(TAG, String.valueOf(croppedBitmap.getWidth()));

                faceVerification(croppedBitmap);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("CheckResult")
    private void faceVerification(Bitmap faceBitmap) throws Exception {
        IdCardExtractDto idCardExtract = getIdCardResult();
        Log.i(TAG, idCardExtract.toString());
        mEkycService.faceVerification(faceBitmap, idCardExtract.getFrontUrl())
                .subscribe(response -> {
                    Log.i(TAG, response.toString());
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    private IdCardExtractDto getIdCardResult() {
        String json = new KeyValueSharedPreferences(this, "IdCardExtractResult")
                .getData();

        Gson gson = new Gson();
        return gson.fromJson(json, IdCardExtractDto.class);
    }

}