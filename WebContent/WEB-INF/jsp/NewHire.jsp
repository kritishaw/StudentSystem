<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<s:head theme="xhtml" />
<script type="text/javascript" src="/${initParam.appName}/assets/js/common.js"></script>
<script type="text/javascript" src="/${initParam.appName}/assets/js/scw.js">

var today = new Date();
document.getElementById('hireDate').innerHTML=today;

</script>
<h3 id="errorHeading" style="color: RED"></h3>
<c:if test="${message != null}">
	<h3 style="color: RED">${detailsMessage}</h3>
</c:if>

<form action="submitNewHire" method="post" id="newStudentForm">
	<input type="hidden" id="id" name="employee.id" value="${employee.id}" />
	<input type="hidden" id="ufid" name="ufid" value="${ufid}" />
		<input type="hidden" name="employee.hireDate" id="hireDate"
					size="15" readonly="readonly"
					value="12-15-2016"/>
					
	
	<div id="EmployeeDetails">
		 <div id="new" class="panel panel-primary">
		 <div id="new2" class="panel-heading">
		<b>New Hire Form</b>
		</div>
		<div id="new3" class="panel-footer">
		<table>
			<tr>
				
			
			
				<td width="20%" ><label class="label">Title</label></td>
				<td width="30%"><select name="employee.title" id="titlePosition" onchange="loadTitleChanges()">
						<option value="OPS">OPS</option>
						<option value="RA">RA</option>
						<option value="TA">TA</option>
					<!-- 	<option value="studentAssistant">Student Asst</option> -->
				</select></td><td width="20%"></td>
				<td width="40%"></td>
			</tr>
			<tr>
				<td><label class="label">Semester</label></td>
				<td><select name="employee.Semester" id="semester" onchange="fillDates()">
						<option value="Fall">Fall</option>
						<option value="Spring">Spring</option>
						<option value="Summer">Summer</option>
				</select></td>
				
				<td><label class="label">Year</label></td>
				<td><select name="employee.year" id="year" onchange="fillDates()">
						<c:forEach items="${yearList}" var="yearValue">
							<option value="${yearValue}">${yearValue}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td><label class="label">Start Date</label><img src='/${initParam.appName}/assets/images/required_icon.png'
					title='Required' alt='Required' height="8px"/></td>
				<td><input type="text" name="employee.fromDate" id="fromDate" readonly="readonly" 
					size="15" value='<fmt:formatDate type="date" pattern="MM-dd-yyyy" value="${employee.fromDate}"/>' />
					<img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('fromDate'),event);" onload="loadInitialDates()" /></td>
				
				<td><label class="label">End Date</label><img src='/${initParam.appName}/assets/images/required_icon.png'
					title='Required' alt='Required' height="8px"/></td>
				<td><input type="text" name="employee.toDate" id="toDate" readonly="readonly" 
					size="15" value='<fmt:formatDate type="date" pattern="MM-dd-yyyy" value="${employee.toDate}" />' />
					<img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('toDate'),event);" /></td>
			</tr>
		</table>
		</div>
		</div>

		<div id="personalInfoMain">
		 	<div id="new" class="panel panel-primary">
		 	<div id="new2" class="panel-heading">
			<b>Personal Information</b>
			</div>
			<div id="new3" class="panel-footer">
			<table>
				<tr>
					<td width="20%"><label class="label">Last Name</label><img src='/${initParam.appName}/assets/images/required_icon.png'
					title='Required' alt='Required' height="8px"/></td>
					<td width="40%"><input type="text" name="employee.lastName"
						value="${employee.lastName}" id="lastname"/></td>
					
					<td width="20%"> <label class="label">First Name</label><img src='/${initParam.appName}/assets/images/required_icon.png'
					title='Required' alt='Required' height="8px"/></td>
					<td width="40%"><input type="text" name="employee.firstName"
						value="${employee.firstName}" id="firstname"/></td>
				</tr>

				<tr>
					<td width="20%"><label class="label">Email</label><img src='/${initParam.appName}/assets/images/required_icon.png'
					title='Required' alt='Required' height="8px"/></td>
					<td width="40%"><input type="text" name="employee.email"
						value="${employee.email}" id="email"/></td>
					
					<td width="20%"><label class="label">UFID (No dashes)</label><img src='/${initParam.appName}/assets/images/required_icon.png'
					title='Required' alt='Required' height="8px"/> (No dashes)</td>
					<td width="40%"><input type="text" maxlength="8" name="employee.ufid"
						value="${employee.ufid}" id="ufidValue"/></td>
				</tr>
				<tr>
					<td width="20%"><label class="label">Building/Room No</label></td>
					<td width="40%"><input type="text" name="employee.building"
						value="${employee.building}" /></td>
					
					<td width="20%"><label class="label">Phone Number</label></td>
					<td width="40%"><input type="text" name="employee.phoneNumber"
						value="${employee.phoneNumber}" id="phoneNumber"/></td>
				</tr>
			</table>
			<input type="hidden" name="employee.supervisor.name" value="${employee.supervisor.name}"/>
			Is this person a relative of yours? <select name="employee.relative"
				id="relative">
				<option value="false">No</option>
				<option value="true">Yes</option>
			</select>
           </div>
           </div>
		</div>
		
		
		<div id="new" class="panel panel-primary">
		 <div id="new2" class="panel-heading">
		 <b>Details</b>
		 </div>
		<div id="new3" class="panel-footer">
		<!-- <div style="border: 2px solid; width: 600px;">
			<h4>PLEASE READ CAREFULLY</h4>
			<b>If student is an REU/REV/VRS participant, there is no need to
				provide "FTE/HRS PER WEEK" or "HOURLY RATE." Instead, please provide
				the total amount of money you want the student to receive this
				semester in the block to the right. </b>
		</div> -->
		<table>
			<tr>
				<td width="20%"><label class="label">Fellowship</label>
				<img alt="fellowshipImage" title="If student is an REU/REV/VRS participant, there is no need to provide 'FTE/HRS PER WEEK' or 'HOURLY RATE.' Instead, please provide the total amount of money you want the student to receive this semester in the block to the right."
					src='/${initParam.appName}/assets/images/Information-icon.png'	height="18px">
				</td>
				<td width="40%"> <select name="employee.fellowship" id="fellowship" onchange="viewFTEDisplayDetails()">
						<option value="">Select</option>
						<option value="REU">REU</option>
						<option value="REV">REV</option>
						<option value="VRS">VRS</option>
				</select></td>
			
				
				<td width="20%"><input type="hidden" name="employee.emplRecord"
					value="0" size="7" default="0"/></td><td width="40%"></td>
			</tr>
			<tr>
				<td width="20%"><label class="label">Federal Work Study</label></td>
				<td width="40%"><select name="employee.fedWorkStudy" id="fedWorkStudy">
						<option value="false">No</option>
						<option value="true">Yes</option>
				</select></td>
				
				<td width="20%"><label class="label">Target Amount($)</label></td>
				<td width="40%"><input type="text" name="employee.targetAmount"
					value="${employee.targetAmount}" size="10" /></td>
			</tr>
			<tr>
				
				
				<td width="20%"><label class="label" id="fteLabel">FTE(HRS PER WEEK)</label></td>
				
				<td width="40%"><select name="employee.fte" id="fte"  onChange="calculateBiweeklyRate()" >
						<option value="0.17">0.17(6.80)</option>
						<option value="0.20">0.20(8)</option>
						<option value="0.25">0.25(10)</option>
						<option value="0.33">0.33(13)</option>
						<option value="0.50">0.50(20)</option>
						<option value="0.75">0.75(30)</option>
						<option value="1.00">1.00(40)</option>
						
						
				</select></td>	
				
			
				
				<td width="10%"><label class="label" id="hourlyRateLabel">HourlyRate($)</label><img src='/${initParam.appName}/assets/images/required_icon.png'
					id="requiredHourlyRate" title='Required' alt='Required' height="8px"/></td>
				<td width="40%"><input type="text" name="employee.hourly"
					value="${employee.hourly}" size="10" id="hourlyRate"  onChange="calculateBiweeklyRate()" /></td>
			</tr>
			<tr>
				<td><label class="label" id="biweeklyLabel">Biweekly Rate($)</label></td>
				<td><input type="text" name="employee.biweeklyRate"
					value="0" size="7" id="biweekly" default="0" readonly="readonly"/></td>
				
				<td><label class="label" id="annualRateLabel">Annual Rate($)</label><img src='/${initParam.appName}/assets/images/required_icon.png'
					id="requiredAnnualRate" title='Required' alt='Required' height="8px"/></td>
				<td><input type="text" name="employee.annualRate"
					value="0" size="7" id="annualRate" default="0"  onChange="calculateBiweeklyRate()" /></td>
			</tr>
		</table>
		
		
		<table style="padding-top: 10px;">
			<tr>
				<td ><label class="label">Duties</label><img src='/${initParam.appName}/assets/images/required_icon.png'
					title='Required' alt='Required' height="8px"/></td>
				<td ><textarea rows="4" cols="70" name="employee.duties" id="duties">${employee.duties}</textarea></td>
			</tr>
		</table>

		<table>
			<tr>
				<td><label class="label">Notes</label></td>
				<td style="padding-left: 15px;"><textarea rows="4" cols="70" name="employee.notes">${employee.notes}</textarea></td>
			</tr>
		</table>
		</div>
		</div>
		 <div id="new" class="panel panel-primary">
 		<div id="new2" class="panel-heading">
		<b>Payroll Details (All fields in a row are required. Enter NA if project name/number is not available.<br>If using multiple funding sources, please provide the appropriate percentage for each funding source.)</b> 
	</div>
		<div id="new3" class="panel-footer"> 
			<input type="hidden" id="projects" name="projects" value="${projects}" />
		<table>
		
			<tr>
				<th></th>
				<th>Project Name</th>
				<th>Project Number</th>
				<th>Percentage</th>
				<th>Start Date</th>
				<th>End Date</th>
				
				<td><img alt="Add row" onClick="addRow()"
					src='/${initParam.appName}/assets/images/button_add.png'
					height="20px"></td>
			</tr>
			<tr>
				<td><label class="label">Project 1</label></td>
				<td><input type="text" id="pName1" value="" size="30" onChange="defaultdata()" /></td>
				<td><input type="text" id="pNumber1" value="" size="10"  onChange="defaultdata()" /></td>
				<td><input type="text" id="percent1" value="" onChange="updateTotal()" size="6" onkeyup="this.value = this.value.replace(/[^0-9]/, '')"/></td>
				
				<!-- Added for start date and end date -->		
				
				<td><input type="text" id="startDate1" size="6" value="" /><img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('startDate1'),event);" /></td>
				<td><input type="text" id="endDate1" size="6" value="" /><img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('endDate1'),event);" /></td>
				
				<!-- Added for start date and end date -->	
				
				<td id="img1"></td>
			</tr>
			<c:forEach begin="2" end="10" var="cur">
				<tr style="display: none;" id="project${cur}">
					<td><label class="label">Project ${cur}</label></td>
					<td><input type="text" id="pName${cur}" value=""
						size="30" /></td>
					<td><input type="text" id="pNumber${cur}" value=""
						size="10" /></td>
					<td><input type="text" id="percent${cur}" value=""
						onChange="updateTotal()" size="6" onkeyup="this.value = this.value.replace(/[^0-9]/, '')"/></td>
						
						
				<!-- Added for start date and end date -->		

				<td><input type="text" id="startDate${cur}" size="6" value="" /><img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('startDate${cur}'),event);" /></td>
	
				<td><input type="text" id="endDate${cur}" size="6" value="" /><img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('endDate${cur}'),event);" /></td>
				
				<!-- Added for start date and end date -->	
					
					<td style="display: none;" id="img${cur}"><img
						alt="Delete row" onClick="deleteRow()"
						src="/${initParam.appName}/assets/images/button-minus.png"
						height="20px"></td>
				</tr>
			</c:forEach>
			<tr>
				<td><label class="label"><b>Total</b></label></td>
				<td></td>
				<td></td>
				<td><input type="text" id="total" value="" size="6"
					readonly="readonly" /></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>

		<br> <input type="button" style="margin-left: 35%; margin-right: 65%; position: relative;" name="submitButton" value="Submit" onclick="validateAndSubmitForm()" class="btn"></input>
		</div>
		</div>

	</div>
</form>
<script type="text/javascript">
	var projectDisplayCount = 1;
	displayProjects(projectDisplayCount);

	function displayProjects(count) {
		
		for (var i = 2; i <= count; i++) {
			//Hide any button image at the end
			var cell = document.getElementById("img" + i);
			cell.style.display = 'none';

			var rowStyle = document.getElementById("project" + i).style;
			rowStyle.display = 'table-row';
			
			document.getElementById("startDate" + i).value = document.getElementById("fromDate").value;
			document.getElementById("endDate" + i).value = document.getElementById("toDate").value;
		}

		if (count > 1) {
			var lastRow = document.getElementById("img" + count);
			lastRow.style.display = 'table-cell';
		}
		
		//Do not hide the first row
		if(count == 0) {
			count = 1;
		}
		
		//Hide rest of the rows and remove their values
		for (var i = count + 1; i <= 10; i++) {
			var rowStyle = document.getElementById("project" + i).style;
			rowStyle.display = 'none';

			document.getElementById("pName" + i).value = "";

			document.getElementById("pNumber" + i).value = "";

			document.getElementById("percent" + i).value = "";

			document.getElementById("startDate" + i).value = document.getElementById("fromDate").value;
			
			document.getElementById("endDate" + i).value = document.getElementById("toDate").value;

		}
	}

	function deleteRow() {
		projectDisplayCount--;
		displayProjects(projectDisplayCount);
		updateTotal();
	}

	function addRow() {
		if (projectDisplayCount < 10) {
			projectDisplayCount++;
			displayProjects(projectDisplayCount);
		}
	}

	function updateTotal() {
		var value = 0;
		for (var i = 1; i <= 10; i++) {
			var percent = document.getElementById("percent" + i).value;
			var nextValue = 0;
			
			if(percent.trim()!='' && !isNaN(percent.trim())){
				nextValue = parseInt(percent.trim());
			}
			
			value = value + nextValue;
		}
		
		document.getElementById("total").value = value;
	}
	
	function createProjectStr(){
		var projectStr = "";
		
		for (var i = 0; i < projectDisplayCount; i++) {
			var iTemp = i + 1;
			projectStr = projectStr + document.getElementById("pName" + iTemp).value;
			projectStr = projectStr + ";;&";
			projectStr = projectStr + document.getElementById("pNumber" + iTemp).value;
			projectStr = projectStr + ";;&";
			projectStr = projectStr + document.getElementById("percent" + iTemp).value;
			projectStr = projectStr + ";;&";
			projectStr = projectStr + document.getElementById("startDate" + iTemp).value;
			projectStr = projectStr + ";;&";
			projectStr = projectStr + document.getElementById("endDate" + iTemp).value;
			projectStr = projectStr + ";;&";
		}
		
		projectStr = projectStr.substring(0,projectStr.length - 3);
		
		document.getElementById("projects").value = projectStr;
	}
	
	
	function viewFTEDisplayDetails() 
	{
		if(checkEmpty(document.getElementById("fellowship")))
		{
			document.getElementById("fte").style.display="block";
			document.getElementById("hourlyRate").style.display="block";
			document.getElementById("fteLabel").style.display="block";
			document.getElementById("hourlyRateLabel").style.display="block";
		}
		else
		{
			document.getElementById("fte").style.display="none";
			document.getElementById("hourlyRate").style.display="none";
			document.getElementById("fteLabel").style.display="none";
			document.getElementById("hourlyRateLabel").style.display="none";
		}
	}

// Function to check project fields 
	
	function checkProjectValues()
	{
		for (var i = 0; i < projectDisplayCount; i++) {
			var iTemp = i + 1;
			
			if(checkEmpty(document.getElementById("pName" + iTemp))|| checkEmpty(document.getElementById("pNumber" + iTemp)) || checkEmpty(document.getElementById("percent" + iTemp)) || checkEmpty(document.getElementById("startDate" + iTemp)) || checkEmpty(document.getElementById("endDate" + iTemp)))
			{
				document.getElementById("errorHeading").innerHTML="Enter all the Project fields";
				return false;
			}
					
		}
		return true;
	}
	
	
	function validateAndSubmitForm()
	{
		if((!checkEmpty(document.getElementById("hireDate"))) && (!checkEmpty(document.getElementById("fromDate"))) && (!checkEmpty(document.getElementById("toDate"))) && (!checkEmpty(document.getElementById("lastname"))) && (!checkEmpty(document.getElementById("firstname"))) && (!checkEmpty(document.getElementById("email"))) && (!checkEmpty(document.getElementById("ufidValue"))) && (!checkEmpty(document.getElementById("duties"))))
			{
				if(emailValidator(document.getElementById("email")))
					{
						if(numbersCheck(document.getElementById("ufidValue")))
						{
							//if(numbersCheck(document.getElementById("phoneNumber")))
							//	{
								if(checkProjectValues())
									{
										createProjectStr();
										document.getElementById("newStudentForm").submit();
									}
								//}
							//else
							//	{
							//	document.getElementById("errorHeading").innerHTML="Phone Number Format should contain only digits";					
							//	}
						}
						else
						{
						document.getElementById("errorHeading").innerHTML="Ufid Format should contain only digits";
						}
					}
					else
					{
						document.getElementById("errorHeading").innerHTML="Email Format should be correct";
					}
			}
		else
			{
				document.getElementById("errorHeading").innerHTML="Enter all the required fields";
			}
	}

	function loadInitialDates()
	{
		<%
		String fallDates=(String)session.getAttribute("FALL_DATES");
		String springDates=(String)session.getAttribute("SPRING_DATES");
		String summerDates=(String)session.getAttribute("SUMMER_DATES");
		String nextSemester=(String)session.getAttribute("NEXT_SEMESTER");
		String nextYear=(String)session.getAttribute("NEXT_YEAR");
		%>
	
		var nextSemesterValue = '<%=nextSemester%>';
		var nextYearValue = <%=nextYear%>;
	
		document.getElementById("semester").value = nextSemesterValue;
		document.getElementById("year").value = nextYearValue;
		fillDates();
	}
	
	//Function to fill from and to dates based on the semester and year fields

	function fillDates()
	{
		
		if((!checkEmpty(document.getElementById("semester"))) && (!checkEmpty(document.getElementById("year"))))
			{
				if(document.getElementById("semester").value=="Fall")
				{
					var fallDateRange = '<%=fallDates%>';
					var rangeValues = fallDateRange.split(",");
					document.getElementById("fromDate").value=rangeValues[0]+document.getElementById("year").value;
					document.getElementById("toDate").value=rangeValues[1]+document.getElementById("year").value;
				}
				else if(document.getElementById("semester").value=="Spring")
				{
					var springDateRange = '<%=springDates%>';
					var rangeValues = springDateRange.split(",");
					document.getElementById("fromDate").value=rangeValues[0]+document.getElementById("year").value;
					document.getElementById("toDate").value=rangeValues[1]+document.getElementById("year").value;
					// document.getElementById("fromDate").value="01-01-2015"; 
				}
				else
				{
					var summerDateRange = '<%=summerDates%>';
					var rangeValues = summerDateRange.split(",");
					document.getElementById("fromDate").value=rangeValues[0]+document.getElementById("year").value;
					document.getElementById("toDate").value=rangeValues[1]+document.getElementById("year").value;
					/* document.getElementById("fromDate").innerHTML="05-16-"+document.getElementById("year").value; */
				}
			}
		else
			{
				var currentYear = '<%=fallDates%>';
				var fallDateRange = '<%=fallDates%>';
				var rangeValues = fallDateRange.split(",");
				document.getElementById("fromDate").value=rangeValues[0]+currentYear;
				document.getElementById("toDate").value=rangeValues[1]+currentYear;
			}
		
		document.getElementById("startDate1").value = document.getElementById("fromDate").value;		
		document.getElementById("endDate1").value = document.getElementById("toDate").value;
	}

window.onload = function() {
		loadTitleChanges();
	};	
	
	function loadTitleChanges()
	{
		if(document.getElementById("titlePosition").value=="OPS" || document.getElementById("titlePosition").value=="studentAssistant")
			{
				document.getElementById("annualRateLabel").style.display = "none";
				document.getElementById("annualRate").style.display = "none";
				
				document.getElementById("requiredAnnualRate").style.display = "none";
				document.getElementById("hourlyRateLabel").style.display="block";
				document.getElementById("hourlyRate").style.display="block";
				document.getElementById("requiredHourlyRate").style.display = "block";
			}
		else
			{
				document.getElementById("hourlyRateLabel").style.display="none";
				document.getElementById("hourlyRate").style.display="none";
				document.getElementById("requiredHourlyRate").style.display = "none";
				document.getElementById("annualRateLabel").style.display = "block";
				document.getElementById("annualRate").style.display = "block";
				
				document.getElementById("requiredAnnualRate").style.display = "block";
			}
	}
	
	 function calculate1() {
	    	
		    var fte = document.getElementById('fte').value; 
		    var hourlyRate = document.getElementById('hourlyRate').value;
		    
		    if (hourlyRate==""||hourlyRate==null||hourlyRate==" "||hourlyRate== "undefined" ){
		    	
		    	hourlyRate=0;
		    }
		    var myResult =2* fte * hourlyRate * 40;
		    myResult=Math.round(myResult * 100) / 100;
		    
		    document.getElementById('biweekly').value=myResult ;
			}
		    
		    
		    function calculate2() {
		        var fte = document.getElementById('fte').value; 
		        
		        var annualRate = document.getElementById('annualRate').value; 
		        if (annualRate==""||annualRate==null||annualRate==" "||annualRate== "undefined" ){
		        	
		        	annualRate=0;
		        }
		        var myResult =( fte * annualRate * 2)/26.1;
		        myResult=Math.round(myResult * 100) / 100;
		        document.getElementById('biweekly').value=myResult ;
		    	}
			
		    function calculateBiweeklyRate()
			{
				if(document.getElementById("titlePosition").value=="OPS" || document.getElementById("titlePosition").value=="studentAssistant")
					{
						calculate1();
					}
				else
					{
						calculate2();
					}
			}
		    function defaultdata()
			{
				var value= document.getElementById("percent1").value;
				var value2= document.getElementById("total").value;
					
					  if (value==""||value==null||value==" "||value== "undefined" ){
				        	
						  document.getElementById("percent1").value=0;
				        }
					  
					  if (value2==""||value2==null||value2==" "||value2== "undefined" ){
				        	
						  document.getElementById("total").value=0;
				        }
			}
		    
</script>