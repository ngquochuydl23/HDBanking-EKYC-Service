package com.socialv2.ewallet.https.api.phoneHttp;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.checkOtp.CheckPhoneDto;
import com.socialv2.ewallet.dtos.idCard.IdCardExtractDto;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IHttpPhone {
    @POST("user/checkValidPhoneNumber")
    Observable<HttpResponseDto<Object>> checkValidPhoneNumber(@Body CheckPhoneDto body);
}
