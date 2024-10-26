package com.socialv2.ewallet.https.api.transferHttp;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.accounts.AccountBalanceDto;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.accounts.RequestLinkingAccount;
import com.socialv2.ewallet.dtos.transfers.RequestBankTransferDto;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IHttpTransfer {

    @POST("transaction/Transfer/BankTransfer")
    Observable<HttpResponseDto<AccountDto>> bankTransfer(
            @Header("X-EncryptedPin") String encryptedPin,
            @Body RequestBankTransferDto body
    );
}
