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
				<td><select name="Semester1" id="semesterId">
						<option value="" selected="selected">--Select--</option>
						<option value="Fall">Fall</option>
						<option value="Spring">Spring</option>
						<option value="Summer">Summer</option>
				</select></td>
			</tr>
			<tr>
				<td><label >Year</label></td>
				<td><select name="year1" id="year">
						<option value="0" selected="selected">--Select--</option>
						<c:forEach items="${yearList}" var="yearValue">
							<option value="${yearValue}">${yearValue}</option>
						</c:forEach>
				</select></td>
			</tr>
			
		</table>
  		</div>
  		</div>

		<input type="Submit" name="search" value="Submit" ></input>

	</div>
</form>


