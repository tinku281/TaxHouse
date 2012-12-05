package com.taxhouse.model;

import java.util.Calendar;
import java.util.Date;

import org.drools.spi.KnowledgeHelper;

import com.taxhouse.app.Utils;
import com.taxhouse.model.ArmedForcePersonnel.SpecialTask;
import com.taxhouse.model.TaxRecord.Entry;

public class Format {

	SeniorCitizen item;
	TaxRecord taxRecord;
	KnowledgeHelper drools;
	SpecialTask specialtask;

	private void format() {

		// Date enddate = specialtask.getEndDate();
		// Date now = new Date();
		//
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(enddate);
		//
		// Calendar cal1 = Calendar.getInstance();
		// cal1.setTime(now);
		// cal.add(Calendar.YEAR, -10); // subtract 10 years from current date
		//
		// if (cal.after(cal1)) {
		// double taxCal = 0;
		// double exmpPer = 60;
		//
		// taxCal = Utils.calculatePercentage(taxRecord.getTotalTax(), exmpPer);
		// item.setTax(taxRecord.getTotalTax() - taxCal);
		// taxRecord.addEntry(new
		// Entry("Tax Exemption Rebate (Personnel Previously worked in A2 Combat Zone)",
		// exmpPer, -taxCal));
		// }

		Investment investment;
		Exemption exemption;

		double amount = item.getTaxableIncome();
		double percent = 0;

		if (item.getState().substring(0, 1) == "A"
				|| item.getState().substring(0, 1) == "B") {
			percent = 8;
		} else if (item.getState().substring(0, 1) == "C"
				|| item.getState().substring(0, 1) == "D") {
			percent = 9;
		} else {
			percent = 7;
		}

		double fraction = Utils.calculatePercentage(amount, percent);
		taxRecord.addEntry(new Entry("Base State tax for Graduate Assistants @ ", percent,fraction));

	}
}
