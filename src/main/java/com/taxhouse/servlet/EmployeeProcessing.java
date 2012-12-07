package com.taxhouse.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.taxhouse.db.DBHandler;
import com.taxhouse.model.ArmedForcePersonnel;
import com.taxhouse.model.Employee;
import com.taxhouse.model.Employee.MaritalStatus;
import com.taxhouse.model.Exemption;
import com.taxhouse.model.Investment;
import com.taxhouse.model.SeniorCitizen;
import com.taxhouse.model.Student;
import com.taxhouse.model.TaxPayer.Nationality;

/**
 * Servlet implementation class EmployeeProcessing
 */
public class EmployeeProcessing extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	public EmployeeProcessing()
	{

		super();
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{

		HttpSession httpSession = request.getSession();
		RequestDispatcher requestDispatcher;

		if ( httpSession.isNew() )
		{
			httpSession.invalidate();
			requestDispatcher = request.getRequestDispatcher( "login.jsp" );
			requestDispatcher.forward( request, response );
			return;
		}

		int functionType = Integer.parseInt( httpSession.getAttribute( "functionType" ).toString() );
		int exmpCount = Integer.parseInt( request.getParameter( "count" ).toString() );
		int invCount = Integer.parseInt( request.getParameter( "inv_count" ).toString() );

		int empType;
		if ( functionType == 4 )
			empType = Integer.parseInt( request.getParameter( "emp_type" ).toString() );
		else
		{
			String sEmpType = request.getParameter( "emp_type" ).toString();
			
			if(sEmpType.equals( "Student" ))
				empType = 1;
			else if(sEmpType.equals( "Senior Citizen" ))
				empType = 2;
			else if(sEmpType.equals( "Army" ))
				empType = 3;
			else
				empType = 0;
		}

		int empMaritalStatus = Integer.parseInt( request.getParameter( "emp_mar_status" ).toString() );
		int spouseUtin = 0;
		if ( empMaritalStatus == 1 )
		{
			spouseUtin = Integer.parseInt( request.getParameter( "spouse_utin" ).toString() );
		}

		ArrayList<Exemption> exemptionsList = new ArrayList<Exemption>();

		if ( exmpCount > 0 )
		{
			DBHandler dbHandler = new DBHandler();

			for ( int index = 0; index < exmpCount; index++ )
			{
				int i = index + 1;
				String exempName = request.getParameter( "exemptionname" + i );
				int exempType = Integer.parseInt( request.getParameter( "exemptiontype" + i ) );
				Double exempAmount = Double.parseDouble( request.getParameter( "exemptionamount" + i ) );
				int exempId = dbHandler.getExemptionId( exempName.trim() );

				Exemption exemption;
				if ( exempType == 1 )
				{
					exemption = new Exemption( exempId, exempName, exempAmount, 0 );
				}
				else
				{
					exemption = new Exemption( exempId, exempName, 0, exempAmount );
				}

				exemptionsList.add( exemption );
			}
		}

		ArrayList<Investment> investmentsList = new ArrayList<Investment>();

		if ( invCount > 0 )
		{
			// DBHandler dbHandler = new DBHandler();

			for ( int index = 0; index < invCount; index++ )
			{
				int i = index + 1;
				String invName = request.getParameter( "investmentname" + i );
				Double invAmount = Double.parseDouble( request.getParameter( "investmentamount" + i ) );
				Double invPer = Double.parseDouble( request.getParameter( "investmentper" + i ) );
				int invId = 0; // handle the id, retrieve from database or
								// whatever you want

				Investment investment = new Investment( invId, invName, invAmount, invPer );
				investmentsList.add( investment );

				System.out.println( "Investment Name: " + invName + ", Investment Amount: " + invAmount + ", Investment Percentage: " + invPer );

			}
		}

		Employee employee;

		if ( empType == Employee.SubType.STUDENT.ordinal() )
		{
			employee = new Student();
		}
		else if ( empType == Employee.SubType.SENIOR_CITIZEN.ordinal() )
		{
			employee = new SeniorCitizen();
		}
		else if ( empType == Employee.SubType.ARMY.ordinal() )
		{
			employee = new ArmedForcePersonnel();
		}
		else
		{
			employee = new Employee();
		}

		employee.setFirstName( httpSession.getAttribute( "firstname" ).toString() );
		employee.setLastName( httpSession.getAttribute( "lastname" ).toString() );
		employee.setPassword( httpSession.getAttribute( "password" ).toString() );
		employee.setCity( httpSession.getAttribute( "city" ).toString() );
		employee.setState( httpSession.getAttribute( "state" ).toString() );

		int nationality = Integer.parseInt( httpSession.getAttribute( "nationality" ).toString() );
		if ( nationality == 1 )
			employee.setNationality( Nationality.USA );
		else
			employee.setNationality( Nationality.NON_USA );

		switch ( empMaritalStatus )
		{
			case 0:
				employee.setMaritalStatus( MaritalStatus.SINGLE );
				break;
			case 1:
				employee.setMaritalStatus( MaritalStatus.MARRIED );
				employee.setSpouseUtin( spouseUtin );
				break;
			case 2:
				employee.setMaritalStatus( MaritalStatus.DIVORCED );
				break;
			case 3:
				employee.setMaritalStatus( MaritalStatus.WIDOW );
				break;
		}

		if ( exmpCount > 0 )
			employee.setExemptions( exemptionsList );
		if ( invCount > 0 )
			employee.setInvestments( investmentsList );

		System.out.println( "\nEmployee FirstName: " + employee.getFirstName() );
		System.out.println( "\nEmployee LastName: " + employee.getLastName() );
		System.out.println( "\nEmployee City: " + employee.getCity() );
		System.out.println( "\nEmployee State: " + employee.getState() );
		System.out.println( "\nEmployee Nationality: " + employee.getNationality().name() );
		System.out.println( "\nEmployee Marital Status: " + employee.getMaritalStatus().name() );
		if ( empMaritalStatus == 1 )
			System.out.println( "\nEmployee Spouse UTIN: " + employee.getSpouseUtin() );
		System.out.println( "\nEmployee Exemption Count: " + exmpCount );
		if ( exmpCount > 0 )
		{
			List<Exemption> exmpList = employee.getExemptions();
			for ( Exemption e : exmpList )
			{
				System.out.println( "Exemption Name: " + e.getName() );
				System.out.println( "Exemption Id: " + e.getId() );
				System.out.println( "Exemption Amount: " + e.getAmount() );
				System.out.println( "Exemption Percentage: " + e.getPercentage() );
			}
		}

		httpSession.setAttribute( "employee", employee );
		httpSession.setAttribute( "empType", empType );

		if ( empType == Employee.SubType.STUDENT.ordinal() )
		{
			request.setAttribute( "type", functionType );
			requestDispatcher = request.getRequestDispatcher( "insert_student.jsp" );
			requestDispatcher.forward( request, response );
		}
		else if ( empType == Employee.SubType.SENIOR_CITIZEN.ordinal() )
		{
			if( functionType == 4)
				requestDispatcher = request.getRequestDispatcher( "insert_senior_citizen.jsp" );
			else
				requestDispatcher = request.getRequestDispatcher( "update_senior_citizen.jsp" );
			
			requestDispatcher.forward( request, response );
		}
		else if ( empType == Employee.SubType.ARMY.ordinal() )
		{
			
			if( functionType == 4)
				requestDispatcher = request.getRequestDispatcher( "insert_armed.jsp" );
			else
				requestDispatcher = request.getRequestDispatcher( "update_armed.jsp" );
			
			requestDispatcher.forward( request, response );
		}
		else
		{
			if (functionType == 4) {
				if (DBHandler.getInstance().insertTaxPayer(employee)) {
					// forward to insertion successful page
					System.out.println("Employee Processing: Inserted");
				} else {
					// error inserting record
				}
			}

			else {

				if (DBHandler.getInstance().updateTaxPayer(employee)) {
					// forward to insertion successful page
					System.out.println("Employee Processing: Updated");
				} else {
					// error inserting record
				}
			}
		}
	}

}
