package com.socialv2.ewallet.https.api.ekycHttp;

import android.graphics.Bitmap;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.faceVerification.FaceVerificationDto;
import com.socialv2.ewallet.dtos.idCard.IdCardExtractDto;

import io.reactivex.rxjava3.core.Observable;

public interface IEkycService {

    Observable<HttpResponseDto<IdCardExtractDto>> extractIdCard(Bitmap front, Bitmap back, String phoneNumber);

    Observable<HttpResponseDto<FaceVerificationDto>> faceVerification(Bitmap face, String frontIdCardUrl);
}
