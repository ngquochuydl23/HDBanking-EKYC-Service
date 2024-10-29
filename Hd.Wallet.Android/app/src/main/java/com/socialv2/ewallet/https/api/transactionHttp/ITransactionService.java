package com.socialv2.ewallet.https.api.transactionHttp;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.banks.BankDto;
import com.socialv2.ewallet.dtos.transactions.TransactionDto;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Query;

public interface ITransactionService {
    Observable<HttpResponseDto<List<TransactionDto>>> getTransactions(
            String transactionType,
            String transactionStatus,
            String transactionDateMin,
            String transactionDateMax,
            Double amountIn);
}
