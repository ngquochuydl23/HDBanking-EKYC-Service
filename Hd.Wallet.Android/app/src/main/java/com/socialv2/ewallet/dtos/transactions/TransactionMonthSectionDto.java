package com.socialv2.ewallet.dtos.transactions;

public class TransactionMonthSectionDto {
    private String monthYear;
    private double totalIn;
    private double totalOut;

    public TransactionMonthSectionDto() {
        
    }

    public TransactionMonthSectionDto(String monthYear, double totalIn, double totalOut) {
        this.monthYear = monthYear;
        this.totalIn = totalIn;
        this.totalOut = totalOut;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public double getTotalIn() {
        return totalIn;
    }

    public void setTotalIn(double totalIn) {
        this.totalIn = totalIn;
    }

    public double getTotalOut() {
        return totalOut;
    }

    public void setTotalOut(double totalOut) {
        this.totalOut = totalOut;
    }

    @Override
    public String toString() {
        return "TransactionMonthSectionDto{" +
                "monthYear='" + monthYear + '\'' +
                ", totalIn=" + totalIn +
                ", totalOut=" + totalOut +
                '}';
    }
}
