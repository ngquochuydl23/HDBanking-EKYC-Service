package com.socialv2.ewallet.https.api.optHttp;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.otp.RequestProvideOptDto;
import com.socialv2.ewallet.dtos.otp.RequestVerifyOtpDto;
import com.socialv2.ewallet.dtos.otp.ResponseProvideOtpDto;
import com.socialv2.ewallet.dtos.otp.ResponseVerifyOtpDto;
import com.socialv2.ewallet.dtos.users.RequestUpdateAvatarDto;
import com.socialv2.ewallet.dtos.users.UserDto;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IHttpOtp {

    @POST("account/Otp/RequestProvideOtp")
    Observable<HttpResponseDto<ResponseProvideOtpDto>> requestProvideOtp(@Body RequestProvideOptDto body);

    @POST("account/Otp/VerifyOtp")
    Observable<HttpResponseDto<ResponseVerifyOtpDto>> verifyOtp(
            @Header("Token") String token,
            @Body RequestVerifyOtpDto body);


}
