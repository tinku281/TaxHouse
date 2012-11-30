package com.taxhouse.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.taxhouse.db.DBHandler;
import com.taxhouse.model.TaxPayer;

public class LoginValidation extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	public LoginValidation()
	{

		super();
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{

		String sUtin = request.getParameter( "utin" );
		String passwd = request.getParameter( "passwd" );
		String role = request.getParameter( "role" );

		PrintWriter output = response.getWriter();
		response.setContentType( "text/html" );
		HttpSession httpSession = request.getSession();
		httpSession.setMaxInactiveInterval( 15*60 );

		if ( role.equals( "taxpayer" ) )
		{

			if ( DBHandler.getInstance().validateTaxPayer( sUtin, passwd ) )
			{

				TaxPayer taxPayer = DBHandler.getInstance().getTaxPayer( Integer.parseInt( sUtin ) );

				if ( taxPayer != null )
				{
					RequestDispatcher requestDispatcher = request.getRequestDispatcher( "details.jsp" );
					request.setAttribute( "taxpayer", taxPayer );
					httpSession.setAttribute( "role", "taxpayer" );
					requestDispatcher.forward( request, response );
					
					// output.println("<br/>" + taxPayer.getFirstName() +
					// "<br/>"
					// + taxPayer.getLastName() + "<br/>"
					// + taxPayer.getIncome());
				}

			}
			else
			{
				String errorMsg = "Invalid UTIN or password";
				response.sendRedirect( "login.jsp?error=" + errorMsg );
			}

		}
		else
		{
			if ( DBHandler.getInstance().validateAdmin( sUtin, passwd ) )
			{
				output.println( "Validated" );
				httpSession.setAttribute( "role", "admin" );
				httpSession.setAttribute( "functionType", "1" );
				RequestDispatcher requestDispatcher = request.getRequestDispatcher( "enter_utin.jsp" );
				requestDispatcher.forward( request, response );

			}
			else
			{
				String errorMsg = "Invalid AdminstratorID or password";
				response.sendRedirect( "login.jsp?error=" + errorMsg );
			}
		}
	}

}
