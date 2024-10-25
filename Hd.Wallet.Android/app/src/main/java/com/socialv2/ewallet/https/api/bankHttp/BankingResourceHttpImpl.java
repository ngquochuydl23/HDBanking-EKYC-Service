package com.socialv2.ewallet.https.api.bankHttp;

import android.content.Context;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.auth.LoginRequestDto;
import com.socialv2.ewallet.dtos.auth.LoginResponseDto;
import com.socialv2.ewallet.dtos.banks.BankDto;
import com.socialv2.ewallet.https.HttpSettingImpl;
import com.socialv2.ewallet.https.IHttpSetting;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BankingResourceHttpImpl implements IBankingResourceService {

    private IHttpSetting mHttpSetting;
    private IHttpBankResource mHttpBankingResource;

    public BankingResourceHttpImpl(Context context) {

        mHttpSetting = new HttpSettingImpl(context);
        mHttpBankingResource = mHttpSetting
                .getRetrofitBuilder()
                .create(IHttpBankResource.class);
    }

    @Override
    public Observable<HttpResponseDto<List<BankDto>>> getBanks(String search) {
        return mHttpBankingResource.getBanks(search)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<List<BankDto>>> getTopBanks() {
        return mHttpBankingResource.getTopBanks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<BankDto>> getBankByBin(String bin) {
        return mHttpBankingResource.getBankByBin(bin)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
