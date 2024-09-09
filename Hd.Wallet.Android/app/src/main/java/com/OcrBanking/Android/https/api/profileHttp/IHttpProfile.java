package com.OcrBanking.Android.https.api.profileHttp;



import com.socialv2.wechat.dtos.HttpResponseDto;
import com.socialv2.wechat.dtos.response.ResponseProfileDto;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface IHttpProfile {
    @GET("profile")
    @NonNull
    Observable<HttpResponseDto<ResponseProfileDto>> getProfile();

}
