package com.socialv2.ewallet.utils;

import com.google.gson.Gson;
import com.socialv2.ewallet.dtos.HttpResponseDto;

import retrofit2.HttpException;
import retrofit2.Response;

public class ParseHttpError {
    public static HttpResponseDto<?> parse(Throwable throwable) throws Exception {
        HttpException httpException = (HttpException) throwable;
        Response<?> response = httpException.response();

        String errorBody = response
                .errorBody()
                .string();

        Gson gson = new Gson();
        return gson.fromJson(errorBody, HttpResponseDto.class);
    }

    public static int getStatusCode(Throwable throwable) throws Exception {
        HttpException httpException = (HttpException) throwable;
        Response<?> response = httpException.response();

        return response.code();
    }
}