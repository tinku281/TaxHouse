package com.taxhouse.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.taxhouse.app.TaxRulesLogic;
import com.taxhouse.model.TaxPayer;
import com.taxhouse.model.TaxRecord;

/**
 * Servlet implementation class TaxCalculator
 */
public class TaxCalculator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaxCalculator() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		HttpSession httpSession = request.getSession();

		if (httpSession.isNew()) {
			httpSession.invalidate();
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
			requestDispatcher.forward(request, response);
			return;
		}

		TaxPayer taxPayer = (TaxPayer) httpSession.getAttribute("taxpay");

		if (taxPayer != null) {
			request.setAttribute("taxpayer", taxPayer);

			try {
				TaxRecord taxRecord = TaxRulesLogic.triggerRules(taxPayer);
				request.setAttribute("taxrecord", taxRecord);

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

			RequestDispatcher requestDispatcher = request.getRequestDispatcher("taxdetails.jsp");
			requestDispatcher.forward(request, response);
		}
	}

	

}
