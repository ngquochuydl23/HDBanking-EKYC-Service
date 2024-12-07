package com.socialv2.ewallet.dtos.otp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseProvideOtpDto {
    @SerializedName("otpId")
    @Expose
    private int otpId;
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("expiredAt")
    @Expose
    private String expiredAt;

    public ResponseProvideOtpDto(int otpId, String expiredAt, String type, String token) {
        this.otpId = otpId;
        this.expiredAt = expiredAt;
        this.type = type;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getOtpId() {
        return otpId;
    }

    public void setOtpId(int otpId) {
        this.otpId = otpId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }

    @Override
    public String toString() {
        return "ResponseProvideOtpDto{" +
                "otpId=" + otpId +
                ", token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", expiredAt='" + expiredAt + '\'' +
                '}';
    }
}
