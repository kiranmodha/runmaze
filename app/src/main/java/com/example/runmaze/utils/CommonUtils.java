package com.example.runmaze.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CommonUtils {

    public static String getStringDateDMY(String dateYMD) {
        Date date = null;
        DateFormat dfYMD = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Convert string into Date
            date = dfYMD.parse(dateYMD);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dfDMY = new SimpleDateFormat("dd/MM/yyyy");
        return (dfDMY.format(date));
    }


    public static String TitleCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }

    public static Date convertToDate(String dateTimeYMDHM) {
        Date theDate = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            // Convert string into Date
            theDate = df.parse(dateTimeYMDHM);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return theDate;
    }
}
