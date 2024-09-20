package com.socialv2.ewallet.dtos.faceVerification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class FaceVerificationResultDto {

    @SerializedName("verified")
    @Expose
    private Boolean verified;

    @SerializedName("distance")
    @Expose
    private Double distance;

    @SerializedName("threshold")
    @Expose
    private Double threshold;

    public FaceVerificationResultDto() { }

    public FaceVerificationResultDto(Boolean verified, Double distance, Double threshold) {
        this.verified = verified;
        this.distance = distance;
        this.threshold = threshold;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaceVerificationResultDto that = (FaceVerificationResultDto) o;
        return Objects.equals(verified, that.verified) && Objects.equals(distance, that.distance) && Objects.equals(threshold, that.threshold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(verified, distance, threshold);
    }

    @Override
    public String toString() {
        return "FaceVerificationResultDto{" +
                "verified=" + verified +
                ", distance=" + distance +
                ", threshold=" + threshold +
                '}';
    }
}
