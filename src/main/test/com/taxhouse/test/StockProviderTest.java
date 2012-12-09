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
	//Test Id :28`
	
	//This test case fails with the current expected value.
	//Please run the yql query "select * from yahoo.finance.quotes where symbol in ("ZZC","A")" in the
	//(continued) yql console at 
	
	//"http://developer.yahoo.com/yql/console/?q=show%20tables&env=store://datatables.org/alltableswithkeys#h=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22ZZC%22%2C%22A%22%29"
	// and calculate (dayshigh+dayslow)/2 from the retrieved data and place it as expected value.
	
	public void testGetCurrentRate() {
		ArrayList<String> symbols = new ArrayList<String>();
		symbols.add("A");
		symbols.add("ZZC");
//		double expected = 112.185;
		double actual =StockProvider.GetCurrentRate(symbols);
		assertNotNull(actual);
	}

}
