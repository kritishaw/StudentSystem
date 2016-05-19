<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


   <div  id="sidebar">
          <div class="list-group">
          	<a href="#" class="list-group-item active">Menu</a>
          	<a href="/${initParam.appName}/search/newEmployeesView" class="list-group-item">Home</a>
            <a href="/${initParam.appName}/search/" class="list-group-item">Search</a>
            <a href="/${initParam.appName}/notify/notifyView" class="list-group-item">Notify Faculty</a>
            <a href="/${initParam.appName}/modifytemplate/modifyTemplateView"  class="list-group-item">Edit Email Template</a>
             <c:if test="${role=='Admin_Faculty' }">
			 <a href="/${initParam.appName}/faculty/homeView" class="list-group-item">Switch to Faculty View</a>
  			 </c:if>
            <a href="#" onclick="showDiv('facultyManagement');"  class="list-group-item">Faculty Management</a>
            <div id="facultyManagement" class="leftSubMenu">
				<a href="/${initParam.appName}/faculty/getAddFacultyView" class="list-group-item">Add Faculty</a>
				<a href="/${initParam.appName}/faculty/getEditFacultyView" class="list-group-item">Edit Faculty</a>
			</div>
			 <a href="#" onclick="showDiv('accountManagement');"  class="list-group-item">Account Management</a>
            <div id="accountManagement" class="leftSubMenu">
				<a href="/${initParam.appName}/account/getAddUserView" class="list-group-item">Add User</a>
				<a href="/${initParam.appName}/account/getEditUserView" class="list-group-item">Edit User</a>
			</div>
            <a href="/${initParam.appName}/search/snapshotView" class="list-group-item">Snapshot Export</a>
            <a href="/${initParam.appName}/passwd/view" class="list-group-item">Change Password</a>
            <a href="/${initParam.appName}/login/signOut" class="list-group-item">Sign out</a>
            <a href="/${initParam.appName}/login/help" class="list-group-item">Help</a>
          
          </div>
   </div><!--/.sidebar-offcanvas-->
 <script>
	function showDiv(divId){
		document.getElementById(divId).style.display ='block';
	}
</script>	
   
   
   
