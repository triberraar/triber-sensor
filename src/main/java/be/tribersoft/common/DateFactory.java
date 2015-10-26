package be.tribersoft.common;

import java.util.Date;

public class DateFactory {

	private static Date date;

	public static void fixateDate(Date fixationDate) {
		date = fixationDate;
	}

	public static void release() {
		date = null;
	}

	public static Date generate() {
		return date == null ? new Date() : date;
	}
}
