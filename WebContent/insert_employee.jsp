<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Employee</title>
<script>
	var count = 0;
	function addExmpDetails()
	{
		document.insertEmployee.count.value = 	++count;
		var labelExmpAmount =document.createElement("label");
		labelExmpAmount.setAttribute("class","label1");
		labelExmpAmount.innerHTML="Exemption Amount";
		
		var textBoxExmpAmount = document.createElement("input");
		textBoxExmpAmount.setAttribute("type", "text");
		textBoxExmpAmount.setAttribute("class", "textbox2 rightfloat");
		textBoxExmpAmount.setAttribute("name", "exemptionamount"+count);
		textBoxExmpAmount.setAttribute("value", "Enter Exemption Amount"+count);

		
		var divExmpAmount = document.createElement("div");
		divExmpAmount.setAttribute("class", "formrow");
		divExmpAmount.appendChild(labelExmpAmount);
		divExmpAmount.appendChild(textBoxExmpAmount);
		
		var labelExmpPer =document.createElement("label");
		labelExmpPer.setAttribute("class","label1");
		labelExmpPer.innerHTML="Exemption Per";
		
		var textBoxExmpPer = document.createElement("input");
		textBoxExmpPer.setAttribute("type", "text");
		textBoxExmpPer.setAttribute("class", "textbox2 rightfloat");
		textBoxExmpPer.setAttribute("name", "exemptionper"+count);
		textBoxExmpPer.setAttribute("value", "Enter Exemption Per"+count);

		
		var divExmpPer = document.createElement("div");
		divExmpPer.setAttribute("class", "formrow");
		divExmpPer.appendChild(labelExmpPer);
		divExmpPer.appendChild(textBoxExmpPer);
		
		document.getElementById('exemp_details').appendChild(divExmpAmount).appendChild(divExmpPer);
		
	}
	
	function validate() 
	{
			document.insertEmployee.submit();
	}
</script>
</head>
<body>
<%@ include file="header.jsp" %>
<%@ include file="admin_panel.html" %>

<div class="subheader width600">
	<div class="header">
		<h2>Employee Details</h2>
	</div>
	<form name="insertEmployee" method="POST" action="insert_employee.do">
			<div class="formrow">
				<label class="label1">Employee Type</label>
				<select name="emp_type" class="drop rightfloat">
					<option value="1">Student</option>
					<option value="2">Senior Citizen</option>
					<option value="3">Army</option>
				</select>
			</div>
		
		<div class="formrow">
				<label class="label1">Marital Status</label>
				<select name="emp_mar_status" class="drop rightfloat">
					<option value="0">Single</option>
					<option value="1">Married</option>
					<option value="2">Divorced</option>
					<option value="3">Widow</option>
				</select>
			</div>
		<div class="formrow"><input class="textbox2 rightfloat" type="text" name="spouse_utin" value="Enter Spouse UTIN" disabled="disabled" /></div>
			
	
	<div class="header">
		<h2>Exemption Details</h2>
	</div>
	<div id="exemp_details">
			<div class="formrow"><label class="label1 ">Exemption Amount</label><input class="textbox2 rightfloat" type="text" name="exemptionamount" value="Enter Exemption Amount" /></div>
			<div class="formrow"><label class="label1 ">Exemption Per</label><input class="textbox2 rightfloat" type="text" name="exemptionper" value="Enter Exemption Per"/></div>
	</div>	
			<div class="formrow "><input class=" rightfloat button_blue display_block margin10 margin_b" type="button" value="Add More" onClick="addExmpDetails()"/></div>
	
	<div class="header">
		<h2>Investment Details</h2>
	</div>
			<div class="formrow"><label class="label1 ">Investment Amount</label><input class="textbox2 rightfloat" type="text" name="investmentamount" value="Enter Investment Amount" /></div>
			<div class="formrow"><label class="label1 ">Applicable Per</label><input class="textbox2 rightfloat" type="text" name="investmentper" value="Enter Investment Per"/></div>
			<div class="formrow"><input class=" rightfloat button_blue display_block margin_b"  type = "button" value="Add More"/></div>
			
	
	<input type="hidden" name="count" value="0" />
			
	<input class=" center_div button_blue display_block margin10"  type = "submit" value="Submit"/>
	
	</form>
	
</div>
</body>
</html>