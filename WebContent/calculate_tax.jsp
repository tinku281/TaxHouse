<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tax Payment</title>
</head>
<body>
	<%@ include file="header.jsp" %>
	<%@ include file="admin_panel.html" %>
	
	<div class="subheader width300">
	<div class="header">
		<h2>LOGIN</h2>
	</div>
	<form name="loginForm" method="POST" action="login.do">
		<input class="textbox1" type="text" name="utin" value="Enter UTIN" onfocus="clearText()" onblur ="setText()"/>
		<input class=" center_div button_blue display_block margin10"  type = "button" value="Login" onClick="validate()"/>
	</form>
</div>
</body>
</html>