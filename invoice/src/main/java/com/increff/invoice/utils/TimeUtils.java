package com.increff.invoice.utils;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static String getCurrentTime()
    {
        LocalDateTime currentTime = LocalDateTime.now(ZoneOffset.UTC);
        return DateTimeFormatter.ofPattern("HH:mm:ss").format(currentTime);
    }
    public  static String getCurrentDate()
    {
        LocalDateTime currentDate = LocalDateTime.now(ZoneOffset.UTC);
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(currentDate);
    }

}
