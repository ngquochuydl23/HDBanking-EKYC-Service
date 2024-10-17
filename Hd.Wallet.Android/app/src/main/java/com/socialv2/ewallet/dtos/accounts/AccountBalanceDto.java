package com.socialv2.ewallet.dtos.accounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountBalanceDto {

    @SerializedName("balance")
    @Expose
    private double balance;

    @SerializedName("accountId")
    @Expose
    private String accountId;

    public AccountBalanceDto() {}

    public AccountBalanceDto(double balance, String accountId) {
        this.balance = balance;
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "AccountBalanceDto{" +
                "balance=" + balance +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
