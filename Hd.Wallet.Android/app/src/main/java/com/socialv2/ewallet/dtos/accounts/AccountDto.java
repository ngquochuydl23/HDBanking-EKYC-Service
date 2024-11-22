package com.socialv2.ewallet.dtos.accounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.socialv2.ewallet.dtos.users.PublicUserDto;

import java.util.Objects;

public class AccountDto {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("isBankLinking")
    @Expose
    private boolean isBankLinking;

    @SerializedName("walletBalance")
    @Expose
    private double walletBalance;

    @SerializedName("transactionLimit")
    @Expose
    private double transactionLimit;

    @SerializedName("accountType")
    @Expose
    private int accountType;

    @SerializedName("accountBank")
    @Expose
    private AccountBankDto accountBank;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("user")
    @Expose
    private PublicUserDto user;

    public AccountDto() {

    }

    public AccountDto(String id, String userId, Boolean isBankLinking, double walletBalance, double transactionLimit, int accountType, AccountBankDto accountBank, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.isBankLinking = isBankLinking;
        this.walletBalance = walletBalance;
        this.transactionLimit = transactionLimit;
        this.accountType = accountType;
        this.accountBank = accountBank;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getBankLinking() {
        return isBankLinking;
    }

    public void setBankLinking(boolean bankLinking) {
        isBankLinking = bankLinking;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public double getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(double transactionLimit) {
        this.transactionLimit = transactionLimit;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public AccountBankDto getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(AccountBankDto accountBank) {
        this.accountBank = accountBank;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isBankLinking() {
        return isBankLinking;
    }

    public PublicUserDto getUser() {
        return user;
    }

    public void setUser(PublicUserDto user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "AccountDto{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", isBankLinking=" + isBankLinking +
                ", walletBalance=" + walletBalance +
                ", transactionLimit=" + transactionLimit +
                ", accountType=" + accountType +
                ", accountBank=" + accountBank +
                ", createdAt='" + createdAt + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return Double.compare(walletBalance, that.walletBalance) == 0 && Double.compare(transactionLimit, that.transactionLimit) == 0 && accountType == that.accountType && Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(isBankLinking, that.isBankLinking) && Objects.equals(accountBank, that.accountBank) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, isBankLinking, walletBalance, transactionLimit, accountType, accountBank, createdAt);
    }
}
