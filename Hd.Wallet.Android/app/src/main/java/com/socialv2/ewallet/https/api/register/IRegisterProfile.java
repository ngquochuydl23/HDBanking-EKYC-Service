package com.socialv2.ewallet.https.api.register;


import androidx.annotation.NonNull;

import com.socialv2.ewallet.dtos.HttpResponseDto;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface IRegisterProfile {
    @GET("account/Register")
    @NonNull
    Observable<HttpResponseDto<Object>> getProfile();
}
