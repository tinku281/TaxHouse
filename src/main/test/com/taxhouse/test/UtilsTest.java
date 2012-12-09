package com.taxhouse.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taxhouse.app.Utils;

public class UtilsTest {

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
	//Test Id :41
	public void testGetCurrentYear() {
		int expected =2012;
		int actual = Utils.getCurrentYear();
		assertEquals(expected,actual);
	}

	@Test
	//Test Id :42
	public void testCalculatePercentage() {
		double expected =200;
		double actual =Utils.calculatePercentage(2000, 10);
		assertEquals(expected,actual,2);
	}

	@Test
	//Test Id :43
	public void testFormatDouble() {
		String expected ="12.22";
		String actual = Utils.formatDouble(12.2232);
		assertEquals(expected,actual);
	}

}
