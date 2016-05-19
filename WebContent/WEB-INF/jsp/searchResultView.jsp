<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript" src="/${initParam.appName}/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="/${initParam.appName}/assets/js/jquery.colorbox.js"></script>
<script type="text/javascript" src="/${initParam.appName}/assets/js/selectbox.js"></script>
<link rel="stylesheet" type="text/css"  href="/${initParam.appName}/assets/css/colorbox.css" />

<s:head theme="xhtml" />


<table class=" table-bordered table-striped">
<tr>
  <th>UFID</th>
  <th>First Name</th>
  <th>Last Name</th>
<!--   <th>Semester</th>
  <th>Year</th> -->
</tr>
<c:forEach items="${searchResult}" var="result">
 <tr> 
    <td><a href="/${initParam.appName}/search/employeeResult?employee.ufid=${result.ufid}">${result.ufid}</a></td>
     <td>${result.firstName}</td>
     <td>${result.lastName}</td>
<%--      <td>${result.semester}</td>
     <td>${result.year}</td> --%>
 </tr>
</c:forEach>
</table>


