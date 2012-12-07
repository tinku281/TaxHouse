<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Armed Personnel</title>
<script>

var count = 0;

String.prototype.isValidDate = function() {
	var IsoDateRe = new RegExp("^([0-9]{4})-([0-9]{2})-([0-9]{2})$");
	var matches = IsoDateRe.exec(this);
	
	if(!matches)
		return false;
	
	var composedDate = new Date(matches[1] , matches[2]-1,matches[3]);
	
	return ((composedDate.getMonth() == (matches[2] -1) )  &&  (composedDate.getDate() == matches[3]) && (composedDate.getFullYear() == matches[1]));
};

function isNumeric(val)
{
        var validChars = '0123456789';


        for(var i = 0; i < val.length; i++) 
        {
            if(validChars.indexOf(val.charAt(i)) == -1)
                return false;
        }

        return true;
}


function compareDate(start,end)
{
	var IsoDateRe = new RegExp("^([0-9]{4})-([0-9]{2})-([0-9]{2})$");
	var matches = IsoDateRe.exec(start);
	
	var startDate = new Date(matches[1] , matches[2]-1,matches[3]);
	
	 matches = IsoDateRe.exec(end);
	 
	 var endDate = new Date(matches[1] , matches[2]-1,matches[3]);
	
	if(startDate > endDate) 
		return false; 
	else 
		return true;
}

function addSpecialTask()
{
	var startDate = document.insertApForm.start_date.value;
	var endDate = document.insertApForm.end_date.value;
	if(!(startDate.isValidDate()))
	{
		alert(startDate +"Start Date is not valid");
		return false;
	}
	if(!(endDate.isValidDate()))
	{
		alert("End Date is not valid");
		return false;
	}
	if(!compareDate(startDate,endDate))
	{
		alert("Start Date cannot be after than End Date");
		return false;
	}
	
	document.insertApForm.count.value = ++count;
	
	/*------------SPECIAL TASK NAME-----------*/
	
	var labelSTName =document.createElement("label");
	labelSTName.setAttribute("class","label1");
	labelSTName.innerHTML="Special Task";
	
	var etSTName = document.createElement("input");
	etSTName.type="text";
	etSTName.name="scname"+count;
	etSTName.setAttribute("class", "textbox2 rightfloat");
	etSTName.setAttribute("value", document.insertApForm.sc_name.value);
	etSTName.readOnly = "readonly";
	
	var divSTName = document.createElement("div");
	divSTName.setAttribute("class", "formrow");
	divSTName.appendChild(labelSTName);
	divSTName.appendChild(etSTName);
	
	/*------------SPECIAL TASK COMBAT ZONE-----------*/	
	
	var labelSTCombat =document.createElement("label");
	labelSTCombat.setAttribute("class","label1");
	labelSTCombat.innerHTML = "Combat Zone";
	
	var etSTCombat = document.createElement("input");
	etSTCombat.type="text";
	etSTCombat.name="sccombat"+count;
	etSTCombat.setAttribute("class", "textbox2 rightfloat");
	etSTCombat.setAttribute("value", document.insertApForm.sc_combat.value);
	etSTCombat.readOnly = "readonly";

	var divSTCombat = document.createElement("div");
	divSTCombat.setAttribute("class", "formrow");
	divSTCombat.appendChild(labelSTCombat);
	divSTCombat.appendChild(etSTCombat);
	
	/*------------SPECIAL TASK START DATE-----------*/	
	
	var labelSTSDate =document.createElement("label");
	labelSTSDate.setAttribute("class","label1");
	labelSTSDate.innerHTML = "Start Date";
	
	var etSTSDate = document.createElement("input");
	etSTSDate.type="text";
	etSTSDate.name="scstartdate"+count;
	etSTSDate.setAttribute("class", "textbox2 rightfloat");
	etSTSDate.setAttribute("value", startDate);
	etSTSDate.readOnly = "readonly";
	
	var divSTSDate = document.createElement("div");
	divSTSDate.setAttribute("class", "formrow");
	divSTSDate.appendChild(labelSTSDate);
	divSTSDate.appendChild(etSTSDate);
	
	/*------------SPECIAL TASK END DATE-----------*/	
	
	var labelSTEDate =document.createElement("label");
	labelSTEDate.setAttribute("class","label1");
	labelSTEDate.innerHTML = "End Date";
	
	var etSTEDate = document.createElement("input");
	etSTEDate.type="text";
	etSTEDate.name="scenddate"+count;
	etSTEDate.setAttribute("class", "textbox2 rightfloat");
	etSTEDate.setAttribute("value", endDate);
	etSTEDate.readOnly = "readonly";

	var divSTEDate = document.createElement("div");
	divSTEDate.setAttribute("class", "formrow");
	divSTEDate.appendChild(labelSTEDate);
	divSTEDate.appendChild(etSTEDate);
	
	document.getElementById('sc_details').appendChild(divSTName).appendChild(divSTCombat).appendChild(divSTSDate).appendChild(divSTEDate);
	
	return true;
}

function validate() 
{
	var orgUtin = document.insertApForm.emp_org_utin.value;
	var empDesg = document.insertApForm.emp_designation.value;
	var empJoinDate = document.insertApForm.emp_org_join_date.value;
	
	if (orgUtin == "") 
	{
		alert("Please enter Organtization UTIN");
		document.insertApForm.emp_org_utin.focus();
		return false;
	}
	
	if(empDesg == "")
	{
		alert("Please enter Designation");
		document.insertApForm.emp_designation.focus();
		return false;
	}
	if(empJoinDate == "")
	{
		alert("Please enter Joining Date");
		document.insertApForm.emp_org_join_date.focus();
		return false;
	}	
	if(!isNumeric(orgUtin))
	{
		alert("Please enter Organtization UTIN as digits");
		document.insertApForm.emp_org_utin.focus();
		return false;
	}	
	if(!empJoinDate.isValidDate())
	{
		alert("Please enter a valid Joining Date");
		document.insertApForm.emp_org_join_date.focus();
		return false;
	}	
	document.insertApForm.submit();
	return true;
}
</script>
</head>
<body>
<%@ include file = "header.jsp"%>
<%@ include file = "subheader.jsp"%>
<%@ include file = "admin_panel.html" %>

<div class="subheader width700" >
	
	<form name="insertApForm" method="POST" action="EmployeeSubtypeProcessing.do" >
		<%@ include file = "insert_works_at.jsp"%>
		
		<div class="header">
			<h2>Enter Armed Personnel Details</h2>
		</div>
	
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
				<label class="label1 ">Start Date (yyyy-mm-dd)</label>
				<input class="textbox2 rightfloat" type="text" name="start_date"  />
			</div>
			<div class="formrow">
				<label class="label1 ">End Date (yyyy-mm-dd)</label>
				<input class="textbox2 rightfloat" type="text" name="end_date"  />
			</div>
			<div id="sc_details"></div>	
			<div class="formrow ">
				<input class=" rightfloat button_blue display_block margin10 margin_b" type="button" value="Add Special Task" onClick="addSpecialTask()"/>
			</div>
			
			<input type="hidden" name="count" value="0" />
			<input class=" center_div button_blue display_block margin10"  type = "button" value="Submit" onClick="validate()"/>
	</form>
</div>	


</body>
</html>