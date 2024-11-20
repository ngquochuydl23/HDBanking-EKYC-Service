package com.socialv2.ewallet.dtos.transactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class RecentlyDestinationDto {

    @SerializedName("userAvatar")
    @Expose
    private String userAvatar;

    @SerializedName("bin")
    @Expose
    private String bin;

    @SerializedName("ownerName")
    @Expose
    private String ownerName;

    @SerializedName("accountNo")
    @Expose
    private String accountNo;

    @SerializedName("bankName")
    @Expose
    private String bankName;

    @SerializedName("shortName")
    @Expose
    private String shortName;

    @SerializedName("logoUrl")
    @Expose
    private String logoUrl;

    @SerializedName("bankFullName")
    @Expose
    private String bankFullName;

    public RecentlyDestinationDto() {

    }

    public RecentlyDestinationDto(String userAvatar, String bin, String ownerName, String accountNo, String bankName, String shortName, String logoUrl, String bankFullName) {
        this.userAvatar = userAvatar;
        this.bin = bin;
        this.ownerName = ownerName;
        this.accountNo = accountNo;
        this.bankName = bankName;
        this.shortName = shortName;
        this.logoUrl = logoUrl;
        this.bankFullName = bankFullName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBankFullName() {
        return bankFullName;
    }

    public void setBankFullName(String bankFullName) {
        this.bankFullName = bankFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecentlyDestinationDto that = (RecentlyDestinationDto) o;
        return Objects.equals(bin, that.bin) && Objects.equals(accountNo, that.accountNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bin, accountNo);
    }

    @Override
    public String toString() {
        return "RecentlyTransactionDto{" +
                "userAvatar='" + userAvatar + '\'' +
                ", bin='" + bin + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", bankName='" + bankName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", bankFullName='" + bankFullName + '\'' +
                '}';
    }
}
