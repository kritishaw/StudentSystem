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
<b>Search Result</b>
</div>
<div id="new3" class="panel-footer">
<table border="1">
	<tr>
		<th>First Name</th>
  <th>Last Name</th>
  <th>Semester</th>
  <th>Year</th>
  <th>Title</th>
  <th>Status</th>
  <th>Details</th>
	</tr>
	<c:forEach items="${searchResult}" var="result">
		<tr>
			  <td>${result.firstName}</td>
     <td>${result.lastName}</td>
     <td>${result.semester}</td>
     <td>${result.year}</td>
     <td>${result.title}</td>
     <td>${result.status}</td>
     <td><a href="/${initParam.appName}/details/detailsView?employee.id=${result.id}">View Details</a></td>
		</tr>
	</c:forEach>
</table>
</div>
</div>