<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<s:head theme="xhtml" />
<c:if test="${message != null}">
	<h3 style="color: BLUE">${message}</h3>
</c:if>
<br>

<form action="addFaculty" method="post" id="addFacultyForm">
		<div id="new" class="panel panel-primary">
		<div id="new2" class="panel-heading">
		<b>Add Faculty Form</b>
 		</div>
		<div id="new3" class="panel-footer">
		<table>
			<tr>
				<td><label >Email</label></td>
				<td><input type="text" name="supervisor.email" value="${faculty.email}" id="email" /></td>
			</tr>
			<tr>
				<td><label >First Name</label></td>
				<td><input type="text" name="supervisor.firstName"
					value="${faculty.firstName}" id="firstName" /></td>
			</tr>
			<tr>
				<td><label >Last Name</label></td>
				<td><input type="text" name="supervisor.lastName"
					value="${faculty.lastName}" id="lastName" /></td>
			</tr>
			<tr>
				<td><label >Secretary Email</label></td>
				<td><input type="text" name="supervisor.secretaryEmail" value="${faculty.secretaryEmail}" 
						id="secretaryEmail" /></td>
			</tr>
		</table>
		</div>
		</div>
		<br>
		<input
			style="margin-left: 35%; margin-right: 65%; position: relative;"
			type="submit" value="Submit" name="submit" id="submitButton" class="btn" />

</form>