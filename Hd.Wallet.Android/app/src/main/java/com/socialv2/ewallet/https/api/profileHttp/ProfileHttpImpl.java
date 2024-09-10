package com.socialv2.ewallet.https.api.profileHttp;

import android.content.Context;

import com.socialv2.ewallet.https.HttpSettingImpl;
import com.socialv2.ewallet.https.IHttpSetting;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileHttpImpl implements IHttpProfile {

    private IHttpSetting mHttpSetting;
    private IHttpProfile mHttpProfile;

    public ProfileHttpImpl(Context context) {

        mHttpSetting = new HttpSettingImpl(context);
        mHttpProfile = mHttpSetting
                .getRetrofitBuilder()
                .create(IHttpProfile.class);
    }


//    @Override
//    public Observable<HttpResponseDto<ResponseProfileDto>> getProfile() {
//        return mHttpProfile.getProfile()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io());
//    }
}
