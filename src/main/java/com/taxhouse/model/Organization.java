package com.taxhouse.model;

import java.util.Date;
import java.util.HashMap;

import com.taxhouse.db.DBHandler;

public class Organization extends TaxPayer {

	private Date estblDate;
	private int combinationId, categoryId, typeId, scaleId, slabId;
	private double turnover, taxPer, sharedTax;

	private HashMap<Integer, Double> shares;
	private boolean SharedExecuted = false;
	private boolean hasSharesExecuted = false;

	public boolean isSharedExecuted() {

		return SharedExecuted;
	}

	public void setSharedExecuted(boolean isShared) {

		this.SharedExecuted = isShared;
	}

	public boolean isHasSharesExecuted() {

		return hasSharesExecuted;
	}

	public void setHasSharesExecuted(boolean hasShares) {

		this.hasSharesExecuted = hasShares;
	}

	public double getSharedTax() {

		return sharedTax;
	}

	public void setSharedTax(double sharedTax) {

		this.sharedTax = sharedTax;
	}

	public HashMap<Integer, Double> getShares() {

		return shares;
	}

	public void setShares(HashMap<Integer, Double> shares) {

		this.shares = shares;
	}

	public double getTaxPer() {

		return taxPer;
	}

	public void setTaxPer(double taxPer) {

		this.taxPer = taxPer;
	}

	public double getTurnover() {

		return turnover;
	}

	public void setTurnover(double turnover) {

		this.turnover = turnover;
	}

	public int getSlabId() {

		return slabId;
	}

	public void setSlabId(int slabId) {

		this.slabId = slabId;
	}

	public Date getEstblDate() {

		return estblDate;
	}

	public void setEstblDate(Date estblDate) {

		this.estblDate = estblDate;
	}

	public int getCombinationId() {

		return combinationId;
	}

	public void setCombinationId(int combinationId) {

		this.combinationId = combinationId;
	}

	public int getCategoryId() {

		return categoryId;
	}

	public void setCategoryId(int categoryId) {

		this.categoryId = categoryId;
	}

	public int getTypeId() {

		return typeId;
	}

	public void setTypeId(int typeId) {

		this.typeId = typeId;
	}

	public int getScaleId() {

		return scaleId;
	}

	public void setScaleId(int scaleId) {

		this.scaleId = scaleId;
	}

	public static class Scale {

		public static final String LARGE = "Large";
		public static final String MEDIUM = "Medium";
		public static final String SMALL = "Small";

		public static HashMap<String, Integer> idMap;

		public static int getId(String scale) {

			if (idMap == null) {
				try {
					idMap = DBHandler.getInstance().getAllOrganizationScale();
				} catch (Exception e) {
					return 0;
				}
			}

			return idMap.get(scale);
		}

	}

	public static class Type {

		public static final String NON_PROFIT_PRIVATE = "Non-Profit Private";
		public static final String NON_PROFIT_PUBLIC = "Non-Profit Public";
		public static final String PROFIT_PRIVATE = "Profit Private";
		public static final String PROFIT_PUBLIC = "Profit Public";

		public static HashMap<String, Integer> idMap;

		public static int getId(String type) {

			if (idMap == null) {
				if (idMap == null) {
					try {
						idMap = DBHandler.getInstance().getAllOrganizationType();
					} catch (Exception e) {
						return 0;
					}
				}
			}

			return idMap.get(type);
		}

	}

	public static class Category {

		public static final String AGRICULTURE = "Agriculture";
		public static final String EDUCATIONAL = "Educational";
		public static final String AUTOMOBILE = "Automobile";
		public static final String HEALTH = "Health";
		public static final String INSURANCE = "Insurance";
		public static final String MANUFACTURING = "Manufacturing";
		public static final String TELECOMMUNICATION = "Telecommunication";
		public static final String FINANCE = "Finance";
		public static final String ENTERTAINMENT = "Entertainment";
		public static final String TRANSPORT = "Transport";
		public static final String LOGISTICS = "Logistics";
		public static final String REAL_ESTATE = "Real Estate";
		public static final String INFORMATION_TECHNOLOGY = "Information Technology";
		public static final String CHEMICALS = "Chemicals";
		public static final String PETROLEUM = "Petroleum";
		public static final String ARMY = "Army";

		public static HashMap<String, Integer> idMap;

		public static int getId(String category) {

			if (idMap == null) {
				if (idMap == null) {
					try {
						idMap = DBHandler.getInstance().getAllOrganizationCategory();
					} catch (Exception e) {
						return 0;
					}
				}
			}

			return idMap.get(category);
		}
	}

}
