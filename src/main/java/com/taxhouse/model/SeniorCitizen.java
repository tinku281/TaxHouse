package com.taxhouse.model;

import java.util.List;

public class SeniorCitizen extends Employee {

	private List<Income> incomes;

	public void setIncomes(List<Income> incomes) {
		this.incomes = incomes;
	}

	public List<Income> getIncomes() {
		return incomes;
	}

	@Override
	public double getIncome() {
		double totalIncome = 0;

		for (Income income : incomes) {
			totalIncome += income.getAmount();
		}

		return totalIncome;
	}

	public static class Income {

		private String source;
		private double amount;

		public Income(String source, double amount) {
			this.source = source;
			this.amount = amount;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}
	}

}
