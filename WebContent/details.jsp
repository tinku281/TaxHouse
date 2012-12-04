<%@page import="java.util.HashMap"%>
<%@page import="com.taxhouse.model.Stock"%>
<%@page import="com.taxhouse.db.DBHandler"%>
<%@page import="com.taxhouse.app.Utils"%>
<%@page import="com.taxhouse.model.TaxPayer"%>
<%@page import="com.taxhouse.model.Employee"%>
<%@page import="com.taxhouse.model.Organization"%>
<%@page import="com.taxhouse.model.Student"%>
<%@page import="com.taxhouse.model.ArmedForcePersonnel"%>
<%@page import="com.taxhouse.model.ArmedForcePersonnel.SpecialTask"%>
<%@page import="com.taxhouse.model.SeniorCitizen"%>
<%@page import="com.taxhouse.model.SeniorCitizen.Income"%>
<%@page import="com.taxhouse.model.Investment"%>
<%@page import="com.taxhouse.model.Exemption"%>
<%@page import="com.taxhouse.model.Stock"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Details</title>
<script type="text/javascript">

function calculateTax() 
{
    
	document.location.href='${pageContext.request.contextPath}/calculateTax.do';

}

</script>
<link rel="stylesheet" type="text/css" href="main.css" />
</head>
<body>
	<%@include file="header.jsp"%>

	<%
		TaxPayer taxPayer = (TaxPayer) request.getAttribute("taxpayer");
		if (session.getAttribute("role").toString().equals("admin"))
		{
	%>
			<%@include file="admin_panel.html"%>
	<%
		}
	%>
	<div class="subheader width500 padding_bottom20">
		<div class="header">
			<h2>Employee Details</h2>
		</div>

		<div class="labels">
			<div class="display_block">
				<p class="leftfloat">First Name</p>
				<p class="leftfloat values"><%=taxPayer.getFirstName()%></p>
			</div>
			<div class="display_block">
				<p class="leftfloat">Last Name</p>
				<p class="leftfloat values"><%=taxPayer.getLastName()%></p>
			</div>
			<div class="display_block">
				<p class="leftfloat">City</p>
				<p class="leftfloat values"><%=taxPayer.getCity()%></p>
			</div>
			<div class="display_block">
				<p class="leftfloat">State</p>
				<p class="leftfloat values"><%=taxPayer.getState()%></p>
			</div>
			<div class="display_block">
				<p class="leftfloat">Nationality</p>
				<p class="leftfloat values"><%=taxPayer.getNationality()%></p>
			</div>

			<%
			if (taxPayer instanceof Employee) 
			{
				Employee employee = (Employee) taxPayer;
		%>
				<div class="display_block">
					<p class="leftfloat">Date of Birth</p>
					<p class="leftfloat values"><%=DBHandler.dateFormat.format(employee.getDateOfBirth())%></p>
				</div>
				<div class="display_block">
					<p class="leftfloat">Gender</p>
					<p class="leftfloat values"><%=employee.getGender().name()%></p>
				</div>
				<div class="display_block">
					<p class="leftfloat">No. of dependents</p>
					<p class="leftfloat values"><%=employee.getNoOfDependants()%></p>
				</div>
				<div class="display_block">
					<p class="leftfloat">Marital Status</p>
					<p class="leftfloat values"><%=employee.getMaritalStatus().name()%></p>
				</div>
				<div class="display_block">
					<p class="leftfloat">Residency Status</p>
					<p class="leftfloat values"><%=employee.getResidencyStatus().name()%></p>
				</div>
			<%
				if (!(employee instanceof SeniorCitizen)) 
				{
			%>
					<div class="display_block">
						<p class="leftfloat">Organization Name</p>
						<p class="leftfloat values"><%=employee.getOrganization().getFirstName()%></p>
					</div>
					<div class="display_block">
						<p class="leftfloat">Designation</p>
						<p class="leftfloat values"><%=employee.getDesignation()%></p>
					</div>
					<div class="display_block">
						<p class="leftfloat">Income</p>
						<p class="leftfloat values"><%="$ " +employee.getIncome()%></p>
					</div>
			<%
				}
				if(!(employee instanceof Student))
				{
			%>	
					<div class="display_block">
						<p class="leftfloat">Ex Military</p>
						<p class="leftfloat values"><%=employee.getExMilatary( )%></p>
					</div>
			<%
				}
			
			%>
			
			<br />
			<%
				if (employee instanceof Student) 
				{
					Student student = (Student) employee;
			%>
					<div class="display_block">
						<p class="leftfloat">Fee Waiver Amount</p>
						<p class="leftfloat values"><%="$ " +student.getFeeWaiverAmt()%></p>
					</div>

			<%
				} 
				else if (employee instanceof SeniorCitizen) 
				{
					SeniorCitizen seniorCitizen = (SeniorCitizen) employee;
					List<Income> incomeList = seniorCitizen.getIncomes();

					if (incomeList != null) 
					{
						for (int index = 0; index < incomeList.size(); index++) 
						{
		%>
							<div class="display_block">
								<p class="leftfloat">
									Income Source
									<%=index + 1%></p>
								<p class="leftfloat values"><%=incomeList.get(index).getSource()%></p>
							</div>
				
							<div class="display_block">
								<p class="leftfloat">
									Income Amount
									<%=index + 1%></p>
								<p class="leftfloat values"><%="$ " +incomeList.get(index).getAmount()%></p>
							</div>
		<%
						}
					}
						//put else code here if want to display anything if no income is there
				} 
				else if (employee instanceof ArmedForcePersonnel) 
				{
					ArmedForcePersonnel armedForcePersonnel = (ArmedForcePersonnel) employee;
					List<SpecialTask> spTaskList = armedForcePersonnel.getSpecialTasks();
					if (spTaskList != null)
					{
						for (int index = 0; index < spTaskList.size(); index++) 
						{
		%>
							<div class="display_block">
								<p class="leftfloat">
									Special Task
									<%=index + 1%></p>
								<p class="leftfloat values"><%=spTaskList.get(index).getName()%></p>
							</div>
				
							<div class="display_block">
								<p class="leftfloat">Start Date</p>
								<p class="leftfloat values"><%=DBHandler.dateFormat.format(spTaskList.get(index).getStartDate())%></p>
							</div>
				
							<div class="display_block">
								<p class="leftfloat">End Date</p>
								<p class="leftfloat values"><%=DBHandler.dateFormat.format(spTaskList.get(index).getEndDate())%></p>
							</div>
							
							<%	String combatZone = spTaskList.get( index ).getCombatZone(  );
								if(!(combatZone == null) && !combatZone.trim(  ).equals( "" ))
								{	
							%>
								<div class="display_block">
									<p class="leftfloat">Combat Zone</p>
									<p class="leftfloat values"><%=spTaskList.get( index ).getCombatZone(  )%></p>
								</div>
							<%
								}
						}
					}

				}
			
				List<Stock> stockList = employee.getStocks(  );
				if (stockList != null) 
				{
					for (int index = 0; index < stockList.size(); index++) 
					{
				%>	
						<div class="display_block">
							<p class="leftfloat">Stock <%=index + 1 %> Symbol</p>
							<p class="leftfloat values"><%=stockList.get(index).getSymbol(  )%></p>
							<br />
						</div>
						
						<div class="display_block">
							<p class="leftfloat">Stock <%=index+1 %> Purchase Date </p>
							<p class="leftfloat values"><%=stockList.get(index).getPurchaseDate(  )%></p>
							<br />
						</div>
						
						<div class="display_block">
							<p class="leftfloat">Stock  <%=index+1 %> Quantity</p>
							<p class="leftfloat values"><%=stockList.get(index).getQuantity(  )%></p>
							<br />
						</div>
				<%	
					}
				}

			} 
			else if (taxPayer instanceof Organization) 
			{
				Organization organization = (Organization)taxPayer;
			%>	
				
				<div class="display_block">
					<p class="leftfloat">Established Date</p>
					<p class="leftfloat values"><%=organization.getEstblDate().toString(  )%></p>
				</div>
				<div class="display_block">
					<p class="leftfloat">Turnover</p>
					<p class="leftfloat values"><%= "$ " + Utils.formatDouble( organization.getTurnover())%></p>
				</div>
				<div class="display_block">
					<p class="leftfloat">Profit</p>
					<p class="leftfloat values"><%= "$ "+ Utils.formatDouble( organization.getIncome())%></p>
				</div>
				<div class="display_block">
					<p class="leftfloat">Organization Scale</p>
					<p class="leftfloat values"><%=request.getAttribute( "scale" ).toString(  )%></p>
				</div>
				<div class="display_block">
					<p class="leftfloat">Organization Category</p>
					<p class="leftfloat values"><%=request.getAttribute( "category" ).toString(  )%></p>
				</div>
				<div class="display_block">
					<p class="leftfloat">Organization Type</p>
					<p class="leftfloat values"><%=request.getAttribute( "type" ).toString(  )%></p>
				</div>
			<%
				HashMap<Integer,Double> sharesList = organization.getShares(  );
				for(Integer utin: sharesList.keySet(  ))
				{
			%>	
					<div class="display_block">
						<p class="leftfloat">ShareHolder's UTIN</p>
						<p class="leftfloat values"><%=utin%></p>
					</div>
					<div class="display_block">
						<p class="leftfloat">Share Percentage</p>
						<p class="leftfloat values"><%=sharesList.get( utin )+" %"%></p>
					</div>
			<%	
				}
			
				
			}
			
			List<Exemption> exemptions = taxPayer.getExemptions();
			if (exemptions != null) 
			{
				for (int index = 0; index < exemptions.size(); index++) 
				{
			%>
					<div class="display_block">
						<p class="leftfloat">Exemption <%=index + 1 %> Name</p>
						<p class="leftfloat values"><%=exemptions.get(index).getName()%></p>
						<br />
					</div>
					
					<div class="display_block">
						<p class="leftfloat">Exemption <%=index+1 %> Amount</p>
						<p class="leftfloat values"><%=exemptions.get(index).getAmount(  )%></p>
						<br />
					</div>
			<%
				}
			}
			%>
			
			<br />
			<%
				List<Investment> investments = taxPayer.getInvestments();
				if (investments != null) 
				{
					for (int index = 0; index < investments.size(); index++) 
					{
			%>
						<div class="display_block">
							<p class="leftfloat">Investment <%=index+1 %> Name</p>
							
							<p class="leftfloat values"><%=investments.get(index).getName()%></p>
							<br />
						</div>	
						<div class="display_block">
							<p class="leftfloat">Investment <%=index+1 %> Amount</p>
							
							<p class="leftfloat values"><%=investments.get(index).getAmount(  )%></p>
							<br />
						</div>	
			<%
					}
				}
			%>

		</div>
		<form method="POST" action="calculateTax.do"/>
			<input class=" center_div button_blue display_block" type="submit" value="Calculate Tax"  />
		</form>	
	</div>
</body>
</html>