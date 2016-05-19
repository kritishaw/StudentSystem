<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Employment Management</title>

<!-- Style definitions -->
<link rel="stylesheet" type="text/css"  href="/se-servlet/assets/css/loginstyle.css" />


</head>
<body>
<% String id = request.getParameter("facultyId"); 

String login = request.getParameter("loginValue");


session.setAttribute("login", true);
session.setAttribute("supervisorId", id);
request.setAttribute("supervisorId", id);

%>
<jsp:useBean id="mj" class="edu.ufl.ece.studentemployment.actions.FacultyLoginSupport" scope="request">







<div class="login">
      <div class="login-screen">
			<div class="app-title">
				<h1>Student Employment Management Login</h1>
			</div>
			
					<form action="loginSubmit" id="loginForm" method="post">
					<div class="login-form">
							
							
							<div class="control-group">
							
							<input class="btn btn-primary btn-large btn-block" type="Submit" value="Submit" name="Submit" <%-- onclick ="<% mj.submit(id); %> --%>"  >
							
							
							</div>
			
			       </div>
				  </form>
	</div>
 </div>
 </jsp:useBean>
</body>
</html>


<%-- <%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Employment Management</title>

<!-- Style definitions -->




</head>
<body>



<div class="login">
      <div class="login-screen">
			<div class="app-title">
				<h1>Student Employment Management Login</h1>
			</div>
			
					<form action="edu.ufl.ece.studentemployment.actions.LoginActionSupport/submit" id="loginForm" method="post">
					<div >
							
							
							<div class="control-group">
							   <input type="button" name="Submit" id="Submit"  onclick ="<% mj.submit(id); %>"    >
							</div>
			
			       </div>
				  </form>
	</div>
 </div>
 
</body>
</html> --%>