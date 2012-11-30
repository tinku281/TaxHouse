package com.taxhouse.model;

public class TaxPayer {

	public enum Nationality {
		USA, NON_USA
	}

	public enum SubType {
		NONE, EMPLOYEE, ORGANIZATION
	}

	private int utin;
	private String firstName, lastName, city, state;
	private Nationality nationality;

	private double income;
	private double tax;

	public int getUtin() {
		return utin;
	}

	public void setUtin(int utin) {
		this.utin = utin;
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
	
	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}
	public void setPassword(String password)
	{
		
	}
	public String getPassowrd()
	{
		return "password";
	}

}
