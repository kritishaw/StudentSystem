package edu.ufl.ece.studentemployment.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import studentemployee.data.EmailTemplate;
import studentemployee.data.Employee;
import studentemployee.data.Supervisor;
import studentemployee.db.EmployeeDB;
import studentemployee.utils.SendMail;
import edu.ufl.ece.studentemployment.exceptions.GeneratorException;
import edu.ufl.ece.studentemployment.utils.CommonConstants;
import edu.ufl.ece.studentemployment.utils.EmailHelper;
import edu.ufl.ece.studentemployment.utils.PdfGenerator;
import edu.ufl.ece.studentemployment.utils.UtilFunctions;

@SuppressWarnings("serial")
public class NotifyActionSupport extends BaseActionSupport {

	private Logger logger = Logger.getLogger(NotifyActionSupport.class);
	private String ufid;
	private List<Supervisor> supervisors;
	private Supervisor supervisor;
	private List<Employee> searchResult;
	public List<Employee> getSearchResult1() {
		return searchResult1;
	}

	public void setSearchResult1(List<Employee> searchResult1) {
		this.searchResult1 = searchResult1;
	}

	private List<Employee> searchResult1;
	private String message;
    String pathSep = System.getProperty("file.separator");
	private InputStream pdfStream;

	public void setPdfStream(InputStream pdfStream) {
		this.pdfStream = pdfStream;
	}

	public InputStream getPdfStream() {
		return pdfStream;
	}

	public String getUfid() {
		return ufid;
	}

	public void setUfid(String ufid) {
		this.ufid = ufid;
	}

	public List<Supervisor> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(List<Supervisor> supervisors) {
		this.supervisors = supervisors;
	}

	public List<Employee> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List<Employee> searchResult) {
		this.searchResult = searchResult;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String view() 
	{
		EmployeeDB employeeDB = new EmployeeDB();
		supervisors = employeeDB.getSupervisors();
		for(Supervisor supervisorObj: supervisors)
		{
			if(supervisorObj.getName()!=null)
			{
				String[] nameParts = supervisorObj.getName().split(",");
				supervisorObj.setName(nameParts[1].trim() + ", " + nameParts[0].trim());
				
			}
		}
		
		if(supervisor!=null)
		{
			String[] nameParts = supervisor.getName().split(",");
			supervisor.setName(nameParts[1].trim() + ", " + nameParts[0].trim());
			session.setAttribute(CommonConstants.SUPERVISOR_NAME,supervisor.getName());
		}
		else
		{
			supervisor = supervisors.get(0);
			session.setAttribute(CommonConstants.SUPERVISOR_NAME,"");
		}
	    Collections.sort(supervisors, new Supervisor());

			return "NOTIFY_VIEW";
	}

	public String getEmployees() {
		Employee employee = new Employee();
		EmployeeDB employeeDB = new EmployeeDB();
		supervisor = employeeDB.getSupervisor(supervisor.getId());
		employee.setSupervisor(supervisor);
		
		String[] currentSemesterYear = UtilFunctions.getCurrentSemester();
		employee.setSemester(currentSemesterYear[0]);
		employee.setYear(Integer.parseInt(currentSemesterYear[1]));
		
		searchResult = employeeDB.getEmployees(employee, false,false);
		for(Employee employee2:searchResult)
		{
		if((employee2.getFacultyFlag()==1 && employee2.getAdminFlag()==0)||(employee2.getFacultyFlag()==0 && employee2.getAdminFlag()==0))
		{
			employee2.setOpen("Open");
		}
		else
		{
			employee2.setOpen("Completed");

		}}
			
String[] nextSemesterYear = UtilFunctions.getNextSemester();
Employee employee1 = new Employee();

employee1.setSemester(nextSemesterYear[0]);
employee1.setYear(Integer.parseInt(nextSemesterYear[1]));
employee1.setSupervisor(supervisor);
searchResult1 = employeeDB.getEmployees(employee1, false,false);
for(Employee employee2:searchResult1)
{
if((employee2.getFacultyFlag()==1 && employee2.getAdminFlag()==0)||(employee2.getFacultyFlag()==0 && employee2.getAdminFlag()==0))
{
	employee2.setOpen("Open");
}
else
{
	employee2.setOpen("Completed");

}}
	
		return view();
	}
	
	
		
	

	public String notifyFaculty() throws IOException {
		Employee employee = new Employee();
		EmployeeDB employeeDB = new EmployeeDB();
		employeeDB.updateSecretaryEmail(supervisor.getSecretaryEmail(),
				supervisor.getId());
		String actiondate = request.getParameter("ActionDate");
		logger.debug("ActionDate"+actiondate);
		supervisor = employeeDB.getSupervisor(supervisor.getId());
		logger.debug(supervisor.getFirstName());
		String[] currentSemesterYear = UtilFunctions.getCurrentSemester();
		employee.setSupervisor(supervisor);
		employee.setSemester(currentSemesterYear[0]);
		employee.setYear(Integer.parseInt(currentSemesterYear[1]));
		
		searchResult = employeeDB.getEmployees(employee, false,false);

		StringBuffer students = new StringBuffer();

		for (Employee result : searchResult) {
			students.append(result.getLastName());
			logger.debug(result.getFirstName());
			students.append(", ");
			students.append(result.getFirstName());
			students.append("<br>");
			result.setAdminFlag(CommonConstants.RESET_FLAG);
			result.setFacultyFlag(CommonConstants.RESET_FLAG);
			employeeDB.updateFlags(result);
		}

		EmailTemplate template = employeeDB.getTemplate("NOTIFY");
		logger.debug("email"+supervisor.getEmail());
		String emailMessage = template.getTemplate();
		
		String students1 = students.toString();
		String sem = employee.getSemester();
		Integer year = employee.getYear();
		String Title = employee.getTitle();
		String name = supervisor.getName();
		logger.debug(name+students1+sem+year+Title+emailMessage);
		String[] nextSemesterYear = UtilFunctions.getNextSemester();
		
		employee.setSemester(nextSemesterYear[0]);
		employee.setYear(Integer.parseInt(nextSemesterYear[1]));
		String firstName = supervisor.getName().split(",")[0].trim();
        String lastName = supervisor.getName().split(",")[1].trim();
        String supervisorName = (firstName+" "+lastName);
		emailMessage = emailMessage.replaceAll("\\$FacultyName",
				supervisorName).replaceAll("\\$StudentName",
				students.toString()).replaceAll("\\$Semester", employee.getSemester()).replaceAll("\\$Year", employee.getYear().toString()).replaceAll("\\$ActionDate", actiondate);

		SendMail mailer = new SendMail();
		mailer.sendNotifyFacultyEmail(emailMessage, template, supervisor);
		
		

		message = supervisor.getName() + " has been notified.";

		return view();
	}
	
	/*
	 * Generate LOAs for students
	 */
	
	public String generateLOA() throws IOException 
	{
		EmployeeDB employeeDB = new EmployeeDB();
		int supervisorId = Integer.parseInt(request.getParameter("supervisorId"));
		supervisor = employeeDB.getSupervisor(supervisorId);
		
		String[] currentSemesterYear = UtilFunctions.getNextSemester();
		String currentSemester = currentSemesterYear[0];
		String currentYear = currentSemesterYear[1];
		String startDate = null;
		String endDate = null;
		
		if(currentSemester.equalsIgnoreCase(CommonConstants.SEMESTER_FALL))
		{
			String[] fallDates = CommonConstants.FALL_HIRE_DATES.split(",");
			startDate = fallDates[0] + currentYear;
			endDate = fallDates[1] + currentYear;
		}
		else if(currentSemester.equalsIgnoreCase(CommonConstants.SEMESTER_SPRING))
		{
			String[] springDates = CommonConstants.SPRING_HIRE_DATES.split(",");
			startDate = springDates[0] + currentYear;
			endDate = springDates[1] + currentYear;
		}
		else
		{
			String[] summerDates = CommonConstants.SUMMER_HIRE_DATES.split(",");
			startDate = summerDates[0] + currentYear;
			endDate = summerDates[1] + currentYear;
		}

		PdfGenerator pdfGenerator = new PdfGenerator();
		
		try {
			

			pdfGenerator.generateAppointmentLetters(supervisor, currentSemester, currentYear, startDate, endDate, startDate,"RA");
			
            Employee tempEmployee =  new Employee();
            tempEmployee.setSupervisor(supervisor);
            tempEmployee.setSemester(currentSemester);
        	int year1 = Integer.parseInt(currentYear);

            tempEmployee.setYear(year1);
           
            List<Employee> employeeList = employeeDB.getEmployees(tempEmployee, false, false);
        	
            for(Employee employee : employeeList) {
            	logger.debug(employee.getTitle()+"dcfdfcd");
            	if(employee.getTitle().equals("RA") || employee.getTitle().equals("TA")){
            		
			 employeeDB.updateLOAGeneration(employee.getId());
			 
            	}
            }
		} 
		catch (GeneratorException e) 
		{
			e.printStackTrace();
		}

		message = "LOAs has been generated";
		 return getEmployees();
		 
	}

	public String viewLOA() throws IOException, GeneratorException 
	{ 
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		int supervisorId = Integer.parseInt(request.getParameter("supervisorId"));
		 EmployeeDB employeeDB = new EmployeeDB();
		 Employee employee = new Employee();
		 //employee.setAdminFlag(0);
		// employee.setFacultyFlag(0);
		// employee.setId(employeeId);
		 //employeeDB.updateFlags(employee);
		
	supervisor = employeeDB.getSupervisor(supervisorId);
	logger.debug(supervisor.getName());
	String[] currentSemesterYear = UtilFunctions.getNextSemester();
	String currentSemester = currentSemesterYear[0];
	String currentYear = currentSemesterYear[1];
	
	String directories = CommonConstants.APPOINTMENTLETTERS_LOCATION +pathSep
            + currentYear + pathSep + currentSemester + pathSep + supervisor.getName().split(",")[1].trim()+"_"+supervisor.getName().split(",")[0].trim()+ pathSep;
	Employee tempEmployee = new Employee();
	tempEmployee.setSupervisor(supervisor);
	tempEmployee.setId(employeeId);
	//tempEmployee.setTitle(title);
    List<Employee> employeeList = employeeDB.getEmployees(tempEmployee, false, false);

	 for(Employee employee1 : employeeList) {

        
         String fileName = employee.getFirstName() + "_"
                 + employee.getLastName() + "_" + employee.getUfid() + "_" + employee.getId()+".pdf";
         String fileLoc = directories + fileName;
     logger.debug(fileLoc);
	 }
	
	try {
		setPdfStream(PdfGenerator.viewAppointmentLetters2(employeeList, currentSemester, currentYear));
	} catch (Exception e) {
		message = "LOA has not been generated yet";
		return getEmployees();
		
	}
	logger.debug("value returned");
	return "pdfView";
	}
		
	public String viewEval() throws IOException, GeneratorException 
	{ 
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		int supervisorId = Integer.parseInt(request.getParameter("supervisorId"));
		 EmployeeDB employeeDB = new EmployeeDB();
		 Employee employee = new Employee();
		 //employee.setAdminFlag(0);
		 //employee.setFacultyFlag(0);
		 //employee.setId(employeeId);
		 //employeeDB.updateFlags(employee);
		
	supervisor = employeeDB.getSupervisor(supervisorId);
	logger.debug(supervisor.getName());
	String[] currentSemesterYear = UtilFunctions.getCurrentSemester();
	String currentSemester = currentSemesterYear[0];
	String currentYear = currentSemesterYear[1];
	
	String directories = CommonConstants.EVALUATION_LETTERS_LOCATION+ pathSep
            + currentYear + pathSep + currentSemester + pathSep + supervisor.getName().split(",")[1].trim()+"_"+supervisor.getName().split(",")[0].trim()+ pathSep;
	Employee tempEmployee = new Employee();
	tempEmployee.setSupervisor(supervisor);
	tempEmployee.setId(employeeId);
	//tempEmployee.setTitle(title);
    List<Employee> employeeList = employeeDB.getEmployees(tempEmployee, false, false);

	 for(Employee employee1 : employeeList) {

        
         String fileName = employee.getFirstName() + "_"
                 + employee.getLastName() + "_" + employee.getUfid() + "_" + employee.getId()+".pdf";
         String fileLoc = directories + fileName;
     logger.debug(fileLoc);
	 }
	
	try {
		setPdfStream(PdfGenerator.viewEvalLetters(employeeList, currentSemester, currentYear));
	} catch (Exception e) {
		message = "Evaluation Letter has not been generated yet";
		return getEmployees();
	}
	logger.debug("value returned");
	return "pdfView";
	}
		
	
	public String sendLOA() throws IOException 
	{
		// Get the path
		  try {
	            /*Properties emailProps = new Properties();
	            File emailPropFile = new File("EmailSetting.properties");
	            if (emailPropFile.exists()) 
	            {*/
	               // emailProps.load(new FileInputStream(emailPropFile));
			  		String ccEmailIds = CommonConstants.CCFIELD;
	                String[] ccEmailIdArr = new String[0];
	                if (ccEmailIds != null) 
	                {
	                    ccEmailIdArr = ccEmailIds.split(",");
	                }
	    
	                EmployeeDB employeeDB = new EmployeeDB();
	        		int supervisorId = Integer.parseInt(request.getParameter("supervisorId"));
	        		supervisor = employeeDB.getSupervisor(supervisorId);
	        		
	        		String[] currentSemesterYear = UtilFunctions.getNextSemester();
	        		String currentSemester = currentSemesterYear[0];
	        		String currentYear = currentSemesterYear[1];
	        		
	        		String directories = CommonConstants.APPOINTMENTLETTERS_LOCATION +pathSep
	                        + currentYear + pathSep + currentSemester + pathSep +supervisor.getName().split(",")[1].trim()+"_"+supervisor.getName().split(",")[0].trim()+ pathSep;

//	        		String title  = request.getParameter("title");
//	                if(title.equals("RA")){
//	                	directories = directories+pathSep+"RA\\";
//	                }else if((title.equals("TA"))){
//	                	directories = directories+pathSep+"TA\\";
//	                }
	  
	                new EmailHelper().sendLOAMail(supervisor, directories, ccEmailIdArr);
		  
		  } catch (edu.ufl.ece.studentemployment.exceptions.EmailException e) {
	            logger.error("Error in send mail method", e);
	            
	        }catch (Exception e) {
	            logger.error("Error in send mail method", e);
	        }

		// Go through the list and send LOA to each student
		message = "LOAs has been sent";
		 return getEmployees();
	}

/*
 * Generate ELs for students
 */

public String generateEvaluationLetters() throws IOException 
{
	 logger.debug("inside generateEvaluationLetters");
	EmployeeDB employeeDB = new EmployeeDB();
	int supervisorId = Integer.parseInt(request.getParameter("supervisorId"));
	 logger.debug("inside"+supervisorId);
	supervisor = employeeDB.getSupervisor(supervisorId);
	
	String[] currentSemesterYear = UtilFunctions.getCurrentSemester();
	String currentSemester = currentSemesterYear[0];
	String currentYear = currentSemesterYear[1];
	String startDate = null;
	String endDate = null;
	
	if(currentSemester.equalsIgnoreCase(CommonConstants.SEMESTER_FALL))
	{
		String[] fallDates = CommonConstants.FALL_HIRE_DATES.split(",");
		startDate = fallDates[0] + currentYear;
		endDate = fallDates[1] + currentYear;
	}
	else if(currentSemester.equalsIgnoreCase(CommonConstants.SEMESTER_SPRING))
	{
		String[] springDates = CommonConstants.SPRING_HIRE_DATES.split(",");
		startDate = springDates[0] + currentYear;
		endDate = springDates[1] + currentYear;
	}
	else
	{
		String[] summerDates = CommonConstants.SUMMER_HIRE_DATES.split(",");
		startDate = summerDates[0] + currentYear;
		endDate = summerDates[1] + currentYear;
	}

	PdfGenerator pdfGenerator = new PdfGenerator();
	try {
		logger.debug("calling generateEvaluationForms");
		pdfGenerator.generateEvaluationForms(supervisor, currentSemester, currentYear, startDate, endDate);
		
		 Employee tempEmployee =  new Employee();
         tempEmployee.setSupervisor(supervisor);
         tempEmployee.setSemester(currentSemester);
     	int year1 = Integer.parseInt(currentYear);

         tempEmployee.setYear(year1);
        
         List<Employee> employeeList = employeeDB.getEmployees(tempEmployee, false, false);
     	
         for(Employee employee : employeeList) {
         	logger.debug(employee.getTitle()+"dcfdfcd");
         	if(employee.getTitle().equals("RA") || employee.getTitle().equals("TA")){
         		
			 employeeDB.updateEVALGeneration(employee.getId());
			 
         	}
         }
	} 
	catch (GeneratorException e) 
	{
		e.printStackTrace();
	}

	message = "Evaluation letters has been generated";
	 return getEmployees();
}

public String sendEvaluationLetters() throws IOException 
{
	// Get the path
	  try {
         /*   Properties emailProps = new Properties();
            File emailPropFile = new File("EmailSetting.properties");
            if (emailPropFile.exists()) 
            {*/
            //    emailProps.load(new FileInputStream(emailPropFile));
                String ccEmailIds = CommonConstants.CCFIELD;
                String[] ccEmailIdArr = new String[0];
                if (ccEmailIds != null) 
                {
                    ccEmailIdArr = ccEmailIds.split(",");
                }
    logger.debug("inside sendEvaluationLetters");
                EmployeeDB employeeDB = new EmployeeDB();
        		int supervisorId = Integer.parseInt(request.getParameter("supervisorId"));
        		supervisor = employeeDB.getSupervisor(supervisorId);
        		 logger.debug("inside"+supervisorId);

        		String[] currentSemesterYear = UtilFunctions.getCurrentSemester();
        		String currentSemester = currentSemesterYear[0];
        		String currentYear = currentSemesterYear[1];
        		
        		String directories = CommonConstants.EVALUATION_LETTERS_LOCATION+ pathSep
        	            + currentYear + pathSep + currentSemester + pathSep +supervisor.getName().split(",")[1].trim()+"_"+supervisor.getName().split(",")[0].trim()+ pathSep;
        		 logger.debug("inside"+directories);

        		//String title  = request.getParameter("title");
              /*  if(title.equals("RA")){
                	directories = directories+pathSep+"RA\\";
                }else if((title.equals("TA"))){
                	directories = directories+pathSep+"TA\\";
                }*/
  
                new EmailHelper().sendEvaluationFormMail(supervisor, directories, ccEmailIdArr);
       // }
       
        }  catch (edu.ufl.ece.studentemployment.exceptions.EmailException e) {
            logger.error("Error in send mail method", e);
            
        }catch (Exception e) {
            logger.error("Error in send mail method", e);
        }

	// Go through the list and send LOA to each student
	message = "Evaluation letters has been sent";
	 return getEmployees();
}

public String resetFlag()
{
	int employeeId = Integer.parseInt(request.getParameter("employeeId"));
	int supervisorId = Integer.parseInt(request.getParameter("supervisorId"));
	 EmployeeDB employeeDB = new EmployeeDB();
	 Employee employee = new Employee();
	 employee.setAdminFlag(0);
	 employee.setFacultyFlag(0);
	 employee.setId(employeeId);
	 employeeDB.updateFlags(employee);
	 supervisor = employeeDB.getSupervisor(supervisorId);
	 message = "Faculty has been notified";
	 return getEmployees();
}



}