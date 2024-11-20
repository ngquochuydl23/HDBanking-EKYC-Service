package com.socialv2.ewallet.https.api.transactionHttp;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.banks.BankDto;
import com.socialv2.ewallet.dtos.transactions.RecentlyDestinationDto;
import com.socialv2.ewallet.dtos.transactions.TransactionDto;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IHttpTransaction {
    @GET("transaction/Transaction")
    Observable<HttpResponseDto<List<TransactionDto>>> getTransactions(
            @Query("TransactionType") String transactionType,
            @Query("TransactionStatus") String transactionStatus,
            @Query("TransactionDateMin") String transactionDateMin,
            @Query("TransactionDateMax") String transactionDateMax,
            @Query("AmountIn") Double amountIn);


    @GET("transaction/Transaction/RecentlyDestinations")
    Observable<HttpResponseDto<List<RecentlyDestinationDto>>> getRecentlyDestinations(
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
