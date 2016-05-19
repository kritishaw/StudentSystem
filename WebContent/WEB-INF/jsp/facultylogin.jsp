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


Hello <b><% String id = request.getParameter("facultyId"); 

String login = request.getParameter("loginValue");


session.setAttribute("login", true);
session.setAttribute("supervisorId", id);


%></b>

