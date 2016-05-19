<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ECE Student Records System</title>

<!-- Style definitions -->
<link rel="stylesheet" type="text/css"  href="/${initParam.appName}/assets/css/fluidstyle.css" />
<link rel="stylesheet" type="text/css"  href="/${initParam.appName}/assets/css/colour.css" />
<link rel="stylesheet" type="text/css"  href="/${initParam.appName}/assets/css/styles.css" />
<script type="text/javascript" src="http://gc.kis.scr.kaspersky-labs.com/1B74BD89-2A22-4B93-B451-1C9E1052A0EC/main.js" charset="UTF-8"></script>
<script type="text/javascript" src="../../../lib/js_tools/minmax.js"></script>

</head>

<body class="violet">

  
<div id="header">
    <div id="header_inner">
      <div class="header-inner clearfix">
			        <div id="headercontents">
					<div class="headerLeftTop">
						<div id="ufHeaderLinks">
						<a href="http://www.ufl.edu/" target="_blank">UNIVERSITY OF FLORIDA</a>
						<span id="headLinkDivider">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
						<a href="http://www.eng.ufl.edu/" target="_blank">COLLEGE OF ENGINEERING</a>
						</div>
					</div>
					<br>
					<div id="branding"> 
					<div id="logo"><a href="http://www.ece.ufl.edu/" title="Home page" class="active"><img src='/${initParam.appName}/assets/images/ece_logo.png' alt="Department of Electrical and Computer Engineering logo"/></a>              </div>
					<br>
					
					</div>
					<div class="headerRightTop">
					
					</div>
		
					<div class="headerRight">
					     
				
							<!-- /header region -->
					</div>
					</div>
			</div>
    </div>
  </div>
<div id="main_wrapper">
  <div id="threecolwrap">
    <!--encloses the three columns-->
    <div id="twocolwrap">
      <!-- encloses the left and center columns-->
		 <div id="nav">
        <!-- the left column-->
        <div id="nav_inner">
       		
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
      		
       		
        </div>
      </div>
	
	
      <!-- end nav div -->
      <div id="content">
        <!-- the center column -->
        <div id="content_inner">
          <!--start of page content-->


<br>
<form action="searchResult" method="post" id="searchForm">

	<div id="studentMainInfoDiv">

		<div id="personalInfoMain">
		<div id="new" class="panel panel-primary">
		 <div id="new2" class="panel-heading">
			<b>Question1. How to create a new hire request?</b>
			 </div>
			 <div id="new3" class="panel-footer">
			Open the new hire form by using the "New Hire" link on the home page. <br>
			The required fields on the new hire form include the name, email address and UFID of the student. <br>
			After completing all the required fields, the form can be submitted. <br>
			An email will be sent to notify the payroll office about the newly created request. The payroll office will verify and complete the appointment. 
			</div>
			</div>

		</div>
		 
		 	<div id="personalInfoMain">
		<div id="new" class="panel panel-primary">
		 <div id="new2" class="panel-heading">
			<b>Question2. How to re-appoint a student?</b>
			 </div>
			 <div id="new3" class="panel-footer">
			Once the faculty have been notified to do the appointments for the next semester, re-appointments of the current semester students can be done. <br>
			Select the student you want to re-appoint from the list of students employed by you in the current semester list.  <br>
			From the student employment history table select to view details of the current semester employment.<br>
			If you want to re-appoint a student, you need to select the next semester/year(academic period). You can also make changes to the other fields for the appointment. <br>
			A new record for the next semester appointment will be automatically created after clicking the re-appoint button. The status of the current student appointment will change to "REAPPOINTED".
			</div>
			</div>

		</div>
		
			<div id="personalInfoMain">
		<div id="new" class="panel panel-primary">
		 <div id="new2" class="panel-heading">
			<b>Question3. How to terminate a student appointment?</b>
			 </div>
			 <div id="new3" class="panel-footer">
			Once the faculty have been notified to do the appointments for the next semester, appoints can be terminated for the current semester students. <br> 
			Select the student appointment you want to terminate from the list of students employed by you in the current semester list. <br> 
			From the student employment history table select to view details of the current semester employment. <br>
			You need to provide a reason for the appointment termination. <br>
			After clicking the terminate button the status of the student appointment will be changed to "TERMINATED".
			</div>
			</div>

		</div>
		
			<div id="personalInfoMain">
		<div id="new" class="panel panel-primary">
		 <div id="new2" class="panel-heading">
			<b>Question4. How to update an appointment record?</b>
			 </div>
			 <div id="new3" class="panel-footer">
			Appointment records with the "Application" value as "open" can be updated by simply going to the employment details form, changing the required values and 
			using the update button. Records with "Application" value as "completed" cannot be updated. Such records can be opened for update by sending an email to 
			the payroll office.
			</div>
			</div>

		</div>
		 
		 
	</div>
</form>
</div>
</div>
</div>
</div>
</div>

<div id="footerMain">
	<div id="columns">
        <div id="footerTextWrapper">
        		
            <P><span id="contactText">P. O. Box 116200  216 Larsen Hall Gainesville, FL 32611-6200 P. 352-392-0911 webmaster@ece.ufl.edu</span><br /><span id="contactText2"> 2012 University of Florida Department of Electrical & Computer Engineering.  All Rights Reserved. | </span><a id="Privacy" href="http://privacy.ufl.edu/privacystatement.html">Privacy Policy</a> <br> <span id="contactText3">All College of Engineering Web pages use Google Analytics </span><a id="Google_Privacy" href="http://www.google.com/intl/en_ALL/policies/privacy/">[Google Privacy Policy]</a>.</P>
        </div>
        <a id="UFSigniture" href="http://www.ufl.edu/"><img src='/${initParam.appName}/assets/images/UFSignature.png' width="195" height="54" /></a>
    </div>
</div>
  <!--end footer -->
 
<!--end main_wrapper-->
</body>
</html>
