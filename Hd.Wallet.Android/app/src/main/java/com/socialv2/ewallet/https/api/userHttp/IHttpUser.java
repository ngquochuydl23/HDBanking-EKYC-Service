package com.socialv2.ewallet.https.api.userHttp;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.checkOtp.CheckPhoneDto;
import com.socialv2.ewallet.dtos.users.RequestUpdateAvatarDto;
import com.socialv2.ewallet.dtos.users.RequestUpdateUserDto;
import com.socialv2.ewallet.dtos.users.UserDto;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IHttpUser {
    @GET("account/User/FindUserByPhone")
    Observable<HttpResponseDto<UserDto>> getUserByPhone(@Query("phone") String phone);

    @GET("account/User/UserInfo")
    Observable<HttpResponseDto<UserDto>> getUserInfo();

    @POST("account/User/UpdateAvatar")
    Observable<HttpResponseDto<UserDto>> updateAvatar(@Body RequestUpdateAvatarDto body);


    @GET("account/User/UserInfo")
    Observable<HttpResponseDto<UserDto>> updateUserInfo(@Body RequestUpdateUserDto body);
}
