package com.xb.cinstar.utility;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Utility {
    private static final DateTimeFormatter fomatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static String convertDateToString(LocalDateTime date)
    {
       return date.format(fomatter);
    }
    public static LocalDateTime convertStringToDate(String date)
    {
        return LocalDateTime.parse(date,fomatter);
    }

    public Utility() {

    }
}
