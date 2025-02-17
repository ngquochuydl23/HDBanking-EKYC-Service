package com.socialv2.ewallet.https.api.userHttp;

import androidx.annotation.NonNull;
import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.users.RequestUpdateUserDto;
import com.socialv2.ewallet.dtos.users.UserDto;

import io.reactivex.rxjava3.core.Observable;


public interface IUserService {
    Observable<HttpResponseDto<UserDto>> getUserByPhone(String phone);

    Observable<HttpResponseDto<UserDto>> getUserInfo();

    Observable<HttpResponseDto<UserDto>> updateAvatar(String url);

    Observable<HttpResponseDto<UserDto>> updateUserInfo(RequestUpdateUserDto body);



}