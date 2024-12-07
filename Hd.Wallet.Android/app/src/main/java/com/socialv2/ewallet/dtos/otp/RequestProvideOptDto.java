package com.socialv2.ewallet.dtos.otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestProvideOptDto {
    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("type")
    @Expose
    private String type;

    public RequestProvideOptDto(String key, String type) {
        this.key = key;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RequestProvideOptDto{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
