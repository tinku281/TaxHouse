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

public class DBHandler
{

	private static DBHandler				mInstance			= null;
	private DataSource						dataSource;

	// connection parameters
	public static final String				MySQL_HOST			= "localhost:3306";
	public static final String				MySQL_USERNAME		= "root";
	public static final String				MySQL_PASSWORD		= "root";

	// table Admin
	public static final String				ADMIN_ID			= "Admin_Id";
	public static final String				ADMIN_PASSWORD		= "Admin_Password";

	// table Tax_payer
	public static final String				UTIN				= "UTIN";
	public static final String				TP_PASSWORD			= "TP_Password";
	public static final String				FIRST_NAME			= "First_Name";
	public static final String				LAST_NAME			= "Last_Name";
	public static final String				CITY				= "City";
	public static final String				STATE				= "State";
	public static final String				NATIONALITY			= "Nationality";
	public static final String				TP_CATEGORY			= "TP_Category";

	// table Employee
	public static final String				DATE_OF_BIRTH		= "Date_of_Birth";
	public static final String				GENDER				= "Gender";
	public static final String				NO_OF_DEPENDENTS	= "No_of_Dependents";
	public static final String				MARITAL_STATUS		= "Marital_Status";
	public static final String				RESIDENCY_STATUS	= "Residency_Status";
	public static final String				EMP_CATEGORY		= "emp_category";
	public static final String				DEP_AMOUNT			= "income";
	public static final String				EMP_SLAB			= "Slab_Id";

	// table Organization, Organization Income
	public static final String				ESTBL_DATE			= "Estbl_Date";
	public static final String				TURNOVER			= "turnover";
	public static final String				ORG_SLAB			= "Slab_Id";
	public static final String				GROSS_PROFIT		= "Gross_Profit";
	public static final String				ORG_TAX_PER			= "Tax_Perct";

	// table Combination
	public static final String				COMBINATION_ID		= "Combination_Id";
	public static final String				SCALE_ID			= "Scale_Id";
	public static final String				TYPE_ID				= "Type_Id";
	public static final String				CATEGORY_ID			= "Category_Id";

	// table Works_At
	public static final String				EMP_UTIN			= "Emp_UTIN";
	public static final String				ORG_UTIN			= "Org_UTIN";
	public static final String				START_DATE			= "Start_Date";
	public static final String				END_DATE			= "End_Date";
	public static final String				DESIGNATION			= "Designation";
	public static final String				GROSS_INCOME		= "Gross_Income";

	// table Scale, Type, Category
	public static final String				SCALE_NAME			= "scale_name";
	public static final String				TYPE_NAME			= "type_name";
	public static final String				CATEGORY_NAME		= "category_name";

	// table Sc_Income
	public static final String				INCOME_SOURCE		= "inc_src";
	public static final String				INCOME_AMOUNT		= "inc_amt";
	public static final String				EX_MILATARY			= "Ex_Milatary";
	// table AF_Specl
	public static final String				TASK_ID				= "task_id";
	public static final String				TASK_NAME			= "task_name";

	// table Student
	public static final String				FEE_WAIVER_AMT		= "Fee_Waiver_Amt";

	// table Exemption
	public static final String				EXEMP_ID			= "Exemption_Id";
	public static final String				EXEMP_NAME			= "Exemption_Name";
	public static final String				EXEMP_AMT			= "Exemption_Amt";
	public static final String				EXEMP_PER			= "Exemption_Per";

	// table Investment
	public static final String				INV_ID				= "Investment_Id";
	public static final String				INV_NAME			= "Investment_Name";
	public static final String				INV_AMT				= "Investment_Amt";
	public static final String				INV_APPL_PER		= "Applicable_Per";

	// table StockHolders
	public static final String				SYMBOL				= "symbol";
	public static final String				PURCHASE_DATE		= "purchasedate";
	public static final String				QUANTITY			= "quantity";

	// Combat Zones
	public static final String				COMBAT_ZONE			= "combat_zone";

	// Armed Ranks
	public static final String				RANK				= "rank";

	// HAS_PARTNERSHIP TABLE
	public static final String				SHARE_UTIN			= "Share_UTIN";
	public static final String				SHARE_PERCENT		= "Share_Percent";

	public static final SimpleDateFormat	dateFormat			= new SimpleDateFormat( "yyyy-MM-dd" );

	public DBHandler()
	{

		try
		{
			// Get DataSource
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup( "java:/comp/env" );
			dataSource = (DataSource) envContext.lookup( "jdbc/tax_house" );

		}
		catch ( NamingException e )
		{

		}
	}

	public static DBHandler getInstance()
	{

		if ( mInstance == null )
		{
			mInstance = new DBHandler();
		}
		return mInstance;
	}

	private Connection getConnection() throws SQLException
	{

		// Class.forName("com.mysql.jdbc.Driver");
		// return DriverManager.getConnection("jdbc:mysql://" + MySQL_HOST +
		// "/tax_house", MySQL_USERNAME,
		// MySQL_PASSWORD);

		return dataSource.getConnection();
	}

	public boolean validateAdmin( String adminId, String password )
	{

		String sql = "select * from administrator where " + ADMIN_ID + " = " + adminId + " and " + ADMIN_PASSWORD + " = '" + password + "'";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			if ( rs.next() )
				return true;

		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return false;
	}

	public boolean validateTaxPayer( String utin, String password )
	{

		String sql = "select * from tax_payer where " + UTIN + " = " + utin + " and " + TP_PASSWORD + " = '" + password + "'";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			if ( rs.next() )
				return true;

		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return false;
	}

	public TaxPayer getTaxPayer( int utin )
	{

		String sql = "select " + TP_CATEGORY + " from tax_payer where UTIN = " + utin;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			if ( rs.next() )
			{

				final int tpCategory = rs.getInt( TP_CATEGORY ); // to check the
																	// sub type
																	// of
																	// Tax Payer

				if ( tpCategory == TaxPayer.SubType.EMPLOYEE.ordinal() )
				{ // Employee
					return getEmployee( utin );

				}

				else if ( tpCategory == TaxPayer.SubType.ORGANIZATION.ordinal() )
				{ // Organization
					return getOrganization( utin );
				}
			}

		}
		catch ( Exception e )
		{
			System.err.println( e.getMessage() );
			return null;
		}

		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return null;
	}

	public Employee getEmployee( int utin ) throws SQLException, ClassNotFoundException, ParseException
	{

		Connection con = null;
		Statement stmt = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Employee emp = null;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			stmt1 = con.createStatement();
			stmt2 = con.createStatement();

			String sql = "select * from tax_payer T natural join employee E where UTIN = " + utin;
			String query = "select income from dependant_income where UTIN=" + utin;
			String query1 = "select * from exmilitary where UTIN=" + utin;
			rs = stmt.executeQuery( sql );

			rs1 = stmt1.executeQuery( query );
			rs2 = stmt2.executeQuery( query1 );
			if ( rs.next() )
			{

				final int empCategory = rs.getInt( EMP_CATEGORY ); // to check
																	// the
																	// sub type
																	// of
																	// Employee

				if ( empCategory == Employee.SubType.SENIOR_CITIZEN.ordinal() )
				{ // Senior
					// Citizen
					emp = new SeniorCitizen();

				}
				else if ( empCategory == Employee.SubType.ARMY.ordinal() )
				{ // Armed
					// Force
					emp = new ArmedForcePersonnel();

				}
				else if ( empCategory == Employee.SubType.STUDENT.ordinal() )
				{ // Student
					emp = new Student();

				}
				else
				{
					emp = new Employee();
				}

				putEmployeeDetails( emp, rs, rs1, rs2 ); // for putting basic
															// employee
															// details from
															// Employee
															// and
															// TaxPayer table

				if ( emp.getMaritalStatus() == MaritalStatus.MARRIED ) // for
																		// putting
																		// marriage
																		// details
					putMarriageDetails( emp ); // from MarriedTo table

				if ( emp instanceof SeniorCitizen ) // for putting income
													// details
					putIncomeDetails( (SeniorCitizen) emp ); // from SC_Income
																// table
				else
					putIncomeDetails( emp ); // from Works_At table

				if ( emp instanceof ArmedForcePersonnel ) // for putting special
															// task details
					putSpecialTaskDetails( (ArmedForcePersonnel) emp ); // from
																		// Specl_task
																		// table

				if ( emp instanceof Student ) // for putting fee waiver details
					putStudentDetails( (Student) emp ); // from Student table

				putStockDetails( emp ); // for putting stock details
				putExemptionDetails( emp );
				putInvestmentDetails( emp );
			}

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return emp;
	}

	private void putEmployeeDetails( Employee emp, ResultSet rs, ResultSet rs1, ResultSet rs2 ) throws SQLException, ParseException
	{

		emp.setUtin( rs.getInt( UTIN ) );
		emp.setFirstName( rs.getString( FIRST_NAME ) );
		emp.setLastName( rs.getString( LAST_NAME ) );
		emp.setCity( rs.getString( CITY ) );
		emp.setState( rs.getString( STATE ) );
		emp.setNationality( Nationality.valueOf( rs.getString( NATIONALITY ) ) );
		emp.setDateOfBirth( dateFormat.parse( rs.getString( DATE_OF_BIRTH ) ) );
		emp.setGender( Gender.valueOf( rs.getString( GENDER ) ) );
		emp.setNoOfDependants( rs.getInt( NO_OF_DEPENDENTS ) );
		emp.setMaritalStatus( MaritalStatus.valueOf( rs.getString( MARITAL_STATUS ) ) );
		emp.setResidencyStatus( ResidencyStatus.valueOf( rs.getString( RESIDENCY_STATUS ) ) );
		emp.setInvestments( this.getEmployeeInvestments( emp.getUtin() ) );
		emp.setExemptions( this.getEmployeeExemptions( emp.getUtin() ) );

		if ( rs2.next() )
		{
			emp.setExMilatary( "Y" );
		}
		else
		{
			emp.setExMilatary( "N" );
		}

		if ( rs1.next() )
			emp.setDependantIncome( rs1.getDouble( DEP_AMOUNT ) );

	}

	private void putMarriageDetails( Employee emp ) throws SQLException, ClassNotFoundException
	{

		String sql = null;

		if ( emp.getGender() == Gender.MALE )
			sql = "select utinf as spouse_utin from marriedto where utinm = " + emp.getUtin();
		else
			sql = "select utinm as spouse_utin from marriedto where utinf = " + emp.getUtin();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			if ( rs.next() )
			{
				emp.setSpouseUtin( rs.getInt( "spouse_utin" ) );
			}

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}
	}

	private void putIncomeDetails( Employee emp ) throws SQLException, ClassNotFoundException, ParseException
	{

		String sql = "select * from works_at where emp_utin = " + emp.getUtin() + " and end_date = ''";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			if ( rs.next() )
			{
				emp.setDesignation( rs.getString( DESIGNATION ) );
				emp.setJobStartDate( dateFormat.parse( rs.getString( START_DATE ) ) );
				emp.setIncome( rs.getDouble( GROSS_INCOME ) );
				emp.setOrganization( getOrganization( rs.getInt( ORG_UTIN ) ) );
			}

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}
	}

	private void putIncomeDetails( SeniorCitizen sc ) throws SQLException, ClassNotFoundException
	{

		String sql = "select * from sc_income where utin = " + sc.getUtin();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );
			List<Income> incomes = new ArrayList<Income>();
			while ( rs.next() )
			{
				incomes.add( new Income( rs.getString( INCOME_SOURCE ), rs.getInt( INCOME_AMOUNT ) ) );
			}

			sc.setIncomes( incomes );

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}
	}

	private void putSpecialTaskDetails( ArmedForcePersonnel afPerson ) throws SQLException, ClassNotFoundException, ParseException
	{

		String sql = "select * from af_specl natural join specl_task where utin = " + afPerson.getUtin();
		String query1 = "select combat_zone from combat_zones where utin=" + afPerson.getUtin();

		Connection con = null;
		Statement stmt = null;
		Statement stmt1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			stmt1 = con.createStatement();
			rs = stmt.executeQuery( sql );
			rs1 = stmt1.executeQuery( query1 );
			List<SpecialTask> tasks = new ArrayList<SpecialTask>();
			while ( rs.next() )
			{
				if ( rs1.next() )
				{
					tasks.add( new SpecialTask( rs.getInt( TASK_ID ), rs.getString( TASK_NAME ), dateFormat.parse( rs.getString( START_DATE ) ), dateFormat
							.parse( rs.getString( END_DATE ) ), rs1.getString( COMBAT_ZONE ) ) );

				}
				else
				{
					tasks.add( new SpecialTask( rs.getInt( TASK_ID ), rs.getString( TASK_NAME ), dateFormat.parse( rs.getString( START_DATE ) ), dateFormat
							.parse( rs.getString( END_DATE ) ), " " ) );
				}
			}

			afPerson.setSpecialTasks( tasks );

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}
	}

	private void putStudentDetails( Student student ) throws SQLException, ClassNotFoundException
	{

		String sql = "select * from student where utin = " + student.getUtin();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			if ( rs.next() )
			{
				student.setFeeWaiverAmt( rs.getDouble( FEE_WAIVER_AMT ) );
			}

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

	}

	private void putStockDetails( Employee emp ) throws SQLException, ClassNotFoundException, ParseException
	{

		String sql = "select * from stockholders where UTIN = " + emp.getUtin();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			List<Stock> stocks = new ArrayList<Stock>();

			while ( rs.next() )
			{
				stocks.add( new Stock( rs.getString( SYMBOL ), rs.getInt( QUANTITY ), dateFormat.parse( rs.getString( PURCHASE_DATE ) ) ) );
			}

			emp.setStocks( stocks );

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

	}

	private void putExemptionDetails( TaxPayer tp ) throws SQLException
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Exemption> exemptions = null;

		String sql = "select * from exemption natural join has_exmp where utin = " + tp.getUtin();

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			while ( rs.next() )
			{
				if ( exemptions == null )
					exemptions = new ArrayList<Exemption>();

				exemptions.add( new Exemption( rs.getInt( EXEMP_ID ), rs.getString( EXEMP_NAME ), rs.getDouble( EXEMP_AMT ), rs.getDouble( EXEMP_PER ) ) );
			}

			tp.setExemptions( exemptions );

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}
	}

	private void putInvestmentDetails( TaxPayer tp ) throws SQLException
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Investment> investments = null;

		String sql = "select * from investment natural join has_inv where utin = " + tp.getUtin();

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			while ( rs.next() )
			{
				if ( investments == null )
					investments = new ArrayList<Investment>();

				investments.add( new Investment( rs.getInt( INV_ID ), rs.getString( INV_NAME ), rs.getDouble( INV_AMT ), rs.getDouble( INV_APPL_PER ) ) );
			}

			tp.setInvestments( investments );

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}
	}

	public HashMap<Integer, Double> getOrganizationShares( int utin ) throws SQLException
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		HashMap<Integer, Double> shares = null;
		String query = "select * from has_partnership where utin=" + utin;
		con = getConnection();
		stmt = con.createStatement();
		rs = stmt.executeQuery( query );
		shares = new HashMap<Integer, Double>();

		while ( rs.next() )
		{
			shares.put( rs.getInt( SHARE_UTIN ), rs.getDouble( SHARE_PERCENT ) );
		}

		return shares;
	}

	public Organization getOrganization( int utin ) throws SQLException, ParseException
	{

		Connection con = null;
		Statement stmt = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Organization org = null;

		String sql = "select * from Combination natural join (select * from Tax_Payer T natural join Organization O) as TPO  where UTIN = " + utin;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			if ( rs.next() )
			{

				org = new Organization();
				org.setUtin( utin );
				org.setFirstName( rs.getString( FIRST_NAME ) );
				org.setLastName( rs.getString( LAST_NAME ) );
				org.setCity( rs.getString( CITY ) );
				org.setState( rs.getString( STATE ) );
				org.setNationality( Nationality.valueOf( rs.getString( NATIONALITY ) ) );
				org.setEstblDate( (dateFormat.parse( rs.getString( ESTBL_DATE ) )) );
				org.setCombinationId( rs.getInt( COMBINATION_ID ) );

				String query1 = "select slab_id from org_slab where slab_min_amt<= " + rs.getDouble( GROSS_PROFIT ) + " and slab_max_amt > "
						+ rs.getDouble( GROSS_PROFIT );

				stmt2 = con.createStatement();
				rs2 = stmt2.executeQuery( query1 );

				if ( rs2.next() )
				{
					org.setSlabId( rs2.getInt( ORG_SLAB ) );

					String query = "select * from org_tax where combination_id=" + rs.getInt( COMBINATION_ID ) + " and slab_id=" + rs2.getInt( ORG_SLAB );

					stmt1 = con.createStatement();
					rs1 = stmt1.executeQuery( query );

					if ( rs1.next() )
					{
						org.setTaxPer( rs1.getDouble( ORG_TAX_PER ) );
					}
				}

				org.setScaleId( rs.getInt( SCALE_ID ) );
				org.setTypeId( rs.getInt( TYPE_ID ) );
				org.setCategoryId( rs.getInt( CATEGORY_ID ) );
				org.setTurnover( rs.getDouble( TURNOVER ) );
				org.setIncome( rs.getDouble( GROSS_PROFIT ) );
				org.setShares( this.getOrganizationShares( utin ) );

				putExemptionDetails( org );
				putInvestmentDetails( org );

			}

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return org;

	}

	// public double getOrganizationIncome(int utin, int year) {
	// Connection con = null;
	// PreparedStatement stmt = null;
	// Statement stmt1 = null;
	// ResultSet rs = null;
	// ResultSet rs1 = null;
	//
	// double income = 0;
	//
	// try {
	// con = getConnection();
	// stmt = con
	// .prepareStatement("select * from org_income where UTIN = ? and inc_year = ?");
	// stmt.setInt(1, utin);
	// stmt.setInt(2, year);
	//
	// rs = stmt.executeQuery();
	//
	// if (rs.next()) {
	// income = rs.getDouble(TURNOVER);
	// String query=
	// "Select Slab_Id from org_slab where Slab_Min_Amt>="+income+"and Slab_Max_Amt<="+income;
	// stmt1 = con.createStatement();
	// rs1= stmt.executeQuery(query);
	// if(rs1.next()){
	//
	// }
	// }
	//
	// } catch (Exception e) {
	//
	// } finally {
	// closeConnectionObjects(rs, stmt, con);
	// }
	//
	// return income;
	// }

	public HashMap<String, Integer> getAllOrganizationScale()
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		HashMap<String, Integer> idMap = null;

		String sql = "select * from org_Scale";

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			idMap = new HashMap<String, Integer>();
			while ( rs.next() )
			{
				idMap.put( rs.getString( SCALE_NAME ), rs.getInt( SCALE_ID ) );
			}

		}
		catch ( Exception e )
		{
			return null;

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return idMap;
	}

	public HashMap<String, Integer> getAllOrganizationType()
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		HashMap<String, Integer> idMap = null;

		String sql = "select * from org_type";

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			idMap = new HashMap<String, Integer>();
			while ( rs.next() )
			{
				idMap.put( rs.getString( TYPE_NAME ), rs.getInt( TYPE_ID ) );
			}

		}
		catch ( Exception e )
		{
			return null;

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return idMap;
	}

	public HashMap<String, Integer> getAllOrganizationCategory()
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		HashMap<String, Integer> idMap = null;

		String sql = "select * from org_category";

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			idMap = new HashMap<String, Integer>();
			while ( rs.next() )
			{
				idMap.put( rs.getString( CATEGORY_NAME ), rs.getInt( CATEGORY_ID ) );
			}

		}
		catch ( Exception e )
		{
			return null;

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return idMap;
	}

	public List<Exemption> getEmployeeExemptions( int utin )
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Exemption> exemptions = null;

		String sql = "select * from exemption natural join has_exmp where utin = " + utin;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			while ( rs.next() )
			{
				if ( exemptions == null )
					exemptions = new ArrayList<Exemption>();

				exemptions.add( new Exemption( rs.getInt( EXEMP_ID ), rs.getString( EXEMP_NAME ), rs.getDouble( EXEMP_AMT ), rs.getDouble( EXEMP_PER ) ) );
			}

		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return exemptions;
	}

	public List<Investment> getEmployeeInvestments( int utin )
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Investment> investments = null;

		String sql = "select * from investment natural join has_inv where utin = " + utin;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			while ( rs.next() )
			{
				if ( investments == null )
					investments = new ArrayList<Investment>();

				investments.add( new Investment( rs.getInt( INV_ID ), rs.getString( INV_NAME ), rs.getDouble( INV_AMT ), rs.getDouble( INV_APPL_PER ) ) );
			}

		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return investments;
	}

	public String getScaleName( int id )
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "select * from org_Scale where scale_id = " + id;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			if ( rs.next() )
			{
				return rs.getString( SCALE_NAME );
			}

		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return null;
	}

	public String getTypeName( int id )
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "select * from org_type where type_id = " + id;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			if ( rs.next() )
			{
				return rs.getString( TYPE_NAME );
			}

		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return null;
	}

	public String getCategoryName( int id )
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "select * from org_category where category_id = " + id;

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );

			if ( rs.next() )
			{
				return rs.getString( CATEGORY_NAME );
			}

		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return null;
	}

	public String[] getExemptionNames( Integer... ids )
	{

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String[] names = new String[ids.length];

		String sql = "select * from exemption where exemption_id in (" + buildInClause( ids.length ) + ");";

		try
		{
			con = getConnection();
			stmt = con.prepareStatement( sql );

			for ( int i = 0; i < ids.length; i++ )
			{
				stmt.setInt( i + 1, ids[i] );
			}

			rs = stmt.executeQuery();

			int index = 0;
			while ( rs.next() )
			{
				names[index] = rs.getString( EXEMP_NAME );
				index++;
			}

			return names;

		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return null;
	}

	public String[] getInvestmentNames( Integer... ids )
	{

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String[] names = new String[ids.length];

		String sql = "select * from investment where investment_id in (" + buildInClause( ids.length ) + ");";

		try
		{
			con = getConnection();
			stmt = con.prepareStatement( sql );

			for ( int i = 0; i < ids.length; i++ )
			{
				stmt.setInt( i + 1, ids[i] );
			}

			rs = stmt.executeQuery();

			int index = 0;
			while ( rs.next() )
			{
				names[index] = rs.getString( INV_NAME );
				index++;
			}

			return names;

		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return null;
	}

	public String[] getTaskNames( Integer... ids )
	{

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String[] names = new String[ids.length];

		String sql = "select * from specl_task where task_id in (" + buildInClause( ids.length ) + ");";

		try
		{
			con = getConnection();
			stmt = con.prepareStatement( sql );

			for ( int i = 0; i < ids.length; i++ )
			{
				stmt.setInt( i + 1, ids[i] );
			}

			rs = stmt.executeQuery();

			int index = 0;
			while ( rs.next() )
			{
				names[index] = rs.getString( TASK_NAME );
				index++;
			}

			return names;

		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return null;
	}

	public String[] getExemptionNames()
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sqlTotal = "Select COUNT(DISTINCT " + EXEMP_NAME + ") AS namecount FROM exemption";
		String sql = "Select DISTINCT " + EXEMP_NAME + " FROM exemption";

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sqlTotal );
			rs.next();
			int count = rs.getInt( "namecount" );

			System.out.println( "Count of exemep names: " + count );
			if ( count <= 0 )
				return null;

			String[] exempNames = new String[count];

			rs = stmt.executeQuery( sql );

			int index = 0;
			while ( rs.next() )
			{
				exempNames[index++] = rs.getString( EXEMP_NAME );
			}

			return exempNames;

		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return null;
	}

	public String[] getInvestmentNames()
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sqlTotal = "Select COUNT(DISTINCT " + INV_NAME + ") AS namecount FROM investment";
		String sql = "Select DISTINCT " + INV_NAME + " FROM investment";

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sqlTotal );
			rs.next();
			int count = rs.getInt( "namecount" );

			System.out.println( "Count of investment names: " + count );
			if ( count <= 0 )
				return null;

			String[] invNames = new String[count];

			rs = stmt.executeQuery( sql );

			int index = 0;
			while ( rs.next() )
			{
				invNames[index++] = rs.getString( INV_NAME );
			}

			return invNames;

		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return null;
	}

	public String[] getStockSymbols()
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sqlTotal = "Select COUNT(DISTINCT " + SYMBOL + ") AS namecount FROM stockcompanies";
		String sql = "Select DISTINCT " + SYMBOL + " FROM stockcompanies";

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sqlTotal );
			rs.next();
			int count = rs.getInt( "namecount" );

			System.out.println( "Count of stock symbols: " + count );
			if ( count <= 0 )
				return null;

			String[] stockSymbols = new String[count];

			rs = stmt.executeQuery( sql );

			int index = 0;
			while ( rs.next() )
			{
				stockSymbols[index++] = rs.getString( SYMBOL );
			}

			return stockSymbols;

		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return null;
	}

	public int getExemptionId( String exempetionName )
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "Select " + EXEMP_ID + " FROM exemption WHERE " + EXEMP_NAME + " = '" + exempetionName + "'";

		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( sql );
			rs.next();
			int id = rs.getInt( EXEMP_ID );
			return id;
		}
		catch ( Exception e )
		{

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return -1;

	}

	public int getSlabId( double income )
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		int slabId = 0;
		String query = "select slab_id from org_slab where slab_min_amt<= " + income + " and slab_max_amt > " + income;
		try
		{
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery( query );
			if ( rs.next() )
			{
				slabId = rs.getInt( EMP_SLAB );
			}
			return slabId;
		}
		catch ( Exception e )
		{
			System.out.println( e );
			return 0;
		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}
	}

	public double getSharePercents( int utin )
	{

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try
		{
			con = getConnection();

			String query = "select Share_Percent from has_partnership where Share_UTIN=" + utin;
			double percents = 0;
			stmt = con.createStatement();
			rs = stmt.executeQuery( query );

			while ( rs.next() )
			{
				percents += rs.getInt( SHARE_PERCENT );
			}

			return (100 - percents);

		}
		catch ( SQLException e )
		{

		}

		return 0;
	}

	public boolean insertTaxPayer( TaxPayer taxPayer )
	{

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "insert into tax_payer(tp_password, firstname, lastname, city, state, nationality, tp_category) values(?,?,?,?,?,?,?);";

		try
		{
			con = getConnection();
			stmt = con.prepareStatement( sql );

			stmt.setString( 1, taxPayer.getPassword() );
			stmt.setString( 2, taxPayer.getFirstName() );
			stmt.setString( 3, taxPayer.getLastName() );
			stmt.setString( 4, taxPayer.getCity() );
			stmt.setString( 5, taxPayer.getState() );
			stmt.setString( 6, taxPayer.getNationality().toString() );
			stmt.setInt( 7, taxPayer instanceof Employee ? TaxPayer.SubType.EMPLOYEE.ordinal() : TaxPayer.SubType.ORGANIZATION.ordinal() );

			stmt.executeUpdate();
			stmt.close();
			stmt = null;

			if ( taxPayer instanceof Employee )
			{
				sql = "insert into employee values(?,?,?,?,?,?,?);";
				stmt = con.prepareStatement( sql );

				stmt.setInt( 1, taxPayer.getUtin() );
				stmt.setString( 2, dateFormat.format( ((Employee) taxPayer).getDateOfBirth() ) );
				stmt.setString( 3, ((Employee) taxPayer).getGender().toString() );
				stmt.setInt( 4, ((Employee) taxPayer).getNoOfDependants() );
				stmt.setString( 5, ((Employee) taxPayer).getMaritalStatus().toString() );
				stmt.setString( 6, ((Employee) taxPayer).getResidencyStatus().toString() );

				if ( taxPayer instanceof Student )
					stmt.setInt( 7, Employee.SubType.STUDENT.ordinal() );
				else if ( taxPayer instanceof SeniorCitizen )
					stmt.setInt( 7, Employee.SubType.SENIOR_CITIZEN.ordinal() );
				else if ( taxPayer instanceof ArmedForcePersonnel )
					stmt.setInt( 7, Employee.SubType.ARMY.ordinal() );
				else
					stmt.setInt( 7, Employee.SubType.NONE.ordinal() );

				stmt.executeUpdate();
				stmt.close();
				stmt = null;
			}

		}
		catch ( Exception e )
		{
			return false;

		}
		finally
		{
			closeConnectionObjects( rs, stmt, con );
		}

		return true;
	}

	private String buildInClause( int batchSize )
	{

		StringBuilder inClause = new StringBuilder();
		boolean firstValue = true;

		for ( int i = 0; i < batchSize; i++ )
		{
			if ( firstValue )
			{
				firstValue = false;
			}
			else
			{
				inClause.append( ',' );
			}

			inClause.append( '?' );
		}

		return inClause.toString();
	}

	private void closeConnectionObjects( ResultSet rs, Statement stmt, Connection con )
	{

		if ( rs != null )
			try
			{
				rs.close();
			}
			catch ( SQLException ignore )
			{
			}
		if ( stmt != null )
			try
			{
				stmt.close();
			}
			catch ( SQLException ignore )
			{
			}
		if ( con != null )
			try
			{
				con.close();
			}
			catch ( SQLException ignore )
			{
			}
	}

}
