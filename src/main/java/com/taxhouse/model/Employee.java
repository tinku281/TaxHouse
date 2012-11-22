package com.taxhouse.model;

import java.util.Date;
import java.util.List;

import com.taxhouse.db.DBHandler;

public class Employee extends TaxPayer {

	public enum Gender {
		MALE, FEMALE
	}

	public enum MaritalStatus {
		SINGLE, MARRIED, DIVORCED, WIDOW
	}

	public enum ResidencyStatus {
		FULL_TIME, PART_TIME, NON_RESIDENT
	}

	public enum SubType {
		NONE, STUDENT, SENIOR_CITIZEN, ARMY
	}

	private Date dateOfBirth;
	private int noOfDependants;
	private Gender gender;
	private MaritalStatus maritalStatus;
	private ResidencyStatus residencyStatus;
	private int spouseUtin;

	private Organization organization;
	private String designation;
	private Date jobStartDate;

	private List<Stock> stocks;

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getNoOfDependants() {
		return noOfDependants;
	}

	public void setNoOfDependants(int noOfDependants) {
		this.noOfDependants = noOfDependants;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public ResidencyStatus getResidencyStatus() {
		return residencyStatus;
	}

	public void setResidencyStatus(ResidencyStatus residencyStatus) {
		this.residencyStatus = residencyStatus;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Date getJobStartDate() {
		return jobStartDate;
	}

	public void setJobStartDate(Date startDate) {
		this.jobStartDate = startDate;
	}

	public int getSpouseUtin() {
		return spouseUtin;
	}

	public void setSpouseUtin(int spouseUtin) {
		this.spouseUtin = spouseUtin;
	}

	public double getDeductibleExemption() {
		double exmpAmt = 0;

		List<Exemption> exemptions = DBHandler.getInstance().getEmployeeExemptions(getUtin());

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

		List<Investment> investments = DBHandler.getInstance().getEmployeeInvestments(getUtin());

		if (investments != null && investments.size() > 0) {
			for (Investment investment : investments) {
				invAmt += investment.getApplicablePercent() * investment.getAmount() / 100;
			}
		}

		return invAmt;
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public boolean hasStocks() {
		return (stocks != null && !stocks.isEmpty());
	}

}
