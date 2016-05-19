<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<s:head theme="xhtml" />
<c:if test="${message != null}">
	<h3 style="color: BLUE">${message}</h3>
</c:if>

<form action="addUser" method="post" id="addUserForm">
		<div id="new" class="panel panel-primary">
		<div id="new2" class="panel-heading">
		<b>Add User</b>
 		</div>
		<div id="new3" class="panel-footer">
		<table>
			<tr>
				<td><label >Email</label></td>
				<td><input type="text" name="user.email" value="${user.email}" id="email" /></td>
			</tr>
			<tr>
				<td><label >Password</label></td>
				<td><input type="text" name="user.password" value="${user.password}" 
						id="password" /></td>
			</tr>
			<tr>
				<td><label >First Name</label></td>
				<td><input type="text" name="user.firstName"
					value="${user.firstName}" id="firstName" /></td>
			</tr>
			<tr>
				<td><label >Last Name</label></td>
				<td><input type="text" name="user.lastName"
					value="${user.lastName}" id="lastName" /></td>
			</tr>
			
		</table>
		<br>
		<input
			style="margin-left: 35%; margin-right: 65%; position: relative;"
			type="submit" value="Submit" name="submit" id="submitButton" class="btn" />
			</div>
			</div>
			
			

</form>