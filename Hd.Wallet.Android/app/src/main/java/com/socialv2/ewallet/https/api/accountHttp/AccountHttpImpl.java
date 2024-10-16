package com.socialv2.ewallet.https.api.accountHttp;

import android.content.Context;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.accounts.RequestLinkingAccount;
import com.socialv2.ewallet.dtos.auth.LoginRequestDto;
import com.socialv2.ewallet.dtos.auth.LoginResponseDto;
import com.socialv2.ewallet.https.HttpSettingImpl;
import com.socialv2.ewallet.https.IHttpSetting;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AccountHttpImpl implements IAccountService {

    private IHttpSetting mHttpSetting;
    private IHttpAccount mHttpAccount;

    public AccountHttpImpl(Context context) {

        mHttpSetting = new HttpSettingImpl(context);
        mHttpAccount = mHttpSetting
                .getRetrofitBuilder()
                .create(IHttpAccount.class);
    }

    @Override
    public Observable<HttpResponseDto<AccountDto>> getAccountById(String accountId) {
        return mHttpAccount.getAccountById(accountId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<AccountDto>> addLinkingAccount(RequestLinkingAccount body) {
        return mHttpAccount.addLinkingAccount(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<List<AccountDto>>> getAccounts() {
        return mHttpAccount.getAccounts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
