package com.socialv2.ewallet.https.api.userHttp;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.checkOtp.CheckPhoneDto;
import com.socialv2.ewallet.dtos.users.UserDto;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IHttpUser {
    @GET("account/User/FindUserByPhone")
    Observable<HttpResponseDto<Object>> getUserByPhone(@Query("phone") String phone);
}
