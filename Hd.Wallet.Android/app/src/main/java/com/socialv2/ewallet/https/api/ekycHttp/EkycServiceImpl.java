package com.socialv2.ewallet.https.api.ekycHttp;

import android.content.Context;
import android.graphics.Bitmap;

import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.idCard.IdCardExtractDto;
import com.socialv2.ewallet.https.HttpSettingImpl;
import com.socialv2.ewallet.https.IHttpSetting;
import com.socialv2.ewallet.utils.ImageToBitmap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EkycServiceImpl implements IEkycService {
    private IHttpSetting mHttpSetting;
    private IHttpEkyc mHttpEkyc;

    public EkycServiceImpl(Context context) {

        mHttpSetting = new HttpSettingImpl(context, "https://ocr-banking.pgonevn.com/ekyc-api/");
        mHttpEkyc = mHttpSetting
                .getRetrofitBuilder()
                .create(IHttpEkyc.class);
    }

    @Override
    public Observable<HttpResponseDto<IdCardExtractDto>> extractIdCard(
            Bitmap front,
            Bitmap back,
            String phoneNumber) {

        byte[] frontByte = ImageToBitmap.bitmapToByte(front);
        byte[] backByte = ImageToBitmap.bitmapToByte(back);

        RequestBody frontRequestBody = RequestBody.create(MediaType.parse("image/jpeg"), frontByte);
        RequestBody backRequestBody = RequestBody.create(MediaType.parse("image/jpeg"), backByte);

        MultipartBody.Part frontFile = MultipartBody.Part
                .createFormData("front_id_card", "image.jpeg", frontRequestBody);

        MultipartBody.Part backFile = MultipartBody.Part
                .createFormData("back_id_card", "image1.png", backRequestBody);

        return mHttpEkyc.extractIdCard(frontFile, backFile)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<HttpResponseDto<Object>> faceVerification(Bitmap face, String frontIdCardUrl) {

        byte[] faceByte = ImageToBitmap.bitmapToByte(face);
        RequestBody faceRequestBody = RequestBody.create(MediaType.parse("image/jpeg"), faceByte);
        MultipartBody.Part frontFile = MultipartBody.Part
                .createFormData("face", "image.jpeg", faceRequestBody);

        RequestBody frontIdCardRequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                frontIdCardUrl
        );

        return mHttpEkyc.faceVerfication(frontIdCardRequestBody, frontFile)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }


}
