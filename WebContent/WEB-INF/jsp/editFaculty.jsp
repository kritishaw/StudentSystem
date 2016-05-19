<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<s:head theme="xhtml" />

<script type="text/javascript">
	
	function selectedValue(){  

	    var status =<%=request.getParameter("supervisor.active")%>;  
	    if(value !=null)  
	        {

	        document.f1.slvalue.selectedIndex=status ;          

	        }    

	} 
	</script>


<c:if test="${message != null}">
	<h3 style="color: BLUE">${message}</h3>
</c:if>
<br>
<br>
<form action="getFaculty" method="post" id="getFacultyForm">
    <div id="new" class="panel panel-primary">
	<div id="new2" class="panel-heading">
	 <b>Choose Faculty</b>
 	</div>
	<div id="new3" class="panel-footer">
	
	<select
		style="margin-left: 35%; margin-right: 65%; position: relative;"
		name="supervisor.id" id="facultyName">
		<c:forEach items="${supervisors}" var="faculty">
			<option value="${faculty.id }" ${faculty.name == supervisor.name ? 'selected="selected"' : ''}>${faculty.name}</option>
		</c:forEach>
	</select><br> <br> 
	<input	style="margin-left: 35%; margin-right: 65%; position: relative;"
		type="submit" value="Get Faculty Info" name="getFacultyInfo" id="getFacultyId" class="btn"/>
	</div>
	</div>
</form>

<br>


<form action="editFaculty" method="post" id="editFacultyForm">
	<c:if test="${supervisor != null}">
		<input name="supervisor.id" value="${supervisor.id}" type="hidden" />
		<input name="supervisor.active" value="${supervisor.active}" type="hidden" />
		
		<div id="new" class="panel panel-primary">
		<div id="new2" class="panel-heading">
		<b>Details</b>
 		</div>
 		
 		<div id="new3" class="panel-footer">
		<table>
			<tr>
				<td><label >Email</label></td>
				<td><input type="text" name="supervisor.email" value="${supervisor.email}" /></td>
			</tr>
			<tr>
				<td><label >First Name</label></td>
				<td><input type="text" name="firstName"
					value="${firstName}" id="firstName" /></td>
			</tr>
			<tr>
				<td><label >Last Name</label></td>
				<td><input type="text" name="lastName"
					value="${lastName}" id="lastName" /></td>
			</tr>
			<tr>
				<td><label >Secretary Email</label></td>
				<td><input type="text" name="supervisor.secretaryEmail" value="${supervisor.secretaryEmail}" /></td>
			</tr>
			<tr>
				<td><label class="label">Status</label></td>
				<td><select name ="slvalue"><option value="1"  ${supervisor.active == "1"? 'selected="selected"' : ''}>Active</option>
  <option value="0"  ${supervisor.active == "0"? 'selected="selected"' : ''}>Inactive</option>
 
</select></td>
			</tr>
			
		</table>
		<br>
		<input
			style="margin-left: 35%; margin-right: 65%; position: relative;"
			type="submit" value="Submit" name="submit" id="submitButton" class="btn"/>
			</div>
			</div>
	</c:if>
	
</form>