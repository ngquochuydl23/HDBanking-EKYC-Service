package com.socialv2.ewallet.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class CropImageUtils {


    public static Bitmap crop(Bitmap bitmap, View cameraView, Rect rect) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        int overlayTop = rect.top;
        int overlayBottom = rect.bottom;
        int overlayLeft = rect.left;
        int overlayRight = rect.right;

        int cameraWidth = cameraView.getWidth();
        int cameraHeight = cameraView.getHeight();

        float ratioWidth = (float) rotatedBitmap.getWidth() / (float) cameraWidth;
        float ratioHeight = (float) rotatedBitmap.getHeight() / (float) cameraHeight;

        int realFrameWidth = Math.round((overlayRight - overlayLeft) * ratioWidth);
        int realFrameHeight = Math.round((overlayBottom - overlayTop) * ratioHeight);
        int realOverlayLeft = Math.round(overlayLeft * ratioWidth);
        int realOverlayTop = Math.round(overlayTop * ratioHeight);

        if (realOverlayLeft + realFrameWidth > rotatedBitmap.getWidth()) {
            realFrameWidth = rotatedBitmap.getWidth() - realOverlayLeft;
        }
        if (realOverlayTop + realFrameHeight > rotatedBitmap.getHeight()) {
            realFrameHeight = rotatedBitmap.getHeight() - realOverlayTop;
        }

        return Bitmap.createBitmap(rotatedBitmap, realOverlayLeft, realOverlayTop, realFrameWidth, realFrameHeight);
    }
}
