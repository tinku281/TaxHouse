<%@page import="com.taxhouse.model.TaxPayer"%>
<%@page import="com.taxhouse.model.TaxRecord"%>
<%@page import="com.taxhouse.app.Utils"%>
<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tax Details</title>
<link rel="stylesheet" type="text/css" href="main.css" />
</head>
<body>
	<%@include file="header.jsp"%>

	<%
		TaxPayer taxPayer = (TaxPayer) request.getAttribute("taxpayer");
		TaxRecord taxRecord = (TaxRecord) request.getAttribute("taxrecord");
		String error="";
	%>
	<div class="subheader width800 padding_bottom20">
		<div class="header">
			<h2>Tax Details</h2>
		</div>

		<div class="labels fullwidth">
			<div class="display_block fullwidth">
				<p class="leftfloat">Gross Income</p>
				<p class="leftfloat values2"><%="$ " +Utils.formatDouble(taxPayer.getIncome())%></p>
			</div>
			
			<%
				if (taxRecord != null && taxRecord.getDeductionSize() > 0) 
				{
					for (int index = 0; index < taxRecord.getDeductionSize(); index++) 
					{
			%>
			<div class="display_block fullwidth">
				<p class="leftfloat"><%=taxRecord.getDeduction(index).getName()%>
				
				<p class="leftfloat values2"><%="$ -" +Utils.formatDouble(taxRecord.getDeduction(index).getValue())%></p>
			</div>
				<%
					}
				%>
			<div class="display_block fullwidth">
				<p class="leftfloat">Taxable Income</p>
				<p class="leftfloat values2"><%="$ " +Utils.formatDouble(taxPayer.getTaxableIncome())%></p>
			</div>			
			<% 	
				}
			%>
			
			<%
				if (taxRecord != null) 
				{
					for (int index = 0; index < taxRecord.getEntrySize(); index++) 
					{
			%>
			<div class="display_block fullwidth">
				<p class="leftfloat"><%=taxRecord.getEntry(index).getName()%>
				<%if (taxRecord.getEntry(index).getRate() != 0){ %>
				 <%=" @ "+Utils.formatDouble(taxRecord.getEntry(index).getRate()) +"%"%>
				 <%} %>
				 </p>
				<p class="leftfloat values2"><%="$ " +Utils.formatDouble(taxRecord.getEntry(index).getValue())%></p>
			</div>
			<%
				}
			%>
			<div class="display_block fullwidth">
				<p class="leftfloat">Payable Tax Amount</p>
				<p class="leftfloat values2"><%="$ " +Utils.formatDouble(taxRecord.getTotalTax())%></p>
			</div>
			<%
				} else {
					error = "Error while calculating tax";
				}
			%>
			
		</div>
		<br />
		<div class="margin10" style="color:#FF0000"><%=error %></div>
		
	</div>
</body>
</html>