package com.socialv2.ewallet.https.api.accountHttp;

import android.content.Context;
import android.util.Log;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.accounts.AccountBalanceDto;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.accounts.RequestLinkingAccount;
import com.socialv2.ewallet.dtos.auth.LoginRequestDto;
import com.socialv2.ewallet.dtos.auth.LoginResponseDto;
import com.socialv2.ewallet.https.HttpSettingImpl;
import com.socialv2.ewallet.https.IHttpSetting;
import com.socialv2.ewallet.utils.AesEncryptionUtils;

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
    public Observable<HttpResponseDto<AccountDto>> addLinkingAccount(String pin, RequestLinkingAccount body) {
        return mHttpAccount.addLinkingAccount(AesEncryptionUtils.encrypt(pin), body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<List<AccountDto>>> getAccounts() {
        return mHttpAccount.getAccounts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<AccountBalanceDto>> getAccountBalance() {
        return mHttpAccount.getAccountBalance()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<AccountDto>> getPrimaryAccount() {
        return mHttpAccount.getPrimaryAccount()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<AccountDto>> unlinkAccount(String pin, String accountId) {
        return mHttpAccount.unlinkAccount(AesEncryptionUtils.encrypt(pin), accountId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<AccountDto>> getWalletAccountByPhone(String accountId) {
        return mHttpAccount.getWalletAccountByPhone(accountId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
