package com.taxhouse.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taxhouse.db.DBHandler;
import com.taxhouse.db.HistoryProvider;
import com.taxhouse.model.TaxHistory;

public class HistoryProviderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	//Test Id :26
	public void testGetHistoricRate() {
		ArrayList<String> symbols = new ArrayList<String>();
		ArrayList<String> dates = new ArrayList<String>();
		symbols.add("A");
		symbols.add("ZZC");
		dates.add("2012-01-17");
		dates.add("2012-01-18");
		double actual=HistoryProvider.GetHistoricRate(symbols, dates);
		double expected =201.26; //calculated manually from mongodb
		assertEquals(expected,actual,2);
		}

	@SuppressWarnings({ "restriction" })
	@Test
	//Test Id :27
	public final void testGetTaxHistory() throws Exception   {
		TaxHistory expected = new TaxHistory();
		int utin=300000;
		int taxYear=2011;
		String dueDate="2011-01-15";
		String paidDate="2011-01-15";
		double investement=3710415.99;
		double exemption=1890454.26;
		double taxPaid=461574.57;
		double penalityPaid=0;
		
		expected.setUtin(utin);
		expected.setTaxYear(taxYear);
		expected.setTaxDueDate(DBHandler.dateFormat.parse(dueDate));
		expected.setTaxPaidDate(DBHandler.dateFormat.parse(paidDate));
		expected.setInvestments(investement);
		expected.setExemptions(exemption);
		expected.setTaxPaid(taxPaid);
		expected.setPenaltyPaid(penalityPaid);
		TaxHistory actual = HistoryProvider.getTaxHistory(utin,taxYear );
		assertEquals(actual.getUtin(),expected.getUtin());
		assertEquals(actual.getTaxYear(),expected.getTaxYear());
		assertEquals(actual.getTaxPaidDate(),expected.getTaxPaidDate());
		assertEquals(actual.getTaxPaid(),expected.getTaxPaid(),3);
		assertEquals(actual.getInvestments(),expected.getInvestments(),3);
		assertEquals(actual.getTaxDueDate(),expected.getTaxDueDate());
		assertEquals(actual.getPenaltyPaid(),expected.getPenaltyPaid(),3);
		
	
	}

}
