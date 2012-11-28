package com.taxhouse.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		TaxPayer taxPayer = (TaxPayer)request.getAttribute( "employee");
		if(taxPayer == null)
			System.out.println("Taxpayer: NULL");
		else
			System.out.println("First Name: "+taxPayer.getFirstName());
		
		int exmpCount  = Integer.parseInt( request.getParameter( "count" ).toString() );
//		int invCount  = Integer.parseInt( request.getParameter( "inv_count" ).toString() );
		
		int empType = Integer.parseInt( request.getParameter( "emp_type" ).toString() );
		int empMaritalStatus = Integer.parseInt( request.getParameter( "emp_mar_status" ).toString() );
		int spouseUtin=0;
		if(empMaritalStatus == 1)
		{
			Integer.parseInt( request.getParameter( "spouse_utin" ).toString() );
		}
		
		String[] exmpNames = null,exmpAmount = null;
		int[] exmpType = null;
		
		if(exmpCount > 0)
		{
			exmpNames = new String[exmpCount];
			exmpType = new int[exmpCount];
			exmpAmount = new String[exmpCount];
			
			for(int index = 0; index < exmpCount; index++)
			{
				int i = index+1;
				String str = request.getParameter( "exemptionname"+i);
				System.out.println("\n String"+i+": "+str);
				exmpNames[index] = request.getParameter( "exemptionname"+i ).toString();
				exmpType[index] = Integer.parseInt(request.getParameter( "exemptiontype"+(index + 1) ).toString());
				exmpAmount[index] = request.getParameter( "exemptionamount"+(index + 1) ).toString();
			}
		}
		
		System.out.println("\nEmployee Type: "+empType);
		System.out.println("\nEmployee Marital Status: "+empMaritalStatus);
		System.out.println("\nEmployee Spouse UTIN: "+ spouseUtin);
		System.out.println("\nEmployee Exemption Count: "+exmpCount);
		
		if(exmpCount > 0)
		{
			for(int index= 0;index < exmpCount ; index++)
			{	
				System.out.println("\n Exemption Name: "+exmpNames[index]+", Exemption Type: "+exmpType[index]+", Exemption Amount/Per: "+exmpAmount[index]);
			}
		}
		
	
	}

}
