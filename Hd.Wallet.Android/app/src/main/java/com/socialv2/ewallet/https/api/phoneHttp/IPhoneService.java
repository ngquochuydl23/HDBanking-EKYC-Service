package com.socialv2.ewallet.https.api.phoneHttp;

import android.graphics.Bitmap;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.idCard.IdCardExtractDto;

import io.reactivex.rxjava3.core.Observable;

public interface IPhoneService {
    Observable<HttpResponseDto<Object>> checkValidPhoneNumber(String phoneNumber);
}
