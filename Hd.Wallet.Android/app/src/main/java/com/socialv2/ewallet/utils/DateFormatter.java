package com.socialv2.ewallet.utils;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateFormatter {
    public static String formatToVietnameseDate(String input) {
        try {
            // Định dạng chuỗi ISO 8601 có offset (+00:00)
            DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");

            // Chuyển chuỗi thành OffsetDateTime
            OffsetDateTime dateTime = OffsetDateTime.parse(input, isoFormatter);

            // Định dạng thành ngày tiếng Việt: "27 tháng 10, 2024"
            DateTimeFormatter vietnameseFormatter = DateTimeFormatter.ofPattern("d 'tháng' M, yyyy", new Locale("vi", "VN"));

            return dateTime.format(vietnameseFormatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return "Ngày không hợp lệ";
        }
    }
}
