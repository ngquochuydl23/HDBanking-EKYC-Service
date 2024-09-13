package com.socialv2.ewallet.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class HttpResponseDto<T> implements Serializable {


    @SerializedName("statusCode")
    @Expose
    private int statusCode;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("result")
    @Expose
    private T result;


    public HttpResponseDto() {

    }

    public HttpResponseDto(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public HttpResponseDto(int statusCode, T result) {
        this.statusCode = statusCode;
        this.result = result;
    }

    public HttpResponseDto(int statusCode, String msg, T result) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.result = result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HttpResponseDto)) return false;
        HttpResponseDto<?> that = (HttpResponseDto<?>) o;
        return getStatusCode() == that.getStatusCode() && Objects.equals(getMsg(), that.getMsg()) && Objects.equals(getResult(), that.getResult());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatusCode(), getMsg(), getResult());
    }

    @Override
    public String toString() {
        return "HttpResponseDto{" +
                "statusCode=" + statusCode +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
