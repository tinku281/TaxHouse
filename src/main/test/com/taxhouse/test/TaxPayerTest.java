package com.taxhouse.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.ParseException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taxhouse.db.*;
import com.taxhouse.model.*;

public class TaxPayerTest {

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
	//Test Id :29
	public void testHasExemptions() {
	TaxPayer tp = DBHandler.getInstance().getTaxPayer(2);
	boolean actual = tp.hasExemptions();
	boolean expected =true;
	assertEquals(expected,actual);
	tp = DBHandler.getInstance().getTaxPayer(3);
	actual = tp.hasExemptions();
	expected = false;
	assertEquals(expected,actual);
	}

	@Test
	//Test Id :30
	public void testHasInvestments() {
		TaxPayer tp = DBHandler.getInstance().getTaxPayer(1);
		boolean actual = tp.hasInvestments();
		boolean expected =true;
		assertEquals(expected,actual);
		tp = DBHandler.getInstance().getTaxPayer(5);
		actual = tp.hasInvestments();
		expected = false;
		assertEquals(expected,actual);
		
	}

	@Test
	//Test Id :31
	public void testGetDeductibleExemption() {
		TaxPayer tp = DBHandler.getInstance().getTaxPayer(2);
		double expected =18307.57;
		double actual = tp.getDeductibleExemption();
		assertEquals(expected,actual,2);
				
	}

	@Test
	//Test Id :32
	public void testGetDeductibleInvestment() {
		TaxPayer tp = DBHandler.getInstance().getTaxPayer(2);
		double expected =6413.05;
		double actual = tp.getDeductibleInvestment();
		assertEquals(expected,actual,2);
	}

	@Test
	//Test Id :33
	public void testGetTaxableIncome() {
		TaxPayer tp = DBHandler.getInstance().getTaxPayer(2);
		double expected =106422.47;
		double actual = tp.getTaxableIncome();
		assertEquals(expected,actual,2);
	}

	@Test
	//Test Id :34
	public void testHasLatePayments() {
		TaxPayer tp = DBHandler.getInstance().getTaxPayer(2);
		boolean expected = false;
		boolean actual = tp.hasLatePayments(1);
		assertEquals(expected,actual);
		tp= DBHandler.getInstance().getTaxPayer(30000);
		expected = true;
		actual = tp.hasLatePayments(2);
		assertEquals(expected,actual);
	}

}
