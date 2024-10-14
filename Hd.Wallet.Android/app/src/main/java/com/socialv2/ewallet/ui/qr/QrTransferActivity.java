package com.socialv2.ewallet.ui.qr;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
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
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.HdWalletToolbar;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.https.api.accountHttp.AccountHttpImpl;
import com.socialv2.ewallet.https.api.accountHttp.IAccountService;
import com.socialv2.ewallet.permissions.Permissions;
import com.socialv2.ewallet.ui.transfer.TransferMoneyActivity;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QrTransferActivity extends BaseActivity {

    private static final String TAG = QrTransferActivity.class.getName();
    private static final float MAX_ZOOM_RATIO = 5.0f;

    private ListenableFuture<ProcessCameraProvider> mCameraProviderListenableFuture;

    private HdWalletToolbar mToolbar;
    private Permissions appPermission;
    private PreviewView mPreviewView;
    private Camera mCamera;
    private CameraSelector mCameraSelector;
    private ProcessCameraProvider mCameraProvider;
    private Preview mPreviewUseCase;
    private ImageAnalysis mAnalysisUseCase;
    private ExecutorService mCameraExecutor;
    private IAccountService mAccountService;


    private boolean isZooming = false;

    public QrTransferActivity() {
        super(R.layout.activity_qr_transfer);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAccountService = new AccountHttpImpl(this);

        mPreviewView = findViewById(R.id.previewView);
        mToolbar = findViewById(R.id.toolbar);
        initView();
        requestPermissions();
    }

    private void initView() {
        mToolbar.setTitleTextColor(getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.ic_back_navigation_white);
        if (mToolbar != null) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();
            params.setMargins(params.leftMargin, getStatusBarHeight(), params.rightMargin, params.bottomMargin);
            mToolbar.setLayoutParams(params);
        }
    }

    private void requestPermissions() {
        appPermission = new Permissions(this, new String[]{Manifest.permission.CAMERA});

        if (!appPermission.checkIsGranted()) {
            Log.d(TAG, "Camera permission is denied");
            appPermission.request();
        } else {
            setupCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == appPermission.PERMISSION_REQUEST_CODE) {
            setupCamera();
        } else {
            Log.i(TAG, "Permission error");
        }
    }

    private void setupCamera() {
        isZooming = false;

        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        int lensFacing = CameraSelector.LENS_FACING_BACK;
        mCameraSelector = new CameraSelector
                .Builder()
                .requireLensFacing(lensFacing)
                .build();

        cameraProviderFuture.addListener(() -> {
            try {
                mCameraProvider = cameraProviderFuture.get();
                bindAllCameraUseCases();
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "cameraProviderFuture.addListener Error", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindAllCameraUseCases() {
        if (mCameraProvider != null) {
            mCameraProvider.unbindAll();
            bindPreviewUseCase();
            bindAnalysisUseCase();
        }
    }

    private void bindPreviewUseCase() {
        if (mCameraProvider == null) {
            return;
        }

        if (mPreviewUseCase != null) {
            mCameraProvider.unbind(mPreviewUseCase);
        }

        Preview.Builder builder = new Preview.Builder();
        builder.setTargetRotation(getRotation());

        mPreviewUseCase = builder.build();
        mPreviewUseCase.setSurfaceProvider(mPreviewView.getSurfaceProvider());

        try {
            mCameraProvider.bindToLifecycle(this, mCameraSelector, mPreviewUseCase);
        } catch (Exception e) {
            Log.e(TAG, "Error when bind preview", e);
        }
    }

    private void bindAnalysisUseCase() {
        if (mCameraProvider == null) {
            return;
        }

        if (mAnalysisUseCase != null) {
            mCameraProvider.unbind(mAnalysisUseCase);
        }

        mCameraExecutor = Executors.newSingleThreadExecutor();

        mAnalysisUseCase = new ImageAnalysis
                .Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setTargetRotation(getRotation())
                .build();

        mAnalysisUseCase.setAnalyzer(mCameraExecutor, this::analyze);

        try {
            mCamera = mCameraProvider.bindToLifecycle(this, mCameraSelector, mAnalysisUseCase);
        } catch (Exception e) {
            Log.e(TAG, "Error when bind analysis", e);
        }
    }

    protected int getRotation() throws NullPointerException {
        return mPreviewView.getDisplay().getRotation();
    }

    @SuppressLint("UnsafeOptInUsageError")
    private void analyze(@NonNull ImageProxy image) {
        if (image.getImage() == null) {
            return;
        }

        Log.i(TAG, "analyze image");

        InputImage inputImage = InputImage.fromMediaImage(
                image.getImage(),
                image.getImageInfo().getRotationDegrees()
        );

        BarcodeScanner barcodeScanner = BarcodeScanning.getClient();

        barcodeScanner.process(inputImage)
                .addOnSuccessListener(this::onSuccessListener)
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Barcode process failure", e);
                    e.printStackTrace();
                })
                .addOnCompleteListener(task -> image.close());
    }

    private void onSuccessListener(List<Barcode> barcodes) {
        Log.i(TAG, barcodes.toString());

        if (!barcodes.isEmpty()) {

            for (Barcode barcode : barcodes) {
                String rawValue = barcode.getRawValue();
                if (rawValue != null) {
                    zoomToQr(barcode);
                    Log.i(TAG, "Success Analyse - QR Result: " + rawValue);

                    handleQrCodeResult(rawValue);
                    stopCamera();
                }
            }
        } else {
            Log.w(TAG, "Success Analyse - But no QR found");
        }
    }

    private void stopCamera() {
        mCameraProvider.unbindAll();
        mCameraExecutor.shutdown();
    }

    @SuppressLint("CheckResult")
    private void handleQrCodeResult(String accountId) {
        UUID uuid = UUID.fromString(accountId);

        if (!uuid.toString().equals(accountId)) {

            Log.e(TAG, "Invalid UUID Result");
            // show bottomsheet invalid qr code
            return;
        }

        mAccountService.getAccountById(accountId)
                .subscribe(response -> {
                    Log.e(TAG, "GetAccountById successfully");

                    AccountDto account = response.getResult();
                    String json = new Gson().toJson(account);

                    Intent intent = new Intent(this, TransferMoneyActivity.class);
                    intent.putExtra("AccountJsonResult", json);
                    //startActivity(intent);
                },throwable -> {

                    // fitler error to show http error
                    throwable.printStackTrace();
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        requestPermissions();

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = 1.0f;  // Maximum brightness (1.0f)
        getWindow().setAttributes(layoutParams);
    }

    private void zoomToQr(Barcode barcode) {
        if (mCamera == null || isZooming) {
            return;
        }

        Rect boundingBox = barcode.getBoundingBox();
        if (boundingBox != null) {
            float qrSize = Math.max(boundingBox.width(), boundingBox.height());
            float viewSize = Math.min(mPreviewView.getWidth(), mPreviewView.getHeight());
            float zoomRatio = Math.min(MAX_ZOOM_RATIO, viewSize / qrSize);

            Log.i(TAG, "Zooming to QR with ratio: " + zoomRatio);

            isZooming = true;
            mCamera.getCameraControl()
                    .setZoomRatio(zoomRatio)
                    .addListener(() -> isZooming = false, ContextCompat.getMainExecutor(this));
        }
    }


}