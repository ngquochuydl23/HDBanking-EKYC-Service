package com.socialv2.ewallet.https;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.sharedReferences.SaveTokenSharedPreference;
import com.socialv2.ewallet.ui.login.LoginActivity;
import com.socialv2.ewallet.ui.login.LoginPasswordActivity;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpSettingImpl implements IHttpSetting {

    private final String TAG = HttpSettingImpl.class.getName();

    private Retrofit.Builder builder;
    private String baseUrl;
    private Context context;


    public HttpSettingImpl(Context context) {
        this.context = context;
        baseUrl = context.getString(R.string.base_api_url);
        builder = createRetrofitBuilder(baseUrl);
    }

    public HttpSettingImpl(Context context, String otherBaseUrl) {
        this.context = context;

        baseUrl = otherBaseUrl;
        builder = createRetrofitBuilder(baseUrl);
    }

    @Override
    public Retrofit getRetrofitBuilder() {
        return builder.build();
    }


    private Retrofit.Builder createRetrofitBuilder(String url) {
        String accessToken = new SaveTokenSharedPreference(context).getData();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {

                        Request originalRequest = chain.request();
                        Headers headers = new Headers.Builder()
                                .addAll(originalRequest.headers())
                                .add("Authorization", "Bearer " + accessToken)
                                .build();

                        Request newRequest = originalRequest.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .headers(headers)
                                .build();
                        Response response = chain.proceed(newRequest);

                        // Check for 401 Unauthorized status code
                        Log.i(TAG, "StatusCode: " + response.code());
                        if (response.code() == 401) {
                            handleTokenExpired();
                        }

                        return response;
                    }
                });

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient.build())
                .build()
                .newBuilder();
    }

    private void handleTokenExpired() {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            Intent intent = new Intent(activity, LoginPasswordActivity.class); // Replace with your LoginActivity class
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
