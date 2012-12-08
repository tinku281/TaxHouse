package com.taxhouse.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.taxhouse.db.HistoryProvider;
import com.taxhouse.model.TaxHistory;
import com.taxhouse.model.TaxPayer;

/**
 * Servlet implementation class GetHistory
 */
public class GetHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession httpSession = request.getSession();

		if (httpSession.isNew()) {
			httpSession.invalidate();
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
			requestDispatcher.forward(request, response);
			return;
		}

		TaxPayer taxPayer = (TaxPayer) httpSession.getAttribute("taxpay");
		RequestDispatcher requestDispatcher;

		if (taxPayer != null) {
			
			ArrayList<TaxHistory> histories = HistoryProvider.getTaxHistory(taxPayer.getUtin());
			httpSession.setAttribute("tax_history", histories);
			
			requestDispatcher = request.getRequestDispatcher("tax_history.jsp");
			requestDispatcher.forward(request, response);
		}
	}

}
