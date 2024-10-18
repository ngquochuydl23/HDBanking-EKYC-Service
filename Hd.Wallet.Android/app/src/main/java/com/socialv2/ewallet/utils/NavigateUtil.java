package com.socialv2.ewallet.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.socialv2.ewallet.ui.main.MainHomeActivity;

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

    public static void navigateToHomeClearState(Context ctx) {
        Intent intent = new Intent(ctx, MainHomeActivity.class); // Replace with your LoginActivity class
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
        ctx.startActivity(intent);

        Activity activity = (Activity) ctx;
        activity.finish();
    }
}
