package com.socialv2.ewallet.s3;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.reactivex.rxjava3.core.Single;


public class S3Service implements IAmazonS3Service {

    private static final String TAG = S3Service.class.getName();
    private static final String BUCKET_NAME = "hd-wallet";

    private CognitoCachingCredentialsProvider credentialsProvider;
    private AmazonS3Client s3Client;


    public S3Service(Context context) {
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "ap-southeast-2:6fb550fc-b9f2-49e9-9f52-ae568ed676ec",
                Regions.AP_SOUTHEAST_2
        );

        s3Client = new AmazonS3Client(credentialsProvider);
    }

    public AmazonS3Client getS3Client() {
        return s3Client;
    }

    public static S3Service getInstance(Context context) {
        return new S3Service(context);
    }

    @Override
    public Single<PutObjectResult> upload(String fileName, File file) {
        return Single.create(emitter -> {
            try {
                PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, fileName, file);
                PutObjectResult result = s3Client.putObject(request);

                Log.i(TAG, "Upload Successfully.");
                emitter.onSuccess(result);
            } catch (Exception e) {
                Log.e(TAG, "Upload Failed.", e);

                emitter.onError(e);
            }
        });
    }

    @Override
    public Single<PutObjectResult> upload(File file) {
        String uuidText = "UUID: " + UUID
                .randomUUID()
                .toString();

        String dateText = "Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return upload(uuidText + "-" + dateText, file);
    }

    public static String getUrl(String fileName) {
        return "https://hd-wallet.s3.ap-southeast-2.amazonaws.com/" + fileName;
    }
}
