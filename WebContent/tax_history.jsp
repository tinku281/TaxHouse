<%@page import="com.taxhouse.db.DBHandler"%>
<%@page import="com.taxhouse.model.TaxHistory"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.taxhouse.model.TaxPayer"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tax History</title>
<script type="text/javascript">

</script>
</head>
<body>
<%@include file="header.jsp"%>
	<%@ include file = "subheader.jsp"%>
	<%
		ArrayList<TaxHistory> histories = (ArrayList<TaxHistory>)session.getAttribute("tax_history");
		if (session.getAttribute("role").toString().equals("admin"))
		{
	%>
			<%@include file="admin_panel.html"%>
	<%
		}
	%>	
		<div id="logout" class="width800"><input class="rightfloat button_blue display_block"  type = "button" value="Back" onClick="history.back()"/></div>
	<%	if(histories != null)
		{	
	%>
			<div class="subheader width600">
	<%		
			for(int index = 0; index < histories.size();index++)
			{	
				TaxHistory taxHistory = histories.get(index);
	%>
				<div class="header">
					<h2>Tax History <%=index+1 %></h2>
				</div>

				<div class="labels marginBottom50">
					<div class="display_block">
						<p class="leftfloat">Tax Year</p>
						<p class="leftfloat values"><%=taxHistory.getTaxYear()%></p>
					</div>
					<div class="display_block">
						<p class="leftfloat">Tax Due date</p>
						<p class="leftfloat values"><%=DBHandler.dateFormat.format(taxHistory.getTaxDueDate())%></p>
					</div>
					<div class="display_block">
						<p class="leftfloat">Tax Paid date</p>
						<p class="leftfloat values"><%=DBHandler.dateFormat.format(taxHistory.getTaxPaidDate())%></p>
					</div>
					<div class="display_block">
						<p class="leftfloat">Investments</p>
						<p class="leftfloat values"><%="$ "+taxHistory.getInvestments()%></p>
					</div>
					<div class="display_block">
						<p class="leftfloat">Tax Paid</p>
						<p class="leftfloat values"><%="$ "+taxHistory.getTaxPaid()%></p>
					</div>
					<div class="display_block">
						<p class="leftfloat">Penalty Paid</p>
						<p class="leftfloat values"><%="$ "+taxHistory.getPenaltyPaid()%></p>
					</div>
					<div class="display_block">
						<p class="leftfloat">Exemptions</p>
						<p class="leftfloat values"><%="$ "+taxHistory.getExemptions()%></p>
					</div>
				</div>	
	<%			
	
			}
		}		
	%>
	</div>

</body>
</html>