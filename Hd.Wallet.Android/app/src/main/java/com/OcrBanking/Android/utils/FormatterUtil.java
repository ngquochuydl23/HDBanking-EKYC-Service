package com.OcrBanking.Android.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatterUtil {
    public static String formatCurrencyVietNam(double money) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(money);
    }
}
