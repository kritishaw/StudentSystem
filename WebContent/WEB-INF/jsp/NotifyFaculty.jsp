<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<s:head theme="xhtml" />
<c:if test="${message != null}">
	<h3 style="color: BLUE">${message}</h3>
</c:if>
 <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
  
<script>
  $(function() {
    $( "#datepicker" ).datepicker();
  });
  
  
  
  function validateAndSubmitForm()
	{
	  var val = document.getElementById("datepicker").value;
	
		if(val == "" || val == null)
				{
									alert("Please Enter the Action date");
									return false;
										}
							
		
	}
  </script>

<form action="getEmployees" method="post" id="studentInfoForm">
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
	</select><br> <br> <input
		style="margin-left: 35%; margin-right: 65%; position: relative;"
		type="submit" value="Get Students" name="getStudent" id="getStudent" class="btn"/>
	</div>
	</div>	
</form>

<br>


<form action="Notify" method="post" id="studentInfoForm" onsubmit="return validateAndSubmitForm()">
	<c:if test="${searchResult !=null}">
		<input id="supervisorId" name="supervisor.id" value="${supervisor.id }" type="hidden" />
		 <div id="new" class="panel panel-primary">
		 <div id="new2" class="panel-heading">
		 <b>Email Details</b>
 		</div>
		<div id="new3" class="panel-footer">
		<table>
			<tr>
				<td><label >Email</label></td>
				<td><input type="text" value="${supervisor.email}"
					readonly="readonly" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td><label >Secretary Email</label></td>
				<td><input type="text" name="supervisor.secretaryEmail"
					value="${supervisor.secretaryEmail}" id="secretaryEmail" /></td>
			</tr>
		</table>
		</div>
		</div>
		<br>
		<div id="new" class="panel panel-primary">
		 <div id="new2" class="panel-heading">
		 <b>Current Semester Students</b>
 		</div>
 		<div id="new3" class="panel-footer">
		<table class=" table-bordered table-striped">
			<tr>
							
			
				<th>UFID</th>
				
				<th>Last Name</th>
				<th>First Name</th>
				<th>Semester</th>
				<th>Year</th>
				<th>Title</th>
				<th>Status</th>
				
				<th>Eval Letter</th>
								<th>Notify</th>
								<th>View Details</th>
								<th>Application</th>
				
			</tr>
			<c:forEach items="${searchResult}" var="result">
				<tr>
				
					<td>${result.ufid}</td>
				
					<td>${result.lastName}</td>
						<td>${result.firstName}</td>
					<td>${result.semester}</td>
					<td>${result.year}</td>
					<td>${result.title}</td>
					<td>${result.status}</td>
					
		
		<c:choose> 
  <c:when test="${result.EVAL_Generated == '1'}">
    				<td><a href="/${initParam.appName}/notify/viewEval?employeeId=${result.id}&supervisorId=${supervisor.id}">View</a></td>  
  </c:when>
  <c:otherwise>
        				<td>NA</td>    

  </c:otherwise>
</c:choose>
		
					<td><a href="/${initParam.appName}/notify/resetFlag?employeeId=${result.id}&supervisorId=${supervisor.id}">Notify</a></td>
		 <td><a href="/${initParam.appName}/details/detailsView?employee.id=${result.id}">View Details</a></td>
		 <td>${result.open}</td>
				</tr>
			</c:forEach>
			<tr>
			
			
			</tr>
		</table>
		Action Date: <input type="text" name= "ActionDate" id="datepicker">
		</div>
		</div>
		
		
		<input  type="submit" value="Notify"  id="notify"  class="btn"/>&nbsp;	
		<input type="button" onclick = "generateEvaluationLetters()" value="Generate Evaluation" name="generateELButton" id="generateELId"  class="btn" />&nbsp;
		<input type="button" onclick = "sendEvaluationLetters()" value="Send Evaluation Letters" name="sendELButton" id="sendELId"  class="btn"/>	
		
			
	</c:if>
</form>

<form action="Notify" method="post" id="studentInfoForm1" >
	<c:if test="${searchResult1 !=null}">
		<input id="supervisorId" name="supervisor.id" value="${supervisor.id }" type="hidden" />
		 
		<br>
		<div id="new" class="panel panel-primary">
		 <div id="new2" class="panel-heading">
		 <b>Next Semester Students</b>
 		</div>
 		<div id="new3" class="panel-footer">
		<table  class=" table-bordered table-striped">
			<tr>
							
			
				<th>UFID</th>
				
				<th>Last Name</th>
				<th>First Name</th>
				<th>Semester</th>
				<th>Year</th>
				<th>Title</th>
				<th>Status</th>
				<th>LOA</th>
					<th>Notify</th>
				<th>Details</th>
				<th>Application</th>
				
							
				
			</tr>
			<c:forEach items="${searchResult1}" var="result1">
				<tr>
				
					<td>${result1.ufid}</td>
					
					<td>${result1.lastName}</td>
					<td>${result1.firstName}</td>
					<td>${result1.semester}</td>
					<td>${result1.year}</td>
					<td>${result1.title}</td>
					<td>${result1.status}</td>
					
<c:choose> 
  <c:when test="${result1.LOA_Generated == '1'}">
    				<td><a href="/${initParam.appName}/notify/viewLOA?employeeId=${result1.id}&supervisorId=${supervisor.id}">View</a></td>    
  </c:when>
  <c:otherwise>
        				<td>NA</td>    

  </c:otherwise>
</c:choose>
		
					<td><a href="/${initParam.appName}/notify/resetFlag?employeeId=${result1.id}&supervisorId=${supervisor.id}">Notify</a></td>
			 <td><a href="/${initParam.appName}/details/detailsView?employee.id=${result1.id}">View Details</a></td>
			  <td>${result1.open}</td>
				</tr>
			</c:forEach>
			<tr>
			
			</tr>
		</table>
		</div>
		</div>
		
	<input type="button" onclick="generateLOA()" value="Generate LOA" name="generateLOAbutton" id="generateLOAId"  class="btn"/>&nbsp;
			
		<input type="button" onclick = "sendLOA()" value="Send LOA" name="sendLOAButton" id="sendLOAId"  class="btn"/>&nbsp;
			
	</c:if>
</form>

<script>


function generateLOA()
{
	location.href='/${initParam.appName}/notify/generateLOA?supervisorId=' + document.getElementById("supervisorId").value;
}
function viewLOA()
{
	location.href='/${initParam.appName}/notify/viewLOA?supervisorId=' + document.getElementById("supervisorId").value;
}

function sendLOA()
{
	
	location.href='/${initParam.appName}/notify/sendLOA?supervisorId=' + document.getElementById("supervisorId").value;
			//+ '&title=' + document.getElementById("titleId").value;
	
}

function generateEvaluationLetters()
{
	location.href='/${initParam.appName}/notify/generateEL?supervisorId=' + document.getElementById("supervisorId").value;
}

function sendEvaluationLetters()
{
	location.href='/${initParam.appName}/notify/sendEL?supervisorId=' + document.getElementById("supervisorId").value ;
			//+ '&title=' + document.getElementById("titleId").value;
}
</script>