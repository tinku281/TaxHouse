<%@page import="com.taxhouse.model.Student"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Student</title>
<script>

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
	var orgUtin = document.insertStudentForm.emp_org_utin.value;
	var empDesg = document.insertStudentForm.emp_designation.value;
	var empJoinDate = document.insertStudentForm.emp_org_join_date.value;
	
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
	
	if (orgUtin == "") 
	{
		alert("Please enter Organtization UTIN");
		document.insertStudentForm.emp_org_utin.focus();
		return false;
	}
	
	if(empDesg == "")
	{
		alert("Please enter Designation");
		document.insertStudentForm.emp_designation.focus();
		return false;
	}
	if(empJoinDate == "")
	{
		alert("Please enter Joining Date");
		document.insertStudentForm.emp_org_join_date.focus();
		return false;
	}	
	if(!isNumeric(orgUtin))
	{
		alert("Please enter Organtization UTIN as digits");
		document.insertStudentForm.emp_org_utin.focus();
		return false;
	}	
	if(!empJoinDate.isValidDate())
	{
		alert("Please enter a valid Joining Date");
		document.insertStudentForm.emp_org_join_date.focus();
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
<div id="logout" class="width800"><input class="rightfloat button_blue display_block"  type = "button" value="Back" onClick="history.back()"/></div>

<div class="subheader width700" >
	<div class="header">
		<h2>Enter Student Details</h2>
	</div>
	<form name="insertStudentForm" method="POST" action="EmployeeSubtypeProcessing.do" >
			<div class="formrow">
				<label class="label1 ">Free Waiver Amount</label>
				<input class="textbox2 rightfloat" type="text" name="waiver_amount" value="<%=freeWaiverAmt %>" onFocus="clearText()" onBlur="setText()"/>
			</div>
			
			<%@ include file = "insert_works_at.jsp"%>
			<input class=" center_div button_blue display_block margin10"  type = "button" value="Submit" onClick="validate()"/>
	</form>
</div>	


</body>
</html>