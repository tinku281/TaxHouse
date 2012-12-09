package com.taxhouse.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taxhouse.model.TaxRecord;

public class TaxRecordTest {

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
	//Test Id :35
	public void testAddDeduction() {
		TaxRecord.Deduction expected = new TaxRecord.Deduction("abcd", 123);
		TaxRecord t = new TaxRecord();
		t.addDeduction(expected);
		TaxRecord.Deduction actual = t.getDeduction(0);
		assertEquals(expected.getName(),actual.getName());
		assertEquals(expected.getName(),actual.getName());
	}

	@Test
	//Test Id :36
	public void testGetDeductionSize() {
		TaxRecord.Deduction d = new TaxRecord.Deduction("abcd", 123);
		TaxRecord t = new TaxRecord();
		t.addDeduction(d);
		int expected = 1;
		int actual = t.getDeductionSize();
		assertEquals(expected,actual);
	}

	@Test
	//Test Id :37
	public void testGetDeduction() {
		TaxRecord.Deduction expected = new TaxRecord.Deduction("abcd", 123);
		TaxRecord t = new TaxRecord();
		t.addDeduction(expected);
		TaxRecord.Deduction actual = t.getDeduction(0);
		assertEquals(expected.getName(),actual.getName());
		assertEquals(expected.getName(),actual.getName());
	}

	@Test
	//Test Id :38
	public void testAddEntry() {
		TaxRecord.Entry expected = new TaxRecord.Entry("abcd", 123);
		TaxRecord t = new TaxRecord();
		t.addEntry(expected);
		TaxRecord.Entry actual = t.getEntry(0);
		assertEquals(expected.getName(),actual.getName());
		assertEquals(expected.getValue(),actual.getValue(),2);
		expected = new TaxRecord.Entry("abcd", 123,234);
		t.addEntry(expected);
		actual = t.getEntry(1);
		assertEquals(expected.getName(),actual.getName());
		assertEquals(expected.getValue(),actual.getValue(),2);
		assertEquals(expected.getRate(),actual.getRate(),2);
	}

	@Test
	//Test Id :39
	public void testGetEntrySize() {
		TaxRecord.Entry e = new TaxRecord.Entry("abc", 123);
		TaxRecord t = new TaxRecord();
		t.addEntry(e);
		int actual = t.getEntrySize();
		int expected =1;
		assertEquals(expected,actual);
	}

	@Test
	//Test Id :40
	public void testGetEntry() {
		TaxRecord.Entry expected = new TaxRecord.Entry("abcd", 123);
		TaxRecord t = new TaxRecord();
		t.addEntry(expected);
		TaxRecord.Entry actual = t.getEntry(0);
		assertEquals(expected.getName(),actual.getName());
		assertEquals(expected.getValue(),actual.getValue(),2);
	}

}
