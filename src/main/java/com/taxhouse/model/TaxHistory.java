package com.taxhouse.model;

import java.util.Date;

public class TaxHistory {

	private int utin;
	private int taxYear;
	private Date taxDueDate;
	private Date taxPaidDate;
	private double investments;
	private double taxPaid;
	private double penaltyPaid;

	public int getUtin() {
		return utin;
	}

	public void setUtin(int utin) {
		this.utin = utin;
	}

	public int getTaxYear() {
		return taxYear;
	}

	public void setTaxYear(int taxYear) {
		this.taxYear = taxYear;
	}

	public Date getTaxDueDate() {
		return taxDueDate;
	}

	public void setTaxDueDate(Date taxDueDate) {
		this.taxDueDate = taxDueDate;
	}

	public Date getTaxPaidDate() {
		return taxPaidDate;
	}

	public void setTaxPaidDate(Date taxPaidDate) {
		this.taxPaidDate = taxPaidDate;
	}

	public double getInvestments() {
		return investments;
	}

	public void setInvestments(double investments) {
		this.investments = investments;
	}

	public double getTaxPaid() {
		return taxPaid;
	}

	public void setTaxPaid(double taxPaid) {
		this.taxPaid = taxPaid;
	}

	public double getPenaltyPaid() {
		return penaltyPaid;
	}

	public void setPenaltyPaid(double penaltyPaid) {
		this.penaltyPaid = penaltyPaid;
	}

}
