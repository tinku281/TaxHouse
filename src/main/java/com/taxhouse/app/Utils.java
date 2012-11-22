package com.taxhouse.app;

import java.util.Calendar;
import java.util.Date;

public class Utils {

	public static int getCurrentYear() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.YEAR);
	}

	public static double calculateTax(double salary, double percent) {
		return (salary / 100) * percent;
	}

}