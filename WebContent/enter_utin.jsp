<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
	String error = (String) request.getParameter("error");
	if(error == null || error == "null")
	{
	 	error="";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tax Payment</title>
<script>

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
function clearText()
{
	if (document.utinForm.utin.value == "Enter UTIN") 
	{
		document.utinForm.utin.value = "";
	}
}

function setText()
{
	if (document.utinForm.utin.value == "") 
	{
		document.utinForm.utin.value = "Enter UTIN";
	}
}

function validate() 
{
	if (document.utinForm.utin.value == "") 
	{
		alert("Please enter UTIN");
		document.utinForm.utin.focus();
		return false;
	}
	if(!isNumeric(document.utinForm.utin.value))
	{
		alert("UTIN should be digits");
		document.utinForm.utin.focus();
		return false;
	}

	document.utinForm.submit();
	return true;
}
</script>
</head>
<body>
<% int functionType = Integer.parseInt( session.getAttribute( "functionType" ).toString(  ) ); %>
	<%@ include file="header.jsp" %>
	<%@ include file="admin_panel.html" %>
	<div class="subheader width300">
	<div class="header">
	<% String buttonLabel;
		if(functionType == 1)
		{
			buttonLabel = "Proceed";
	%>
			<h2>CALCULATE TAX</h2>
	<% 	}
		else if(functionType == 2)
		{	
			buttonLabel = "Delete";
	%>	
			<h2>DELETE TAX PAYER</h2>	
	<%	}
		else
		{
			buttonLabel = "Update";
	%>		
			<h2>UPDATE TAX PAYER</h2>	
	<%	}
	%>	
	</div>
	<form name="utinForm" method="GET" action="HandleUTIN.do">
		<input class="textbox1" type="text" name="utin" value="Enter UTIN" onfocus="clearText()" onblur ="setText()"/>
		<%
			if(!error.equals( "" ))
			{	
		%>
			<label class="error_msg"><%='*'+error+" Please try again." %></label>
		<%	}
		%>
		<input class=" center_div button_blue display_block margin10"  type = "button" value=<%= buttonLabel %> onClick="validate()"/>
		
	</form>
</div>
</body>
</html>