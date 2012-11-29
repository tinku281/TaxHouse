package com.taxhouse.servlet;

import java.io.IOException;
import java.util.ArrayList;

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
import com.taxhouse.model.SeniorCitizen;
import com.taxhouse.model.Student;

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
		
		if(httpSession.isNew())
		{
			httpSession.invalidate();
			requestDispatcher  = request.getRequestDispatcher( "login.jsp" );
			requestDispatcher.forward( request, response );
			return;
		}
		
		
		
		int exmpCount  = Integer.parseInt( request.getParameter( "count" ).toString() );
//		int invCount  = Integer.parseInt( request.getParameter( "inv_count" ).toString() );
		
		int empType = Integer.parseInt( request.getParameter( "emp_type" ).toString() );
		int empMaritalStatus = Integer.parseInt( request.getParameter( "emp_mar_status" ).toString() );
		int spouseUtin=0;
		if(empMaritalStatus == 1)
		{
			spouseUtin = Integer.parseInt( request.getParameter( "spouse_utin" ).toString() );
		}
		
		ArrayList<Exemption> exemptionsList = new ArrayList<Exemption>();
		
		if(exmpCount > 0)
		{
			DBHandler dbHandler = new DBHandler();
			
			for(int index = 0; index < exmpCount; index++)
			{
				int i = index+1;
				String exempName = request.getParameter( "exemptionname"+i );
				int exempType = Integer.parseInt(request.getParameter( "exemptiontype"+i ));
				Double exempAmount = Double.parseDouble( request.getParameter( "exemptionamount"+i ));
				int exempId = dbHandler.getExemptionId( exempName.trim() );
				
				Exemption exemption;
				if(exempType == 1)
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
		
		System.out.println("\nEmployee Type: "+empType);
		System.out.println("\nEmployee Marital Status: "+empMaritalStatus);
		System.out.println("\nEmployee Spouse UTIN: "+ spouseUtin);
		System.out.println("\nEmployee Exemption Count: "+exmpCount);
		
//		if(exmpCount > 0)
//		{
//			for(int index= 0;index < exmpCount ; index++)
//			{	
//				System.out.println("\n Exemption Name: "+exmpNames[index]+", Exemption Type: "+exmpType[index]+", Exemption Amount/Per: "+exmpAmount[index]);
//			}
//		}
		
		Employee employee;
		
		if(empType == Employee.SubType.STUDENT.ordinal())
		{
			employee = new Student();
		}
		else if(empType == Employee.SubType.SENIOR_CITIZEN.ordinal())
		{
			employee = new SeniorCitizen();
		}
		else if(empType == Employee.SubType.ARMY.ordinal())
		{
			employee = new ArmedForcePersonnel();
		}
		else
		{
			employee =new Employee();
		}
		
		employee.setFirstName( httpSession.getAttribute( "firstname" ).toString() );
		employee.setLastName( httpSession.getAttribute( "firstname" ).toString() );
		employee.setPassword( httpSession.getAttribute( "password" ).toString() );
		employee.setCity( httpSession.getAttribute( "city" ).toString() );
		employee.setState( httpSession.getAttribute( "state" ).toString() );
		
		switch ( empMaritalStatus )
		{
			case 0: employee.setMaritalStatus( MaritalStatus.SINGLE);
					break;
			case 1: employee.setMaritalStatus( MaritalStatus.MARRIED);
					employee.setSpouseUtin( spouseUtin );
					break;
			case 2: employee.setMaritalStatus( MaritalStatus.DIVORCED);
					break;
			case 3: employee.setMaritalStatus( MaritalStatus.WIDOW);
					break;
		}
		
		if(exmpCount > 0)
			employee.setExemptions( exemptionsList );
		
	}
	

}
