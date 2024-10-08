package com.socialv2.ewallet.https.api.authHttp;


import androidx.annotation.NonNull;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.auth.LoginResponseDto;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface IAuthService {
    Observable<HttpResponseDto<LoginResponseDto>> login(String phone, String password);
}
