package com.socialv2.ewallet.https.api.accountHttp;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.auth.LoginRequestDto;
import com.socialv2.ewallet.dtos.auth.LoginResponseDto;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IHttpAccount {
    @GET("account/Account/{accountId}")
    Observable<HttpResponseDto<AccountDto>> getAccountById(@Path("accountId") String accountId);
}
