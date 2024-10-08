package com.socialv2.ewallet.https.api.register;

import android.content.Context;

import androidx.annotation.NonNull;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.https.HttpSettingImpl;
import com.socialv2.ewallet.https.IHttpSetting;

import io.reactivex.rxjava3.core.Observable;

public class ProfileHttpImpl implements IRegisterProfile {

    private IHttpSetting mHttpSetting;
    private IRegisterProfile mHttpProfile;

    public ProfileHttpImpl(Context context) {

        mHttpSetting = new HttpSettingImpl(context);
        mHttpProfile = mHttpSetting
                .getRetrofitBuilder()
                .create(IRegisterProfile.class);
    }

    @NonNull
    @Override
    public Observable<HttpResponseDto<Object>> getProfile() {
        return null;
    }


//    @Override
//    public Observable<HttpResponseDto<ResponseProfileDto>> getProfile() {
//        return mHttpProfile.getProfile()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io());
//    }
}
