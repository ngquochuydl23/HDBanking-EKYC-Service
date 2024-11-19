package com.socialv2.ewallet.s3;


import com.amazonaws.services.s3.model.PutObjectResult;
import java.io.File;
import io.reactivex.rxjava3.core.Single;

public interface IAmazonS3Service {
    Single<PutObjectResult> upload(String fileName, File file);

    Single<PutObjectResult> upload(File file);
}
