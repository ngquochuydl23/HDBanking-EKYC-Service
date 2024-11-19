package com.socialv2.ewallet.ui.profile;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.amazonaws.services.s3.AmazonS3Client;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.s3.S3Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        uploadFile();
    }

    private void uploadFile() {
        String fileName = "huyndaitucson";
        File fakeFile = new File(getApplication().getCacheDir(), fileName);

        try (FileOutputStream fos = new FileOutputStream(fakeFile)) {
            fos.write("kia K3".getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        S3Service.getInstance(this)
                .upload(fileName, fakeFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> Log.d("S3 Upload", "Upload successful, ETag: " + result.getETag()),
                        error -> Log.e("S3 Upload Error", "Failed to upload: " + error.getMessage())
                );
    }
}