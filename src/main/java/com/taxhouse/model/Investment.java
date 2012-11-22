package com.taxhouse.model;

public class Investment {

	private int id;
	private String name;
	private double amount;
	private double applicablePercent;

	public Investment(int id, String name, double amount, double applicablePercent) {
		super();
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.applicablePercent = applicablePercent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getApplicablePercent() {
		return applicablePercent;
	}

	public void setApplicablePercent(double applicablePercent) {
		this.applicablePercent = applicablePercent;
	}

}
