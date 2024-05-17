package com.kajisaab.ecommerce.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class HelperUtils {
    private static final Logger logger = LoggerFactory.getLogger(HelperUtils.class);
    private HelperUtils() {
        // private constructor
    }


    //convert 2024-02-29 10:39:17.102 to Instant
    public static LocalDateTime toCustomDateTimeFormatter(String dateTime) {

        if (dateTime == null || dateTime.isEmpty()) {
            // Handle the case when the input is null or empty
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.SSS][.SS][.S]");

        // Parse the timestamp string into a LocalDateTime object
        return LocalDateTime.parse(dateTime, formatter);
    }

    public static LocalDateTime toLocalDateTimeFormatter(String dateTime){
        if (dateTime == null || dateTime.isEmpty()) {
            return null;
        }
        // Define date-time formatters for both types of timestamps
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy, hh:mm:ss a");
        DateTimeFormatter formatter2 = DateTimeFormatter.ISO_INSTANT;

        LocalDateTime localDateTimeFomat = null;
        try {
            // Attempt to parse timestamp1 with formatter1
            localDateTimeFomat = LocalDateTime.parse(dateTime, formatter1);
        } catch (DateTimeParseException e1) {
            // If parsing with formatter1 fails, attempt to parse timestamp2 with formatter2
            try {
                localDateTimeFomat = LocalDateTime.parse(dateTime, formatter2);
            } catch (DateTimeParseException e2) {
                // If parsing with both formatters fails, print an error message
                logger.error(e2.getMessage());
            }
        }
        return localDateTimeFomat;
    }

    public static boolean isBlankOrNull(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static String camelToSnake(String camel) {
        StringBuilder snakeBuilder = new StringBuilder();
        boolean isFirst = true;
        for(char c : camel.toCharArray()) {
            if(Character.isUpperCase(c)) {
                if(!isFirst){
                    snakeBuilder.append("_");
                }
                snakeBuilder.append(Character.toLowerCase(c));
            }else {
                snakeBuilder.append(c);
            }
            isFirst = false;
        }
        return snakeBuilder.toString();
    }
}