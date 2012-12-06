package com.taxhouse.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	public Logout()
	{

		super();
	}

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{

		HttpSession httpSession = request.getSession(false);
		
		if(httpSession != null)
			httpSession.invalidate();
		
		RequestDispatcher requestDispatcher;
		requestDispatcher = request.getRequestDispatcher( "login.jsp" );
		requestDispatcher.forward( request, response );
		return;
	}
}
