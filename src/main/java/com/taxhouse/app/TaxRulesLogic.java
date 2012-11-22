package com.taxhouse.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.taxhouse.db.DBHandler;
import com.taxhouse.model.ArmedForcePersonnel;
import com.taxhouse.model.Employee;
import com.taxhouse.model.Organization;
import com.taxhouse.model.SeniorCitizen;
import com.taxhouse.model.Student;
import com.taxhouse.model.TaxPayer;

public class TaxRulesLogic {

	public static void main(String args[]) {

		try {

			System.out.println("WELCOME TO TAX HOUSE !!!");
			System.out.print("\nPlease enter the UTIN: ");
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String sUtin = bufferRead.readLine();

			int utin = 0;
			try {
				utin = Integer.parseInt(sUtin);
			} catch (NumberFormatException e) {
				System.out.println("Please provide a valid input.");
			}

			KnowledgeBase kbase = null;
			TaxPayer taxPayer = DBHandler.getInstance().getTaxPayer(utin);

			if (taxPayer == null) {
				System.out.println("Tax Payer not found.");
				return;
			}

			System.out.println("\nName: " + taxPayer.getFirstName() + " " + taxPayer.getLastName() + ",");
			System.out.println("\nGross Income: " + taxPayer.getIncome());

			// put applicable rule files in KnowledgeBase
			if (taxPayer instanceof Employee) {

				if (taxPayer instanceof SeniorCitizen) {
					kbase = readKnowledgeBase("SeniorCitizen.drl");

				} else if (taxPayer instanceof ArmedForcePersonnel) {
					kbase = readKnowledgeBase(/* add files here */);

				} else if (taxPayer instanceof Student) {
					kbase = readKnowledgeBase(/* add files here */);

				} else {
					kbase = readKnowledgeBase("employeePay.drl", "NonProfit.drl");
				}

			} else if (taxPayer instanceof Organization) {
				kbase = readKnowledgeBase(/* add files here */);
			}

			if (kbase != null) {
				StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
				ksession.insert(taxPayer);
				ksession.fireAllRules();

				System.out.println("Total Tax: " + taxPayer.getTax());

			} else
				System.out.println("Tax cannot be calculated with present information");

		} catch (Exception e) {
			System.err.println(e);
		}
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
