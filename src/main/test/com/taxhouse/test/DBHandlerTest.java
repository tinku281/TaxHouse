package com.taxhouse.test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.taxhouse.db.DBHandler;
import com.taxhouse.model.ArmedForcePersonnel;
import com.taxhouse.model.ArmedForcePersonnel.SpecialTask;
import com.taxhouse.model.Employee;
import com.taxhouse.model.Exemption;
import com.taxhouse.model.Investment;
import com.taxhouse.model.Organization;
import com.taxhouse.model.SeniorCitizen;
import com.taxhouse.model.SeniorCitizen.Income;
import com.taxhouse.model.Stock;
import com.taxhouse.model.Student;
import com.taxhouse.model.TaxPayer;

public class DBHandlerTest {

	public static final String MySQL_HOST = "localhost:3306";
	public static final String MySQL_USERNAME = "root";
	public static final String MySQL_PASSWORD = "root";
	public static final String DB_NAME = "tax_house_test";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			// Create initial context
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
			System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

			InitialContext ic = new InitialContext();

			ic.createSubcontext("java:");
			ic.createSubcontext("java:/comp");
			ic.createSubcontext("java:/comp/env");
			ic.createSubcontext("java:/comp/env/jdbc");

			// Construct DataSource
			MysqlDataSource ds = new MysqlDataSource();
			ds.setURL("jdbc:mysql://" + MySQL_HOST + "/" + DB_NAME);
			ds.setUser(MySQL_USERNAME);
			ds.setPassword(MySQL_PASSWORD);

			ic.bind("java:/comp/env/jdbc/tax_house", ds);
		} catch (NamingException ex) {
//			Logger.getLogger(DBHandlerTest.class.getName()).log(Level.SEVERE, null, ex);
		}
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
	// Test Id :1
	public void testGetInstance() {
		DBHandler actual = DBHandler.getInstance();
		DBHandler expected = DBHandler.getInstance();
		assertEquals(actual.toString(), expected.toString());
	}

	@Test
	// Test Id :2
	public void testValidateAdmin() {
		DBHandler db = DBHandler.getInstance();
		boolean actual;
		actual = db.validateAdmin("1", "pnwfvo");
		boolean expected = true;
		assertEquals(actual, expected);
		actual = db.validateAdmin("2", "afdd");
		expected = false;
		assertEquals(actual, expected);
	}

	@Test
	// Test Id :3
	public void testValidateTaxPayer() {
		DBHandler db = DBHandler.getInstance();
		boolean actual;
		actual = db.validateTaxPayer("1", "yfegjt");
		boolean expected = true;
		assertEquals(actual, expected);
		actual = db.validateTaxPayer("2", "afdd");
		expected = false;
		assertEquals(actual, expected);
	}

	@SuppressWarnings("restriction")
	@Test
	// Test Id :4
	public final void testGetEmployee() {

		int studentUtin = 2;
		int armedUtin = 40001;
		int seniorUtin = 20002;
		int empUtin = 80000;
		DBHandler db = DBHandler.getInstance();

		Employee actual = null;
		try {
			actual = db.getEmployee(studentUtin);
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		Employee expected = new Student();

		expected.setUtin(2);
		expected.setDependantIncome(3670.67);
		expected.setExMilatary("N");
		((Student) expected).setFeeWaiverAmt(17320.02);

		assertEquals(actual.getClass(), expected.getClass());
		assertEquals(actual.getUtin(), expected.getUtin());
		assertEquals(actual.getDependantIncome(), expected.getDependantIncome(), 2);
		assertEquals(actual.getExMilatary(), expected.getExMilatary());
		assertEquals(((Student) actual).getFeeWaiverAmt(), ((Student) expected).getFeeWaiverAmt(), 2);

		try {
			actual = db.getEmployee(armedUtin);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		expected = new ArmedForcePersonnel();
		expected.setUtin(40001);
		List<SpecialTask> tasks = new ArrayList<SpecialTask>();
		try {
			tasks.add(new SpecialTask(1, "Red Cross", DBHandler.dateFormat.parse("1996-06-13"), DBHandler.dateFormat
					.parse("2000-08-08"), ""));
		} catch (ParseException e) {
		}
		((ArmedForcePersonnel) expected).setSpecialTasks(tasks);
		assertEquals(actual.getClass(), expected.getClass());
		assertEquals(actual.getUtin(), expected.getUtin());
		assertEquals(((ArmedForcePersonnel) actual).getSpecialTasks().get(0).getId(), ((ArmedForcePersonnel) expected)
				.getSpecialTasks().get(0).getId());

		try {
			actual = db.getEmployee(seniorUtin);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		expected = new SeniorCitizen();
		List<Income> incomes = new ArrayList<Income>();
		incomes.add(new Income("Other State", 11503.38));
		((SeniorCitizen) expected).setIncomes(incomes);

		assertEquals(actual.getClass(), expected.getClass());
		assertEquals(((SeniorCitizen) actual).getIncomes().get(0).getSource(), ((SeniorCitizen) expected).getIncomes()
				.get(0).getSource());
		assertEquals(((SeniorCitizen) actual).getIncomes().get(0).getAmount(), ((SeniorCitizen) expected).getIncomes()
				.get(0).getAmount(), 2);

		try {
			actual = db.getEmployee(empUtin);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {

			e1.printStackTrace();
		}
		expected = new Employee();
		List<Exemption> exemption = new ArrayList<Exemption>();
		exemption.add(new Exemption(1065, "Exemption ufmiyx", 591.44, 0.00));
		List<Investment> investment = new ArrayList<Investment>();
		investment.add(new Investment(262, "Investment igherg", 3982.10, 70.64));
		List<Stock> stocks = new ArrayList<Stock>();
		try {
			stocks.add(new Stock("MAN", 496, DBHandler.dateFormat.parse("2012-10-26")));
		} catch (ParseException e) {

			e.printStackTrace();
		}

		expected.setStocks(stocks);
		expected.setInvestments(investment);
		expected.setExemptions(exemption);
		expected.setMaritalStatus(com.taxhouse.model.Employee.MaritalStatus.MARRIED);
		expected.setSpouseUtin(82111);
		expected.setIncome(42578.00);

		assertEquals(actual.getClass(), expected.getClass());
		assertEquals(actual.getMaritalStatus(), expected.getMaritalStatus());
		assertEquals(actual.getSpouseUtin(), expected.getSpouseUtin());
		assertEquals(actual.getIncome(), expected.getIncome(), 2);
		assertEquals(actual.getStocks().get(0).getSymbol(), expected.getStocks().get(0).getSymbol());
		assertEquals(actual.getInvestments().get(0).getAmount(), expected.getInvestments().get(0).getAmount(), 2);
		assertEquals(actual.getExemptions().get(0).getAmount(), expected.getExemptions().get(0).getAmount(), 2);

	}

	@SuppressWarnings("restriction")
	@Test
	// Test Id :5
	public final void testGetOrganization() {
		DBHandler db = DBHandler.getInstance();
		int oUtin = 207108;
		String firstName = "King";
		String lastName = "Floer";
		String city = "Hibbing";
		String state = "Minnesota";
		int combinationId = 53;
		int scaleId = 1;
		int typeId = 4;
		double income = 285731081.99;
		int category = 5;
		double turnOver = 375796436.46;
		int slabId = 4;
		double taxPer = 17.58;
		Organization actual = null;
		try {
			actual = db.getOrganization(oUtin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Organization expected = new Organization();

		assertEquals(actual.getClass(), expected.getClass());
		assertEquals(actual.getUtin(), oUtin);
		assertEquals(actual.getFirstName(), firstName);
		assertEquals(actual.getLastName(), lastName);
		assertEquals(actual.getCity(), city);
		assertEquals(actual.getState(), state);
		assertEquals(actual.getCombinationId(), combinationId);
		assertEquals(actual.getScaleId(), scaleId);
		assertEquals(actual.getTypeId(), typeId);
		assertEquals(actual.getIncome(), income, 2);
		assertEquals(actual.getCategoryId(), category);
		assertEquals(actual.getTurnover(), turnOver, 2);
		assertEquals(actual.getSlabId(), slabId);
		assertEquals(actual.getTaxPer(), taxPer, 2);
	}

	@SuppressWarnings("restriction")
	@Test
	// Test Id :6
	public final void testGetTaxPayer() {
		DBHandler db = DBHandler.getInstance();
		int eutin = 1;
		int outin = 250530;
		TaxPayer actual = db.getTaxPayer(eutin);
		Employee expected = null;
		try {
			expected = db.getEmployee(eutin);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ParseException e) {

			e.printStackTrace();
		}
		assertEquals(((Object) actual).getClass(), ((Object) expected).getClass());
		actual = db.getTaxPayer(outin);
		Organization expected1 = null;
		try {
			expected1 = db.getOrganization(250530);
		} catch (Exception e) {

			e.printStackTrace();
		}
		assertEquals(actual.getClass(), expected1.getClass());
	}

	@SuppressWarnings("restriction")
	@Test
	// Test Id :7
	public final void testGetOrganizationShares() {
		DBHandler db = DBHandler.getInstance();
		int oUtin = 200018;
		int shareUtin = 201204;
		double percentage = 17.44;
		HashMap<Integer, Double> actual = null;
		try {
			actual = db.getOrganizationShares(200018);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		HashMap<Integer, Double> expected = new HashMap<Integer, Double>();
		expected.put(shareUtin, percentage);
		assertEquals(actual.get(oUtin), expected.get(oUtin));
	}

	@SuppressWarnings("restriction")
	@Test
	// Test Id :8
	public final void testGetAllOrganizationScale() {
		DBHandler db = DBHandler.getInstance();
		HashMap<String, Integer> expected = new HashMap<String, Integer>();
		expected.put("Small", 1);
		expected.put("Medium", 2);
		expected.put("Large", 3);
		HashMap<String, Integer> actual = db.getAllOrganizationScale();
		assertEquals(actual, expected);

	}

	@SuppressWarnings("restriction")
	@Test
	// Test Id :9
	public final void testGetAllOrganizationType() {
		DBHandler db = DBHandler.getInstance();
		HashMap<String, Integer> expected = new HashMap<String, Integer>();
		expected.put("Non-Profit Private", 1);
		expected.put("Non-Profit Public", 2);
		expected.put("Profit Private", 3);
		expected.put("Profit Public", 4);
		HashMap<String, Integer> actual = db.getAllOrganizationType();
		assertEquals(actual, expected);

	}

	@SuppressWarnings("restriction")
	@Test
	// Test Id :10
	public final void testGetAllOrganizationCategory() {
		DBHandler db = DBHandler.getInstance();
		HashMap<String, Integer> expected = new HashMap<String, Integer>();
		expected.put("Agriculture", 1);
		HashMap<String, Integer> actual = db.getAllOrganizationCategory();
		assertEquals(actual.get(1), expected.get(1));
	}

	@SuppressWarnings("restriction")
	@Test
	// Test Id :11
	public final void testGetEmployeeExemptions() {
		DBHandler db = DBHandler.getInstance();
		int exmpId = 4439;
		String exemptionName = "Exemption nsyxzm";
		double exemptionAmount = 4480.40;
		double exmpPer = 0;
		List<Exemption> expected = new ArrayList<Exemption>();
		expected.add(new Exemption(exmpId, exemptionName, exemptionAmount, exmpPer));
		List<Exemption> actual = db.getEmployeeExemptions(55401);
		assertEquals(actual.get(0).getId(), expected.get(0).getId());
	}

	@SuppressWarnings("restriction")
	@Test
	// Test Id :12
	public final void testGetEmployeeInvestments() {
		DBHandler db = DBHandler.getInstance();
		int invId = 9986;
		String invName = "Investment jxsdma";
		double invAmt = 1843.11;
		double invPer = 31.7;
		List<Investment> expected = new ArrayList<Investment>();
		expected.add(new Investment(invId, invName, invAmt, invPer));
		List<Investment> actual = db.getEmployeeInvestments(40184);
		assertEquals(actual.size(), expected.size());
		assertEquals(actual.get(0).getId(), expected.get(0).getId());
	}

	@Test
	// Test Id :13
	public void testGetScaleName() {
		String expected = "Small";
		String actual = DBHandler.getInstance().getScaleName(1);
		assertEquals(actual, expected);
	}

	@Test
	// Test Id :14
	public void testGetTypeName() {
		String expected = "Non-Profit Private";
		String actual = DBHandler.getInstance().getTypeName(1);
		assertEquals(actual, expected);
	}

	@Test
	// Test Id :15
	public void testGetCategoryName() {
		String expected = "Agriculture";
		String actual = DBHandler.getInstance().getCategoryName(1);
		assertEquals(actual, expected);
	}

	@Test
	// Test Id :16
	public void testGetExemptionNamesIntegerArray() {
		String expected[] = { "Exemption xyabjl", "Exemption ztmxkg", "Exemption robvai" };
		String actual[] = DBHandler.getInstance().getExemptionNames(1, 2, 3);
		for (int i = 0; i < actual.length; i++) {
			assertEquals(actual[i], expected[i]);
		}
	}

	@Test
	// Test Id :17
	public void testGetInvestmentNamesIntegerArray() {
		String expected[] = { "Investment hwbtrl", "Investment wwszcq", "Investment fwopfn" };
		String actual[] = DBHandler.getInstance().getInvestmentNames(1, 2, 3);
		for (int i = 0; i < actual.length; i++) {
			assertEquals(actual[i], expected[i]);
		}
	}

	@Test
	// Test Id :18
	public void testGetTaskNames() {
		String expected[] = { "Red Cross", "US Marine", "Combat" };
		String actual[] = DBHandler.getInstance().getTaskNames(1, 2, 3);
		for (int i = 0; i < actual.length; i++) {
			assertEquals(actual[i], expected[i]);
		}
	}

	@Test
	// Test Id :19
	public void testGetExemptionNames() {

		int expectedSize = 9999;
		String actual[] = DBHandler.getInstance().getExemptionNames();
		assertEquals(actual.length, expectedSize);

	}

	@Test
	// Test Id :20
	public void testGetInvestmentNames() {
		int expectedSize = 10000;
		String actual[] = DBHandler.getInstance().getInvestmentNames();
		assertEquals(actual.length, expectedSize);
	}

	@Test
	// Test Id :21
	public void testGetExemptionId() {
		int expected = 1;
		int actual = DBHandler.getInstance().getExemptionId("Exemption xyabjl");
		assertEquals(expected, actual);
	}

	@Test
	// Test Id :22
	public void testGetSlabId() {
		int expected = 1;
		int actual = DBHandler.getInstance().getSlabId(100000);
		assertEquals(expected, actual);
	}

	@SuppressWarnings("restriction")
	@Test
	// Test Id :23
	public final void testGetSharePercents() {
		DBHandler db = DBHandler.getInstance();
		int oUtin = 205304;
		double expected = 35.12;
		double actual = db.getSharePercents(oUtin);
		assertEquals(actual, expected, 2);

	}

	@Test
	// Test Id :24
	public void testInsertTaxPayer() {
		TaxPayer actualPayer = DBHandler.getInstance().getTaxPayer(200001);
		actualPayer.setPassword("oooo");
		// UTIN in insertion is auto increment. Therefore it the utin which we got in the above step doesn't matter.
		// Using the above function is only just the tax payer records with out manually loading them

		boolean actual = DBHandler.getInstance().insertTaxPayer(actualPayer);
		boolean expected = true; // Manually verified in database if the values are inserted.
		assertEquals(expected, actual);

		actualPayer = DBHandler.getInstance().getTaxPayer(1);
		actualPayer.setPassword("ssss");
		actual = DBHandler.getInstance().insertTaxPayer(actualPayer); // To insert students
		assertEquals(expected, actual);

		actualPayer = DBHandler.getInstance().getTaxPayer(20001); // To insert Senior Citizens
		actualPayer.setPassword("scsc");
		actual = DBHandler.getInstance().insertTaxPayer(actualPayer);
		assertEquals(expected, actual);

		actualPayer = DBHandler.getInstance().getTaxPayer(80001); // To insert Employees
		actualPayer.setPassword("eeee");
		actual = DBHandler.getInstance().insertTaxPayer(actualPayer);
		assertEquals(expected, actual);

		actualPayer = DBHandler.getInstance().getTaxPayer(40001); // To insert Armed Force Personnel
		actualPayer.setPassword("afaf");
		actual = DBHandler.getInstance().insertTaxPayer(actualPayer);
		assertEquals(expected, actual);
	}

	@Test
	// Test 25
	public void testdeleteTaxPayer() {
		boolean expected = true;
		boolean actual = DBHandler.getInstance().deleteTaxPayer(299999);
		assertEquals(expected, actual);
	}

	@Test
	public void testupdateTaxPayer() {
		boolean expected = true;
		TaxPayer actualPayer = DBHandler.getInstance().getTaxPayer(200001);
		actualPayer.setPassword("oooo");
		boolean actual = DBHandler.getInstance().updateTaxPayer(actualPayer);
		assertEquals(expected, actual);
	}

}
