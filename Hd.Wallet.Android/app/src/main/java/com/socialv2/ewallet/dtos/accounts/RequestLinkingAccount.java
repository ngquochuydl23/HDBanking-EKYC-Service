package com.socialv2.ewallet.dtos.accounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestLinkingAccount {

    @SerializedName("bin")
    @Expose
    private String bin;

    @SerializedName("bankAccountId")
    @Expose
    private String bankAccountId;

    @SerializedName("bankOwnerName")
    @Expose
    private String bankOwnerName;

    @SerializedName("idCardNo")
    @Expose
    private String idCardNo;


    public RequestLinkingAccount() { }

    public RequestLinkingAccount(String bin, String bankAccountId, String bankOwnerName, String idCardNo) {
        this.bin = bin;
        this.bankAccountId = bankAccountId;
        this.bankOwnerName = bankOwnerName;
        this.idCardNo = idCardNo;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getBankOwnerName() {
        return bankOwnerName;
    }

    public void setBankOwnerName(String bankOwnerName) {
        this.bankOwnerName = bankOwnerName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    @Override
    public String toString() {
        return "RequestLinkingAccount{" +
                "bin='" + bin + '\'' +
                ", bankAccountId='" + bankAccountId + '\'' +
                ", bankOwnerName='" + bankOwnerName + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                '}';
    }
}
