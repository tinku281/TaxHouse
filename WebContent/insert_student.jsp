<%@page import="com.taxhouse.model.Student"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Student</title>
<script>

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
function clearText()
{
	if (document.insertStudentForm.waiver_amount.value == "Enter Amount") 
	{
		document.insertStudentForm.waiver_amount.value = "";
	}
}

function setText()
{
	if (document.insertStudentForm.waiver_amount.value == "") 
	{
		document.insertStudentForm.waiver_amount.value = "Enter Amount";
	}
}

function validate() 
{
	if (document.insertStudentForm.waiver_amount.value == "") 
	{
		alert("Please enter Free Waiver Amount");
		document.insertStudentForm.waiver_amount.focus();
		return false;
	}
	if(!isNumeric(document.insertStudentForm.waiver_amount.value))
	{
		alert("Amount should be in digits");
		document.insertStudentForm.waiver_amount.focus();
		return false;
	}

	document.insertStudentForm.submit();
	return true;
}
</script>
</head>
<body>
<%  int type  = Integer.parseInt( request.getAttribute( "type" ).toString());
	String freeWaiverAmt= "Enter Amount";
			if(type != 4)
			{
				Student student = (Student)session.getAttribute( "taxpayee" );
				
				if(student != null)
					freeWaiverAmt = String.valueOf( student.getFeeWaiverAmt(  ));
			}
%>
<%@ include file = "header.jsp"%>
<%@ include file = "subheader.jsp"%>
<%@ include file = "admin_panel.html" %>

<div class="subheader width500" >
	<div class="header">
		<h2>Enter Student Details</h2>
	</div>
	<form name="insertStudentForm" method="POST" action="EmployeeSubtypeProcessing.do" >
			<div class="formrow">
				<label class="label1 ">Free Waiver Amount</label>
				<input class="textbox2 rightfloat" type="text" name="waiver_amount" value="<%=freeWaiverAmt %>" onFocus="clearText()" onBlur="setText()"/>
			</div>
			<input class=" center_div button_blue display_block margin10"  type = "button" value="Submit" onClick="validate()"/>
	</form>
</div>	


</body>
</html>