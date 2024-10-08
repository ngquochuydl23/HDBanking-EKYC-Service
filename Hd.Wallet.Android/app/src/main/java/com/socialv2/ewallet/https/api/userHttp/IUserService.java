package com.socialv2.ewallet.https.api.userHttp;

import androidx.annotation.NonNull;
import com.socialv2.ewallet.dtos.HttpResponseDto;

import io.reactivex.rxjava3.core.Observable;


public interface IUserService {
    Observable<HttpResponseDto<Object>> getUserByPhone(String phone);
}
