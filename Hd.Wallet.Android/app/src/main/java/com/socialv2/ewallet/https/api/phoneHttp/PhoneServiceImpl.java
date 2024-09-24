package com.socialv2.ewallet.https.api.phoneHttp;

import android.content.Context;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.checkOtp.CheckPhoneDto;
import com.socialv2.ewallet.https.HttpSettingImpl;
import com.socialv2.ewallet.https.IHttpSetting;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PhoneServiceImpl implements IPhoneService{

    private IHttpSetting mHttpSetting;
    private IHttpPhone mHttpPhone;

    public PhoneServiceImpl(Context context) {

        mHttpSetting = new HttpSettingImpl(context);
        mHttpPhone = mHttpSetting
                .getRetrofitBuilder()
                .create(IHttpPhone.class);
    }

    @Override
    public Observable<HttpResponseDto<Object>> checkValidPhoneNumber(String phoneNumber) {

        return mHttpPhone.checkValidPhoneNumber(new CheckPhoneDto(phoneNumber))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
