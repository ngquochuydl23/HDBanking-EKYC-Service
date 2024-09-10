package com.socialv2.ewallet.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NavigateUtil {


    public static void navigateTo(Context ctx, Class<?> activityClass) {
        Intent intent = new Intent(ctx, activityClass);
        ctx.startActivity(intent);
    }

    public static void navigateToWithId(Context ctx, Class<?> activityClass, String id) {
        Intent intent = new Intent(ctx, activityClass);
        intent.putExtra("id", id);
        ctx.startActivity(intent);
    }
}
