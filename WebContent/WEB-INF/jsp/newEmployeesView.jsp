<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript" src="/${initParam.appName}/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="/${initParam.appName}/assets/js/jquery.colorbox.js"></script>
<script type="text/javascript" src="/${initParam.appName}/assets/js/selectbox.js"></script>
<link rel="stylesheet" type="text/css"  href="/${initParam.appName}/assets/css/colorbox.css" />

<s:head theme="xhtml" />


 <div id="new" class="panel panel-primary">
 <div id="new2" class="panel-heading">
 <b>Appointment Dashboard</b>
 </div>
<div id="new3" class="panel-footer">
<table  class=" table-bordered table-striped">
<tr>
 <th>EID</th>
 <th>Last Name</th>
  <th>First Name</th>
  
  <th>UFID</th>
  <th>Semester</th>
  <th>Year</th>
  <th>Title</th>
  <th>Semester End Status</th>
    <th>Supervisor</th>
  
  <th>Details</th>
</tr>
<c:forEach items="${searchResult}" var="result">
 <tr> 
    <td>${result.id}</td>
     <td>${result.lastName}</td>
     <td>${result.firstName}</td>
    
     <td>${result.ufid}</td>
     <td>${result.semester}</td>
     <td>${result.year}</td>
     <td>${result.title}</td>
     <td>${result.status}</td>
     <td>${result.supervisor.name}</td>
     <td><a href="/${initParam.appName}/details/detailsView?employee.id=${result.id}">View Details</a></td>
 </tr>
</c:forEach>
</table>
</div>
</div>