package com.taxhouse.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.taxhouse.model.ArmedForcePersonnel;
import com.taxhouse.model.ArmedForcePersonnel.SpecialTask;
import com.taxhouse.model.Employee;
import com.taxhouse.model.Employee.Gender;
import com.taxhouse.model.Employee.MaritalStatus;
import com.taxhouse.model.Employee.ResidencyStatus;
import com.taxhouse.model.Exemption;
import com.taxhouse.model.Investment;
import com.taxhouse.model.Organization;
import com.taxhouse.model.SeniorCitizen;
import com.taxhouse.model.SeniorCitizen.Income;
import com.taxhouse.model.Stock;
import com.taxhouse.model.Student;
import com.taxhouse.model.TaxPayer;
import com.taxhouse.model.TaxPayer.Nationality;

public class DBHandler {

	private static DBHandler mInstance;
	private DataSource dataSource;

	// connection parameters
	public static final String MySQL_HOST = "localhost:3306";
	public static final String MySQL_USERNAME = "root";
	public static final String MySQL_PASSWORD = "root";

	// table Admin
	public static final String ADMIN_ID = "Admin_Id";
	public static final String ADMIN_PASSWORD = "Admin_Password";

	// table Tax_payer
	public static final String UTIN = "UTIN";
	public static final String TP_PASSWORD = "TP_Password";
	public static final String FIRST_NAME = "First_Name";
	public static final String LAST_NAME = "Last_Name";
	public static final String CITY = "City";
	public static final String STATE = "State";
	public static final String NATIONALITY = "Nationality";
	public static final String TP_CATEGORY = "TP_Category";

	// table Employee
	public static final String DATE_OF_BIRTH = "Date_of_Birth";
	public static final String GENDER = "Gender";
	public static final String NO_OF_DEPENDENTS = "No_of_Dependents";
	public static final String MARITAL_STATUS = "Marital_Status";
	public static final String RESIDENCY_STATUS = "Residency_Status";
	public static final String EMP_CATEGORY = "emp_category";

	// table Organization, Organization Income
	public static final String ESTBL_DATE = "Estbl_Date";
	public static final String TURNOVER = "turnover";

	// table Combination
	public static final String COMBINATION_ID = "Combination_Id";
	public static final String SCALE_ID = "Scale_Id";
	public static final String TYPE_ID = "Type_Id";
	public static final String CATEGORY_ID = "Category_Id";

	// table Works_At
	public static final String EMP_UTIN = "Emp_UTIN";
	public static final String ORG_UTIN = "Org_UTIN";
	public static final String START_DATE = "Start_Date";
	public static final String END_DATE = "End_Date";
	public static final String DESIGNATION = "Designation";
	public static final String GROSS_INCOME = "Gross_Income";

	// table Scale, Type, Category
	public static final String SCALE_NAME = "scale_name";
	public static final String TYPE_NAME = "type_name";
	public static final String CATEGORY_NAME = "category_name";

	// table Sc_Income
	public static final String INCOME_SOURCE = "inc_src";
	public static final String INCOME_AMOUNT = "inc_amt";

	// table AF_Specl
	public static final String TASK_ID = "task_id";
	public static final String TASK_NAME = "task_name";

	// table Student
	public static final String FEE_WAIVER_AMT = "Fee_Waiver_Amt";

	// table Exemption
	public static final String EXEMP_ID = "Exemption_Id";
	public static final String EXEMP_NAME = "Exemption_Name";
	public static final String EXEMP_AMT = "Exemption_Amt";
	public static final String EXEMP_PER = "Exemp_Per";

	// table Investment
	public static final String INV_ID = "Investment_Id";
	public static final String INV_NAME = "Investment_Name";
	public static final String INV_AMT = "Investment_Amt";
	public static final String INV_APPL_PER = "Applicable_Per";

	// table StockHolders
	public static final String SYMBOL = "symbol";
	public static final String PURCHASE_DATE = "purchasedate";
	public static final String QUANTITY = "quantity";

	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public DBHandler() {
		try {
			// Get DataSource
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/tax_house");

		} catch (NamingException e) {

		}
	}

	public static DBHandler getInstance() {
		if (mInstance == null)
			mInstance = new DBHandler();

		return mInstance;
	}

	private Connection getConnection() throws SQLException {
		// Class.forName("com.mysql.jdbc.Driver");
		// return DriverManager.getConnection("jdbc:mysql://" + MySQL_HOST + "/tax_house", MySQL_USERNAME,
		// MySQL_PASSWORD);

		return dataSource.getConnection();
	}

	public boolean validateAdmin(String adminId, String password) {
		String sql = "select * from administrator where " + ADMIN_ID + " = " + adminId + " and " + ADMIN_PASSWORD
				+ " = '" + password + "'";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
				return true;

		} catch (Exception e) {

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}

		return false;
	}

	public boolean validateTaxPayer(String utin, String password) {
		String sql = "select * from tax_payer where " + UTIN + " = " + utin + " and " + TP_PASSWORD + " = '" + password
				+ "'";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
				return true;

		} catch (Exception e) {

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}

		return false;
	}

	public TaxPayer getTaxPayer(int utin) {

		String sql = "select " + TP_CATEGORY + " from tax_payer where UTIN = " + utin;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {

				final int tpCategory = rs.getInt(TP_CATEGORY); // to check the sub type of Tax Payer

				if (tpCategory == TaxPayer.SubType.EMPLOYEE.ordinal()) { // Employee
					return getEmployee(utin);

				} else if (tpCategory == TaxPayer.SubType.ORGANIZATION.ordinal()) { // Organization
					return getOrganization(utin);
				}
			}

		} catch (Exception e) {

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}

		return null;
	}

	public Employee getEmployee(int utin) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Employee emp = null;

		try {
			con = getConnection();
			stmt = con.createStatement();

			String sql = "select * from tax_payer T natural join employee E where UTIN = " + utin;
			rs = stmt.executeQuery(sql);

			if (rs.next()) {

				final int empCategory = rs.getInt(EMP_CATEGORY); // to check the sub type of Employee

				if (empCategory == Employee.SubType.SENIOR_CITIZEN.ordinal()) { // Senior Citizen
					emp = new SeniorCitizen();

				} else if (empCategory == Employee.SubType.ARMY.ordinal()) { // Armed Force
					emp = new ArmedForcePersonnel();

				} else if (empCategory == Employee.SubType.STUDENT.ordinal()) { // Student
					emp = new Student();

				} else {
					emp = new Employee();
				}

				putEmployeeDetails(emp, rs); // for putting basic employee details from Employee and TaxPayer table

				if (emp.getMaritalStatus() == MaritalStatus.MARRIED) // for putting marriage details
					putMarriageDetails(emp); // from MarriedTo table

				if (emp instanceof SeniorCitizen) // for putting income details
					putIncomeDetails((SeniorCitizen) emp); // from SC_Income table
				else
					putIncomeDetails(emp); // from Works_At table

				if (emp instanceof ArmedForcePersonnel) // for putting special task details
					putSpecialTaskDetails((ArmedForcePersonnel) emp); // from Specl_task table

				if (emp instanceof Student) // for putting fee waiver details
					putStudentDetails((Student) emp); // from Student table

				putExemptionDetails(emp); // for putting exemption details
				putInvestmentDetails(emp); // for putting investment details
				putStockDetails(emp); // for putting stock details

			}

		} catch (Exception e) {
			return null;

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}

		return emp;
	}

	private void putEmployeeDetails(Employee emp, ResultSet rs) throws SQLException, ParseException {

		emp.setUtin(rs.getInt(UTIN));
		emp.setFirstName(rs.getString(FIRST_NAME));
		emp.setLastName(rs.getString(LAST_NAME));
		emp.setCity(rs.getString(CITY));
		emp.setState(rs.getString(STATE));
		emp.setNationality(Nationality.valueOf(rs.getString(NATIONALITY)));
		emp.setDateOfBirth(dateFormat.parse(rs.getString(DATE_OF_BIRTH)));
		emp.setGender(Gender.valueOf(rs.getString(GENDER)));
		emp.setNoOfDependants(rs.getInt(NO_OF_DEPENDENTS));
		emp.setMaritalStatus(MaritalStatus.valueOf(rs.getString(MARITAL_STATUS)));
		emp.setResidencyStatus(ResidencyStatus.valueOf(rs.getString(RESIDENCY_STATUS)));
	}

	private void putMarriageDetails(Employee emp) throws SQLException, ClassNotFoundException {

		String sql = null;

		if (emp.getGender() == Gender.MALE)
			sql = "select utinf as spouse_utin from marriedto where utinm = " + emp.getUtin();
		else
			sql = "select utinm as spouse_utin from marriedto where utinf = " + emp.getUtin();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				emp.setSpouseUtin(rs.getInt("spouse_utin"));
			}

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}
	}

	private void putIncomeDetails(Employee emp) throws SQLException, ClassNotFoundException, ParseException {

		String sql = "select * from works_at where emp_utin = " + emp.getUtin() + " and end_date = ''";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				emp.setDesignation(rs.getString(DESIGNATION));
				emp.setJobStartDate(dateFormat.parse(rs.getString(START_DATE)));
				emp.setIncome(rs.getDouble(GROSS_INCOME));
				emp.setOrganization(getOrganization(rs.getInt(ORG_UTIN)));
			}

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}
	}

	private void putIncomeDetails(SeniorCitizen sc) throws SQLException, ClassNotFoundException {

		String sql = "select * from sc_income where utin = " + sc.getUtin();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			List<Income> incomes = new ArrayList<Income>();
			while (rs.next()) {
				incomes.add(new Income(rs.getString(INCOME_SOURCE), rs.getInt(INCOME_AMOUNT)));
			}

			sc.setIncomes(incomes);

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}
	}

	private void putSpecialTaskDetails(ArmedForcePersonnel afPerson) throws SQLException, ClassNotFoundException,
			ParseException {

		String sql = "select * from af_specl natural join specl_task where utin = " + afPerson.getUtin();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			List<SpecialTask> tasks = new ArrayList<SpecialTask>();
			while (rs.next()) {
				tasks.add(new SpecialTask(rs.getInt(TASK_ID), rs.getString(TASK_NAME), dateFormat.parse(rs
						.getString(START_DATE)), dateFormat.parse(rs.getString(END_DATE))));
			}

			afPerson.setSpecialTasks(tasks);

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}
	}

	private void putStudentDetails(Student student) throws SQLException, ClassNotFoundException {

		String sql = "select * from student where utin = " + student.getUtin();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				student.setFeeWaiverAmt(rs.getDouble(FEE_WAIVER_AMT));
			}

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}

	}

	private void putStockDetails(Employee emp) throws SQLException, ClassNotFoundException, ParseException {

		String sql = "select * from stockholders where UTIN = " + emp.getUtin();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			List<Stock> stocks = new ArrayList<Stock>();

			while (rs.next()) {
				stocks.add(new Stock(rs.getString(SYMBOL), rs.getInt(QUANTITY), dateFormat.parse(rs
						.getString(PURCHASE_DATE))));
			}

			emp.setStocks(stocks);

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}

	}

	private void putExemptionDetails(Employee emp) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Exemption> exemptions = null;

		String sql = "select * from exemption natural join has_exmp where utin = " + emp.getUtin();

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				if (exemptions == null)
					exemptions = new ArrayList<Exemption>();

				exemptions.add(new Exemption(rs.getInt(EXEMP_ID), rs.getString(EXEMP_NAME), rs.getDouble(EXEMP_AMT), rs
						.getDouble(EXEMP_PER)));
			}

			emp.setExemptions(exemptions);

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}
	}

	private void putInvestmentDetails(Employee emp) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Investment> investments = null;

		String sql = "select * from investment natural join has_inv where utin = " + emp.getUtin();

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				if (investments == null)
					investments = new ArrayList<Investment>();

				investments.add(new Investment(rs.getInt(INV_ID), rs.getString(INV_NAME), rs.getDouble(INV_AMT), rs
						.getDouble(INV_APPL_PER)));
			}

			emp.setInvestments(investments);

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}
	}

	public Organization getOrganization(int utin) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Organization org = null;

		String sql = "select * from Combination natural join (select * from Tax_Payer T natural join Organization O) as TPO where UTIN = "
				+ utin;

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				org = new Organization();
				org.setUtin(utin);
				org.setFirstName(rs.getString(FIRST_NAME));
				org.setLastName(rs.getString(LAST_NAME));
				org.setCity(rs.getString(CITY));
				org.setState(rs.getString(STATE));
				org.setNationality(Nationality.valueOf(rs.getString(NATIONALITY)));
				org.setEstblDate((dateFormat.parse(rs.getString(ESTBL_DATE))));
				org.setCombinationId(rs.getInt(COMBINATION_ID));
				org.setScaleId(rs.getInt(SCALE_ID));
				org.setTypeId(rs.getInt(TYPE_ID));
				org.setCategoryId(rs.getInt(CATEGORY_ID));
			}

		} catch (Exception e) {
			return null;
		} finally {
			closeConnectionObjects(rs, stmt, con);
		}

		return org;
	}

	public double getOrganizationIncome(int utin, int year) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		double income = 0;

		try {
			con = getConnection();
			stmt = con.prepareStatement("select * from org_income where UTIN = ? and inc_year = ?");
			stmt.setInt(1, utin);
			stmt.setInt(2, year);

			rs = stmt.executeQuery();

			if (rs.next()) {
				income = rs.getDouble(TURNOVER);
			}

		} catch (Exception e) {

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}

		return income;
	}

	public HashMap<String, Integer> getAllOrganizationScale() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		HashMap<String, Integer> idMap = null;

		String sql = "select * from org_Scale";

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			idMap = new HashMap<String, Integer>();
			while (rs.next()) {
				idMap.put(rs.getString(SCALE_NAME), rs.getInt(SCALE_ID));
			}

		} catch (Exception e) {
			return null;

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}

		return idMap;
	}

	public HashMap<String, Integer> getAllOrganizationType() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		HashMap<String, Integer> idMap = null;

		String sql = "select * from org_type";

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			idMap = new HashMap<String, Integer>();
			while (rs.next()) {
				idMap.put(rs.getString(TYPE_NAME), rs.getInt(TYPE_ID));
			}

		} catch (Exception e) {
			return null;

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}

		return idMap;
	}

	public HashMap<String, Integer> getAllOrganizationCategory() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		HashMap<String, Integer> idMap = null;

		String sql = "select * from org_category";

		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			idMap = new HashMap<String, Integer>();
			while (rs.next()) {
				idMap.put(rs.getString(CATEGORY_NAME), rs.getInt(CATEGORY_ID));
			}

		} catch (Exception e) {
			return null;

		} finally {
			closeConnectionObjects(rs, stmt, con);
		}

		return idMap;
	}

	private void closeConnectionObjects(ResultSet rs, Statement stmt, Connection con) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException ignore) {
			}
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException ignore) {
			}
		if (con != null)
			try {
				con.close();
			} catch (SQLException ignore) {
			}
	}

}
