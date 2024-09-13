package com.socialv2.ewallet.https.api.ocrHttp;


import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.idCard.IdCardExtractDto;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IHttpOcr {

    @POST("extract")
    @Multipart
    Observable<HttpResponseDto<IdCardExtractDto>> extractIdCard(
            @Part MultipartBody.Part file
            //,
       //     @Part("phoneNumber") RequestBody phoneNumber
    );

}