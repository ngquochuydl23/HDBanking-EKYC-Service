package com.socialv2.ewallet.dtos.accounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountBankDto {
    @SerializedName("bankName")
    @Expose
    private String bankName;

    @SerializedName("bankAccountId")
    @Expose
    private String bankAccountId;

    @SerializedName("bankOwnerName")
    @Expose
    private String bankOwnerName;

    @SerializedName("idCardNo")
    @Expose
    private String idCardNo;

    public AccountBankDto() {}

    public AccountBankDto(String bankName, String bankAccountId, String bankOwnerName, String idCardNo) {
        this.bankName = bankName;
        this.bankAccountId = bankAccountId;
        this.bankOwnerName = bankOwnerName;
        this.idCardNo = idCardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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
        return "AccountBankDto{" +
                "bankName='" + bankName + '\'' +
                ", bankAccountId='" + bankAccountId + '\'' +
                ", bankOwnerName='" + bankOwnerName + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                '}';
    }
}
