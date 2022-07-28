package com.bp.test.util;

import java.time.LocalDateTime;

public class Util {

    private Util(){
        // not called
    }

    public static LocalDateTime getStartDate(){
        return LocalDateTime.parse(String.format("%s-%s-%s 00:00:01", String.valueOf(LocalDateTime.now().getYear()), String.valueOf(LocalDateTime.now().getMonthValue()), String.valueOf(LocalDateTime.now().getDayOfMonth())));
    }

    public static LocalDateTime getEndDate(){
        return LocalDateTime.parse(String.format("%s-%s-%s 23:59:59", String.valueOf(LocalDateTime.now().getYear()), String.valueOf(LocalDateTime.now().getMonthValue()), String.valueOf(LocalDateTime.now().getDayOfMonth())));
    }


}
