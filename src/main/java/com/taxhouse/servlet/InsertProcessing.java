package com.taxhouse.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.taxhouse.db.DBHandler;
import com.taxhouse.model.TaxPayer;

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
		
		int iCategory = Integer.parseInt( request.getParameter( "tp_category" ).toString());
		
		HttpSession httpSession = request.getSession();
		
		if(httpSession.isNew())
		{
			httpSession.invalidate();
			RequestDispatcher requestDispatcher  = request.getRequestDispatcher( "login.jsp" );
			requestDispatcher.forward( request, response );
			return;
		}
				
		httpSession.setAttribute( "firstname", request.getParameter( "firstname" ));
		httpSession.setAttribute( "lastname", request.getParameter( "lastname" ));
		httpSession.setAttribute( "password", request.getParameter( "tp_password" ));
		httpSession.setAttribute( "city",  request.getParameter( "city" ).toString());
		httpSession.setAttribute( "state", request.getParameter( "state" ));
		httpSession.setAttribute( "nationality",request.getParameter( "nationality" ));
		
		
		if(iCategory == TaxPayer.SubType.EMPLOYEE.ordinal())
		{
			System.out.println("Employee Ordinal: "+TaxPayer.SubType.EMPLOYEE.ordinal());
			
			String[] exempNames = DBHandler.getInstance().getExemptionNames();
			
			if(exempNames!= null)
			System.out.println("Exemeption names length: "+exempNames.length);
			else
			System.out.println("Exemption names: NULL");	
			
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher( "insert_employee.jsp" );
			request.setAttribute( "exemption_names", exempNames );
			requestDispatcher.forward( request, response );
		}
		else if(iCategory == TaxPayer.SubType.ORGANIZATION.ordinal())
		{
			System.out.println("Organization Ordinal: "+TaxPayer.SubType.ORGANIZATION.ordinal());
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher( "insert_organization.jsp" );
			requestDispatcher.forward( request, response );
		}
		
		
	}
	

}
