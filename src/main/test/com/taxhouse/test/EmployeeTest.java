package com.taxhouse.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.ParseException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taxhouse.db.DBHandler;
import com.taxhouse.model.Employee;
import com.taxhouse.model.TaxPayer;

public class EmployeeTest {

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
	//Test Id :25
	public void testIsProfitedByStocks() {
		TaxPayer tp = new TaxPayer();
		Employee emp = null;
		Employee emp1=null;
		try {
			emp = DBHandler.getInstance().getEmployee(20002);
			emp1=DBHandler.getInstance().getEmployee(20057);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		boolean expected =true;
		boolean actual = emp.isProfitedByStocks();
		assertEquals(expected,actual);
		
		expected = false;
		actual = emp1.isProfitedByStocks();
		assertEquals(expected,actual);
	}

}
