package com.xb.cinstar.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class Utility {
    // Định dạng cho LocalDate
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    // Định dạng cho LocalTime
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
    // Định dạng cho LocalDateTime
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Chuyển đổi từ LocalDate thành String
    public static String convertDateToString(LocalDate date) {
        if(date == null)
        {
            return  null;
        }
        return date.format(dateFormatter);
    }

    // Chuyển đổi từ String thành LocalDate
    public static LocalDate convertStringToDate(String date) {
        return LocalDate.parse(date, dateFormatter);
    }

    // Chuyển đổi từ LocalDateTime thành String
    public static String convertDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(dateTimeFormatter);
    }


    public static LocalTime convertStringToTime(String time) {
        if (time == null)
            return null;
        return LocalTime.parse(time, timeFormatter);
    }
    public static String convertTimeToString(LocalTime localTime) {
        if(localTime == null)
            return  null;
        return localTime.format(timeFormatter);
    }

    // Chuyển đổi từ String thành LocalDateTime
    public static LocalDateTime convertStringToDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    // Private constructor để chặn tạo instance cho lớp này
    private Utility() {
        throw new UnsupportedOperationException("Utility class");
    }
}
