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
		
		String sCategory = request.getParameter( "tp_category" );
		
		HttpSession httpSession = request.getSession();
		
		if(httpSession.isNew())
		{
			httpSession.invalidate();
			RequestDispatcher requestDispatcher  = request.getRequestDispatcher( "login.jsp" );
			requestDispatcher.forward( request, response );
			return;
		}
		
		int functiontype = Integer.parseInt( httpSession.getAttribute( "functionType" ).toString() );
		int iCategory;
		
		if( functiontype == 4)
			iCategory = Integer.parseInt( sCategory );
		
		else 
		{
			if(sCategory.trim().equals( "Employee" ))
				iCategory = 1;
			else
				iCategory =2;
		}
		
				
		httpSession.setAttribute( "firstname", request.getParameter( "firstname" ));
		httpSession.setAttribute( "lastname", request.getParameter( "lastname" ));
		httpSession.setAttribute( "password", request.getParameter( "tp_password" ));
		httpSession.setAttribute( "city",  request.getParameter( "city" ));
		httpSession.setAttribute( "state", request.getParameter( "state" ));
		httpSession.setAttribute( "nationality",request.getParameter( "nationality" ));
		
		String[] exempNames = DBHandler.getInstance().getExemptionNames();
		String[] invNames = DBHandler.getInstance().getInvestmentNames();
		request.setAttribute( "exemption_names", exempNames );
		request.setAttribute( "investment_names", invNames );
		
		if(iCategory == TaxPayer.SubType.EMPLOYEE.ordinal())
		{
			System.out.println("Employee Ordinal: "+TaxPayer.SubType.EMPLOYEE.ordinal());
		
			String[] stockSymobls = DBHandler.getInstance().getStockSymbols();
			
				
			String  forwardPage = (functiontype == 4) ?"insert_employee.jsp":"update_employee.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(forwardPage );
			request.setAttribute( "stock_symbols", stockSymobls );
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
