<%@page import="com.taxhouse.model.Investment"%>
<%@page import="com.taxhouse.model.Employee"%>
<%@page import="com.taxhouse.model.TaxPayer"%>
<%@page import="com.taxhouse.model.Exemption"%>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Details</title>
<link rel="stylesheet" type="text/css" href="main.css" />
</head>
<body>
<%@include file="header.jsp" %>
<%
			TaxPayer taxPayer = (TaxPayer)request.getAttribute( "taxpayer" );
			%>
<div class="subheader width500 padding_bottom20">
		<div class="header">
			<h2>Employee Details</h2>
		</div>
		
		<div class="labels">
			<div class="display_block">
				<p class="leftfloat">First Name</p>
				<p class="leftfloat values"><%=taxPayer.getFirstName() %></p>
			</div>	
			<div class="display_block">
				<p class="leftfloat">Last Name</p>
				<p class="leftfloat values"><%= taxPayer.getLastName() %></p>
			</div>	
			<div class="display_block">	
				<p class="leftfloat">City</p>
				<p class="leftfloat values"><%= taxPayer.getCity() %></p>
			</div>
			<div class="display_block">
				<p class="leftfloat">State</p>
				<p class="leftfloat values"><%= taxPayer.getState() %></p>
			</div>
			<div class="display_block">
				<p class="leftfloat">Nationality</p>
				<p class="leftfloat values"><%= taxPayer.getNationality() %></p>
			</div>
			
			<%
			if(taxPayer instanceof Employee)
			{
				Employee employee= (Employee)taxPayer;
			%>
			<div class="display_block">
				<p class="leftfloat">Date of Birth</p>
				<p class="leftfloat values"><%= employee.getDateOfBirth().toString(  ) %></p>
			</div>	
			<div class="display_block">
				<p class="leftfloat">Gender</p>
				<p class="leftfloat values"><%= employee.getGender().name() %></p>
			</div>
			<div class="display_block">
				<p class="leftfloat">No. of dependents</p>
				<p class="leftfloat values"><%= employee.getNoOfDependants() %></p>
			</div>
			<div class="display_block">
				<p class="leftfloat">Marital Status</p>
				<p class="leftfloat values"><%= employee.getMaritalStatus().name() %></p>
			</div>
			<div class="display_block">
				<p class="leftfloat">Residency Status</p>
				<p class="leftfloat values"><%= employee.getResidencyStatus().name() %></p>
			</div>
			<div class="display_block">
				<p class="leftfloat">Organization Name</p>
				<p class="leftfloat values"><%= employee.getOrganization().getFirstName() %></p>
			</div>
			<div class="display_block">
				<p class="leftfloat">Designation</p>
				<p class="leftfloat values"><%= employee.getDesignation( ) %></p>
			</div>
			<div class="display_block">
				<p class="leftfloat">Income</p>
				<p class="leftfloat values"><%= employee.getIncome(  ) %></p>
			</div>
			<div class="display_block">
				<p class="leftfloat">Exemptions</p>
				<% 	
					List<Exemption> exemptions = employee.getExemptions();
					if(exemptions != null)
					{	
						for(int index=0; index < exemptions.size(); index++ )
						{
				%>
							<p class="leftfloat values"><%= exemptions.get( index ).getName() %></p><br/>
				<%
						}
					}
				%>
			</div>
			<div class="display_block">
				<p class="leftfloat">InvestMents</p>
				<% 	
					List<Investment> investments = employee.getInvestments();
					if(investments != null)
					{
						for(int index=0; index < investments.size(); index++ )
						{
				%>
							<p class="leftfloat values"><%= investments.get( index ).getName() %></p><br/>
				<%
						}
					}
				%>
			</div>
			<%
			} 
			%>
			
		</div>
	<input class=" center_div button_blue display_block"  type = "button" value="Calculate Tax"/>
</div>
</body>
</html>