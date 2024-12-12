package com.socialv2.ewallet.https.api.registerHttp;


import androidx.annotation.NonNull;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.RequestSignUpDto;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IRegisterService {
    @POST("account/Register")
    @NonNull
    Observable<HttpResponseDto<Object>> signUp(@Body RequestSignUpDto body);
}
