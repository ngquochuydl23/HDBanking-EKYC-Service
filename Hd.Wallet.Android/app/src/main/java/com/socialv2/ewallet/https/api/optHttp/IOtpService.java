package com.socialv2.ewallet.https.api.optHttp;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.otp.RequestProvideOptDto;
import com.socialv2.ewallet.dtos.otp.RequestVerifyOtpDto;
import com.socialv2.ewallet.dtos.otp.ResponseProvideOtpDto;
import com.socialv2.ewallet.dtos.otp.ResponseVerifyOtpDto;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IOtpService {
    Observable<HttpResponseDto<ResponseProvideOtpDto>> requestProvideOtp(String key, String type);

    Observable<HttpResponseDto<ResponseVerifyOtpDto>> verifyOtp(String token, String otp);
}
