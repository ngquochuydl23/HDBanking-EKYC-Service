package com.socialv2.ewallet.https.api.bankHttp;

import com.socialv2.ewallet.dtos.CitizenAccountBankDto;
import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.banks.BankDto;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IHttpBankResource {
    @GET("bankingResource/Bank")
    Observable<HttpResponseDto<List<BankDto>>> getBanks(@Query("search") String search);

    @GET("bankingResource/Bank/top")
    Observable<HttpResponseDto<List<BankDto>>> getTopBanks();

    @GET("bankingResource/Bank/{bin}")
    Observable<HttpResponseDto<BankDto>> getBankByBin(@Path("bin") String bin);


    @GET("bankingResource/CitizenAccountBank")
    Observable<HttpResponseDto<CitizenAccountBankDto>> getCitizenAccountBank(
            @Query("bin") String bin,
            @Query("accountNo") String accountNo
    );
}
