package com.taxhouse.model;

public class Exemption {

	private int id;
	private String name;
	private double amount;
	private double percentage;

	public Exemption(int id, String name, double amount, double percentage) {
		super();
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.percentage = percentage;
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

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double applicablePercent) {
		this.percentage = applicablePercent;
	}

}
