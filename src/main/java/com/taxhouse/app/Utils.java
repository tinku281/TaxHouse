package com.taxhouse.app;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

	private static NumberFormat dFormat;

	public static int getCurrentYear() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.YEAR);
	}

	public static double calculatePercentage(double amount, double percent) {
		return (amount / 100) * percent;
	}

	public static String formatDouble(double amount) {
		
		if(dFormat == null){
			dFormat = NumberFormat.getInstance();
			dFormat.setMaximumFractionDigits(2);
			dFormat.setGroupingUsed(false);
		}
		
		return dFormat.format(amount);
	}

}