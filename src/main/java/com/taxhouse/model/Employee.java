package com.taxhouse.model;

import java.util.Date;
import java.util.List;

import com.taxhouse.db.DBHandler;
import com.taxhouse.db.HistoryProvider;
import com.taxhouse.db.StockProvider;

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

	private String exMilatary;
	private double dependantincome;
	private boolean evalSpouse;

	private int slabId;

	public boolean getSpouseEvaluated() {
		return evalSpouse;
	}

	public void setSpouseEvaluated(boolean marriedExecuted) {
		this.evalSpouse = marriedExecuted;
	}

	public String getExMilatary() {
		return exMilatary;
	}

	public void setExMilatary(String exMilatary) {
		this.exMilatary = exMilatary;
	}

	public double getDependantIncome() {
		return dependantincome;
	}

	public void setDependantIncome(double dependantincome) {
		this.dependantincome = dependantincome;
	}

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

	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public boolean hasStocks() {
		return (stocks != null && !stocks.isEmpty());
	}

	public int getSlabId() {
		return slabId;
	}

	public void setSlabId(int slabId) {
		this.slabId = slabId;
	}

	public boolean isProfitedByStocks() {

		if (hasStocks()) {
			double purchaseAmount = 0;
			double currentAmount = 0;

			String pdate, symbol;
			for (Stock stock : stocks) {
				symbol = stock.getSymbol();
				pdate = DBHandler.dateFormat.format(stock.getPurchaseDate()).toString();

				currentAmount += StockProvider.GetCurrentRate(symbol);
				purchaseAmount += HistoryProvider.GetHistoricRate(symbol, pdate);
			}

			if (currentAmount < purchaseAmount)
				return true;
		}

		return false;
	}
	
}
