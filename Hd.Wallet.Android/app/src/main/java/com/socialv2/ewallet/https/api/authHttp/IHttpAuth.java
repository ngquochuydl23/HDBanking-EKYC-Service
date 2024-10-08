package com.socialv2.ewallet.https.api.authHttp;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.auth.LoginRequestDto;
import com.socialv2.ewallet.dtos.auth.LoginResponseDto;
import com.socialv2.ewallet.dtos.checkOtp.CheckPhoneDto;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IHttpAuth {
    @POST("identity/Auth/sign-in")
    Observable<HttpResponseDto<LoginResponseDto>> login(@Body LoginRequestDto body);

}
