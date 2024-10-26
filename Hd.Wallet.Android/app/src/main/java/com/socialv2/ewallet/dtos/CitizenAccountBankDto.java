package com.socialv2.ewallet.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.socialv2.ewallet.dtos.banks.BankDto;

import java.util.Objects;

public class CitizenAccountBankDto {
    @SerializedName("accountNo")
    @Expose
    private String accountNo;

    @SerializedName("ownerName")
    @Expose
    private String ownerName;

    @SerializedName("idCardNo")
    @Expose
    private String idCardNo;

    @SerializedName("bankName")
    @Expose
    private String bankName;

    @SerializedName("bin")
    @Expose
    private String bin;

    @SerializedName("balance")
    @Expose
    private double balance;

    @SerializedName("openedAt")
    @Expose
    private String openedAt;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("bank")
    @Expose
    private BankDto bank;

    public CitizenAccountBankDto() {}

    public CitizenAccountBankDto(String accountNo, String ownerName, String idCardNo, String bankName, String bin, double balance, String openedAt, String status, BankDto bank) {
        this.accountNo = accountNo;
        this.ownerName = ownerName;
        this.idCardNo = idCardNo;
        this.bankName = bankName;
        this.bin = bin;
        this.balance = balance;
        this.openedAt = openedAt;
        this.status = status;
        this.bank = bank;
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

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(String openedAt) {
        this.openedAt = openedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BankDto getBank() {
        return bank;
    }

    public void setBank(BankDto bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "CitizenAccountBankDto{" +
                "accountNo='" + accountNo + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bin='" + bin + '\'' +
                ", balance=" + balance +
                ", openedAt='" + openedAt + '\'' +
                ", status='" + status + '\'' +
                ", bank=" + bank +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CitizenAccountBankDto that = (CitizenAccountBankDto) o;
        return Objects.equals(accountNo, that.accountNo) && Objects.equals(ownerName, that.ownerName) && Objects.equals(idCardNo, that.idCardNo) && Objects.equals(bin, that.bin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNo, ownerName, idCardNo, bin);
    }
}
