package com.taxhouse.model;

import java.util.ArrayList;
import java.util.List;

public class TaxRecord {

	private List<Entry> entries;
	private double total;

	public void addEntry(Entry entry) {

		if (entry == null)
			throw new NullPointerException("Null entry cannot be added to the record");

		if (entries == null)
			entries = new ArrayList<Entry>();

		entries.add(entry);
		total += entry.getValue();
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
		return total;
	}

	public static class Entry {

		private String name;
		private double rate, value;

		public Entry(String name, double rate, double value) {
			this.name = name;
			this.rate = rate;
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
