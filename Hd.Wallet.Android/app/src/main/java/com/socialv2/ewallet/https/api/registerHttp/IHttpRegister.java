package com.socialv2.ewallet.https.api.registerHttp;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.RequestSignUpDto;
import com.socialv2.ewallet.dtos.checkOtp.CheckPhoneDto;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IHttpRegister {
    Observable<HttpResponseDto<Object>> signUp(RequestSignUpDto body);
}
