<%@page import="com.taxhouse.model.Investment"%>
<%@page import="com.taxhouse.model.SeniorCitizen.Income"%>
<%@page import="com.taxhouse.model.Exemption"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.taxhouse.model.ArmedForcePersonnel"%>
<%@page import="com.taxhouse.model.SeniorCitizen"%>
<%@page import="com.taxhouse.model.Student"%>
<%@page import="com.taxhouse.model.Employee"%>
<%@page import="com.taxhouse.model.TaxPayer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
  <%
		String empType,maritalStatus;
  		String spouseUTIN ="Enter Spouse UTIN";
		Employee employee = (Employee)session.getAttribute( "taxpayee" );
		
		if(employee instanceof Student)
			empType = "Student";
		else if (employee instanceof SeniorCitizen)
			empType = "Senior Citizen";
		else if(employee instanceof ArmedForcePersonnel)
			empType = "Army";
		else
			empType="None";
		
		maritalStatus = employee.getMaritalStatus( ).name(  );
		
		if(maritalStatus.equals(Employee.MaritalStatus.MARRIED.name(  ) ))
			spouseUTIN = String.valueOf( employee.getSpouseUtin(  ));
		
		ArrayList<String> maritalStatusList = new ArrayList<String>();
		maritalStatusList.add(Employee.MaritalStatus.SINGLE.name(  ));
		maritalStatusList.add(Employee.MaritalStatus.MARRIED.name(  ));
		maritalStatusList.add(Employee.MaritalStatus.DIVORCED.name(  ));
		maritalStatusList.add(Employee.MaritalStatus.WIDOW.name(  ));
		
		List<Exemption> exemptionList = employee.getExemptions(  );
		List<Investment> investmentList = employee.getInvestments(  );
		
		int exmpsize =0,invSize = 0;
		if(exemptionList!= null)
			exmpsize = exemptionList.size(  );
		if(investmentList != null)
			invSize = investmentList.size(  );
		
		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Employee</title>
<script>
	var count = <%=exmpsize%>;
	var invCount = <%=invSize%>;
	
	function isNumeric(val)
	{
	        var validChars = '0123456789.';
	

	        for(var i = 0; i < val.length; i++) 
	        {
	            if(validChars.indexOf(val.charAt(i)) == -1)
	                return false;
	        }

	        return true;
	}
	function addExmpDetails()
	{
		var exempAmount = document.insertEmployee.exemptionamt.value;
		if(exempAmount == "" || !isNumeric(exempAmount))
		{
			alert("Please enter Exemption Amount as digits.");
			return false;
		}	
		document.insertEmployee.count.value = ++count;
		
		
		var labelExmpAmount =document.createElement("label");
		labelExmpAmount.setAttribute("class","label1");
		labelExmpAmount.innerHTML="Exemption Name";
		
		var labelExmpNameValue = document.createElement("input");
		labelExmpNameValue.type="text";
		labelExmpNameValue.name="exemptionname"+count;
		labelExmpNameValue.setAttribute("class", "textbox2 rightfloat");
		labelExmpNameValue.setAttribute("value", document.insertEmployee.emp_exemp_name.value);
		labelExmpNameValue.readOnly = "readonly";

		
		var divExmpAmount = document.createElement("div");
		divExmpAmount.setAttribute("class", "formrow");
		divExmpAmount.appendChild(labelExmpAmount);
		divExmpAmount.appendChild(labelExmpNameValue);
		
		var labelExmpPer =document.createElement("label");
		labelExmpPer.setAttribute("class","label1");
		
		
		var labelExmpPerValue = document.createElement("input");
		labelExmpPerValue.type="text";
		labelExmpPerValue.name="exemptionamount"+count;
		labelExmpPerValue.setAttribute("class", "textbox2 rightfloat");
		labelExmpPerValue.setAttribute("value", exempAmount);
		labelExmpPerValue.readOnly = "readonly";

		
		var divExmpPer = document.createElement("div");
		divExmpPer.setAttribute("class", "formrow");
		divExmpPer.appendChild(labelExmpPer);
		divExmpPer.appendChild(labelExmpPerValue);
		
		var divExmpType = document.createElement("input");
		divExmpType.type="hidden";
		divExmpType.name="exemptiontype"+count;
		divExmpType.value=document.insertEmployee.exmp_type.value;
		
		document.getElementById('exemp_details').appendChild(divExmpAmount).appendChild(divExmpPer).appendChild(divExmpType);
		if(document.insertEmployee.exmp_type.value == "1")
			labelExmpPer.innerHTML="Exemption Amount";
		else
			labelExmpPer.innerHTML="Exemption Percentage";
		
		return true;
		
	}
	
	function addInvestmentDetails()
	{
		var invAmount,invPer;
		invAmount = document.insertEmployee.investmentamount.value;
		invPer = document.insertEmployee.investmentper.value;
		
		if(invAmount == "" || invPer == "" )
		{
			alert("Please fill in the investment details");	
			return false;
		}
		if(!isNumeric(invAmount))
		{
			alert("Investement amount should be in digits");	
			return false;
		}
		if(!isNumeric(invPer))
		{
			alert("Applicable percentage should be in digits");	
			return false;
		}
		
		document.insertEmployee.inv_count.value = 	++invCount;
		
		/*--------------INVESTMENT NAME--------*/
		
		var labelInvName =document.createElement("label");
		labelInvName.setAttribute("class","label1");
		labelInvName.innerHTML="Investment Name";
		
		var textBoxInvName = document.createElement("input");
		textBoxInvName.setAttribute("type", "text");
		textBoxInvName.setAttribute("class", "textbox2 rightfloat");
		textBoxInvName.setAttribute("name", "investmentname"+invCount);
		textBoxInvName.setAttribute("value", document.insertEmployee.emp_inv_name.value);
		textBoxInvName.readOnly = "readonly";

		var divInvName = document.createElement("div");
		divInvName.setAttribute("class", "formrow");
		divInvName.appendChild(labelInvName);
		divInvName.appendChild(textBoxInvName);
		
		/*--------------INVESTMENT AMOUNT--------*/
		
		var labelInvAmount =document.createElement("label");
		labelInvAmount.setAttribute("class","label1");
		labelInvAmount.innerHTML="Investment Amount";
		
		var textBoxInvAmount = document.createElement("input");
		textBoxInvAmount.setAttribute("type", "text");
		textBoxInvAmount.setAttribute("class", "textbox2 rightfloat");
		textBoxInvAmount.setAttribute("name", "investmentamount"+invCount);
		textBoxInvAmount.setAttribute("value", invAmount);
		textBoxInvAmount.readOnly = "readonly";

		var divInvAmount = document.createElement("div");
		divInvAmount.setAttribute("class", "formrow");
		divInvAmount.appendChild(labelInvAmount);
		divInvAmount.appendChild(textBoxInvAmount);
		
		/*--------------APPLICABLE PERCENTAGE--------*/
		
		var labelInvPer =document.createElement("label");
		labelInvPer.setAttribute("class","label1");
		labelInvPer.innerHTML="Applicable Percentage";
		
		var textBoxInvPer = document.createElement("input");
		textBoxInvPer.setAttribute("type", "text");
		textBoxInvPer.setAttribute("class", "textbox2 rightfloat");
		textBoxInvPer.setAttribute("name", "investmentper"+invCount);
		textBoxInvPer.setAttribute("value", invPer);
		textBoxInvPer.readOnly = "readonly";
		
		var divInvPer = document.createElement("div");
		divInvPer.setAttribute("class", "formrow");
		divInvPer.appendChild(labelInvPer);
		divInvPer.appendChild(textBoxInvPer);
		
		document.getElementById('inv_details').appendChild(divInvName).appendChild(divInvAmount).appendChild(divInvPer);
		return true;
	}
	
	function clearText()
	{
		if (document.insertEmployee.spouse_utin.value == "Enter Spouse UTIN") 
		{
			document.insertEmployee.spouse_utin.value = "";
		}
	}

	function setText()
	{
		if (document.insertEmployee.spouse_utin.value == "") 
		{
			document.insertEmployee.spouse_utin.value = "Enter Spouse UTIN";
		}
	}
	
	function checkTextBox()
	{
		var type = document.insertEmployee.emp_mar_status.value;
		if(type == "1")
		{
			document.insertEmployee.spouse_utin.disabled = false;
		}	
		else
		{
			document.insertEmployee.spouse_utin.disabled = true;
		}
	}

	
	function validate() 
	{
		if(document.insertEmployee.emp_mar_status.value == "1")
		{
			var utinValue = document.insertEmployee.spouse_utin.value;
			
			if(utinValue == "" || utinValue == "Enter Spouse UTIN")
			{
				alert("Please enter spouse UTIN");
				return false;
			}
			else if(!isNumeric(utinValue))
			{
				alert("Spouse UTIN should be digits");
				return false;
			}
			
		}
		document.insertEmployee.submit();
	}
</script>
</head>
<body>

<%@ include file="header.jsp" %>
<%@ include file = "subheader.jsp"%>
<%@ include file="admin_panel.html" %>

<form name="insertEmployee" method="POST" action="insert_employee.do">
<div class="subheader width600">
		<div class="header">
			<h2>Employee Details</h2>
		</div>
	
			<div class="formrow">
				<label class="label1">Employee Type</label>
				<input class="textbox2 rightfloat" type="text" name="emp_type" value="<%=empType%>" readonly="readonly"/>
			</div>
		
		<div class="formrow">
				<label class="label1">Marital Status</label>
				<select name="emp_mar_status" class="drop rightfloat" onChange="checkTextBox()">
				<%
					for(int index = 0;index < maritalStatusList.size(  ); index++)
					{	
						String option = maritalStatusList.get( index );
				%>
						<option value="<%=index%>" <%= ((maritalStatus.equals( option )?"selected":"")) %>><%=option %></option>
				<%
					}
				%>
				</select>
		</div>
		<div class="formrow">
			<input class="textbox2 rightfloat" type="text" name="spouse_utin" value="<%=spouseUTIN %>" <%= (!maritalStatus.equals( Employee.MaritalStatus.MARRIED.name() )?"disabled":"") %> onFocus="clearText()" onBlur="setText()"/>
		</div>
			
	
	<div class="header">
		<h2>Exemption Details</h2>
	</div>
	
	<%
	if(exemptionList != null && exemptionList.size(  ) > 0)
	{
		for(int i=0;i <exemptionList.size(  ); i++ )
		{
			String exemptionName = exemptionList.get( i ).getName(  );
			Double exemptionAmount = exemptionList.get( i ).getAmount(  );
			Double exemptionPer = exemptionList.get( i ).getPercentage(  );
			boolean isExemptionAmt;
			if(exemptionPer == 0.0)
				isExemptionAmt = true;
			else
				isExemptionAmt = false;
	%>
			<div class="formrow"><label class="label1 ">Exemption Name</label>
			<select name="<%="exemptionname"+(i+1)%>" class="drop rightfloat">
			<%
				String[] exempNames = (String[])request.getAttribute( "exemption_names" );
			
				if(exempNames!= null && exempNames.length >= 0)
				{
						for(int index=0; index < exempNames.length; index++)
						{
							String exempName = exempNames[index];							
			%>				
							<option value="<%=exempName%>" <%=((exempName.equals( exemptionName ))?"selected":"") %>><%=exempName %></option>			
			<%				
						}
				}
			%>
			</select>
			</div>
			
			<div class="formrow">
				
				<label class="label1 "><%= isExemptionAmt?"Exemption Amount":"Exemption Per" %></label>
				<input class="textbox2 rightfloat" type="text" name="<%="exemptionamount"+(i+1) %>" value="<%=isExemptionAmt?exemptionAmount:exemptionPer %>" />
			</div>
			
			<input type="hidden" name = "<%="exemptiontype"+(i+1)%>" value="<%=isExemptionAmt?1:2%>"/>
	<%
		}
	}	
	%>	
			<div class="header">
				<h2>Add Exemptions</h2>
			</div>
			<div class="formrow"><label class="label1 ">Exemption Name</label>
			<select name="emp_exemp_name" class="drop rightfloat">
			<%
				String[] exempNames = (String[])request.getAttribute( "exemption_names" );
			
				if(exempNames!= null && exempNames.length >= 0)
				{
						for(int index=0; index < exempNames.length; index++)
						{
							String exempName = exempNames[index];							
							
			%>				<option value="<%=exempName%>"><%=exempName %></option>			
			<%				
						}
				}
			%>
			</select>
			</div>
			<div class="formrow"><label class="label1 ">Exemption Amount/Percentage</label><input class="textbox2 rightfloat" type="text" name="exemptionamt" /></div>
			<div class="formrow rightfloat">
				<select name="exmp_type" class="drop rightfloat">
					<option value="1">Amount</option>
					<option value="2">Percentage</option>
				</select>
			</div>
			<div id="exemp_details"></div>	
			<div class="formrow "><input class=" rightfloat button_blue display_block margin10 margin_b" type="button" value="Add Exemption" onClick="addExmpDetails()"/></div>	
		
	<div class="header">
		<h2>Investment Details</h2>
	</div>
	
	<%
	if(investmentList != null && investmentList.size(  ) > 0)
	{
		for(int i=0;i <investmentList.size(  ); i++ )
		{
			String investmentName = investmentList.get( i ).getName(  );
			Double investmentAmount = investmentList.get( i ).getAmount(  );
			Double investmentPer = investmentList.get( i ).getApplicablePercent(  );
	%>
			<div class="formrow"><label class="label1 ">Exemption Name</label>
			<select name="<%="investmentname"+(i+1)%>" class="drop rightfloat">
			<%
				String[] investmentNames = (String[])request.getAttribute( "investment_names" );
			
				if(investmentNames!= null && investmentNames.length >= 0)
				{
						for(int index=0; index < investmentNames.length; index++)
						{
							String invName = investmentNames[index];							
			%>				
							<option value="<%=invName%>" <%=((invName.equals( investmentName ))?"selected":"") %>><%=invName %></option>			
			<%				
						}
				}
			%>
			</select>
			</div>
			
			<div class="formrow">
				
				<label class="label1 ">Investment Amount</label>
				<input class="textbox2 rightfloat" type="text" name="<%="investmentamount"+(i+1) %>" value="<%=investmentAmount %>" />
			</div>
			
			<div class="formrow">
				
				<label class="label1 ">Applicable Percentage</label>
				<input class="textbox2 rightfloat" type="text" name="<%="investmentper"+(i+1) %>" value="<%=investmentPer %>" />
			</div>
			
	<%
		}
	}	
	%>			
	
	<div class="header">
		<h2>Add Investments</h2>
	</div>
		<div id="inv_details">
			<div class="formrow"><label class="label1 ">Investment Name</label>
			<select name="emp_inv_name" class="drop rightfloat">
			<%
				String[] invNames = (String[])request.getAttribute( "investment_names" );
			
				if(invNames!= null && invNames.length >= 0)
				{
						for(int index=0; index < invNames.length; index++)
						{
							String invName = invNames[index];							
							
			%>				<option value="<%=invName%>"><%=invName %></option>			
			<%				
						}
				}
			%>
			</select>
			</div>
			<div class="formrow"><label class="label1 ">Investment Amount</label><input class="textbox2 rightfloat" type="text" name="investmentamount"  /></div>
			<div class="formrow"><label class="label1 ">Applicable Percentage</label><input class="textbox2 rightfloat" type="text" name="investmentper"/></div>
		</div>	
			<div class="formrow"><input class=" rightfloat button_blue display_block margin_b"  type = "button" value="Add More" onClick="addInvestmentDetails()"/></div>
			
	
	<input type="hidden" name="count" value="<%=exmpsize %>" />
	<input type="hidden" name="inv_count" value="<%=invSize %>" />
			
	<input class=" center_div button_blue display_block margin10"  type = "button" value="Submit" onClick="validate()"/>
	
</div>
</form>
</body>
</html>