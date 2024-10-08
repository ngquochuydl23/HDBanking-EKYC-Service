package com.socialv2.ewallet.https.api.register;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.checkOtp.CheckPhoneDto;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IHttpRegister {
    @POST("user/checkValidPhoneNumber")
    Observable<HttpResponseDto<Object>> checkValidPhoneNumber(@Body CheckPhoneDto body);

}
