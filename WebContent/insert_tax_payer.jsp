<%@page import="com.taxhouse.model.TaxPayer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Tax Payer</title>
<script>

function validate() 
{
	if (document.insertform.firstname.value == "") 
	{
		alert("Please enter First Name");
		document.insertform.firstname.focus();
		return false;
	}
	else if (document.insertform.lastname.value == "") 
	{
		alert("Please enter Last Name");
		document.insertform.lastname.focus();
		return false;
	} 
	else if(document.insertform.tp_password.value== "")
	{
		alert("Please enter password");
		document.insertform.tp_password.focus();
		return false;
	}
	else if (document.insertform.city.value == "") 
	{
		alert("Please enter city");
		document.insertform.city.focus();
		return false;
	} 
	else if (document.insertform.state.value == "") 
	{
		alert("Please enter state");
		document.insertform.state.focus();
		return false;
	} 
	else 
	{
		document.insertform.submit();
	}
}
</script>
</head>
<body>
<%
	String firstName,lastName,password,city,state,buttonLabel;
		int nationality;
		boolean typeDisabled;
		
		TaxPayer taxPayer = (TaxPayer)session.getAttribute( "taxpayee" );
		
		if(taxPayer != null)
		{
	firstName = taxPayer.getFirstName(  );
	lastName = taxPayer.getLastName(  );
	password = taxPayer.getPassword(  );
	city = taxPayer.getCity(  );
	state =taxPayer.getState(  );
	nationality = taxPayer.getNationality(  ).ordinal(  );
	typeDisabled = true;
	buttonLabel = "Update";
		}
		else
		{
	firstName="";
	lastName = "";
	password="";
	city = "";
	state = "";
	nationality = TaxPayer.Nationality.USA.ordinal(  );
	typeDisabled = false;
	buttonLabel = "Submit";
		}
%>

<%@ include file = "header.jsp"%>
<%@ include file = "admin_panel.html" %>

<div class="subheader width500" >
	<div class="header">
		<h2>Enter Details</h2>
	</div>
	<form name="insertform" method="POST" action="insert.do" >
			<div class="formrow"><label class="label1 ">First Name</label><input class="textbox2 rightfloat" type="text" name="firstname" value="<%=firstName%>" /></div>
			<div class="formrow"><label class="label1 ">Last Name</label><input class="textbox2 rightfloat" type="text" name="lastname" value="<%=lastName %>"/></div>
			<div class="formrow"><label class="label1 ">TP Password</label><input class="textbox2 rightfloat" type="password" name="tp_password" value="<%=password %>"/></div>
			<div class="formrow"><label class="label1 ">City</label><input class="textbox2 rightfloat" type="text" name="city" value="<%=city %>" /></div>
			<div class="formrow"><label class="label1 ">State</label><input class="textbox2 rightfloat" type="text" name="state" value="<%=state %>"/></div>
			<div class="formrow"><label class="label1 ">Nationality</label><select name="nationality" class="drop rightfloat">
				<%
					if(nationality == TaxPayer.Nationality.USA.ordinal(  ) )
					{
				%>
						<option value="1" selected="selected">USA</option>
						<option value="2">NON_USA</option>
				<%	}
					else
					{	
				%>
						<option value="1">USA</option>
						<option value="2" selected="selected">NON_USA</option>
				<%	}
				%>	
			</select>
			</div>
			<div class="formrow">
				<label class="label1">TP Category</label>
				<%
					if(typeDisabled)
					{	
				%>
						<select name="tp_category" class="drop rightfloat" disabled="disabled" >
				<%	}
					else
					{	
				%>		<select name="tp_category" class="drop rightfloat" >
				<%	}
				%>
					<option value="1">Employee</option>
					<option value="2">Organization</option>
			</select></div>
			<input class=" center_div button_blue display_block margin10"  type = "button" value="<%=buttonLabel %>" onClick="validate()"/>
	</form>
</div>

</body>
</html>