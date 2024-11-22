package com.socialv2.ewallet.dtos.transactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDto {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("amount")
    @Expose
    private double amount;

    @SerializedName("sourceAccount")
    @Expose
    private TransactionAccountBankDto sourceAccount;

    @SerializedName("destAccount")
    @Expose
    private TransactionAccountBankDto destAccount;

    @SerializedName("transactionDate")
    @Expose
    private String transactionDate;

    @SerializedName("transactionType")
    @Expose
    private String transactionType;

    @SerializedName("transactionStatus")
    @Expose
    private String transactionStatus;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("transferContent")
    @Expose
    private String transferContent;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("isBankingTransfer")
    @Expose
    private boolean isBankingTransfer;

    @SerializedName("useSourceAsLinkingBank")
    @Expose
    private boolean useSourceAsLinkingBank;

    @SerializedName("senderUserId")
    private String senderUserId;


    @SerializedName("receiverUserId")
    private String receiverUserId;


    public TransactionDto() { }

    public TransactionDto(String id, double amount, TransactionAccountBankDto sourceAccount, TransactionAccountBankDto destAccount, String transactionDate, String transactionType, String transactionStatus, String description, String transferContent, String createdAt, boolean isBankingTransfer, boolean useSourceAsLinkingBank, String senderUserId, String receiverUserId) {
        this.id = id;
        this.amount = amount;
        this.sourceAccount = sourceAccount;
        this.destAccount = destAccount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.description = description;
        this.transferContent = transferContent;
        this.createdAt = createdAt;
        this.isBankingTransfer = isBankingTransfer;
        this.useSourceAsLinkingBank = useSourceAsLinkingBank;
        this.senderUserId = senderUserId;
        this.receiverUserId = receiverUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionAccountBankDto getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(TransactionAccountBankDto sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public TransactionAccountBankDto getDestAccount() {
        return destAccount;
    }

    public void setDestAccount(TransactionAccountBankDto destAccount) {
        this.destAccount = destAccount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransferContent() {
        return transferContent;
    }

    public void setTransferContent(String transferContent) {
        this.transferContent = transferContent;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isBankingTransfer() {
        return isBankingTransfer;
    }

    public void setBankingTransfer(boolean bankingTransfer) {
        isBankingTransfer = bankingTransfer;
    }

    public boolean isUseSourceAsLinkingBank() {
        return useSourceAsLinkingBank;
    }

    public void setUseSourceAsLinkingBank(boolean useSourceAsLinkingBank) {
        this.useSourceAsLinkingBank = useSourceAsLinkingBank;
    }

    public String getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(String receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", sourceAccount=" + sourceAccount +
                ", destAccount=" + destAccount +
                ", transactionDate='" + transactionDate + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", description='" + description + '\'' +
                ", transferContent='" + transferContent + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", isBankingTransfer=" + isBankingTransfer +
                ", useSourceAsLinkingBank=" + useSourceAsLinkingBank +
                ", senderUserId='" + senderUserId + '\'' +
                ", receiverUserId='" + receiverUserId + '\'' +
                '}';
    }
}
