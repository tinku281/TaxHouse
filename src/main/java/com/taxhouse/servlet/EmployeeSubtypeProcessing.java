package com.taxhouse.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

		int empType = Integer.parseInt( httpSession.getAttribute( "empType" ).toString() );
		Employee employee = (Employee)httpSession.getAttribute( "employee" );
		
		if ( empType == Employee.SubType.STUDENT.ordinal() )
		{
			Student student  = (Student)employee;
			double waiverAmount = Double.parseDouble( request.getParameter( "waiver_amount" ));
			student.setFeeWaiverAmt(waiverAmount );
			if(DBHandler.getInstance().insertTaxPayer( student ))
			{
				//forward to insertion successful page
				System.out.println("Employee Subtype Processing: Inserted Student");
			}
			else
			{
				// error inserting record
			}
			
		}
		else if ( empType == Employee.SubType.SENIOR_CITIZEN.ordinal() )
		{
			SeniorCitizen seniorCitizen = (SeniorCitizen)employee;
			
			//retrieving income list from insert_senior_citizen form
			ArrayList<Income> incomeList = new ArrayList<Income>();
			
			int incomeCount = Integer.parseInt( request.getParameter( "count" ));
			
			if(incomeCount > 0)
			{
				
				for(int index = 0; index < incomeCount; index++)
				{
					int i = index+1;
					
					String incomeName = request.getParameter( "incomename"+i );
					Double incomeAmount = Double.parseDouble( request.getParameter( "incomeamount"+i ));
					
					Income income = new Income( incomeName, incomeAmount );
					incomeList.add( income );
					System.out.println("Added Income- Source: "+incomeName+" Amount: "+incomeAmount);
				}
			}
			
			
			
			//set Income list for senior citizen
			seniorCitizen.setIncomes( incomeList );
			
			if(DBHandler.getInstance().insertTaxPayer( seniorCitizen ))
			{
				//forward to insertion successful page
				System.out.println("Employee Subtype Processing: Inserted Senior Citizen");
			}
			else
			{
				// error inserting record
			}
			
			
		}
		else if ( empType == Employee.SubType.ARMY.ordinal() )
		{
			ArmedForcePersonnel armedForcePersonnel = (ArmedForcePersonnel)employee;
			
			//retrieving special tasks list from insert_armed form
			ArrayList<SpecialTask> specialTaskList = new ArrayList<SpecialTask>();
			
			int spCount = Integer.parseInt( request.getParameter( "count" ));
			
			if(spCount > 0)
			{
				
				for(int index = 0; index < spCount; index++)
				{
					int i = index+1;
					
					String scName = request.getParameter( "scname"+i );
					String scCombatZone =  request.getParameter( "sccombat"+i );
					String scStartDate = request.getParameter( "scstartdate"+i );
					String scEndDate = request.getParameter( "scenddate"+i );
					
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
					Date startDate = null,endDate = null;
					
					try
					{
						startDate = simpleDateFormat.parse( scStartDate );
						endDate = simpleDateFormat.parse( scEndDate );
						System.out.println("Start Date is: "+startDate.toString()+" , End Date is: "+endDate.toString());
					}
					catch(Exception e)
					{
						System.out.println("EmployeeSubtypeProcessing, Date Parse Exception");
					}
					
					SpecialTask specialTask = new SpecialTask( 0, scName, startDate, endDate, scCombatZone );
					specialTaskList.add( specialTask );
					System.out.println("Added Special Task: "+scName+" Cobat zone: "+scCombatZone);
				}
			}
			
			
			
			//set Income list for senior citizen
			
			if(DBHandler.getInstance().insertTaxPayer( armedForcePersonnel ))
			{
				//forward to insertion successful page
				System.out.println("Employee Subtype Processing: Inserted Armed Force Personnel");
			}
			else
			{
				// error inserting record
			}
		}

	}

}
