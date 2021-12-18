package com.basicapiprogramming.kalai.genericfiles;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {
	
	private Util() {
		throw new IllegalStateException("Utility class");
	}
	
	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}
	
	public static String getCurrentFormattedTime(String dateFormatString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		Calendar calendar = Calendar.getInstance();
		return dateFormat.format(calendar.getTime());
	}
}
