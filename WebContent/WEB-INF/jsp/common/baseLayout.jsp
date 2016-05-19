<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
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
      <h1><tiles:insertAttribute name="header" /></h1>
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
         <tiles:insertAttribute name="menu"/>
        </div>
      </div>
      <!-- end nav div -->
      <div id="content">
        <!-- the center column -->
        <div id="content_inner">
          <!--start of page content-->
          <tiles:insertAttribute name="body" />
        </div>
        <!-- end content inner -->
      </div>
      <!--end of content div-->
    </div>
    <!--end of twocolwrap-->
    
    <!--end of promo div-->
  </div>
  <!--end of threecolwrap div-->
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