package com.taxhouse.model;

import org.drools.spi.KnowledgeHelper;

import com.taxhouse.model.ArmedForcePersonnel.SpecialTask;

public class Format {

	Organization item;
	TaxRecord taxRecord;
	KnowledgeHelper drools;
	SpecialTask specialtask;

	public void format() {

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
		// taxCal = Utils.calculatePercentage(item.getTax(), exmpPer);
		// item.setTax(item.getTax() - taxCal);
		// taxRecord.addEntry(new Entry("Tax Exemption Rebate (Personnel Previously worked in A2 Combat Zone)",
		// exmpPer, -taxCal));
		// }

		// Investment investment;
		// Exemption exemption;
		//
		// Map<Integer, Double> shares = item.getShares();
		//
		// int shareUtin = 0;
		// double sharePercent = 0;
		// double taxForShare = 0;
		//
		// for (Map.Entry<Integer, Double> entry : shares.entrySet()) {
		// shareUtin = entry.getKey();
		// sharePercent = entry.getValue();
		//
		// if (shareUtin != 0) {
		// TaxPayer company = DBHandler.getInstance().getTaxPayer(shareUtin);
		// ((Organization) company).setHasSharesExecuted(true);
		// ((Organization) company).setSharedExecuted(true);
		//
		// taxForShare = TaxRulesLogic.triggerRules(company).getTotalTax() * sharePercent;
		// taxRecord.addEntry(new Entry("Tax for share in Organization " + shareUtin, sharePercent, taxForShare));
		// }
		// }
		//
		// double fraction = Utils.calculatePercentage(amount, percent);
		//

	}
}
