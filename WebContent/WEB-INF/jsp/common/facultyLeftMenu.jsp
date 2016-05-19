
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>




<div  id="sidebar">
          <div class="list-group">
   <a href="#" class="list-group-item active">Faculty Menu</a>
   <a href="/${initParam.appName}/faculty/homeView" class="list-group-item">Home</a>
   
   <a href="/${initParam.appName}/faculty/newHire" class="list-group-item">New Hire</a>
   <c:if test="${role=='Admin_Faculty' }">
	<a  href="/${initParam.appName}/search/newEmployeesView" class="list-group-item">Switch to Admin View</a>
   </c:if>
   <a  href="/${initParam.appName}/login/signOut" class="list-group-item">Sign out</a>
    <a href="/${initParam.appName}/login/facultyhelp" class="list-group-item">Help</a>
   </div>
   </div>
   



   
