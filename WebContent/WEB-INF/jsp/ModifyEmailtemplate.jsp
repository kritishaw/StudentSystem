<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<s:head theme="xhtml" />

<script type="text/javascript" src="/${initParam.appName}/assets/js/common.js"></script>
<script>
<script type="text/javascript">

function selectedValue(){  

    var status =<%=request.getParameter("template.templateName")%>;
    alert(status);
    if(value !=null)  
        {

        document.f1.template.templateName.selectedIndex=status ;          

        }    

} 
function updateTemplateForm()
{
</script>

	
	 

<br>

<h3 id="errorHeading" style="color: RED"></h3>
<form action="getTemplate" method="post" id="EmailTemplates">
	<div id="new" class="panel panel-primary">
	<div id="new2" class="panel-heading">
	<b>Choose Template</b>
	</div>
 	<div id="new3" class="panel-footer">
	<select
		style="margin-left: 35%; margin-right: 65%; position: relative;"
		name="template.templateName" id="templatename">
		<c:forEach items="${templateNames}" var="templatename">
			<%-- <option value="${templatename }">${templatename}</option> --%>
						<option value="${templatename }" ${templatename == template.templateName ? 'selected="selected"' : ''}>${templatename}</option>
			
		</c:forEach>
	</select><br> <br> <input
		style="margin-left: 35%; margin-right: 65%; position: relative;"
		type="submit" value="Get Details" name="getDetails" id="getDetails" class="btn"/>
	</div>
	</div>
</form>

<br>
<br>


<form action="updateTemplate" method="post" id="studentInfoForm">
	<c:if test="${template !=null}">
		<input name="template.id" value="${template.id }" type="hidden" />
		<div id="new" class="panel panel-primary">
		<div id="new2" class="panel-heading">
		<b>Student Info Form</b>
		</div>
 		<div id="new3" class="panel-footer">
		<table>
			<tr>
				<td><label >From</label></td>
				<td><input type="text" name="template.fromAddress"
					value="${template.fromAddress}" id="fromAddress" /></td>
			</tr>
			<tr>
				<td><label >Subject</label></td>
				<td><input type="text" name="template.subject"
					value="${template.subject}" id="subject" /></td>
			</tr>

		</table>
		
	
		<br>
		<div id="sample">
			     <script type="text/javascript" src="http://js.nicedit.com/nicEdit-latest.js"></script> <script type="text/javascript">
				
       				 bkLib.onDomLoaded(function() { nicEditors.allTextAreas() });
  					
  				</script>
				<textarea rows="20" cols="150" name="template.template">${template.template}</textarea>
			
		</div>
		<br>
		<input
			style="margin-left: 40%; margin-right: 60%; position: relative;"
			type="submit" value="Update" name="updateButton" id="update" onclick="updateTemplateForm()" class="btn"/>
	</c:if>
		</div>
		</div>
</form>


