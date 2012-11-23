package com.taxhouse.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taxhouse.db.DBHandler;
import com.taxhouse.model.TaxPayer;

public class LoginValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginValidation() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		String sUtin = request.getParameter("utin");
		String passwd = request.getParameter("passwd");
		String role = request.getParameter("role");

		PrintWriter output = response.getWriter();
		response.setContentType("text/html");

		if (role.equals("taxpayer")) {

			if (DBHandler.getInstance().validateTaxPayer(sUtin, passwd)) {

				TaxPayer taxPayer = DBHandler.getInstance().getTaxPayer(Integer.parseInt(sUtin));

				if (taxPayer != null)
					output.println("<br>" + taxPayer.getFirstName() + "<br>" + taxPayer.getLastName() + "<br>"
							+ taxPayer.getIncome());

			} else {
				String errorMsg = "Invalid UTIN or password";
				response.sendRedirect("login.jsp?error=" + errorMsg);
			}

		} else {
			if (DBHandler.getInstance().validateAdmin(sUtin, passwd)) {
				output.println("Validated");

			} else {
				String errorMsg = "Invalid AdminstratorID or password";
				response.sendRedirect("login.jsp?error=" + errorMsg);
			}
		}
	}

}
