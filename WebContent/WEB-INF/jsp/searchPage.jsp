<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<s:head theme="xhtml" />

<script type="text/javascript" src="/${initParam.appName}/assets/js/common.js"></script>
<script>

function submitSearchForm()
{
	
	var firstnameEmptyCheck = checkEmpty(document.getElementById("firstname"));
	var lastnameEmptyCheck = checkEmpty(document.getElementById("lastname"));
	var emailEmptyCheck = checkEmpty(document.getElementById("email"));
	var ufidEmptyCheck = checkEmpty(document.getElementById("ufid"));
	
	if(firstnameEmptyCheck==false || lastnameEmptyCheck==false || emailEmptyCheck==false || ufidEmptyCheck==false)
		{
			var	emailCheck = emailValidator(document.getElementById("email"));
			
			if(emailCheck==true)
			{
				var numberCheck = numbersCheck(document.getElementById("ufid"));
				if(numberCheck==true)
					{
							if(semesterYearCheck()==true)
							{
			 					document.getElementById("searchForm").submit();
							}
					}
				else
					{
						document.getElementById("errorHeading").innerHTML="UFID should be a number. No special charaters should be entered";
					}
			}
			else
				{
					document.getElementById("errorHeading").innerHTML="Email should be in proper format";
				}
		}
	else
		{
			detailsCheck();			
		}
}


function detailsCheck()
{
	
	var supervisorIdEmptyCheck = checkEmpty(document.getElementById("supervisorId"));
	var titleEmptyCheck = checkEmpty(document.getElementById("title"));
	var yearEmptyCheck = checkEmpty(document.getElementById("year"));
	var semesterEmptyCheck = checkEmpty(document.getElementById("semesterId"));
	
	if(supervisorIdEmptyCheck==true && titleEmptyCheck==true && yearEmptyCheck==true)
	{
		if(semesterEmptyCheck==true)
		{
			document.getElementById("errorHeading").innerHTML="Enter the fields to search the records";
		}
		else
		{
			document.getElementById("errorHeading").innerHTML="Select the year field";
		}
	}
	else
	{
		if(semesterYearCheck()==true)
		{
				document.getElementById("searchForm").submit();
		}
	}
}

function semesterYearCheck()
{
	var semesterEmptyCheck = checkEmpty(document.getElementById("semesterId"));
	var yearEmptyCheck = checkEmpty(document.getElementById("year"));
	
	if(semesterEmptyCheck==false)
	{
		if(yearEmptyCheck==true)
			{
				document.getElementById("errorHeading").innerHTML="Select the year field";
				return false;
			}
		else
			{
				return true;
			}
	}
else
	{
		return true;
	}
	
}
</script>


<c:if test="${message != null}">
	<h3 style="color: RED">${message}</h3>
</c:if>
<h3 id="errorHeading" style="color: RED"></h3>

<h4 style="color: GREEN">Enter one or more fields to search</h4>

<form action="searchResult" method="post" id="searchForm">

	<div id="studentMainInfoDiv">

		<div id="personalInfoMain">
		<div id="new" class="panel panel-primary">
		 <div id="new2" class="panel-heading">
			<b>Personal Information</b>
			 </div>
			 <div id="new3" class="panel-footer">
			<table>
				<tr>
					<td><label ></label></td>
					<td></td>
				</tr>
				<tr>
					<td><label >Last Name</label></td>
					<td><input type="text" name="employee.lastName"
						value="${employee.lastName}" id="lastname"/></td>

					<td><label >First Name</label></td>
					<td><input type="text" name="employee.firstName"
						value="${employee.firstName}" id="firstname"/></td>
				</tr>

				<tr>
					<td><label >Email</label></td>
					<td><input type="text" name="employee.email"
						value="${employee.email}" id="email"/>
					<td><label >UFID</label></td>
					<td><input type="text" name="employee.ufid"
						value="${employee.ufid}" id="ufid" />
				</tr>
			</table>
			</div>
			</div>

		</div>
		 <div id="new" class="panel panel-primary">
 			<div id="new2" class="panel-heading">
		<b>Use combination of fields to search</b>
		 </div>
		<div id="new3" class="panel-footer">
		<table>
			<tr>
				<td><label >Semester</label></td>
				<td><select name="employee.Semester" id="semesterId">
						<option value="" selected="selected">--Select--</option>
						<option value="Fall">Fall</option>
						<option value="Spring">Spring</option>
						<option value="Summer">Summer</option>
				</select></td>
			</tr>
			<tr>
				<td><label >Year</label></td>
				<td><select name="employee.year" id="year">
						<option value="0" selected="selected">--Select--</option>
						<c:forEach items="${yearList}" var="yearValue">
							<option value="${yearValue}">${yearValue}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td><label >Supervisor</label></td>
				<td><select name="employee.supervisor.id" id="supervisorId"><option
							value="0" selected="selected">--Select--</option>
						<c:forEach items="${supervisors}" var="result">
							<option value="${result.id}">${result.name}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td><label >Title</label></td>
				<td><select name="employee.title" id="title">
						<option value="" selected="selected">--Select--</option>
						<option value="OPS">OPS</option>
						<option value="TA">TA</option>
						<option value="RA">RA</option>
				</select></td>
			</tr>
		</table>
  		</div>
  		</div>

		<input type="button" name="searchButton" value="Submit" onclick="submitSearchForm()" class="btn"></input>

	</div>
</form>


