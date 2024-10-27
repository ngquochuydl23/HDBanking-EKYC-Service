package com.socialv2.ewallet.dtos.transactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionAccountBankDto {
    @SerializedName("bin")
    @Expose
    private String bin;

    @SerializedName("bankName")
    @Expose
    private String bankName;

    @SerializedName("shortName")
    @Expose
    private String shortName;

    @SerializedName("accountNo")
    @Expose
    private String accountNo;

    @SerializedName("ownerName")
    @Expose
    private String ownerName;

    @SerializedName("logoUrl")
    @Expose
    private String logoUrl;

    @SerializedName("bankFullName")
    @Expose
    private String bankFullName;

    public TransactionAccountBankDto() { }

    public TransactionAccountBankDto(String bin, String bankName, String shortName, String accountNo, String ownerName, String logoUrl, String bankFullName) {
        this.bin = bin;
        this.bankName = bankName;
        this.shortName = shortName;
        this.accountNo = accountNo;
        this.ownerName = ownerName;
        this.logoUrl = logoUrl;
        this.bankFullName = bankFullName;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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
    public String toString() {
        return "TransactionAccountBankDto{" +
                "bin='" + bin + '\'' +
                ", bankName='" + bankName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", bankFullName='" + bankFullName + '\'' +
                '}';
    }
}
