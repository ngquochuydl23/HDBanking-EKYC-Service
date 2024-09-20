package com.socialv2.ewallet.dtos.faceVerification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaceVerificationDto {

    @SerializedName("match_result")
    @Expose
    private FaceVerificationResultDto result;

    @SerializedName("face_url")
    @Expose
    private String faceUrl;

    public FaceVerificationDto() { }

    public FaceVerificationDto(FaceVerificationResultDto result, String faceUrl) {
        this.result = result;
        this.faceUrl = faceUrl;
    }

    public FaceVerificationResultDto getResult() {
        return result;
    }

    public void setResult(FaceVerificationResultDto result) {
        this.result = result;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    @Override
    public String toString() {
        return "FaceVerificationDto{" +
                "result=" + result +
                ", faceUrl='" + faceUrl + '\'' +
                '}';
    }
}
