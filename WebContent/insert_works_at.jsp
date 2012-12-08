<%@page import="com.taxhouse.model.SeniorCitizen"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.taxhouse.model.Employee"%>
<%@page import="com.taxhouse.model.ArmedForcePersonnel"%>
<%@page import="com.taxhouse.model.TaxPayer"%>
<%@page import="com.taxhouse.model.Student"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
 <%
 		String orgUTIN ="", designation = "", startDate = "";
 		
 		Employee employee = (Employee)session.getAttribute( "taxpayee" );
 		if(employee != null && !(employee instanceof SeniorCitizen))
 		{
 			
 			orgUTIN = String.valueOf( employee.getOrganization(  ).getUtin(  ));
 			designation = employee.getDesignation(  );
 			
 			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
 			startDate = simpleDateFormat.format( employee.getJobStartDate(  ));
 		}
 
 
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
	<div class="header">
		<h2>Enter Working Details</h2>
	</div>
			<div class="formrow">
				<label class="label1 ">Enter Organization UTIN</label>
				<input class="textbox2 rightfloat" type="text" name="emp_org_utin" value="<%=orgUTIN %>"/>
			</div>
			<div class="formrow">
				<label class="label1 ">Enter Designation</label>
				<input class="textbox2 rightfloat" type="text" name="emp_designation" value="<%=designation %>"/>
			</div>
			<div class="formrow">
				<label class="label1 ">Enter Joining Date (yyyy-mm-dd)</label>
				<input class="textbox2 rightfloat" type="text" name="emp_org_join_date" value="<%=startDate %>"/>
			</div>
</body>
</html>