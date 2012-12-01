package com.taxhouse.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.taxhouse.model.ArmedForcePersonnel;
import com.taxhouse.model.Employee;
import com.taxhouse.model.Organization;
import com.taxhouse.model.SeniorCitizen;
import com.taxhouse.model.Student;
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
				TaxRecord taxRecord = triggerRules(taxPayer);
				request.setAttribute("taxrecord", taxRecord);

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

			RequestDispatcher requestDispatcher = request.getRequestDispatcher("taxdetails.jsp");
			requestDispatcher.forward(request, response);
		}
	}

	public static TaxRecord triggerRules(TaxPayer taxPayer) throws Exception {
		KnowledgeBase kbase = null;
		TaxRecord taxRecord = null;

		if (taxPayer instanceof Employee) {

			if (taxPayer instanceof SeniorCitizen) {
				kbase = readKnowledgeBase("seniorcitizens.drl");

			} else if (taxPayer instanceof ArmedForcePersonnel) {
				kbase = readKnowledgeBase("Armedforces.drl");

			} else if (taxPayer instanceof Student) {
				kbase = readKnowledgeBase("students.drl");

			} else {
				kbase = readKnowledgeBase("employeePay.drl", "NonProfit.drl");
			}

		} else if (taxPayer instanceof Organization) {
			kbase = readKnowledgeBase("organization.drl");
		}

		if (kbase != null) {

			taxRecord = new TaxRecord();

			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
//			ksession.addEventListener(new DebugAgendaEventListener());
//			ksession.addEventListener(new DebugWorkingMemoryEventListener());
			ksession.insert(taxPayer);
			ksession.insert(taxRecord);
			ksession.fireAllRules();

			System.out.println("Total Tax: " + taxPayer.getTax());

		} else
			System.out.println("Tax cannot be calculated with present information");

		return taxRecord;
	}

	private static KnowledgeBase readKnowledgeBase(String... resources) throws Exception {

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		for (String resource : resources) {
			kbuilder.add(ResourceFactory.newClassPathResource(resource), ResourceType.DRL);
		}

		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}

		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

		return kbase;
	}

}
