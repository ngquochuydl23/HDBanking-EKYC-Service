package com.socialv2.ewallet.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class VietnameseConcurrency {
    public static String format(double amount) {
        Locale vietnam = new Locale("vi", "VN");

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(vietnam);
        String formattedAmount = currencyFormat.format(amount);

        if (!formattedAmount.endsWith("₫")) {
            formattedAmount = formattedAmount.replace("₫", "") + " ₫";
        }
        return formattedAmount;
    }
}
