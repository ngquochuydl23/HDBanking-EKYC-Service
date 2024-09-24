package com.socialv2.ewallet.ui.idCardTaken;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.google.gson.Gson;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.BackdropLoadingDialogFragment;
import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.idCard.IdCardExtractDto;
import com.socialv2.ewallet.https.api.ekycHttp.IEkycService;
import com.socialv2.ewallet.https.api.ekycHttp.EkycServiceImpl;
import com.socialv2.ewallet.permissions.Permissions;
import com.socialv2.ewallet.sharedReferences.KeyValueSharedPreferences;
import com.socialv2.ewallet.ui.register.ConfirmInformationActivity;
import com.socialv2.ewallet.utils.CropImageUtils;
import com.socialv2.ewallet.utils.ImageToBitmap;
import com.socialv2.ewallet.utils.ParseHttpError;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import retrofit2.HttpException;

public class IdCardTakenActivity extends AppCompatActivity {

    private final String TAG = IdCardTakenActivity.class.getName();

    private ListenableFuture<ProcessCameraProvider> mCameraProviderListenableFuture;

    private IEkycService mOcrCardService;

    private UserCheckIdCardBottomSheet mUserCheckIdCardBottomSheet;
    private IdCardFrameOverlayView mIdCardFrameOverlayView;
    private Toolbar mToolbar;
    private ImageCapture mImageCapture;
    private PreviewView mPreviewView;
    private Permissions appPermission;
    private BackdropLoadingDialogFragment mLoadingBackdropDialog;

    private CompletableFuture<Rect> rectFuture;

    private List<Bitmap> mBitmaps;
    private int mStep = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_id_card_taken);

        mOcrCardService = new EkycServiceImpl(this);
        mBitmaps = new ArrayList<>();
        mIdCardFrameOverlayView = findViewById(R.id.idCardFrameOverlayView);
        mToolbar = findViewById(R.id.toolbar);
        mPreviewView = findViewById(R.id.previewView);

        mUserCheckIdCardBottomSheet = new UserCheckIdCardBottomSheet();
        mLoadingBackdropDialog = new BackdropLoadingDialogFragment();
        mLoadingBackdropDialog.setFragmentManager(getSupportFragmentManager());

        initView();
        requestPermissions();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");

        mLoadingBackdropDialog.setLoading(false);
        mBitmaps.clear();
        mStep = 1;

        mIdCardFrameOverlayView.setStep(mStep);
        startCamera();
    }

    private void initView() {
        ViewGroup.MarginLayoutParams toolbarLayoutParams = (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();
        ViewGroup.MarginLayoutParams overlayLayoutParams = (ViewGroup.MarginLayoutParams) mIdCardFrameOverlayView.getLayoutParams();

        mLoadingBackdropDialog.setLoading(false);

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

        mToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        mUserCheckIdCardBottomSheet.setContinueButtonClick(new UserCheckIdCardBottomSheet.ContinueButtonClick() {
            @Override
            public void onContinue(Bitmap bitmap) {
                if (mStep == 1) {
                    resumeCamera();
                    runOnUiThread(() -> {
                        mStep++;
                        mBitmaps.add(bitmap);
                        mIdCardFrameOverlayView.setStep(mStep);
                        mUserCheckIdCardBottomSheet.dismiss();
                    });

                } else if (mStep == 2){
                    runOnUiThread(() -> mLoadingBackdropDialog.setLoading(true));

                    mBitmaps.add(bitmap);

                    new Thread(() -> {
                        extractIdCardInfo();
                    }).start();
                }
            }
        });

        mUserCheckIdCardBottomSheet.setRetryButtonClick(new UserCheckIdCardBottomSheet.RetryButtonClick() {
            @Override
            public void onClick() {
                resumeCamera();
                runOnUiThread(() -> {
                    mUserCheckIdCardBottomSheet.clearState();
                    mUserCheckIdCardBottomSheet.dismiss();

                    mStep = 1;
                    mIdCardFrameOverlayView.setStep(mStep);
                    mBitmaps.clear();
                });
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
//                .setTargetRotation(mPreviewView.getDisplay().getRotation())
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

    @OptIn(markerClass = ExperimentalGetImage.class)
    private void processImage(@NonNull ImageProxy image) {
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

    @SuppressLint("CheckResult")
    private void extractIdCardInfo() {
        if (mBitmaps.size() == 2) {
            mOcrCardService.extractIdCard(mBitmaps.get(0), mBitmaps.get(1), "abc")
                    .subscribe(idCardExtractDtoHttpResponseDto -> {

                        IdCardExtractDto idCardExtract = idCardExtractDtoHttpResponseDto.getResult();

                        Gson gson = new Gson();
                        String json = gson.toJson(idCardExtract);


                        mUserCheckIdCardBottomSheet.clearState();
                        mUserCheckIdCardBottomSheet.dismiss();

                        new KeyValueSharedPreferences(this, "IdCardExtractResult")
                                .setData(json);

                        Intent intent = new Intent(this, ConfirmInformationActivity.class);
                        intent.putExtra("IdCardExtract", json);
                        startActivity(intent);

                    }, throwable -> {
                        if (throwable instanceof HttpException) {

                            HttpResponseDto<?> errorResponse =  ParseHttpError.parse(throwable);
                            int statusCode = ParseHttpError.getStatusCode(throwable);

                            Log.e(TAG, errorResponse.toString());
                            String errorMsg = errorResponse.getError();

                            if (statusCode== 400) {
                                if (errorMsg.equals(getString(R.string.cannot_recognize_idcard))) {
                                    runOnUiThread(() -> Toast.makeText(this, "Nhận dạng thất bại", Toast.LENGTH_LONG).show());
                                }
                            } else if (statusCode == 500) {


                            } else if (statusCode == 502) {

                            }
                        } else {
                            Log.e(TAG, throwable.getMessage());
                            runOnUiThread(() -> Toast.makeText(this, "Không có kết nối Internet", Toast.LENGTH_LONG).show());
                        }
                        runOnUiThread(() -> mLoadingBackdropDialog.setLoading(false));
                    }, () -> {
                        runOnUiThread(() -> mLoadingBackdropDialog.setLoading(false));
                    });
        }
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
}