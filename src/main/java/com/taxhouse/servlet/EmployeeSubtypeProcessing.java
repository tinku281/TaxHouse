package com.taxhouse.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.taxhouse.db.DBHandler;
import com.taxhouse.model.Employee;
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
				System.out.println("Employee Subtype Processing: SUCCESSFUL");
			}
			else
			{
				// error inserting record
			}
			
		}
		else if ( empType == Employee.SubType.SENIOR_CITIZEN.ordinal() )
		{
			
		}
		else if ( empType == Employee.SubType.ARMY.ordinal() )
		{
			
		}

	}

}
