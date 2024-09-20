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

public class OcrIdCardServiceImpl implements IOcrIdCardService {
    private IHttpSetting mHttpSetting;
    private IHttpEkyc mHttpOcr;

    public OcrIdCardServiceImpl(Context context) {

        mHttpSetting = new HttpSettingImpl(context, "https://ocr-banking.pgonevn.com/ekyc-api/");
        mHttpOcr = mHttpSetting
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

        return mHttpOcr.extractIdCard(frontFile, backFile)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }


}
