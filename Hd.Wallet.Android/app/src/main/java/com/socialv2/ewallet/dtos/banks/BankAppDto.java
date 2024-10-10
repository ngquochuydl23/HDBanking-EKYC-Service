package com.socialv2.ewallet.dtos.banks;

import android.graphics.drawable.Drawable;

public class BankAppDto {
    private String appName;
    private Drawable logo;
    private String bin;

    public BankAppDto() { }

    public BankAppDto(String appName, Drawable logo) {
        this.appName = appName;
        this.logo = logo;
    }

    public BankAppDto(String appName, Drawable logo, String bin) {
        this.appName = appName;
        this.logo = logo;
        this.bin = bin;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getLogo() {
        return logo;
    }

    public void setLogo(Drawable logo) {
        this.logo = logo;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    @Override
    public String toString() {
        return "BankAppDto{" +
                "appName='" + appName + '\'' +
                ", logo='" + logo + '\'' +
                ", bin='" + bin + '\'' +
                '}';
    }
}
