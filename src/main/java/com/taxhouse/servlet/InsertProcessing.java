package com.taxhouse.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taxhouse.model.TaxPayer;
import com.taxhouse.model.TaxPayer.Nationality;

public class InsertProcessing extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	public InsertProcessing()
	{

		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		response.setContentType( "text/html" );
		
		
		TaxPayer taxPayer = new TaxPayer();
		taxPayer.setFirstName( request.getParameter( "firstname" ).toString() );
		taxPayer.setLastName(  request.getParameter( "lastname" ).toString()  );
		taxPayer.setPassword(  request.getParameter( "tp_password" ).toString());
		taxPayer.setCity(  request.getParameter( "city" ).toString()  );
		taxPayer.setState(  request.getParameter( "state" ).toString());
		
		int iNationality = Integer.parseInt( request.getParameter( "nationality" ).toString()); 
		
		if(iNationality == 1)
			taxPayer.setNationality( Nationality.USA );
		else if(iNationality == 2)
			taxPayer.setNationality( Nationality.NON_USA );
		
		int iCategory = Integer.parseInt( request.getParameter( "tp_category" ).toString());
		
		
		
		if(iCategory == TaxPayer.SubType.EMPLOYEE.ordinal())
		{
			System.out.println("Employee Ordinal: "+TaxPayer.SubType.EMPLOYEE.ordinal());
			RequestDispatcher requestDispatcher = request.getRequestDispatcher( "insert_employee.jsp" );
			request.setAttribute( "employee", taxPayer );
			requestDispatcher.forward( request, response );
		}
		else if(iCategory == TaxPayer.SubType.ORGANIZATION.ordinal())
		{
			System.out.println("Organization Ordinal: "+TaxPayer.SubType.ORGANIZATION.ordinal());
			RequestDispatcher requestDispatcher = request.getRequestDispatcher( "insert_organization.jsp" );
			request.setAttribute( "organization", taxPayer );
			requestDispatcher.forward( request, response );
		}
		
		
	}

}
