package com.socialv2.ewallet.ui.qr

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColor
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColors
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogo
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoPadding
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorShapes
import com.socialv2.ewallet.R

fun getQrStyle(context: Context, logoDrawable: Drawable?) : QrVectorOptions{
    return QrVectorOptions.Builder()
        .setLogo(
            QrVectorLogo(
                drawable = logoDrawable ?: context.getDrawable(R.drawable.default_avatar_men),
                size = .25f,
                padding = QrVectorLogoPadding.Natural(.2f),
                shape = QrVectorLogoShape.Circle
            )
        )
        .setColors(
            QrVectorColors(
                dark = QrVectorColor.Solid(Color.WHITE),
                ball = QrVectorColor.Solid(Color.BLACK),
                frame = QrVectorColor.Solid(Color.BLACK),
                light = QrVectorColor.Solid(Color.BLACK)
            )
        )
        .setShapes(
            QrVectorShapes(
                QrVectorPixelShape.RoundCorners(0.5f), // Các điểm bo tròn hoàn toàn
                QrVectorPixelShape.RoundCorners(0.5f), // Cũng bo tròn cho hình dạng phụ
                QrVectorBallShape.RoundCorners(0.5f, true, true, true, true), // Bo tròn các viên QR lớn
                QrVectorFrameShape.RoundCorners(0.25f, 1f, true, true, true, true, 7), // Bo tròn toàn bộ khung viền
                true
            )
        )
        .build()
}