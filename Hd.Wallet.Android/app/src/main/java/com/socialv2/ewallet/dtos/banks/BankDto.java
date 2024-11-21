package com.socialv2.ewallet.dtos.banks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankDto {

    @SerializedName("shortName")
    @Expose
    private String shortName;

    @SerializedName("androidAppId")
    @Expose
    private String androidAppId;

    @SerializedName("logoApp")
    @Expose
    private String logoApp;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("bin")
    @Expose
    private String bin;

    @SerializedName("top")
    @Expose
    private String top;

    public BankDto() {}

    public BankDto(String logoApp) {
        this.logoApp = logoApp;
    }

    public BankDto(String shortName, String androidAppId, String logoApp, String name, String code, String bin, String top) {
        this.shortName = shortName;
        this.androidAppId = androidAppId;
        this.logoApp = logoApp;
        this.name = name;
        this.code = code;
        this.bin = bin;
        this.top = top;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAndroidAppId() {
        return androidAppId;
    }

    public void setAndroidAppId(String androidAppId) {
        this.androidAppId = androidAppId;
    }

    public String getLogoApp() {
        return logoApp;
    }

    public void setLogoApp(String logoApp) {
        this.logoApp = logoApp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return "BankDto{" +
                "shortName='" + shortName + '\'' +
                ", androidAppId='" + androidAppId + '\'' +
                ", logoApp='" + logoApp + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", bin='" + bin + '\'' +
                ", top='" + top + '\'' +
                '}';
    }
}
