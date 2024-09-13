package com.socialv2.ewallet.dtos.idCard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdCardExtractDto {

    @SerializedName("idCard")
    @Expose
    private IdCardDto idCard;


    @SerializedName("url")
    @Expose
    private String url;


    @SerializedName("type")
    @Expose
    private String type;

    public IdCardExtractDto() {

    }

    public IdCardExtractDto(IdCardDto idCard, String url, String type) {
        this.idCard = idCard;
        this.url = url;
        this.type = type;
    }

    public IdCardDto getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCardDto idCard) {
        this.idCard = idCard;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
