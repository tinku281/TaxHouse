package com.taxhouse.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.taxhouse.app.Utils;
import com.taxhouse.db.DBHandler;
import com.taxhouse.model.Exemption;
import com.taxhouse.model.Investment;
import com.taxhouse.model.Organization;
import com.taxhouse.model.TaxPayer.Nationality;

public class OrganizationProcessing extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	public OrganizationProcessing()
	{

		super();
	}

	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{

		HttpSession httpSession = request.getSession();
		RequestDispatcher requestDispatcher;

		// Terminate session and redirect to login.jsp if the session is new
		if ( httpSession.isNew() )
		{
			httpSession.invalidate();
			requestDispatcher = request.getRequestDispatcher( "login.jsp" );
			requestDispatcher.forward( request, response );
			return;
		}

		int exmpCount = Integer.parseInt( request.getParameter( "count" ).toString() );
		int invCount = Integer.parseInt( request.getParameter( "inv_count" ).toString() );
		int sharesCount = Integer.parseInt( request.getParameter( "share_count" ).toString() );
		Double orgTurnover = Double.parseDouble( request.getParameter( "org_turnover" ) );
		Double orgProfit = Double.parseDouble( request.getParameter( "org_profit" ) );
		int orgScaleId = Integer.parseInt( request.getParameter( "org_scale" ) );
		int orgCategoryId = Integer.parseInt( request.getParameter( "org_category" ) );
		int orgTypeId = Integer.parseInt( request.getParameter( "org_type" ) );

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
		Date estbDate = null;
		try
		{
			estbDate = simpleDateFormat.parse( request.getParameter( "org_estb_date" ) );
		}
		catch ( Exception e )
		{

		}

		// Setting general taxpayer details
		Organization organization = new Organization();
		organization.setFirstName( httpSession.getAttribute( "firstname" ).toString() );
		organization.setLastName( httpSession.getAttribute( "lastname" ).toString() );
		organization.setPassword( httpSession.getAttribute( "password" ).toString() );
		organization.setCity( httpSession.getAttribute( "city" ).toString() );
		organization.setState( httpSession.getAttribute( "state" ).toString() );

		int nationality = Integer.parseInt( httpSession.getAttribute( "nationality" ).toString() );
		if ( nationality == 1 )
			organization.setNationality( Nationality.USA );
		else
			organization.setNationality( Nationality.NON_USA );

		// Setting organization specific details
		organization.setEstblDate( estbDate );
		organization.setTurnover( orgTurnover );
		organization.setIncome( orgProfit );
		organization.setScaleId( orgScaleId );
		organization.setCategoryId( orgCategoryId );
		organization.setTypeId( orgTypeId );
		organization.setCombinationId( DBHandler.getInstance().getCombinationId( orgScaleId, orgTypeId, orgCategoryId ) );

		// set ShareHolders
		if ( sharesCount > 0 )
		{
			HashMap<Integer, Double> sharesList = new HashMap<Integer, Double>();

			for ( int index = 0; index < sharesCount; index++ )
			{
				int i = index + 1;
				int holderUTIN = Integer.parseInt( request.getParameter( "holderutin" + i ) );
				Double sharePercentage = Double.parseDouble( request.getParameter( "shareper" + i ) );

				sharesList.put( holderUTIN, sharePercentage );
				System.out.println( "Adding ShareHolder UTIN: " + holderUTIN + ", " + "Share Percentage: " + sharePercentage );
			}

			organization.setShares( sharesList );
		}
		

		if ( exmpCount > 0 )
		{
			ArrayList<Exemption> exemptionsList = new ArrayList<Exemption>();
			
			for ( int index = 0; index < exmpCount; index++ )
			{
				int i = index + 1;
				String exempName = request.getParameter( "exemptionname" + i );
				int exempType = Integer.parseInt( request.getParameter( "exemptiontype" + i ) );
				Double exempAmount = Double.parseDouble( request.getParameter( "exemptionamount" + i ) );
				int exempId = DBHandler.getInstance().getExemptionId( exempName.trim() );

				Exemption exemption;
				if ( exempType == 1 )
				{
					exemption = new Exemption( exempId, exempName, exempAmount, 0 );
				}
				else
				{
					exemption = new Exemption( exempId, exempName, 0, exempAmount );
				}

				exemptionsList.add( exemption );
			}
			
			organization.setExemptions( exemptionsList );
		}

		if ( invCount > 0 )
		{
			ArrayList<Investment> investmentsList = new ArrayList<Investment>();
			
			for ( int index = 0; index < invCount; index++ )
			{
				int i = index + 1;
				String invName = request.getParameter( "investmentname" + i );
				Double invAmount = Double.parseDouble( request.getParameter( "investmentamount" + i ) );
				Double invPer = Double.parseDouble( request.getParameter( "investmentper" + i ) );
				int invId = DBHandler.getInstance().getInvestmentId(invName.trim());

				Investment investment = new Investment( invId, invName, invAmount, invPer );
				investmentsList.add( investment );

			}
			
			organization.setInvestments( investmentsList );
		}

		System.out.println( "\nOrganization FirstName: " + organization.getFirstName() );
		System.out.println( "\nOrganization LastName: " + organization.getLastName() );
		System.out.println( "\nOrganization City: " + organization.getCity() );
		System.out.println( "\nOrganization State: " + organization.getState() );
		System.out.println( "\nOrganization Nationality: " + organization.getNationality().name() );
		System.out.println( "\nOrganization EsblDate: " + organization.getEstblDate() );
		System.out.println( "\nOrganization Turnover: " + Utils.formatDouble( organization.getTurnover() ) );
		System.out.println( "\nOrganization Profit: " + Utils.formatDouble( organization.getIncome() ) );
		System.out.println( "\nOrganization ScaleId: " + organization.getScaleId() );
		System.out.println( "\nOrganization CategoryId: " + organization.getCategoryId() );
		System.out.println( "\nOrganization TypeId: " + organization.getTypeId() );

		if ( DBHandler.getInstance().insertTaxPayer( organization ) )
		{
			System.out.println( "\nOrganization inserted" );
		}

	}

}
