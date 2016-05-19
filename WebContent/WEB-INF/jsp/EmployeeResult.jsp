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

  <th>First Name</th>
  <th>Last Name</th>
  <th>Semester</th>
  <th>Year</th>
  <th>Title</th>
  <th>UFID</th>
  </tr>
<c:forEach items="${searchResult}" var="result">
 <tr> 
    
     <td>${result.firstName}</td>
     <td>${result.lastName}</td>
     <td>${result.semester}</td>
     <td>${result.year}</td>
     <td>${result.title}</td>
     <td><a href="/${initParam.appName}/details/detailsView?employee.id=${result.id}">View Details</a></td>
 </tr>
</c:forEach>
</table>


