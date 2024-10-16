package com.socialv2.ewallet.https.api.bankHttp;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.banks.BankDto;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IHttpBankResource {
    @GET("bankingResource/Bank")
    Observable<HttpResponseDto<List<BankDto>>> getBanks();

    @GET("bankingResource/Bank/top")
    Observable<HttpResponseDto<List<BankDto>>> getTopBanks();

    @GET("bankingResource/Bank/{bin}")
    Observable<HttpResponseDto<BankDto>> getBankByBin(@Path("bin") String bin);
}
