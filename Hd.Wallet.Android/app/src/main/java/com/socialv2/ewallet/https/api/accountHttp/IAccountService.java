package com.socialv2.ewallet.https.api.accountHttp;


import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.auth.LoginResponseDto;

import io.reactivex.rxjava3.core.Observable;

public interface IAccountService {
    Observable<HttpResponseDto<AccountDto>> getAccountById(String accountId);
}
