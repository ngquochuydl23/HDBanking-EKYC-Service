package com.socialv2.ewallet.https.api.transferHttp;


import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.transactions.TransactionDto;
import com.socialv2.ewallet.dtos.transfers.RequestBankTransferDto;
import com.socialv2.ewallet.dtos.transfers.RequestInternalTransferDto;

import io.reactivex.rxjava3.core.Observable;

public interface ITransferService {
    Observable<HttpResponseDto<TransactionDto>> bankTransfer(String pin, RequestBankTransferDto body);


    Observable<HttpResponseDto<TransactionDto>> internalTransfer(String pin, RequestInternalTransferDto body);

}
