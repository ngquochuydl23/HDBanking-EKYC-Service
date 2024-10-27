package com.socialv2.ewallet.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateFormatter {
    public static String formatToVietnameseDate(String input) {
        try {
            // Định dạng chuỗi ISO 8601 (UTC) đầu vào
            DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");

            // Chuyển chuỗi thành LocalDate
            LocalDate date = LocalDate.parse(input, isoFormatter);

            // Định dạng thành ngày tiếng Việt: "12 tháng 4, 2021"
            DateTimeFormatter vietnameseFormatter = DateTimeFormatter.ofPattern("d 'tháng' M, yyyy", new Locale("vi", "VN"));
            return date.format(vietnameseFormatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return "Ngày không hợp lệ";
        }
    }
}
