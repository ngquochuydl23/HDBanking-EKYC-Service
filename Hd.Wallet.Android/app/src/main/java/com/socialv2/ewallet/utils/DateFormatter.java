package com.socialv2.ewallet.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateFormatter {


    public static String formatToVietnameseDateTime(String input) {
        try {
            DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");
            LocalDateTime localDateTime  = LocalDateTime.parse(input.substring(0, 23));
            ZonedDateTime utcDateTime = localDateTime.atZone(ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
            return utcDateTime.format(formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return "Ngày giờ không hợp lệ";
        }
    }

    public static String formatToVietnameseDate(String input) {
        try {
            DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");
            LocalDateTime localDateTime  = LocalDateTime.parse(input.substring(0, 23));
            ZonedDateTime utcDateTime = localDateTime.atZone(ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return utcDateTime.format(formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return "Ngày giờ không hợp lệ";
        }
    }
}
