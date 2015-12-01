package be.tribersoft.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateFactory {

	private static Date date;

	public static void fixateDate(LocalDateTime fixationDate) {
		date = Date.from(fixationDate.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static void release() {
		date = null;
	}

	public static Date generate() {
		return date == null ? new Date() : date;
	}
}
