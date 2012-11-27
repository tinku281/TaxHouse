package com.taxhouse.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taxhouse.model.Exemption;
import com.taxhouse.model.TaxPayer;

/**
 * Servlet implementation class EmployeeProcessing
 */
public class EmployeeProcessing extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	public EmployeeProcessing()
	{

		super();
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		System.out.println("Exmp Count: "+request.getParameter( "count" ).toString());
		String exemptionAmount =  request.getParameter( "exemptionamount" ).toString();
		String exemptionPer =  request.getParameter( "exemptionper" ).toString();
		TaxPayer taxPayer = (TaxPayer)request.getAttribute( "taxpayer");
		
		if(!exemptionAmount.equals( "" ) && !exemptionPer.equals( "" ))
		{	
//			ArrayList<Exemption> exemptionsList = new ArrayList<Exemption>();
//			Exemption exemption = new Exemption( id, name, amount, percentage )
			
			Double exemptionamount = Double.parseDouble( exemptionAmount);
			int exemptionper = Integer.parseInt( request.getParameter( "exemptionper" ).toString() );
			int count = Integer.parseInt( request.getParameter( "count" ).toString() );
			
		}
		
		
		
	}

}
