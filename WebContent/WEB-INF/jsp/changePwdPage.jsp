<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<s:head theme="xhtml" />
<script type="text/javascript" src="/${initParam.appName}/assets/js/common.js"></script>
<script>

function submitPasswordForm()
{
	
	var currentPasswordEmptyCheck = checkEmpty(document.getElementById("currentPassword"));
	var newPasswordEmptyCheck = checkEmpty(document.getElementById("newPassword"));
	var confirmPasswordEmptyCheck = checkEmpty(document.getElementById("confirmPassword"));
	
	if(currentPasswordEmptyCheck==false &&  newPasswordEmptyCheck==false && confirmPasswordEmptyCheck==false)
		{
			if(document.getElementById("newPassword").nodeValue == document.getElementById("confirmPassword").nodeValue)
				{
					document.getElementById("passwordForm").submit();
				}
			else
				{
					document.getElementById("errorHeading").innerHTML="New password and Confirm Password doesnot match";
				}
		}
		else
		{
			document.getElementById("errorHeading").innerHTML="Password cannot be empty";
		}
}
</script>

<h3 id="errorHeading" style="color: RED"></h3>

<c:if test="${message != null}">
  <h3 style="color:Red">${message}</h3>
</c:if>

<br>
<s:form action="changePassword" method="post" id="passwordForm">
        <div id="new" class="panel panel-primary">
		<div id="new2" class="panel-heading">
		<b>Change Password</b>
 		</div>
		
		<s:password name="currentPassword" label="Current Password" id="currentPassword" />
		<s:password name="newPassword" label="New Password" id="newPassword" />
		<s:password name="confirmPassword" label="Confirm Password" id="confirmPassword" />
 		<s:submit  align="center"  type="button" name="password" value="Submit" onclick="submitPasswordForm()" class="btn"></s:submit>
 		</div>
 		
 
 
 
</s:form>