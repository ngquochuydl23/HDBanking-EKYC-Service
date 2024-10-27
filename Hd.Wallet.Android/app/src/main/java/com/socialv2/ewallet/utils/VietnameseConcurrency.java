package com.socialv2.ewallet.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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

    public static String formatWithoutSymbol(double amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator(',');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);

        return formatter.format(amount);
    }

    public static double parseToDouble(String input) {
        try {
            // Loại bỏ dấu phân cách để chuyển thành số
            String cleanString = input.replaceAll("[^\\d.]", "");
            return Double.parseDouble(cleanString);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
