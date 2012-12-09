<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String customMessage = request.getAttribute( "custom_message" ).toString(  );
	if(customMessage == null)
	{
		customMessage = "";
	}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@include file="header.jsp"%>
	<%@ include file = "subheader.jsp"%>

	<%
		if (session.getAttribute("role").toString().equals("admin"))
		{
	%>
			<%@include file="admin_panel.html"%>
	<%
		}
	%>
	
	<div class="subheader width800 padding_bottom20">
		<div class="header">
			<h2><%=customMessage %></h2>
		</div>
	</div>	
</body>
</html>