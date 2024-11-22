package com.socialv2.ewallet.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class LocalFile {
    public static File getFileFromUri(Context context, Uri uri) throws Exception {
        ContentResolver contentResolver = context.getContentResolver();

        // Lấy tên file từ Uri
        String fileName = getFileName(context, uri);
        File tempFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);

        try (InputStream inputStream = contentResolver.openInputStream(uri);
             FileOutputStream outputStream = new FileOutputStream(tempFile)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }

        return tempFile;
    }

    // Lấy tên file từ Uri
    public static String getFileName(Context context, Uri uri) {
        String fileName = "temp_file";
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (cursor.moveToFirst() && nameIndex != -1) {
                fileName = cursor.getString(nameIndex);
            }
            cursor.close();
        }

        return fileName;
    }
}
