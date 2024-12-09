package com.socialv2.ewallet.dtos.otp;

public class RequestVerifyOtpDto {
    private String otpCode;

    public RequestVerifyOtpDto() {

    }

    public RequestVerifyOtpDto(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getOtpCode() {
        return otpCode;
    }


    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }


    @Override
    public String toString() {
        return "RequestVerifyOtpDto{" +
                "otpCode='" + otpCode + '\'' +
                '}';
    }
}
