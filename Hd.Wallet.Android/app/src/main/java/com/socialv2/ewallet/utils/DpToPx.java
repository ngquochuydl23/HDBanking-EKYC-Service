package com.socialv2.ewallet.utils;

import android.content.Context;
import android.util.TypedValue;

public class DpToPx {
    public static int convert(Context context, float sizeInDp) {

        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDp, context.getResources().getDisplayMetrics()));
    }
}
