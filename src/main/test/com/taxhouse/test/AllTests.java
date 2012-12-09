package com.taxhouse.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DBHandlerTest.class, EmployeeTest.class,
		HistoryProviderTest.class, StockProviderTest.class, TaxPayerTest.class,
		TaxRecordTest.class, UtilsTest.class })
public class AllTests {

}
