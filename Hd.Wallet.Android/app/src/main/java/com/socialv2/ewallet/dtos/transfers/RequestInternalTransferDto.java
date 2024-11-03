package com.socialv2.ewallet.dtos.transfers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestInternalTransferDto {

    @SerializedName("sourceAccountId")
    @Expose
    private String sourceAccountId;

    @SerializedName("destWalletAccountNo")
    @Expose
    private String destWalletAccountNo;

    @SerializedName("transferContent")
    @Expose
    private String transferContent;

    @SerializedName("transferAmount")
    @Expose
    private double transferAmount;

    @SerializedName("useLinkingBank")
    @Expose
    private boolean useLinkingBank;

    public RequestInternalTransferDto() { }

    public RequestInternalTransferDto(String sourceAccountId, String destWalletAccountNo, String transferContent, double transferAmount, boolean useLinkingBank) {
        this.sourceAccountId = sourceAccountId;
        this.destWalletAccountNo = destWalletAccountNo;
        this.transferContent = transferContent;
        this.transferAmount = transferAmount;
        this.useLinkingBank = useLinkingBank;
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getDestWalletAccountNo() {
        return destWalletAccountNo;
    }

    public void setDestWalletAccountNo(String destWalletAccountNo) {
        this.destWalletAccountNo = destWalletAccountNo;
    }

    public String getTransferContent() {
        return transferContent;
    }

    public void setTransferContent(String transferContent) {
        this.transferContent = transferContent;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public boolean isUseLinkingBank() {
        return useLinkingBank;
    }

    public void setUseLinkingBank(boolean useLinkingBank) {
        this.useLinkingBank = useLinkingBank;
    }

    @Override
    public String toString() {
        return "RequestInternalTransferDto{" +
                "sourceAccountId='" + sourceAccountId + '\'' +
                ", destWalletAccountNo='" + destWalletAccountNo + '\'' +
                ", transferContent='" + transferContent + '\'' +
                ", transferAmount=" + transferAmount +
                ", useLinkingBank=" + useLinkingBank +
                '}';
    }
}
