package com.socialv2.ewallet.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;


public class Permissions {

    public static final int PERMISSION_REQUEST_CODE = 0x16777215;

    private Context mContext;
    private String[] mPermissions;

    public Permissions(Activity context) {
        mContext = context;
        mPermissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
    }

    public Permissions(Activity context, String[] permissions) {
        mContext = context;
        mPermissions = permissions;
    }

    public void request() {
        if (!checkIsGranted()) {
            ActivityCompat.requestPermissions((Activity) mContext, mPermissions, PERMISSION_REQUEST_CODE);
        }
    }

    public boolean checkIsGranted() {
        if (mPermissions == null || mPermissions.length == 0) {
            return false;
        }

        for (String permission : mPermissions) {
            if (ActivityCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
