package com.socialv2.ewallet.https.api.optHttp;

import android.content.Context;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.otp.RequestProvideOptDto;
import com.socialv2.ewallet.dtos.otp.RequestVerifyOtpDto;
import com.socialv2.ewallet.dtos.otp.ResponseProvideOtpDto;
import com.socialv2.ewallet.dtos.otp.ResponseVerifyOtpDto;
import com.socialv2.ewallet.https.HttpSettingImpl;
import com.socialv2.ewallet.https.IHttpSetting;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OtpServiceImpl implements IOtpService {

    private IHttpSetting mHttpSetting;
    private IHttpOtp mHttpOtp;

    public OtpServiceImpl(Context context) {
        mHttpSetting = new HttpSettingImpl(context);
        mHttpOtp = mHttpSetting
                .getRetrofitBuilder()
                .create(IHttpOtp.class);
    }

    @Override
    public Observable<HttpResponseDto<ResponseProvideOtpDto>> requestProvideOtp(String key, String type) {
        return mHttpOtp.requestProvideOtp(new RequestProvideOptDto(key, type))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<ResponseVerifyOtpDto>> verifyOtp(String token, String otp) {
        return mHttpOtp.verifyOtp(token, new RequestVerifyOtpDto(otp))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
