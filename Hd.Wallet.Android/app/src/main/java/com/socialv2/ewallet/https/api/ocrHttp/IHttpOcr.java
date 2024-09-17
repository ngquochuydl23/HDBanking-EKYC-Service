package com.socialv2.ewallet.https.api.ocrHttp;



import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.idCard.IdCardExtractDto;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IHttpOcr {

    @POST("extract")
    @Multipart
    Observable<HttpResponseDto<IdCardExtractDto>> extractIdCard(
            @Part MultipartBody.Part front_id_card,
            @Part MultipartBody.Part back_id_card
    );

}