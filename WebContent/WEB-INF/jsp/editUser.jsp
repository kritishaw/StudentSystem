<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
function showDiv2() {
	   document.getElementById('welcomeDiv').style.display = "block";
	}
</script>
<s:head theme="xhtml" />
<c:if test="${message != null}">
	<h3 style="color: BLUE">${message}</h3>
</c:if>
<br>
<br>
<form action="getUser" method="post" id="getUserForm">
<br>
		<div id="new" class="panel panel-primary">
		<div id="new2" class="panel-heading">
		<b>Choose User</b>
 		</div>
		<div id="new3" class="panel-footer">
	
	<select
		style="margin-left: 35%; margin-right: 65%; position: relative;"
		name="user.email" id="userName">
		<c:forEach items="${users}" var="userew">
			<option value="${userew.email}" ${user.email == userew.email ? 'selected="selected"' : ''}> ${userew.firstName} ${userew.lastName}</option>
		</c:forEach>
	</select><br> <br> 
	<input	style="margin-left: 35%; margin-right: 65%; position: relative;"
		type="submit" value="Get User Info" name="getUserInfo" id="getUserId" class="btn"/>
		</div>
		</div>
		
</form>

<br>


<form action="editUser" method="post" id="editUserForm">
	<c:if test="${user != null}">
<input name="user.id" value="${user.id}" type="hidden" />
 		<input name="user.active" value="${user.active}" type="hidden" />
 		<div id="new" class="panel panel-primary">
		<div id="new2" class="panel-heading">
		<b>Details</b>
 		</div>
		<div id="new3" class="panel-footer">
		
		<table>
			<tr>
				<td><label >Email</label></td>
				<td><input type="text" name="user.email" value="${user.email}" /></td>
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
			type="submit" value="Submit" name="submit" id="submitButton" class="btn"/>
			<input
			style="margin-left: 35%; margin-right: 65%; position: relative;"
			type="button" value="Reset Password" name="Reset" id="Reset" class="btn" onclick="showDiv2()" />
					<br>
			</div>
			</div>
			
	</c:if>
</form>

<div id = "welcomeDiv" style= "display:none;">
<form action="resetPassword" method="post" id="resetPassword">
	
<c:if test="${user != null}">
<input name="user.id" value="${user.id}" type="hidden" />
<input name="user.firstName" value="${user.firstName}" type="hidden" />
<input name="user.lastName" value="${user.lastName}" type="hidden" />
<input name="user.email" value="${user.email}" type="hidden" />
 		<div id="new" class="panel panel-primary">
		<div id="new2" class="panel-heading">
		<b>Reset Password</b>
 		</div>
		<div id="new3" class="panel-footer">
		
		<table>
			
			<tr>
				<td><label >Password</label></td>
				<td><input type="text" name="user.password" value="${user.password}" 
						id="password" /></td>
			</tr>
			
		</table>
		<br>
		<input
			style="margin-left: 35%; margin-right: 65%; position: relative;"
			type="submit" value="Submit" name="Reset" id="Reset" class="btn"/>
					<br>

			</div>
			</div>
			
	</c:if>
</form>
 </div> 