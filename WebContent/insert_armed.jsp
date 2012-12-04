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

function compareDate(date1,date2)
{
	<!-- code to compare two dates and return false if date1 is after date2 -->
	return true;	
}

function addSpecialTask()
{
	var date1 = "";
	var date2= "";
	if(!compareDate(date1,date2))
	{
		alert("Start Date cannot be more than End Date")
		return false;
	}
	
	document.insertApForm.count.value = ++count;
	
	var labelIncomeName =document.createElement("label");
	labelIncomeName.setAttribute("class","label1");
	labelIncomeName.innerHTML="Special Task";
	
	var etIncomeName = document.createElement("input");
	etIncomeName.type="text";
	etIncomeName.name="scname"+count;
	etIncomeName.setAttribute("class", "textbox2 rightfloat");
	etIncomeName.setAttribute("value", document.insertApForm.sc_name.value);
	etIncomeName.readOnly = "readonly";

	
	var divIncomeName = document.createElement("div");
	divIncomeName.setAttribute("class", "formrow");
	divIncomeName.appendChild(labelIncomeName);
	divIncomeName.appendChild(etIncomeName);
	
		
	var labelIncomeAmt =document.createElement("label");
	labelIncomeAmt.setAttribute("class","label1");
	labelIncomeAmt.innerHTML = "Combat Zone";
	
	
	var etIncomeAmt = document.createElement("input");
	etIncomeAmt.type="text";
	etIncomeAmt.name="sccombat"+count;
	etIncomeAmt.setAttribute("class", "textbox2 rightfloat");
	etIncomeAmt.setAttribute("value", document.insertApform.sc_combat.value);
	etIncomeAmt.readOnly = "readonly";

	
	var divIncomeAmt = document.createElement("div");
	divIncomeAmt.setAttribute("class", "formrow");
	divIncomeAmt.appendChild(labelIncomeAmt);
	divIncomeAmt.appendChild(etIncomeAmt);
	
	document.getElementById('sc_details').appendChild(divIncomeName).appendChild(divIncomeAmt);
	
	return true;
}

function validateDate(date,month,year)
{
	<!-- code to return true if date is valid else return false -->
	return true;
}
function validate() 
{
	if(!validateDate(document.insertApform.sc_start_date.value, document.insertApform.sc_start_month.value, document.insertApform.sc_start_year.value))
	{
		alert("Start Date is not valid");
		return false;
	}
	if(!validateDate(document.insertApform.sc_end_date.value, document.insertApform.sc_end_month.value, document.insertApform.sc_end_year.value))
	{
		alert("End Date is not valid");
		return false;
	}
	
	document.insertApForm.submit();
	return true;
}
</script>
</head>
<body>
<%@ include file = "header.jsp"%>
<%@ include file = "admin_panel.html" %>

<div class="subheader width500" >
	<div class="header">
		<h2>Enter Armed Personnel Details</h2>
	</div>
	<form name="insertApForm" method="POST" action="EmployeeSubtypeProcessing.do" >
			<div class="formrow">
				<label class="label1 ">Special Task</label>
				<select name="sc_name" class="drop rightfloat">
				<%
					ArrayList<String> listTask = new ArrayList<String>();
					listTask.add( "Red Cross" );
					listTask.add( "US Marine" );
					listTask.add( "Combat" );
					for(int index = 0;index < listTask.size();index++)
					{
				%>
						<option value="<%=listTask.get( index ) %>"><%=listTask.get( index ) %></option>
				<%		
					}
				%>
				
				</select>
			</div>
			<div class="formrow">
				<label class="label1 ">Combat Zone</label>
				<select name="sc_combat" class="drop rightfloat">
				<%
					ArrayList<String> combatList = new ArrayList<String>();
					combatList.add( "None" );
					combatList.add( "A1" );
					combatList.add( "A2" );
					combatList.add( "A3" );
					combatList.add( "A4" );
					for(int index = 0;index < combatList.size();index++)
					{
						if(!combatList.get( index ).equals( "None" ))
						{	
				%>
						<option value="<%=combatList.get( index ) %>"><%=combatList.get( index ) %></option>
				<%		}
						else
						{
				%>
							<option value=""><%=combatList.get( index ) %></option>
				<%				
						}
					}
				%>
				</select>
			</div>
			<div class="formrow">
				<label class="label1 ">Start Date</label>
				<select name="sc_start_year" class="drop1 rightfloat">
				<%
					for(int index = 1990;index <=2050;index++)
					{
				%>
						<option value="<%=index %>"><%=index %></option>
				<%		
					}
				%>
				</select>
				<select name="sc_start_month" class="drop1 rightfloat">
				<%
					for(int index = 1;index <=12;index++)
					{
				%>
						<option value="<%=index %>"><%=index %></option>
				<%		
					}
				%>
				</select>
				<select name="sc_start_date" class="drop1 rightfloat">
				<%
					for(int index = 1;index <=31;index++)
					{
				%>
						<option value="<%=index %>"><%=index %></option>
				<%		
					}
				%>
				</select>
			</div>
			<div class="formrow">
				<label class="label1 ">End Date</label>
				<select name="sc_end_year" class="drop1 rightfloat">
				<%
					for(int index = 1990;index <=2050;index++)
					{
				%>
						<option value="<%=index %>"><%=index %></option>
				<%		
					}
				%>
				</select>
				<select name="sc_end_month" class="drop1 rightfloat">
				<%
					for(int index = 1;index <=12;index++)
					{
				%>
						<option value="<%=index %>"><%=index %></option>
				<%		
					}
				%>
				</select>
				<select name="sc_end_date" class="drop1 rightfloat">
				<%
					for(int index = 1;index <=31;index++)
					{
				%>
						<option value="<%=index %>"><%=index %></option>
				<%		
					}
				%>
				</select>
			</div>
			<div class="formrow ">
				<input class=" rightfloat button_blue display_block margin10 margin_b" type="button" value="Add Special Task" onClick="addSpecialTask()"/>
			</div>
			<div id="sc_details"></div>	
			
			<input type="hidden" name="count" value="0" />
			<input class=" center_div button_blue display_block margin10"  type = "button" value="Submit" onClick="validate()"/>
	</form>
</div>	


</body>
</html>