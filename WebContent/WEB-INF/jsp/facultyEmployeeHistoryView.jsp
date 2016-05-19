<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<s:head theme="xhtml" />
<script type="text/javascript" src="/${initParam.appName}/assets/js/scw.js"></script>
<c:if test="${facultyMessage != null}">
	<h3 style="color: BLUE">${facultyMessage}</h3>
</c:if>
<br>
 <div id="new" class="panel panel-primary">
<div id="new2" class="panel-heading">
<b>Student Employment History (Click on the latest employment to reappoint\terminate the student)</b>
</div>
<div id="new3" class="panel-footer">

<table border="1" class=" table-bordered table-striped" >
	<tr>
		
		<th>Name</th>
		
	<!-- 	<th>UFID</th>-->
		<th>Semester</th>
		<th>Year</th>
		<th>Start Date</th>
		<th>End Date</th>
		<th>Title</th>
		<th>Sem End Status</th>
		<th>Details</th>
	</tr>
	<c:forEach items="${searchResult}" var="result">
		<tr>
			
			<td>${result.firstName} ${result.lastName}</td>
			
 		<%-- 	<td>${result.ufid}</td>--%>
 			<td>${result.semester}</td>
			<td>${result.year}</td>
			<td>${result.fromDate}</td>
			<td>${result.toDate}</td>
			<td>${result.title}</td>
			<td>${result.status}</td>
			<td><a href="/${initParam.appName}/faculty/facultyDetails?employee.id=${result.id}">View Details</a></td>
		</tr>
	</c:forEach>
</table>
</div>
</div>

