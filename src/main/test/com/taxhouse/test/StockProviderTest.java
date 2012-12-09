package com.taxhouse.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taxhouse.db.StockProvider;

public class StockProviderTest {

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
	// Test Id :28
	public void testGetCurrentRate() {
		ArrayList<String> symbols = new ArrayList<String>();
		symbols.add("A");
		symbols.add("ZZC");
		double actual = StockProvider.GetCurrentRate(symbols);
		assertNotNull(actual);
	}

}
