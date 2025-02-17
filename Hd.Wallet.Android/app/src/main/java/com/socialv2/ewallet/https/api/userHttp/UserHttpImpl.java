package com.socialv2.ewallet.https.api.userHttp;

import android.content.Context;

import androidx.annotation.NonNull;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.users.RequestUpdateAvatarDto;
import com.socialv2.ewallet.dtos.users.RequestUpdateUserDto;
import com.socialv2.ewallet.dtos.users.UserDto;
import com.socialv2.ewallet.https.HttpSettingImpl;
import com.socialv2.ewallet.https.IHttpSetting;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserHttpImpl implements IUserService {

    private final IHttpSetting mHttpSetting;
    private final IHttpUser mHttpUser;

    public UserHttpImpl(Context context) {
        mHttpSetting = new HttpSettingImpl(context);
        mHttpUser = mHttpSetting
                .getRetrofitBuilder()
                .create(IHttpUser.class);
    }

    @NonNull
    @Override
    public Observable<HttpResponseDto<UserDto>> getUserByPhone(String phone) {
        return mHttpUser.getUserByPhone(phone)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<UserDto>> getUserInfo() {
        return mHttpUser.getUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<UserDto>> updateAvatar(String url) {
        return mHttpUser.updateAvatar(new RequestUpdateAvatarDto(url))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<UserDto>> updateUserInfo(RequestUpdateUserDto body) {
        return mHttpUser.updateUserInfo(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

}
