<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<s:head theme="xhtml" />
<script>


	

function detailsCheck()
{
	
	alert("dfcd");
	var yearEmptyCheck = checkEmpty(document.getElementById("year"));
	var semesterEmptyCheck = checkEmpty(document.getElementById("semesterId"));
	
	if( yearEmptyCheck==true)
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

<c:if test="${facultyMessage != null}">
	<h3 style="color: BLUE">${facultyMessage}</h3>
</c:if>
<br>
 <div id="new" class="panel panel-primary">
 <div id="new2" class="panel-heading">
<b>Current Semester Student Appointments(Click on the UF Id to view student history)</b>
</div>
<div id="new3" class="panel-footer">
<table class=" table-bordered table-striped">
	<tr>
		<th>UFID</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>Title</th>
		<th>Semester End Status</th>
		<!-- <th>Semester</th>
		<th>Year</th>
		<th>Start Date</th>
		<th>End Date</th>
		<th>Status</th> -->
	</tr>
	<c:forEach items="${searchResult}" var="result">
		<tr>
			<td><a
				href="/${initParam.appName}/faculty/employeeHistory?employee.ufid=${result.ufid}">${result.ufid}</a></td>
			<td>${result.firstName}</td>
			<td>${result.lastName}</td>
						<td>${result.title}</td>
						<td>${result.status}</td>
			
<%-- 			<td>${result.semester}</td>
			<td>${result.year}</td>
			<td>${result.fromDate}</td>
			<td>${result.toDate}</td>
			<td>${result.status}</td> --%>
		</tr>
	</c:forEach>
</table>
</div>
</div>
<br>
 <div id="new" class="panel panel-primary">
 <div id="new2" class="panel-heading">
<b>Next Semester Student Appointments (Click on the UF Id to view student details)</b>
</div>
<div id="new3" class="panel-footer">

<table class=" table-bordered table-striped">
	<tr>
		<th>UFID</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>Title</th>
		<!-- <th>Semester</th>
		<th>Year</th>
		<th>Start Date</th>
		<th>End Date</th>-->
		<th>Record</th> 
	</tr>
	
	<c:forEach items="${searchResult1}" var="result1">
		<tr>
			<td><a href="/${initParam.appName}/faculty/facultyDetails?employee.id=${result1.id}">${result1.ufid}</a></td>
			<td>${result1.firstName}</td>
			<td>${result1.lastName}</td>
						<td>${result1.title}</td>
			
<%-- 			<td>${result.semester}</td>
			<td>${result.year}</td>
			<td>${result.fromDate}</td>
			<td>${result.toDate}</td>--%>
			<td>${result1.open}</td> 
		</tr>
	</c:forEach>
</table>
</div>
</div>
<%--  <h4 style="color: GREEN">Enter one or more fields to search</h4> --%>


<form action="showResultFaculty" method="post" >

	<div id="studentMainInfoDiv">

		
		 <div id="new" class="panel panel-primary">
 			<div id="new2" class="panel-heading">
		<b>Please enter Semester and Year to Search for Previously Employed Students</b>
		 </div>
		<div id="new3" class="panel-footer">
		<table>
			<tr>
				<td><label >Semester</label></td>
				<td><select name="employee.semester" id="semesterId">
						<option value="" selected="selected">--Select--</option>
						<option value="Fall">Fall</option>
						<option value="Spring">Spring</option>
						<option value="Summer">Summer</option>
				</select>
		
			
				<td><label >Year</label></td>
				<td><select name="employee.year" id="year">
						<option value="0" selected="selected">--Select--</option>
						<c:forEach items="${yearList}" var="yearValue">
							<option value="${yearValue}">${yearValue}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
		</table>
		<input type="Submit" name="search" value="Submit" class="btn"></input>
  		</div>
  		</div>

		

	</div>


	</form>