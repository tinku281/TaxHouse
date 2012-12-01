<%@page import="com.taxhouse.db.DBHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.lang.*"%>

<%
	String error = (String) request.getParameter("error");
	if(error == null || error == "null"){
	 	error="";
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TaxHouse Login</title>
<link rel="stylesheet" type="text/css" href="main.css" />
<script>

function focusOnUtin()
{
     document.loginForm.utin.focus();
}

function clearText()
{
	if (document.loginForm.utin.value == "UTIN/AdminID") 
	{
	   document.loginForm.utin.value = "";
	}
}

function setText()
{
	if (document.loginForm.utin.value == "") 
	{
	   document.loginForm.utin.value = "UTIN/AdminID";
	}
}

function validate() {
	if (document.loginForm.utin.value == "") {
		alert("Please enter UTIN/AdminID");
		document.loginForm.utin.focus();
		return false;
	}
	else if (document.loginForm.passwd.value == "") {
		alert("Please enter password");
		document.loginForm.passwd.focus();
		return false;
	} 
	else {
		document.loginForm.submit();
	}
}
</script>
</head>
<body>
<%@ include file="header.jsp" %>

<div class="subheader width300">
	<div class="header">
		<h2>LOGIN</h2>
	</div>
	<form name="loginForm" method="POST" action="login.do">
		<input class="textbox1" type="text" name="utin" value="UTIN/AdminID" onfocus="clearText()" onblur ="setText()"/>
		<input class="textbox1" type="password" name="passwd" />
		<div class="center_div margin10">
		<input type="radio" name="role" value="taxpayer" checked="yes"/>Tax Payer
		<input type="radio" name="role" value="admin"/>Admin
		</div>
		<input class=" center_div button_blue display_block margin10"  type = "button" value="Login" onClick="validate()"/>
	</form>
	<br />
	<div align="center" style="color:#FF0000"><%=error%></div>
</div>
	
</body>
</html>