package com.socialv2.ewallet.ui.qr;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.alexzhirkevich.customqrgenerator.QrData;
import com.github.alexzhirkevich.customqrgenerator.style.BitmapScale;
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawableKt;
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColor;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColors;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogo;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoPadding;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoShape;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape;
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorShapes;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.socialv2.ewallet.R;

public class GenQRFragment extends Fragment {
    private static final String TAG = "GenQRFragment";
    private static final String DEFAULT_NUMBER = "1234567890";
    private ImageView mImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_genqr, container, false);
        mImageView = mView.findViewById(R.id.qr_code);
        generateQRCode("3e42cfee-6e62-4d52-b63b-e11f97ff7925");
        return mView;
    }

    private void generateQRCode(String data1) {
        QrData data = new QrData.Text("3e42cfee-6e62-4d52-b63b-e11f97ff7925");
        QrVectorOptions options = new QrVectorOptions.Builder()
                .setPadding(.3f)
                .setColors(
                        new QrVectorColors(
                                new QrVectorColor.Solid(Color.WHITE), // Nền trắng
                                new QrVectorColor.Solid(Color.BLACK), // Màu chính của mã QR
                                new QrVectorColor.Solid(Color.BLACK), // Màu viền
                                new QrVectorColor.Solid(Color.BLACK)  // Màu khung viền xung quanh mã QR
                        )
                )
                .setShapes(
                        new QrVectorShapes(
                                new QrVectorPixelShape.RoundCorners(0.5f), // Các điểm bo tròn hoàn toàn
                                new QrVectorPixelShape.RoundCorners(0.5f), // Cũng bo tròn cho hình dạng phụ
                                new QrVectorBallShape.RoundCorners(0.5f, true, true, true, true), // Bo tròn các viên QR lớn
                                new QrVectorFrameShape.RoundCorners(0.25f, 1f, true, true, true, true, 7), // Bo tròn toàn bộ khung viền
                                true // Bật tính năng khung viền bo tròn
                        )
                )
                .build();
        Drawable drawable = QrCodeDrawableKt.QrCodeDrawable(data, options, null);
//            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//            Bitmap bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 500, 500);
//
        mImageView.setImageDrawable(drawable);


    }
}