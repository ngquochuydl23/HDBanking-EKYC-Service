package com.socialv2.ewallet.https.api.transferHttp;

import android.content.Context;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.accounts.AccountBalanceDto;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.accounts.RequestLinkingAccount;
import com.socialv2.ewallet.dtos.transactions.TransactionDto;
import com.socialv2.ewallet.dtos.transfers.RequestBankTransferDto;
import com.socialv2.ewallet.dtos.transfers.RequestInternalTransferDto;
import com.socialv2.ewallet.https.HttpSettingImpl;
import com.socialv2.ewallet.https.IHttpSetting;
import com.socialv2.ewallet.utils.AesEncryptionUtils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TransferHttpImpl implements ITransferService {

    private IHttpSetting mHttpSetting;
    private IHttpTransfer mHttpTransfer;

    public TransferHttpImpl(Context context) {

        mHttpSetting = new HttpSettingImpl(context);
        mHttpTransfer = mHttpSetting
                .getRetrofitBuilder()
                .create(IHttpTransfer.class);
    }

    @Override
    public Observable<HttpResponseDto<TransactionDto>> bankTransfer(String pin, RequestBankTransferDto body) {
        return mHttpTransfer.bankTransfer(AesEncryptionUtils.encrypt(pin), body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<TransactionDto>> internalTransfer(String pin, RequestInternalTransferDto body) {
        return mHttpTransfer.internalTransfer(AesEncryptionUtils.encrypt(pin), body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
