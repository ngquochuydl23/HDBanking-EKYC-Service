package com.socialv2.ewallet.https.api.transactionHttp;

import android.content.Context;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.transactions.RecentlyDestinationDto;
import com.socialv2.ewallet.dtos.transactions.TransactionDto;
import com.socialv2.ewallet.https.HttpSettingImpl;
import com.socialv2.ewallet.https.IHttpSetting;
import com.socialv2.ewallet.https.api.bankHttp.IHttpBankResource;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TransactionHttpImpl implements ITransactionService {


    private IHttpSetting mHttpSetting;
    private IHttpTransaction mHttpTransaction;

    public TransactionHttpImpl(Context context) {

        mHttpSetting = new HttpSettingImpl(context);
        mHttpTransaction = mHttpSetting
                .getRetrofitBuilder()
                .create(IHttpTransaction.class);
    }

    @Override
    public Observable<HttpResponseDto<List<TransactionDto>>> getTransactions(String transactionType, String transactionStatus, String transactionDateMin, String transactionDateMax, Double amountIn) {
        return mHttpTransaction.getTransactions(
                        transactionType,
                        transactionStatus,
                        transactionDateMin,
                        transactionDateMax,
                        amountIn
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<List<RecentlyDestinationDto>>> getRecentlyDestinations(int limit, int offset) {
        return mHttpTransaction.getRecentlyDestinations(limit <= 0 ? 10 : limit, Integer.max(0, offset))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
