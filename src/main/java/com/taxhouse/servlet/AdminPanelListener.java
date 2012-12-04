package com.taxhouse.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminPanelListener
 */
public class AdminPanelListener extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AdminPanelListener() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession httpSession = request.getSession();
		RequestDispatcher requestDispatcher;

		if (httpSession.isNew()) {
			httpSession.invalidate();
			requestDispatcher = request.getRequestDispatcher("login.jsp");
			requestDispatcher.forward(request, response);
			return;
		}

		String buttonLabel = request.getParameter("panel_button");

		if (buttonLabel.equals("Calculate Tax")) {
			httpSession.setAttribute("functionType", "1");
			requestDispatcher = request.getRequestDispatcher("enter_utin.jsp");
			requestDispatcher.forward(request, response);
		} else if (buttonLabel.equals("Insert Tax Payer")) {
			httpSession.setAttribute("taxpayee", null);
			requestDispatcher = request
					.getRequestDispatcher("insert_tax_payer.jsp");
			requestDispatcher.forward(request, response);
		} else if (buttonLabel.equals("Delete Tax Payer")) {
			httpSession.setAttribute("functionType", "2");
			requestDispatcher = request.getRequestDispatcher("enter_utin.jsp");
			requestDispatcher.forward(request, response);
		} else {
			httpSession.setAttribute("functionType", "3");
			requestDispatcher = request.getRequestDispatcher("enter_utin.jsp");
			requestDispatcher.forward(request, response);
		}

	}

}
