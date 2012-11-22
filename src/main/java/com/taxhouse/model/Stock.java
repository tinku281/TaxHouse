package com.taxhouse.model;

import java.util.Date;

public class Stock {

	private String symbol;
	private int quantity;
	private Date purchaseDate;

	public Stock() {
		// default Constructor
	}

	public Stock(String symbol, int quantity, Date purchaseDate) {
		super();
		this.symbol = symbol;
		this.quantity = quantity;
		this.purchaseDate = purchaseDate;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

}
