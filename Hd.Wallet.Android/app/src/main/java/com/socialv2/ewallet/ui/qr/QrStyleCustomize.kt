package com.socialv2.ewallet.ui.qr

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBackground
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

fun getQrStyle(context: Context, logoDrawable: Drawable?): QrVectorOptions {
    return QrVectorOptions.Builder()
        .setLogo(
            QrVectorLogo(
                drawable = logoDrawable ?: context.getDrawable(R.drawable.default_avatar_men),
                size = .25f,
                padding = QrVectorLogoPadding.Natural(.2f),
                shape = QrVectorLogoShape
                    .Circle
            )
        )
        .setBackground(
            QrVectorBackground(
                drawable = ContextCompat
                    .getDrawable(context, R.drawable.drawable_filled_r15),
            )
        )
        .setColors(
            QrVectorColors(
                dark = QrVectorColor
                    .Solid(ContextCompat.getColor(context, R.color.black)),
                ball = QrVectorColor.Solid(
                    ContextCompat.getColor(context, R.color.secondaryColor)
                ),
                frame = QrVectorColor.LinearGradient(
                    colors = listOf(
                        0f to android.graphics.Color.BLACK,
                        1f to android.graphics.Color.BLACK,
                    ),
                    orientation = QrVectorColor.LinearGradient
                        .Orientation.LeftDiagonal
                )
            )
        )
        .setShapes(
            QrVectorShapes(
                darkPixel = QrVectorPixelShape
                    .RoundCorners(.5f),
                ball = QrVectorBallShape
                    .RoundCorners(.25f),
                frame = QrVectorFrameShape
                    .RoundCorners(.25f),
            )
        )
        .build()
}