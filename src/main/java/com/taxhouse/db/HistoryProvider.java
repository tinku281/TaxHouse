package com.taxhouse.db;

import java.text.ParseException;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.taxhouse.model.TaxHistory;

public class HistoryProvider {

	// connection parameters
	private static final String DB_NAME = "tax";
	private static final String COLLECTION_NAME = "tax_history";
	private static final String HOST = "localhost";
	private static final int PORT = 27017;

	// Keys
	public static final String UTIN = "UTIN";
	public static final String TAX_YEAR = "Tax_year";
	public static final String TAX_DUE_DATE = "Tax_due_date";
	public static final String TAX_PAID_DATE = "Tax_paid_date";
	public static final String INVESTMENTS = "Investments";
	public static final String EXEMPTIONS = "Exemptions";
	public static final String TAX_PAID = "Tax_paid";
	public static final String PENALTY_PAID = "Penality_paid";

	public static final String SYMBOL = "Symbol";
	public static final String HDATE = "Date";
	public static final String HSTOCKRATE = "StockRate";

	static DB db;
	static Mongo mongo;

	public static void connectMongo() {
		try {
			mongo = new Mongo();
			mongo = new Mongo(HOST, PORT);
			db = mongo.getDB(DB_NAME);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static double GetHistoricRate(ArrayList<String> symbol,
			ArrayList<String> dates) {
		double rate = 0;
		try {
			connectMongo();
			DBCollection coll = db.getCollection("stock_history");
			for (int i = 0; i < symbol.size(); i++) {
				for (int j = 0; j < dates.size(); j++) {
					BasicDBObject query = new BasicDBObject();
					query.put(SYMBOL, symbol.get(i));
					query.put(HDATE, dates.get(j));
					DBCursor cursorDoc = coll.find(query);
					if (cursorDoc.hasNext()) {
						DBObject dbObj = cursorDoc.next();
						rate += (double) dbObj.get(HSTOCKRATE);
					}
				}
			}
		} catch (NumberFormatException e) {

			
		}
		return rate;
	}


	public static TaxHistory getTaxHistory(int utin, int year) {

		TaxHistory taxHistory = null;

		connectMongo();
		DBCollection coll = db.getCollection(COLLECTION_NAME);

		BasicDBObject query = new BasicDBObject();
		query.put(UTIN, utin);
		query.put(TAX_YEAR, year);
		DBCursor cursorDoc = coll.find(query);

		if (cursorDoc.hasNext()) {
			DBObject dbObj = cursorDoc.next();

			taxHistory = new TaxHistory();
			taxHistory.setUtin(Integer.parseInt(dbObj.get(UTIN).toString()));
			taxHistory.setTaxYear(Integer.parseInt(dbObj.get(TAX_YEAR).toString()));
			taxHistory.setInvestments(Double.parseDouble(dbObj.get(INVESTMENTS).toString()));
			taxHistory.setExemptions(Double.parseDouble(dbObj.get(EXEMPTIONS).toString()));
			taxHistory.setTaxPaid(Double.parseDouble(dbObj.get(TAX_PAID).toString()));
			taxHistory.setPenaltyPaid(Double.parseDouble(dbObj.get(PENALTY_PAID).toString()));

			try {
				taxHistory.setTaxDueDate(DBHandler.dateFormat.parse((String) dbObj.get(TAX_DUE_DATE)));
				taxHistory.setTaxPaidDate(DBHandler.dateFormat.parse((String) dbObj.get(TAX_PAID_DATE)));

			} catch (ParseException e) {

			}
		}

		return taxHistory;
	}
	
	
	public static ArrayList<TaxHistory> getTaxHistory(int utin) {

		ArrayList<TaxHistory> histories = new ArrayList<TaxHistory>();
		TaxHistory taxHistory = null;

		connectMongo();
		DBCollection coll = db.getCollection(COLLECTION_NAME);

		BasicDBObject query = new BasicDBObject();
		query.put(UTIN, utin);
		DBCursor cursorDoc = coll.find(query);

		while (cursorDoc.hasNext()) {
			DBObject dbObj = cursorDoc.next();

			taxHistory = new TaxHistory();
			taxHistory.setUtin(Integer.parseInt(dbObj.get(UTIN).toString()));
			taxHistory.setTaxYear(Integer.parseInt(dbObj.get(TAX_YEAR).toString()));
			taxHistory.setInvestments(Double.parseDouble(dbObj.get(INVESTMENTS).toString()));
			taxHistory.setExemptions(Double.parseDouble(dbObj.get(EXEMPTIONS).toString()));
			taxHistory.setTaxPaid(Double.parseDouble(dbObj.get(TAX_PAID).toString()));
			taxHistory.setPenaltyPaid(Double.parseDouble(dbObj.get(PENALTY_PAID).toString()));

			try {
				taxHistory.setTaxDueDate(DBHandler.dateFormat.parse((String) dbObj.get(TAX_DUE_DATE)));
				taxHistory.setTaxPaidDate(DBHandler.dateFormat.parse((String) dbObj.get(TAX_PAID_DATE)));

			} catch (ParseException e) {

			}
			
			histories.add(taxHistory);
		}

		return histories;
	}
	
}
