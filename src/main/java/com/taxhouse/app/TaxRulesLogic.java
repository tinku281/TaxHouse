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
import com.taxhouse.model.*;

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

			TaxPayer taxPayer = DBHandler.getInstance().getTaxPayer(utin);

			if (taxPayer == null) {
				System.out.println("Tax Payer not found.");
				return;
			}

			System.out.println("\nName: " + taxPayer.getFirstName() + " " + taxPayer.getLastName() + ",");
			System.out.println("\nGross Income: " + taxPayer.getIncome());

			triggerRules(taxPayer);

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static TaxRecord triggerRules(TaxPayer taxPayer) throws Exception {
		KnowledgeBase kbase = null;
		TaxRecord taxRecord = null;

		if (taxPayer instanceof Employee) {

			if (taxPayer instanceof SeniorCitizen) {
				kbase = readKnowledgeBase("Deductions.drl", "seniorcitizens.drl");

			} else if (taxPayer instanceof ArmedForcePersonnel) {
				kbase = readKnowledgeBase("Armedforces.drl");

			} else if (taxPayer instanceof Student) {
				kbase = readKnowledgeBase("students.drl");

			} else {
				kbase = readKnowledgeBase("Employee.drl");
			}

		} else if (taxPayer instanceof Organization) {
			kbase = readKnowledgeBase("organization.drl");
		}

		if (kbase != null) {

			taxRecord = new TaxRecord();

			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			// ksession.addEventListener(new DebugAgendaEventListener());
			// ksession.addEventListener(new DebugWorkingMemoryEventListener());
			ksession.insert(taxPayer);
			ksession.insert(taxRecord);
			ksession.fireAllRules();

			System.out.println("Total Tax: " + taxRecord.getTotalTax());

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
