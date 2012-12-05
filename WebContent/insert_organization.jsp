<%@page import="com.taxhouse.model.Organization"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Student</title>
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
        var validChars = '0123456789.';


        for(var i = 0; i < val.length; i++) 
        {
            if(validChars.indexOf(val.charAt(i)) == -1)
                return false;
        }

        return true;
}

function addShareDetails()
{
	var holderUTIN = document.insertOrgForm.org_shr_hldr_utin.value;
	var sharePer = document.insertOrgForm.org_shr_hldr_per.value;
	
	if(holderUTIN == "" || sharePer == "" )
	{
		alert("Please fill in the Shareholder's details");	
		return false;
	}
	if(!isNumeric(holderUTIN))
	{
		alert("UTIN amount should be in digits");	
		return false;
	}
	if(!isNumeric(sharePer))
	{
		alert("Share's percentage should be in digits");	
		return false;
	}
	
	document.insertOrgForm.count.value = 	++count;
	
	/*--------------SHAREHOLDER'S UTIN--------*/
	
	var labelholderUTIN =document.createElement("label");
	labelholderUTIN.setAttribute("class","label1");
	labelholderUTIN.innerHTML="Shareholder's UTIN";
	
	var textBoxholderUTIN = document.createElement("input");
	textBoxholderUTIN.setAttribute("type", "text");
	textBoxholderUTIN.setAttribute("class", "textbox2 rightfloat");
	textBoxholderUTIN.setAttribute("name", "holderutin"+count);
	textBoxholderUTIN.setAttribute("value", holderUTIN);
	textBoxholderUTIN.readOnly = "readonly";

	var divholderUTIN = document.createElement("div");
	divholderUTIN.setAttribute("class", "formrow");
	divholderUTIN.appendChild(labelholderUTIN);
	divholderUTIN.appendChild(textBoxholderUTIN);
	
	/*--------------SHARE PERCENTAGE--------*/
	
	var labelsharePer =document.createElement("label");
	labelsharePer.setAttribute("class","label1");
	labelsharePer.innerHTML="Share Percentage";
	
	var textBoxsharePer = document.createElement("input");
	textBoxsharePer.setAttribute("type", "text");
	textBoxsharePer.setAttribute("class", "textbox2 rightfloat");
	textBoxsharePer.setAttribute("name", "shareper"+count);
	textBoxsharePer.setAttribute("value", sharePer);
	textBoxsharePer.readOnly = "readonly";
	
	var divsharePer = document.createElement("div");
	divsharePer.setAttribute("class", "formrow");
	divsharePer.appendChild(labelsharePer);
	divsharePer.appendChild(textBoxsharePer);
	
	document.getElementById('share_details').appendChild(divholderUTIN).appendChild(divsharePer);
	return true;
}

function validate() 
{
	var orgEstbDate = document.insertOrgForm.org_estb_date.value;
	var orgTurnover = document.insertOrgForm.org_turnover.value;
	var orgProfit = document.insertOrgForm.org_profit.value;
	
	if(orgEstbDate == "" || !(orgEstbDate.isValidDate()))
	{
		alert("Please enter a valid established date");
		return false;
	}
	if(orgTurnover == "" || !isNumeric(orgTurnover))
	{
		alert("Please enter turnover as digits");
		return false;
	}
	if(orgProfit == "" || !isNumeric(orgProfit))
	{
		alert("Please enter profit as digits");
		return false;
	}
		
	document.insertOrgForm.submit();
	return true;
}
</script>
</head>
<body>
<%@ include file = "header.jsp"%>
<%@ include file = "admin_panel.html" %>

<div class="subheader width700" >
	<div class="header">
		<h2>Enter Organization Details</h2>
	</div>
	<form name="insertOrgForm" method="POST" action="OrganizationProcessing.do" >
	
			<div class="formrow">
				<label class="label1 ">Established Date (yyyy-mm-dd)</label>
				<input class="textbox2 rightfloat" type="text" name="org_estb_date"/>
			</div>
			<div class="formrow">
				<label class="label1 ">Turnover (in $)</label>
				<input class="textbox2 rightfloat" type="text" name="org_turnover"/>
			</div>
			<div class="formrow">
				<label class="label1 ">Total Profit (in $)</label>
				<input class="textbox2 rightfloat" type="text" name="org_profit"/>
			</div>
			<div class="formrow">
				<label class="label1 ">Organization Scale</label>
				<select name="org_scale" class="drop rightfloat">
					<option value ="<%=Organization.Scale.getId( Organization.Scale.SMALL ) %>"><%=Organization.Scale.SMALL %></option>
					<option value ="<%=Organization.Scale.getId( Organization.Scale.MEDIUM ) %>"><%=Organization.Scale.MEDIUM %></option>
					<option value ="<%=Organization.Scale.getId( Organization.Scale.LARGE ) %>"><%=Organization.Scale.LARGE %></option>
				</select>
			</div>
			<div class="formrow">
				<label class="label1 ">Organization Category</label>
				<select name="org_category" class="drop rightfloat">
					<option value ="<%=Organization.Category.getId( Organization.Category.AGRICULTURE ) %>"><%=Organization.Category.AGRICULTURE%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.ARMY ) %>"><%=Organization.Category.ARMY%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.AUTOMOBILE ) %>"><%=Organization.Category.AUTOMOBILE%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.CHEMICALS ) %>"><%=Organization.Category.CHEMICALS%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.EDUCATIONAL ) %>"><%=Organization.Category.EDUCATIONAL%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.ENTERTAINMENT ) %>"><%=Organization.Category.ENTERTAINMENT%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.FINANCE ) %>"><%=Organization.Category.FINANCE%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.HEALTH ) %>"><%=Organization.Category.HEALTH%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.INFORMATION_TECHNOLOGY ) %>"><%=Organization.Category.INFORMATION_TECHNOLOGY%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.INSURANCE ) %>"><%=Organization.Category.INSURANCE%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.LOGISTICS ) %>"><%=Organization.Category.LOGISTICS%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.MANUFACTURING ) %>"><%=Organization.Category.MANUFACTURING%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.PETROLEUM ) %>"><%=Organization.Category.PETROLEUM%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.REAL_ESTATE ) %>"><%=Organization.Category.REAL_ESTATE%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.TELECOMMUNICATION ) %>"><%=Organization.Category.TELECOMMUNICATION%></option>
					<option value ="<%=Organization.Category.getId( Organization.Category.TRANSPORT ) %>"><%=Organization.Category.TRANSPORT%></option>
				</select>
			</div>
			<div class="formrow">
				<label class="label1 ">Organization Type</label>
				<select name="org_type" class="drop rightfloat">
					<option value ="<%=Organization.Type.getId( Organization.Type.NON_PROFIT_PRIVATE) %>"><%=Organization.Type.NON_PROFIT_PRIVATE %></option>
					<option value ="<%=Organization.Type.getId( Organization.Type.NON_PROFIT_PUBLIC) %>"><%=Organization.Type.NON_PROFIT_PUBLIC %></option>
					<option value ="<%=Organization.Type.getId( Organization.Type.PROFIT_PRIVATE) %>"><%=Organization.Type.PROFIT_PRIVATE %></option>
					<option value ="<%=Organization.Type.getId( Organization.Type.PROFIT_PUBLIC) %>"><%=Organization.Type.PROFIT_PUBLIC %></option>
				</select>
			</div>
			
			<div class="header">
				<h2>ShareHolder Details</h2>
			</div>
					<div class="formrow"><label class="label1 ">Shareholder's UTIN</label><input class="textbox2 rightfloat" type="text" name="org_shr_hldr_utin" /></div>
					<div class="formrow"><label class="label1 ">Share's Percentage</label><input class="textbox2 rightfloat" type="text" name="org_shr_hldr_per" /></div>
					<div id="share_details"></div>	
					<div class="formrow "><input class=" rightfloat button_blue display_block margin10 margin_b" type="button" value="Add Shareholder" onClick="addShareDetails()"/></div>
						
			<input type="hidden" name="count" value="0" />
			<input class=" center_div button_blue display_block margin10"  type = "button" value="Submit" onClick="validate()"/>
			
	</form>
</div>	


</body>
</html>