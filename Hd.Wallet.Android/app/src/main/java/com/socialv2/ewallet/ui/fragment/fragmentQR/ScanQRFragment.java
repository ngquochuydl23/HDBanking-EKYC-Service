package com.socialv2.ewallet.ui.fragment.fragmentQR;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.socialv2.ewallet.R;

public class ScanQRFragment extends Fragment {

    private View mView;

    private final ActivityResultLauncher<ScanOptions> scannerLauncher = registerForActivityResult(
            new ScanContract(), result -> {
                if (result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), ResultQrActivity.class);
                    intent.putExtra("SCAN_RESULT", result.getContents());
                    startActivity(intent);
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_scanqr, container, false);
        startScanning();
        return mView;
    }

    private void startScanning() {
        scannerLauncher.launch(
                new ScanOptions()
                        .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                        .setCaptureActivity(CustomCaptureActivity.class) // Use CustomCaptureActivity
        );
    }
}
