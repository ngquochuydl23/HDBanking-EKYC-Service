package com.socialv2.ewallet.https.api.ocrHttp;

import android.graphics.Bitmap;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.idCard.IdCardExtractDto;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Part;

public interface IOcrIdCardService {
    Observable<HttpResponseDto<IdCardExtractDto>> extractIdCard(Bitmap front, Bitmap back, String phoneNumber);
}
