<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<s:head theme="xhtml" />
<script type="text/javascript" src="/${initParam.appName}/assets/js/common.js"></script>
<script type="text/javascript" src="/${initParam.appName}/assets/js/scw.js"></script>
<h3 id="errorHeading" style="color: RED"></h3>
<c:if test="${message != null}">
	<h3 style="color: BLUE">${message}</h3>
</c:if>

<script type="text/javascript">
	function Select_Value_Set(SelectName, Value) {
		var element = document.getElementById(SelectName);
		element.value = Value;
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
	
	//approve
	function approve() 
	{
		location.href='/${initParam.appName}/search/approve?employee.id='+document.getElementById("id").value;
	}
	
	//print employee
	function printEmployee() 
	{
		//alert("print");
		location.href='/${initParam.appName}/details/printEmployee?employee.id='+document.getElementById("id").value;
	}
	
	//Form validation
	
		function validateAndSubmitForm()
	{
		if( (!checkEmpty(document.getElementById("fromDate"))) && (!checkEmpty(document.getElementById("toDate"))) && (!checkEmpty(document.getElementById("lastname"))) && (!checkEmpty(document.getElementById("firstname"))) && (!checkEmpty(document.getElementById("email"))) && (!checkEmpty(document.getElementById("ufidValue"))) && (!checkEmpty(document.getElementById("duties"))))
			{
				if(emailValidator(document.getElementById("email")))
					{
						if(numbersCheck(document.getElementById("ufidValue")))
						{
							if(numbersCheck(document.getElementById("phoneNumber")))
								{
									createProjectStr();
									if(checkProjectValues())
										document.getElementById("studentInfoForm").submit();
								}
							else
								{
								document.getElementById("errorHeading").innerHTML="Phone Number Format should contain only digits";					
								}
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
	
	
	/* function validateAndSubmitForm()
	{
		alert("gvhj");
		if( (!checkEmpty(document.getElementById("fromDate"))) && (!checkEmpty(document.getElementById("toDate"))) && (!checkEmpty(document.getElementById("lastname"))) && (!checkEmpty(document.getElementById("firstname"))) && (!checkEmpty(document.getElementById("email"))) && (!checkEmpty(document.getElementById("ufidValue"))) && (!checkEmpty(document.getElementById("duties"))))
			{
			alert("1");
				if(emailValidator(document.getElementById("email")))
					{
						//if(numbersCheck(document.getElementById("ufidValue")))
						//{
							//if(numbersCheck(document.getElementById("phoneNumber")))
							//	{
									if(checkProjectValues())
										{
										alert("1");
											createProjectStr();
											alert("1");
											document.getElementById("studentInfoForm").submit();
										}
							//	}
							//else
							//	{
							//		document.getElementById("errorHeading").innerHTML="Phone Number Format should contain only digits";					
							//	}
						//}
						//else
						//{
						//	document.getElementById("errorHeading").innerHTML="Ufid Format should contain only digits";
						//}
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
	} */
	
	// function addRow
</script>

<form action="updateEmployee" method="post" id="studentInfoForm">
	<input type="hidden" id="id" name="employee.id" value="${employee.id}" />
	<input type="hidden" id="ufid" name="ufid" value="${ufid}" />
	
<input type="hidden" name="employee.hireDate" id="hireDate"
					size="15" readonly="readonly"
					value='<fmt:formatDate type="date" pattern="MM-dd-yyyy" value="${employee.hireDate}" />' />
				

  
	<div id="EmployeeDetails" >
	    <div id="new" class="panel panel-primary">
	    <div id="new2" class="panel-heading">
		<b>Appointment Details</b>
		</div>
		<div id="new3" class="panel-footer">
		<table>
			<tr>
				<!-- <td><label >Hire Date</label></td> -->
				
			
				<td width="10%"><label >Title</label></td>
				<td width="40%"><select name="employee.title" id="title">
						<option value="OPS">OPS</option>
						<option value="RA">RA</option>
						<option value="TA">TA</option>
						<!-- <option value="studentAssistant">Student Asst</option> -->
				</select></td><td width="10%"></td><td width="40%"></td>
			</tr>
			<tr>
				<td><label >Semester</label></td>
				<td><select name="employee.Semester" id="semester" onchange="fillDates()">
						<option value="Fall">Fall</option>
						<option value="Spring">Spring</option>
						<option value="Summer">Summer</option>
				</select></td>
				
				<td><label >Year</label></td>
				<td><select name="employee.year" id="year" onchange="fillDates()">
						<c:forEach items="${yearList}" var="yearValue">
							<option value="${yearValue}">${yearValue}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td><label >Start Date</label></td>
				<td><input type="text" name="employee.fromDate" id="fromDate"
					size="15" readonly="readonly" onload="fillDates()" 
					value='<fmt:formatDate type="date" pattern="MM-dd-yyyy" value="${employee.fromDate}" />' />
					<img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('fromDate'),event);" /></td>
				
				<td><label >End Date</label></td>
				<td><input type="text" name="employee.toDate" id="toDate"
					size="15" readonly="readonly"
					value='<fmt:formatDate type="date" pattern="MM-dd-yyyy" value="${employee.toDate}" />' />
					<img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('toDate'),event);" /></td>
			</tr>
		</table>
        </div>
        </div>
		
		<div id="personalInfoMain">
			<div id="new3" class="panel panel-primary">
			<div id="new4" class="panel-heading">
			<b>Personal Information</b>
			</div>
		    <div id="new5" class="panel-footer">
			<table>
				<tr>
					<td width="10%"><label >Last Name</label></td>
					<td width="40%"><input type="text" name="employee.lastName"
						value="${employee.lastName}" id="lastname"/></td>
					
					<td width="10%"><label >First Name</label></td>
					<td width="40%"><input type="text" name="employee.firstName"
						value="${employee.firstName}" id="firstname"/></td>
				</tr>

				<tr>
					<td><label >Email</label></td>
					<td><input type="text" name="employee.email"
						value="${employee.email}" id="email"/>
					
					<td><label >UFID</label></td>
					<td><input type="text" name="employee.ufid"
						value="${employee.ufid}" id="ufidValue"/>
				</tr>
				<tr>
					<td><label >Building</label></td>
					<td><input type="text" name="employee.building"
						value="${employee.building}" />
				
					<td><label >Phone Number</label></td>
					<td><input type="text" name="employee.phoneNumber"
						value="${employee.phoneNumber}" id="phoneNumber"/>
				</tr>
				<tr>
					<td><label >Supervisor</label></td>
					<td><select name="employee.supervisor.id" id="supervisor"><option
								value="0">--Select--</option>
							<c:forEach items="${supervisors}" var="result">
								<option value="${result.id}">${result.name}</option>
							</c:forEach>
					</select></td><td></td><td></td>
				</tr>
			</table>
			<label>Is this person a relative of yours?</label> <select name="employee.relative"
				id="relative">
				<option value="false">No</option>
				<option value="true">Yes</option>
			</select>

		</div>
		</div>
		</div>
		<div id="new5" class="panel panel-primary">
		<div id="new6" class="panel-heading">
		<b>Details</b>
		</div>
		<div id="new7" class="panel-footer">
		<!-- <div style="border: 2px solid; width: 600px;">
			<h4>PLEASE READ CAREFULLY</h4>
			<b>If student is an REU/REV/VRS participant, there is no need to
				provide "FTE/HRS PER WEEK" or "HOURLY RATE." Instead, please provide
				the total amount of money you want the student to receive this
				semester in the block to the right. </b>
		</div> -->
		<table>
			<tr>
				<td width="10%"><label >Fellowship</label></td>
				<td width="40%"><select name="employee.fellowship" id="fellowship">
						<option value="">None</option>
						<option value="REU">REU</option>
						<option value="REV">REV</option>
						<option value="VRS">VRS</option>
				</select></td>
				
				<td width="10%"><label >EMPL Record</label></td>
				<td width="40%"><input type="text" name="employee.emplRecord"
					value="${employee.emplRecord}" size="7" /></td>
			</tr>
			<tr>
				<td><label >Federal Work Study</label></td>
				<td><select name="employee.fedWorkStudy" id="fedWorkStudy">
						<option value="false">No</option>
						<option value="true">Yes</option>
				</select></td>
				
				<td><label >Target Amount($)</label></td>
				<td><input type="text" name="employee.targetAmount"
					value="${employee.targetAmount}" size="7" /></td>
			</tr>
			<tr>
				<td><label  id="fteLabel">FTE(HRS PER WEEK)</label></td>
				<td><input type="text" name="employee.fte"
					value="${employee.fte}" size="7" id="fte" onChange="calculateBiweeklyRate()"/></td>
				
				<td><label  id="hourlyRateLabel">Hourly Rate($)</label></td>
				<td><input type="text" name="employee.hourly"
					value="${employee.hourly}" size="7" id="hourlyRate" onChange="calculateBiweeklyRate()"/></td>
			</tr>
			<tr>
				<td><label  id="biweeklyLabel">Biweekly Rate($)</label></td>
				<td><input type="text" name="employee.biweeklyRate"
					value="${employee.biweeklyRate}" size="7" id="biweekly"/></td>
				
				<td><label  id="annualRateLabel">Annual Rate($)</label></td>
				<td><input type="text" name="employee.annualRate"
					value="${employee.annualRate}" size="7" id="annualRate" onChange="calculateBiweeklyRate()"/></td>
			</tr>
		</table>
		<table style="padding-top: 10px;">
			<tr>
				<td ><label >Duties</label></td>
				<td ><textarea rows="4" cols="70" name="employee.duties" id="duties" >${employee.duties}</textarea></td>
			</tr>
		</table>
		</div>
		</div>
		
		 <div id="new5" class="panel panel-primary">
		<div id="new6" class="panel-heading">
		 <b>Payroll Details</b> 
		</div>
		<div id="new7" class="panel-footer">
		<input type="hidden"
			id="projectCount" name="projectCount" value="${projectCount}" />
			<input type="hidden"
			id="projects" name="projects" value="${projects}" />
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
				<td><label >Project 1</label><input type="hidden" id="pId1" value=""
						size="16" /></td>
				
				<td><input type="text" id="pName1" value="" size="30" /></td>
				<td><input type="text" id="pNumber1" value="" size="10" /></td>
				<td><input type="text" id="percent1" value="" onChange="updateTotal()" size="6" onkeyup="this.value = this.value.replace(/[^0-9]/, '')"/></td>
					
				<!-- Added for start date and end date -->		
								
				<td><input type="text" id="startDate1" size="10" value="" /><img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('startDate1'),event);" /></td>
				<td><input type="text" id="endDate1" size="10" value="" /><img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('endDate1'),event);" /></td>
				
				<!-- Added for start date and end date -->	
				
				<td id="img1"></td>
			</tr>
			<c:forEach begin="2" end="10" var="cur">
			<input type="hidden" id="pId${cur}" value=""
						size="16" />
				<tr style="display: none;" id="project${cur}">
					<td><label >Project ${cur}</label></td>
					<td><input type="text" id="pName${cur}" value=""
						size="30" /></td>
					<td><input type="text" id="pNumber${cur}" value=""
						size="10" /></td>
					<td><input type="text" id="percent${cur}" value=""
						onChange="updateTotal()" size="6" onkeyup="this.value = this.value.replace(/[^0-9]/, '')"/></td>
						
						
					<!-- Added for start date and end date -->		

					<td><input type="text" id="startDate${cur}" size="10" value="" /><img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('startDate${cur}'),event);" /></td>
					<td><input type="text" id="endDate${cur}" size="10" value="" /><img src='/${initParam.appName}/assets/images/scw.gif'
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
				<td><label ><b>Total</b></label></td>
				<td></td>
				<td></td>
				<td><input type="text" id="total" value="" size="6"
					readonly="readonly" /></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<%-- <input type="hidden"
			id="projectCount" name="projectCount" value="${projectCount}" />
			<input type="text"
			id="projects" name="projects" value="${projects}" />
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
				<td><label >Project 1</label><input type="hidden" id="pId1" value=""
						size="8" /></td>
				<td><input type="text" id="pName1" value="" size="8" /></td>
				<td><input type="text" id="pNumber1" value="" size="8" /></td>
				<td><input type="text" id="percent1" value="" onChange="updateTotal()" size="6" onkeyup="this.value = this.value.replace(/[^0-9]/, '')"/></td>
				
				<!-- Added for start date and end date -->		
								
				<td><input type="text" id="startDate1" size="8" value="" /><img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('startDate1'),event);" /></td>
				<td><input type="text" id="endDate1" size="8" value="" /><img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('endDate1'),event);" /></td>
				
				<!-- Added for start date and end date -->	
				
				<td id="img1"></td>
			</tr>
			<c:forEach begin="2" end="10" var="cur">
				<input type="hidden" id="pId${cur}" value=""
						size="16" />
				<tr style="display: none;" id="project${cur}">
					<td><label >Project ${cur}</label>	</td>
					
					<td><input type="text" id="pName${cur}" value=""
						size="8" /></td>
					<td><input type="text" id="pNumber${cur}" value=""
						size="8" /></td>
					<td><input type="text" id="percent${cur}" value=""
						onChange="updateTotal()" size="6" onkeyup="this.value = this.value.replace(/[^0-9]/, '')"/></td>
						
						
					<!-- Added for start date and end date -->		

					<td><input type="text" id="startDate${cur}" size="8" value="" /><img src='/${initParam.appName}/assets/images/scw.gif'
					title='Click Here' alt='Click Here'
					onclick="scwShow(scwID('startDate${cur}'),event);" /></td>
					<td><input type="text" id="endDate${cur}" size="8" value="" /><img src='/${initParam.appName}/assets/images/scw.gif'
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
				<td><label ><b>Total</b></label></td>
				<td></td>
				<td></td>
				<td><input type="text" id="total" value="" size="6"
					readonly="readonly" /></td>
				<td></td>
			</tr>
		</table> --%>
        </div>
        </div> 
		<br> <input type="button"  value="Update" onclick="validateAndSubmitForm()" class="btn"></input>
		<c:if test="${employee.facultyFlag == 1 && employee.adminFlag == 0}">
		<input type="button" value="Accept" onclick="approve()" class="btn"></input>
		</c:if>
	
		<input type="button" value="Print Details" onclick="printEmployee()" style="float: right;" class="btn"></input>
	 	

	</div>
</form>
<script type="text/javascript">
	Select_Value_Set('semester', "${employee.semester}");
	Select_Value_Set('year', "${employee.year}");
	Select_Value_Set('relative', "${employee.relative}");
	Select_Value_Set('fellowship', "${employee.fellowship}");
	Select_Value_Set('title', "${employee.title}");
	Select_Value_Set('fedWorkStudy', "${employee.fedWorkStudy}");
	Select_Value_Set('supervisor', "${employee.supervisor.id}");

	 var projectDisplayCount = ${projectCount};
	displayProjects(projectDisplayCount);
	//alert(projectDisplayCount);
	fillValues(projectDisplayCount);
	updateTotal();

	
	function fillValues(count){
		var projectValues = "${projects}";
		//alert("fillValues"+count);
		//alert("fillValues"+projectValues);

		var projectValuesArray = projectValues.split(";;&");
		
		for (var i = 0; i < count; i++) {
			var iTemp = i + 1;
			
			document.getElementById("pId" + iTemp).value = projectValuesArray[i*6];
			
			document.getElementById("pName" + iTemp).value = projectValuesArray[i*6 + 1];

			document.getElementById("pNumber" + iTemp).value = projectValuesArray[i*6 + 2];

			document.getElementById("percent" + iTemp).value = projectValuesArray[i*6 + 3];
			
			document.getElementById("startDate" + iTemp).value = projectValuesArray[i*6 + 4];
			
			document.getElementById("endDate" + iTemp).value = projectValuesArray[i*6 + 5];
		}
		
	}
	
	function displayProjects(count) {
		//alert("DP"+count);
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

			document.getElementById("pId" + i).value = -1;
			
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
		for (var i = 0; i <= projectDisplayCount; i++) {
			var iTemp = i + 1;
			
			projectStr = projectStr + document.getElementById("pId" + iTemp).value;
			projectStr = projectStr + ";;&";
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
		//alert(projectStr);
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
	
 //Function to fill from and to dates based on the semester and year fields

	function fillDates()
	{
		<%
    		String fallDates=(String)session.getAttribute("FALL_DATES");
			String springDates=(String)session.getAttribute("SPRING_DATES");
			String summerDates=(String)session.getAttribute("SUMMER_DATES");
		%>
		
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
				}
				else
				{
					var summerDateRange = '<%=summerDates%>';
					var rangeValues = summerDateRange.split(",");
					document.getElementById("fromDate").value=rangeValues[0]+document.getElementById("year").value;
					document.getElementById("toDate").value=rangeValues[1]+document.getElementById("year").value;
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

	}

    function calculateBiweeklyRate()
	{
		if(document.getElementById("title").value=="OPS" || document.getElementById("title").value=="studentAssistant")
			{
				calculate1();
			}
		else
			{
				calculate2();
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
			
	
	
</script>
<br>
<br>
<s:form action="updateNotes" method="post" id="employeeNotes">
	<div id="new8" class="panel panel-primary">
		<div id="new9" class="panel-heading">
		<b>Notes (Click on 'add notes' to update notes)</b>
		</div>
		<div id="new10" class="panel-footer">
		<input type="hidden" id="id" name="employee.id" value="${employee.id}" />
		<input type="hidden" id="ufid" name="ufid" value="${ufid}" />
		
				<div><s:label>${employee.notes}</s:label></div>
			
				<div><s:textarea key="newNote" label="Add note" rows="5" cols="67" class="form-control"/></div>
			
				<div><input value="Add Notes" type="submit" class="btn" /></div>
		
		</div>
	</div>
</s:form>