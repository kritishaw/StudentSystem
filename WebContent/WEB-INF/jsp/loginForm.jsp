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




<div class="login">
      <div class="login-screen">
			<div class="app-title">
				<h1>Student Employment Management Login</h1>
			</div>
			
					<form action="loginSubmit" id="loginForm" method="post">
					<div class="login-form">
							<div class="control-group">
							<input type="text" class="login-field"  name="email"  >
							<label class="login-field-icon fui-user" for="login-name"></label>
							</div>
			
							<div class="control-group">
							<input type="password" class="login-field"  name="password" >
							<label class="login-field-icon fui-lock" for="login-pass"></label>
							</div>
							
							<div class="control-group">
							<input class="btn btn-primary btn-large btn-block" type="submit"  value="Submit"  >
							</div>
			
			       </div>
				  </form>
	</div>
 </div>
</body>
</html>