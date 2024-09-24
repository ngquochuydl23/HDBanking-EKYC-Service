package com.socialv2.ewallet.dtos.idCard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdCardExtractDto {

    @SerializedName("id_card")
    @Expose
    private IdCardDto idCard;


    @SerializedName("back-url")
    @Expose
    private String backUrl;

    @SerializedName("front-url")
    @Expose
    private String frontUrl;


    @SerializedName("type")
    @Expose
    private String type;

    public IdCardExtractDto() {

    }

    public IdCardExtractDto(IdCardDto idCard, String backUrl, String frontUrl, String type) {
        this.idCard = idCard;
        this.backUrl = backUrl;
        this.frontUrl = frontUrl;
        this.type = type;
    }

    public IdCardDto getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCardDto idCard) {
        this.idCard = idCard;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IdCardExtractDto{" +
                "idCard=" + idCard +
                ", backUrl='" + backUrl + '\'' +
                ", frontUrl='" + frontUrl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
