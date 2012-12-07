package com.taxhouse.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.taxhouse.db.DBHandler;
import com.taxhouse.model.Organization;
import com.taxhouse.model.TaxPayer;

public class HandleUTIN extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	public HandleUTIN()
	{

		super();
	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
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

		int utin = Integer.parseInt( request.getParameter( "utin" ) );
		int functionType = Integer.parseInt( httpSession.getAttribute( "functionType" ).toString() );
		TaxPayer taxPayer = DBHandler.getInstance().getTaxPayer( utin );

		if ( functionType == 1 )
		{

			if ( taxPayer != null )
			{
				requestDispatcher = request.getRequestDispatcher( "details.jsp" );
				request.setAttribute( "taxpayer", taxPayer );
				if ( taxPayer instanceof Organization )
				{
					Organization organization = (Organization) taxPayer;
					String scale = DBHandler.getInstance().getScaleName( organization.getScaleId() );
					String category = DBHandler.getInstance().getCategoryName( organization.getCategoryId() );
					String type = DBHandler.getInstance().getTypeName( organization.getTypeId() );
					request.setAttribute( "scale", scale );
					request.setAttribute( "category", category );
					request.setAttribute( "type", type );
				}
				httpSession.setAttribute("taxpay", taxPayer);
				requestDispatcher.forward( request, response );

			}

			else
			{
				String errorMsg = "Invalid UTIN";
				response.sendRedirect( "enter_utin.jsp?error=" + errorMsg );
			}
		}
		else if(functionType == 2)
		{
			

			if ( taxPayer != null )
			{
				//code to delete taxpayer whose UTIN = utin from database and delete all relations
				
				if(DBHandler.getInstance().deleteTaxPayer(taxPayer.getUtin()))
					System.out.println("Deleted");
				else
					System.out.println("Not Deleted");
				
				requestDispatcher = request.getRequestDispatcher( "successful_delete.jsp" );
				requestDispatcher.forward( request, response );

			}
			else
			{
				String errorMsg = "Invalid UTIN";
				response.sendRedirect( "enter_utin.jsp?error=" + errorMsg );
			}
		}
		else if(functionType == 3)
		{
			if ( taxPayer != null )
			{
				httpSession.setAttribute( "taxpayee", taxPayer );
				requestDispatcher = request.getRequestDispatcher( "insert_tax_payer.jsp" );
				requestDispatcher.forward( request, response );

			}
			else
			{
				String errorMsg = "Invalid UTIN";
				response.sendRedirect( "enter_utin.jsp?error=" + errorMsg );
			}
		}

	}

}
