package com.socialv2.ewallet.https.api.registerHttp;

import android.content.Context;

import androidx.annotation.NonNull;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.RequestSignUpDto;
import com.socialv2.ewallet.dtos.otp.RequestVerifyOtpDto;
import com.socialv2.ewallet.https.HttpSettingImpl;
import com.socialv2.ewallet.https.IHttpSetting;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterHttpImpl implements IRegisterService {

    private IHttpSetting mHttpSetting;
    private IRegisterService mHttpRegister;

    public RegisterHttpImpl(Context context) {

        mHttpSetting = new HttpSettingImpl(context);
        mHttpRegister = mHttpSetting
                .getRetrofitBuilder()
                .create(IRegisterService.class);
    }

    @NonNull
    @Override
    public Observable<HttpResponseDto<Object>> signUp(RequestSignUpDto body) {
        return mHttpRegister.signUp(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
