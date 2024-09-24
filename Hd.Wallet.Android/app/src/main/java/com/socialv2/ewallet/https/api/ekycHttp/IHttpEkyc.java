package com.socialv2.ewallet.https.api.ekycHttp;



import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.faceVerification.FaceVerificationDto;
import com.socialv2.ewallet.dtos.idCard.IdCardExtractDto;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IHttpEkyc {

    @POST("id-card/extract")
    @Multipart
    Observable<HttpResponseDto<IdCardExtractDto>> extractIdCard(
            @Part MultipartBody.Part frontIdCard,
            @Part MultipartBody.Part backIdCard
    );

    @POST("face/verification")
    @Multipart
    Observable<HttpResponseDto<FaceVerificationDto>> faceVerfication(
            @Part("id_card_path") RequestBody frontIdCardUrl,
            @Part MultipartBody.Part face
    );
}