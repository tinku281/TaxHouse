<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Student</title>
<script>
var count = 0;
function addIncomeDetails()
{
	var incomeAmount = document.insertScForm.income_amount.value;
	if(incomeAmount == "" || incomeAmount == "Enter Amount" || !isNumeric(incomeAmount))
	{
		alert("Please enter Income Amount as digits.");
		return false;
	}	
	document.insertScForm.count.value = ++count;
	
	
	var labelIncomeName =document.createElement("label");
	labelIncomeName.setAttribute("class","label1");
	labelIncomeName.innerHTML="Income Source";
	
	var etIncomeName = document.createElement("input");
	etIncomeName.type="text";
	etIncomeName.name="incomename"+count;
	etIncomeName.setAttribute("class", "textbox2 rightfloat");
	etIncomeName.setAttribute("value", document.insertScForm.income_source.value);
	etIncomeName.readOnly = "readonly";

	
	var divIncomeName = document.createElement("div");
	divIncomeName.setAttribute("class", "formrow");
	divIncomeName.appendChild(labelIncomeName);
	divIncomeName.appendChild(etIncomeName);
	
	var labelIncomeAmt =document.createElement("label");
	labelIncomeAmt.setAttribute("class","label1");
	labelIncomeAmt.innerHTML = "Income Amount";
	
	
	var etIncomeAmt = document.createElement("input");
	etIncomeAmt.type="text";
	etIncomeAmt.name="incomeamount"+count;
	etIncomeAmt.setAttribute("class", "textbox2 rightfloat");
	etIncomeAmt.setAttribute("value", incomeAmount);
	etIncomeAmt.readOnly = "readonly";

	
	var divIncomeAmt = document.createElement("div");
	divIncomeAmt.setAttribute("class", "formrow");
	divIncomeAmt.appendChild(labelIncomeAmt);
	divIncomeAmt.appendChild(etIncomeAmt);
	
	document.getElementById('income_details').appendChild(divIncomeName).appendChild(divIncomeAmt);
	
	document.insertScForm.income_amount.value ="Enter Amount";
	return true;
}

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
function clearText()
{
	if (document.insertScForm.income_amount.value == "Enter Amount") 
	{
		document.insertScForm.income_amount.value = "";
	}
}

function setText()
{
	if (document.insertScForm.income_amount.value == "") 
	{
		document.insertScForm.income_amount.value = "Enter Amount";
	}
}

function validate() 
{
	document.insertScForm.submit();
	return true;
}
</script>
</head>
<body>
<%@ include file = "header.jsp"%>
<%@ include file = "subheader.jsp"%>
<%@ include file = "admin_panel.html" %>
<div id="logout" class="width800"><input class="rightfloat button_blue display_block"  type = "button" value="Back" onClick="history.back()"/></div>

<div class="subheader width500" >
	<div class="header">
		<h2>Enter Senior Citizen Details</h2>
	</div>
	<form name="insertScForm" method="POST" action="EmployeeSubtypeProcessing.do" >
			<div class="formrow">
				<label class="label1 ">Income Source</label>
				<select name="income_source" class="drop rightfloat">
				<%
					ArrayList<String> sources = new ArrayList<String>();
					sources.add("Pension");
					sources.add("PF");
					sources.add("OldAge Fund");
					sources.add("Senior Welfare");
					sources.add("State Fund");
					sources.add("Personal Savings");
					sources.add("Annuity");
					sources.add("Business");
					sources.add("Other State");
					sources.add("Part time");
					
					for(int index = 0;index < sources.size();index++)
					{
				%>
						<option value="<%=sources.get( index ) %>"><%=sources.get( index ) %></option>
				
				<%		
					}
				%>
				
				</select>
			</div>
			<div class="formrow">
				<label class="label1 ">Income Amount</label>
				<input class="textbox2 rightfloat" type="text" value="Enter Amount" name="income_amount" onFocus="clearText()" onBlur="setText()"/>
			</div>
			<div id="income_details"></div>	
			<div class="formrow ">
				<input class=" rightfloat button_blue display_block margin10 margin_b" type="button" value="Add Income" onClick="addIncomeDetails()"/>
			</div>
			
			
			<input type="hidden" name="count" value="0" />
			<input class=" center_div button_blue display_block margin10"  type = "button" value="Submit" onClick="validate()"/>
	</form>
</div>	


</body>
</html>