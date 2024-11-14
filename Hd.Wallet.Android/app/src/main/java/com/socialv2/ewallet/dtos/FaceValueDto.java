package com.socialv2.ewallet.dtos;

import com.socialv2.ewallet.utils.FormatterUtil;

public class FaceValueDto {

    private double value;
    private String currency;

    public FaceValueDto() { }

    public FaceValueDto(double value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "FaceValueDto{" +
                "value=" + FormatterUtil.formatCurrencyVietNam(value) +
                ", currency='" + currency + '\'' +
                '}';
    }
}
