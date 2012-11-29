<%@page import="com.taxhouse.model.TaxPayer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Employee</title>
<script>
	var count = 0;
	var invCount = 0;
	
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
		labelExmpPer.innerHTML="Exemption Per";
		
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
		
		return true;
		
	}
	
	function addInvestmentDetails()
	{
		var invAmount,invPer;
		if(invCount == 0)
		{	
			invAmount = document.insertEmployee.investmentamount.value;
			invPer = document.insertEmployee.investmentper.value;
		}
		else
		{
			invAmount = document.insertEmployee.elements["investmentamount"+invCount].value;
			invPer = document.insertEmployee.elements["investmentper"+invCount].value;
		}	
		if(invAmount == "" || invPer == "" )
		{
			alert("Please complete previous investement details before adding another");	
			return false;
		}
		if(!isNumeric(invAmount) || !isNumeric(invPer))
		{
			alert("Investement details should be in digits");	
			return false;
		}
		if(invCount == 0)
		{
			document.insertEmployee.investmentamount.readOnly="readonly";
			document.insertEmployee.investmentper.readOnly="readonly";
		}
		else
		{
			document.insertEmployee.elements["investmentamount"+invCount].readOnly = "readonly";
			document.insertEmployee.elements["investmentper"+invCount].readOnly = "readonly";
		}	
		
		document.insertEmployee.inv_count.value = 	++invCount;
		
		var labelInvAmount =document.createElement("label");
		labelInvAmount.setAttribute("class","label1");
		labelInvAmount.innerHTML="Exemption Amount";
		
		var textBoxInvAmount = document.createElement("input");
		textBoxInvAmount.setAttribute("type", "text");
		textBoxInvAmount.setAttribute("class", "textbox2 rightfloat");
		textBoxInvAmount.setAttribute("name", "investmentamount"+invCount);

		var divInvAmount = document.createElement("div");
		divInvAmount.setAttribute("class", "formrow");
		divInvAmount.appendChild(labelInvAmount);
		divInvAmount.appendChild(textBoxInvAmount);
		
		
		var labelInvPer =document.createElement("label");
		labelInvPer.setAttribute("class","label1");
		labelInvPer.innerHTML="Applicable Percentage";
		
		var textBoxInvPer = document.createElement("input");
		textBoxInvPer.setAttribute("type", "text");
		textBoxInvPer.setAttribute("class", "textbox2 rightfloat");
		textBoxInvPer.setAttribute("name", "investmentper"+invCount);
		
		var divInvPer = document.createElement("div");
		divInvPer.setAttribute("class", "formrow");
		divInvPer.appendChild(labelInvPer);
		divInvPer.appendChild(textBoxInvPer);
		
		document.getElementById('inv_details').appendChild(divInvAmount).appendChild(divInvPer);
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
<%@ include file="admin_panel.html" %>

<form name="insertEmployee" method="POST" action="insert_employee.do">
<div class="subheader width600">
	<div class="header">
		<h2>Employee Details</h2>
	</div>
	
			<div class="formrow">
				<label class="label1">Employee Type</label>
				<select name="emp_type" class="drop rightfloat">
					<option value="1">Student</option>
					<option value="2">Senior Citizen</option>
					<option value="3">Army</option>
					<option value="0">None</option>
				</select>
			</div>
		
		<div class="formrow">
				<label class="label1">Marital Status</label>
				<select name="emp_mar_status" class="drop rightfloat" onChange="checkTextBox()">
					<option value="0">Single</option>
					<option value="1">Married</option>
					<option value="2">Divorced</option>
					<option value="3">Widow</option>
				</select>
			</div>
		<div class="formrow"><input class="textbox2 rightfloat" type="text" name="spouse_utin" value="Enter Spouse UTIN" disabled="disabled" onFocus="clearText()" onBlur="setText()"/></div>
			
	
	<div class="header">
		<h2>Exemption Details</h2>
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
				</select></div>
	<div id="exemp_details"></div>	
			<div class="formrow "><input class=" rightfloat button_blue display_block margin10 margin_b" type="button" value="Add Exemption" onClick="addExmpDetails()"/></div>
	
	<div class="header">
		<h2>Investment Details</h2>
	</div>
		<div id="inv_details">
			<div class="formrow"><label class="label1 ">Investment Amount</label><input class="textbox2 rightfloat" type="text" name="investmentamount"  /></div>
			<div class="formrow"><label class="label1 ">Applicable Percentage</label><input class="textbox2 rightfloat" type="text" name="investmentper"/></div>
		</div>	
			<div class="formrow"><input class=" rightfloat button_blue display_block margin_b"  type = "button" value="Add More" onClick="addInvestmentDetails()"/></div>
			
	
	<input type="hidden" name="count" value="0" />
	<input type="hidden" name="inv_count" value="0" />
			
	<input class=" center_div button_blue display_block margin10"  type = "button" value="Submit" onClick="validate()"/>
	
</div>
</form>
</body>
</html>