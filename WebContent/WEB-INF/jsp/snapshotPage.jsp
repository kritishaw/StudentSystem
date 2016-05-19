<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<s:head theme="xhtml" />

<script type="text/javascript" src="/${initParam.appName}/assets/js/common.js"></script>

<c:if test="${message != null}">
	<h3 style="color: RED">${message}</h3>
</c:if>
<h3 id="errorHeading" style="color: RED"></h3>
<br>

<form action="exportToExcel" method="post" id="snapshotForm">

	<div id="studentMainInfoDiv">
	<div id="new" class="panel panel-primary">
	<div id="new2" class="panel-heading">
	 <b>Snapshot Export</b>
	 </div>
	 <div id="new3" class="panel-footer">
		<table>
			<tr>
				<td><label >Semester</label></td>
				<td><select name="employee.Semester" id="semesterId">
						<option value="Fall">Fall</option>
						<option value="Spring">Spring</option>
						<option value="Summer">Summer</option>
				</select></td>
			</tr>
			<tr>
				<td><label >Year</label></td>
				<td><select name="employee.year" id="year">
						<c:forEach items="${yearList}" var="yearValue">
							<option value="${yearValue}">${yearValue}</option>
						</c:forEach>
				</select></td>
			</tr>

		</table>
		</div>
		</div>
		
<%-- 		<c:choose> --%>
<%-- <c:when test="${resultCount !=null}"> --%>
<!-- 	<br/> -->
<%-- 	<a href='exportToExcel'><img src='/${initParam.appName}/assets/img/exportToExcel.jpg' title='Export to MS Excel' alt='Export to MS Excel'/></a> --%>
<!-- 	<br/><br/>   -->
<%-- 	<b> Search Query -> ${searchQuery} </b> --%>
<!-- 	<br/><br/> -->
<%-- 	<b>${resultCount}</b>	   --%>
<%-- </c:when> --%>

<%-- <c:otherwise> --%>
<%-- <c:choose> --%>
<%-- 	<c:when test="${empty appList}"> --%>
<!-- 		<h3>No data found</h3> -->
<!-- 		<br/><br/> -->
<%-- 		Search Query -> ${searchQuery} --%>

<%-- 	</c:when> --%>
<%-- <c:otherwise> --%>
<!-- <br/> -->
<%-- <a href='exportToExcel'><img src='/${initParam.appName}/assets/img/exportToExcel.jpg' title='Export to MS Excel' alt='Export to MS Excel'/></a> --%>
<!-- <br/><br/> -->
<%-- Search Query -> ${searchQuery} --%>
<!-- <br/><br/> -->
<%-- </c:otherwise> --%>
<%-- </c:choose> --%>
<%-- </c:otherwise> --%>
<%-- </c:choose>		 --%>
		
		<%-- <input type="file" id="upload" name="upload" style="visibility: hidden; width: 1px; height: 1px" multiple value="${filePath}"/>
		<input type="button" onclick="document.getElementById('upload').click(); return false" name="Select File" value="Select File" />
		<!-- <br> <input type="button" name="exportButton" value="Submit" onclick="validateAndSubmitForm()"></input> --> --%>
		
		
		 <input type="submit" name="exportButton" value="Export to Excel" class="btn"></input> 

	</div>
</form>
