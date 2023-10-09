package com.amdigital.sales.utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Miscellaneous utilities for managing dates.
 *
 * @author Alejandra Villa Fernandes (alejandra.villafernandes@gmail.com)
 */
public class DateUtilities {
	private DateUtilities() {
		super();
	}

	/**
     * Default date format.
     */
    private static final Format DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    /**
     * Convert date in string format to millisecond format.
     *
     * @param date Original date.
     * @return A date in milliseconds format.
     * @throws ParseException 
     */
    public static Date stringDateToDate(String date) throws ParseException {
    	return (Date) DEFAULT_FORMAT.parseObject(date);
    }

    /**
     * Gets the first day of month given a date.
     *
     * @param date Original date.
     * @return First day of month in miliseconds format.
     */
    public static Date dateToStartOfMonth(Date date) {
    	Instant instantOrigin = Instant.ofEpochSecond(date.getTime());
        LocalDateTime ldtOrigin = LocalDateTime.ofInstant(instantOrigin, ZoneId.of("UTC").normalized());
        ZonedDateTime startOfDayOrigin = ldtOrigin.toLocalDate().withDayOfMonth(1).atStartOfDay().atZone(ZoneId.of("UTC").normalized());
        return new Date(startOfDayOrigin.toInstant().toEpochMilli());
    }
    
    /**
     * Change Date to String format yyyyMM.
     *
     * @param date Original date.
     * @return String with format.
     */
    public static String dateToYearAndMonthString(Date date) {
    	String pattern = "yyyy-MM";
    	DateFormat df = new SimpleDateFormat(pattern);
    	return df.format(date);
    }

}
