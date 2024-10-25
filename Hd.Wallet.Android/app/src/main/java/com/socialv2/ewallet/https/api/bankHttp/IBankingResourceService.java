package com.socialv2.ewallet.https.api.bankHttp;


import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.auth.LoginResponseDto;
import com.socialv2.ewallet.dtos.banks.BankDto;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface IBankingResourceService {
    Observable<HttpResponseDto<List<BankDto>>> getBanks(String search);

    Observable<HttpResponseDto<List<BankDto>>> getTopBanks();

    Observable<HttpResponseDto<BankDto>> getBankByBin(String bin);
}
