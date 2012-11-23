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
<script>

function focusOnUtin()
{
     document.loginForm.utin.focus();
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
<body onLoad="focusOnUtin()">
	<form name="loginForm" method="POST" action="login.do">
		UTIN/AdminID:<input type="text" name="utin" size="15" /><br />
		Password:<input type="password" name="passwd" size="15" /><br />
		Role: <input type="radio" checked name="role" value="taxpayer">Taxpayer
		<input type="radio" name="role" value="admin">Admin

		<div align="center">
			<p>
				<input type="button" value="Login" onClick="validate()" />
			</p>
		</div>
	</form>
	<br />
	<div style="color:#FF0000"><%=error%></div>
</body>
</html>