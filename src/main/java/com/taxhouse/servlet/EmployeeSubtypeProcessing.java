package com.taxhouse.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.taxhouse.db.DBHandler;
import com.taxhouse.model.ArmedForcePersonnel;
import com.taxhouse.model.ArmedForcePersonnel.SpecialTask;
import com.taxhouse.model.Employee;
import com.taxhouse.model.Organization;
import com.taxhouse.model.SeniorCitizen;
import com.taxhouse.model.SeniorCitizen.Income;
import com.taxhouse.model.Student;

public class EmployeeSubtypeProcessing extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	public EmployeeSubtypeProcessing()
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

		String customMessage;
		int functionType = Integer.parseInt( httpSession.getAttribute( "functionType" ).toString() );

		int empType = Integer.parseInt( httpSession.getAttribute( "empType" ).toString() );
		Employee employee = (Employee) httpSession.getAttribute( "employee" );
		
		int empOrgUTIN;
		String empDesignation;
		Date empJoinDate;
		try
		{
			empOrgUTIN = Integer.parseInt( request.getParameter( "emp_org_utin" ) );
			empDesignation = request.getParameter( "emp_designation" );
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			empJoinDate = simpleDateFormat.parse( request.getParameter( "emp_org_join_date" ) );
		}
		catch(Exception e)
		{
			empOrgUTIN = -1;
			empDesignation = "";
			empJoinDate = null;
		}
		
		if(employee != null && !(empType == Employee.SubType.SENIOR_CITIZEN.ordinal()))
		{
			try
			{
				Organization organization =  DBHandler.getInstance().getOrganization( empOrgUTIN );
				
				if(organization != null)
					employee.setOrganization( organization);
//				else
					// If the organization UTIN doesn't exist then take the default value	
			}
			catch ( SQLException e )
			{
				e.printStackTrace();
			}
			catch ( ParseException e )
			{
				e.printStackTrace();
			}
			
			employee.setDesignation( empDesignation );
			employee.setJobStartDate( empJoinDate );
		}
		
		if(employee.getOrganization() != null)
			System.out.println("Employee Organization UTIN: "+employee.getOrganization().getUtin());
		
		System.out.println("Employee Designation: "+employee.getDesignation());
		System.out.println("Employee Joining Date: "+employee.getJobStartDate());
		
		if ( empType == Employee.SubType.STUDENT.ordinal() )
		{
			Student student = (Student) employee;
			double waiverAmount = Double.parseDouble( request.getParameter( "waiver_amount" ) );
			student.setFeeWaiverAmt( waiverAmount );
			customMessage = sendToDB( functionType, student );
			
		}
		else if ( empType == Employee.SubType.SENIOR_CITIZEN.ordinal() )
		{
			SeniorCitizen seniorCitizen = (SeniorCitizen) employee;

			// retrieving income list from insert_senior_citizen form
			ArrayList<Income> incomeList = new ArrayList<Income>();

			int incomeCount = Integer.parseInt( request.getParameter( "count" ) );

			if ( incomeCount > 0 )
			{

				for ( int index = 0; index < incomeCount; index++ )
				{
					int i = index + 1;

					String incomeName = request.getParameter( "incomename" + i );
					Double incomeAmount = Double.parseDouble( request.getParameter( "incomeamount" + i ) );

					Income income = new Income( incomeName, incomeAmount );
					incomeList.add( income );
					System.out.println( "Added Income- Source: " + incomeName + " Amount: " + incomeAmount );
				}
			}

			// set Income list for senior citizen
			seniorCitizen.setIncomes( incomeList );

			customMessage = sendToDB( functionType, seniorCitizen );

		}
		else if ( empType == Employee.SubType.ARMY.ordinal() )
		{
			ArmedForcePersonnel armedForcePersonnel = (ArmedForcePersonnel) employee;

			// retrieving special tasks list from insert_armed form
			ArrayList<SpecialTask> specialTaskList = new ArrayList<SpecialTask>();

			int spCount = Integer.parseInt( request.getParameter( "count" ) );


			if (spCount > 0) {
				
				HashMap<String, Integer> taskIds = new HashMap<String, Integer>();
				taskIds.put("Red Cross", 1);
				taskIds.put("US Marine", 2);
				taskIds.put("Combat", 3);

				for ( int index = 0; index < spCount; index++ )
				{
					int i = index + 1;

					String scName = request.getParameter( "scname" + i );
					String scCombatZone = request.getParameter( "sccombat" + i );
					String scStartDate = request.getParameter( "scstartdate" + i );
					String scEndDate = request.getParameter( "scenddate" + i );

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
					Date startDate = null, endDate = null;

					try
					{
						startDate = simpleDateFormat.parse( scStartDate );
						endDate = simpleDateFormat.parse( scEndDate );
						System.out.println( "Start Date is: " + startDate.toString() + " , End Date is: " + endDate.toString() );
					}
					catch ( Exception e )
					{
						System.out.println( "EmployeeSubtypeProcessing, Date Parse Exception" );
					}

					
					SpecialTask specialTask = new SpecialTask(taskIds.get(scName), scName, startDate, endDate, scCombatZone);
					specialTaskList.add(specialTask);
					System.out.println("Added Special Task: " + scName + " Cobat zone: " + scCombatZone);
				}
				
				armedForcePersonnel.setSpecialTasks(specialTaskList);
			}

			customMessage =sendToDB( functionType, armedForcePersonnel );
		}
		else 
		{
			customMessage =sendToDB( functionType, employee );
		}
		
		request.setAttribute( "custom_message", customMessage );
		request.getRequestDispatcher( "display_message.jsp" ).forward( request, response );

	}

	private String sendToDB( int functionType, Employee employee )
	{

		if ( functionType == 4 )
		{
			if ( DBHandler.getInstance().insertTaxPayer( employee ) )
			{
				// forward to insertion successful page
				System.out.println( "Employee Subtype Processing: Inserted" );
				return "Record Insertion Successful";
			}
			else
			{
				// error inserting record
				return "Record Insertion Unsuccessful";
			}
		}

		else
		{

			if ( DBHandler.getInstance().updateTaxPayer( employee ) )
			{
				// forward to updation successful page
				System.out.println( "Employee Subtype Processing: Updated" );
				return "Record Updation Successful";
			}
			else
			{
				// error updating record
				return "Record Updation Unsuccessful";
			}
		}
		
	}

}
