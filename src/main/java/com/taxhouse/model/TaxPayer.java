package com.taxhouse.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.taxhouse.db.HistoryProvider;

public class TaxPayer {

	public enum Nationality {
		USA, NON_USA
	}

	public enum SubType {
		NONE, EMPLOYEE, ORGANIZATION
	}

	private int utin;
	private String password, firstName, lastName, city, state;
	private Nationality nationality;

	private double income;

	private List<Exemption> exemptions;
	private List<Investment> investments;

	public int getUtin() {

		return utin;
	}

	public void setUtin(int utin) {

		this.utin = utin;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public String getPassword() {

		return password;
	}

	public String getFirstName() {

		return firstName;
	}

	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}

	public String getLastName() {

		return lastName;
	}

	public void setLastName(String lastName) {

		this.lastName = lastName;
	}

	public String getCity() {

		return city;
	}

	public void setCity(String city) {

		this.city = city;
	}

	public String getState() {

		return state;
	}

	public void setState(String state) {

		this.state = state;
	}

	public Nationality getNationality() {

		return nationality;
	}

	public void setNationality(Nationality nationality) {

		this.nationality = nationality;
	}

	public double getIncome() {

		return income;
	}

	public void setIncome(double income) {

		this.income = income;
	}

	public List<Exemption> getExemptions() {

		return exemptions;
	}

	public void setExemptions(List<Exemption> exemptions) {

		this.exemptions = exemptions;
	}

	public boolean hasExemptions() {

		return (exemptions != null && !exemptions.isEmpty());
	}

	public List<Investment> getInvestments() {

		return investments;
	}

	public void setInvestments(List<Investment> investments) {

		this.investments = investments;
	}

	public boolean hasInvestments() {

		return (investments != null && !investments.isEmpty());
	}

	public double getDeductibleExemption() {

		double exmpAmt = 0;

		if (exemptions != null && exemptions.size() > 0) {
			for (Exemption exemption : exemptions) {
				if (exemption.getAmount() != 0)
					exmpAmt += exemption.getAmount();

				if (exemption.getPercentage() != 0)
					exmpAmt += (exemption.getPercentage() * getIncome()) / 100;
			}
		}

		return exmpAmt;
	}

	public double getDeductibleInvestment() {

		double invAmt = 0;

		if (investments != null && investments.size() > 0) {
			for (Investment investment : investments) {
				invAmt += investment.getApplicablePercent() * investment.getAmount() / 100;
			}
		}

		return invAmt;
	}

	public double getTaxableIncome() {
		return getIncome() - getDeductibleExemption() - getDeductibleInvestment();
	}

	public boolean hasLatePayments(int noOfYears) {

		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);

		int prevYear = cal.get(Calendar.YEAR) - 1;
		boolean latePayment = false;

		for (int year = prevYear; year > prevYear - noOfYears; year--) {
			TaxHistory tHistory = HistoryProvider.getTaxHistory(getUtin(), year);

			if (tHistory != null) {
				latePayment = tHistory.getTaxDueDate().before(tHistory.getTaxPaidDate());
				if (latePayment)
					return true;
			}
		}

		return false;
	}

}
