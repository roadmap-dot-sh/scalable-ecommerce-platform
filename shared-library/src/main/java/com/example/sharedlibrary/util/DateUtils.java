/*
 * DateUtils.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.sharedlibrary.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * DateUtils.java
 *
 * @author Nguyen
 */
public final class DateUtils {
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static String format(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(ISO_FORMATTER) : null;
    }

    public static LocalDateTime parse(String dateTimeStr) {
        return dateTimeStr != null ? LocalDateTime.parse(dateTimeStr, ISO_FORMATTER) : null;
    }

    public static boolean isExpired(LocalDateTime dateTime, long expirationMinutes) {
        if (dateTime == null) return true;
        return ChronoUnit.MINUTES.between(dateTime, LocalDateTime.now()) > expirationMinutes;
    }

    private DateUtils() {
    }
}
