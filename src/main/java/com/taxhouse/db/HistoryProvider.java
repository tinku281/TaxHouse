package com.taxhouse.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.taxhouse.model.TaxHistory;

public class HistoryProvider {

	//connection parameters
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
	public static final String TAX_PAID = "Tax_paid";
	public static final String PENALTY_PAID = "Penality_paid";

	public static TaxHistory getTaxHistory(int utin, int year) {

		TaxHistory taxHistory = null;

		try {
			Mongo mongo = new Mongo();
			mongo = new Mongo(HOST, PORT);
			DB db = mongo.getDB(DB_NAME);
			DBCollection coll = db.getCollection(COLLECTION_NAME);

			BasicDBObject query = new BasicDBObject();
			query.put(UTIN, Integer.toString(utin));
			query.put(TAX_YEAR, Integer.toString(year));
			DBCursor cursorDoc = coll.find(query);

			if (cursorDoc.hasNext()) {
				DBObject dbObj = cursorDoc.next();

				taxHistory = new TaxHistory();
				taxHistory.setUtin(Integer.parseInt((String) dbObj.get(UTIN)));
				taxHistory.setTaxYear(Integer.parseInt((String) dbObj.get(TAX_YEAR)));
				taxHistory.setTaxDueDate(DBHandler.dateFormat.parse((String) dbObj.get(TAX_DUE_DATE)));
				taxHistory.setTaxPaidDate(DBHandler.dateFormat.parse((String) dbObj.get(TAX_PAID_DATE)));
				taxHistory.setInvestments(Double.parseDouble((String) dbObj.get(INVESTMENTS)));
				taxHistory.setTaxPaid(Double.parseDouble((String) dbObj.get(TAX_PAID)));
				taxHistory.setPenaltyPaid(Double.parseDouble((String) dbObj.get(PENALTY_PAID)));
			}

			return taxHistory;

		} catch (Exception e) {
			System.err.println(HistoryProvider.class.getName() + "\n" + e.getMessage());
			return null;
		}
	}
}
