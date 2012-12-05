package com.taxhouse.model;

import java.util.ArrayList;
import java.util.List;

public class TaxRecord {

	private List<Deduction> deductions;
	private List<Entry> entries;

	private double totalTax;
	private double totalDeduction;

	public void addDeduction(Deduction deduction) {

		if (deduction == null)
			throw new NullPointerException("Null deduction cannot be added to the record");

		if (deductions == null)
			deductions = new ArrayList<TaxRecord.Deduction>();

		deductions.add(deduction);
	}

	public double getTotalDeduction() {
		return totalDeduction;
	}

	public int getDeductionSize() {
		return (deductions != null) ? deductions.size() : 0;
	}

	public Deduction getDeduction(int index) {
		try {

			return deductions.get(index);

		} catch (Exception e) {
			return null;
		}
	}

	public void addEntry(Entry entry) {

		if (entry == null)
			throw new NullPointerException("Null entry cannot be added to the record");

		if (entries == null)
			entries = new ArrayList<Entry>();

		entries.add(entry);
		totalTax += entry.getValue();
	}

	public int getEntrySize() {
		return (entries != null) ? entries.size() : 0;
	}

	public Entry getEntry(int index) {
		try {

			return entries.get(index);

		} catch (Exception e) {
			return null;
		}
	}

	public double getTotalTax() {
		return totalTax;
	}

	public static class Deduction {

		private String name;
		private double value;

		public Deduction(String name, double value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return this.name;
		}

		public double getValue() {
			return this.value;
		}
	}

	public static class Entry {

		private String name;
		private double rate, value;

		public Entry(String name, double rate, double value) {
			this.name = name;
			this.rate = rate;
			this.value = value;
		}

		public Entry(String name, double value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return this.name;
		}

		public double getRate() {
			return this.rate;
		}

		public double getValue() {
			return this.value;
		}
	}

}
