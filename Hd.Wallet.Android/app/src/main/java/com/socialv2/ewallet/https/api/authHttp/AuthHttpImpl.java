package com.socialv2.ewallet.https.api.authHttp;

import android.content.Context;

import androidx.annotation.NonNull;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.auth.LoginRequestDto;
import com.socialv2.ewallet.dtos.auth.LoginResponseDto;
import com.socialv2.ewallet.https.HttpSettingImpl;
import com.socialv2.ewallet.https.IHttpSetting;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthHttpImpl implements IAuthService {

    private IHttpSetting mHttpSetting;
    private IHttpAuth mHttpAuth;

    public AuthHttpImpl(Context context) {

        mHttpSetting = new HttpSettingImpl(context);
        mHttpAuth = mHttpSetting
                .getRetrofitBuilder()
                .create(IHttpAuth.class);
    }

    @Override
    public Observable<HttpResponseDto<LoginResponseDto>> login(String phone, String password) {
        return mHttpAuth.login(new LoginRequestDto(phone, password))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
