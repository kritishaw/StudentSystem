package edu.ufl.ece.studentemployment.actions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

import studentemployee.data.Employee;
import studentemployee.data.Project;
import studentemployee.data.Supervisor;
import studentemployee.db.EmployeeDB;
import studentemployee.vo.UserVO;
import edu.ufl.ece.studentemployment.exceptions.GeneratorException;
import edu.ufl.ece.studentemployment.properties.CommonUtils;
import edu.ufl.ece.studentemployment.utils.CommonConstants;
import edu.ufl.ece.studentemployment.utils.EmployeeDetailsPdfWriter;
import edu.ufl.ece.studentemployment.utils.PdfGenerator;
import edu.ufl.ece.studentemployment.utils.UtilFunctions;

public class EmployeeDetailsActionSupport extends BaseActionSupport {

	private Logger logger = Logger
			.getLogger(EmployeeDetailsActionSupport.class);
	private SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	
	private InputStream pdfStream;

	public void setPdfStream(InputStream pdfStream) {
		this.pdfStream = pdfStream;
	}

	public InputStream getPdfStream() {
		return pdfStream;
	}


	private String ufid;
	private Employee employee;
	private static List<String> yearList;
	private List<Supervisor> supervisors;
	private String newNote;
	private String message;
	private int projectCount;
	private String projects;
	

	static {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		yearList = new ArrayList<String>();
		yearList.add(Integer.toString((currentYear+1)));
		for (int i = 0; i < 2; i++) {
			yearList.add(Integer.toString((currentYear - i)));
		}
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getUfid() {
		return ufid;
	}

	public void setUfid(String ufid) {
		this.ufid = ufid;
	}

	public List<String> getYearList() {
		return yearList;
	}

	public List<Supervisor> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(List<Supervisor> supervisors) {
		this.supervisors = supervisors;
	}

	public void setYearList(List<String> yearList) {
		this.yearList = yearList;
	}

	public String getNewNote() {
		return newNote;
	}

	public void setNewNote(String newNote) {
		this.newNote = newNote;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getProjectCount() {
		return projectCount;
	}

	public void setProjectCount(int projectCount) {
		this.projectCount = projectCount;
	}

	public String getProjects() {
		return projects;
	}

	public void setProjects(String projects) {
		this.projects = projects;
	}

	public String view() 
	{
		logger.debug("Inside view method");
		EmployeeDB employeeDB = new EmployeeDB();
		employee = employeeDB.getEmployees(employee, false,false).get(0);
		supervisors = employeeDB.getSupervisors();

		projectCount = employee.getProjects().size();
		logger.debug("Project Count"+projectCount);

		if (projectCount > 0) {

			StringBuffer projectsStr = new StringBuffer();
			for (Project project : employee.getProjects()) {
				projectsStr.append(project.getId());
				projectsStr.append(";;&");
				projectsStr.append(project.getName());
				projectsStr.append(";;&");
				projectsStr.append(project.getNumber());
				projectsStr.append(";;&");
				projectsStr.append(project.getPercentage());
				projectsStr.append(";;&");
				projectsStr.append(formatter.format(project.getStartDate()));
				projectsStr.append(";;&");
				projectsStr.append(formatter.format(project.getEndDate()));
				projectsStr.append(";;&");
			}

			projects = projectsStr.substring(0, projectsStr.length() - 3);
		} else {
			projects = "";
		}
		
		session.setAttribute("FALL_DATES", CommonConstants.FALL_HIRE_DATES);
		session.setAttribute("SPRING_DATES", CommonConstants.SPRING_HIRE_DATES);
		session.setAttribute("SUMMER_DATES", CommonConstants.SUMMER_HIRE_DATES);
		session.setAttribute("CURRENT_YEAR",Calendar.getInstance().get(Calendar.YEAR));
		
		return "DETAILS_VIEW";
	}

	public String updateNotes() {
			logger.debug("Inside update Notes");
		String userName = ((UserVO) session.getAttribute(CommonUtils.USERVO))
				.getFirstName();
		String currDate = new SimpleDateFormat("MM/dd/yy")
				.format(new java.util.Date());

		String noteToAdd = new StringBuffer("On ").append(currDate).append(" ")
				.append(userName).append(" : ").append(newNote).append("<br>")
				.toString();
		newNote = "";
		EmployeeDB employeeDB = new EmployeeDB();
		employeeDB.updateNotes(noteToAdd, employee.getId());

		message = "Note Added";

		return view();
	}

	public String updateEmployee() {
        logger.debug("Inside update employee method");  
		ufid = employee.getUfid();
		System.out.println("Inside updateEmployee");

       logger.debug("ufid"+ufid); 
		EmployeeDB employeeDB = new EmployeeDB();

String[] newProjects=null;

	System.out.println("Projects"+ projects);
			newProjects = projects.split(";;&");
			System.out.println("Projects"+ newProjects.length);
			
			for(int k=0;k<newProjects.length;k++)
			{
				System.out.println("newProjects"+newProjects[k]);

			}

if(newProjects.length<7)
{
	logger.debug("Value updated");
	newProjects[0]="1";
}
		List<Project> newProjectList = new ArrayList<Project>();
		
		if(newProjects.length>1)
		{
			System.out.println("newProjects"+newProjects.length);

		for (int i = 0; i < newProjects.length; i++) {
			if(newProjects[i].equals("-1")&&newProjects[i+1].equals(""))
			{
				logger.debug("break");
				break;
			}
			System.out.println("i"+i);
			System.out.println("i"+newProjects[i]);

			Project tempProject = new Project();
			if(!(newProjects[i].equals("-1")))
				tempProject.setId(Integer.parseInt(newProjects[i]));
			i++;
			tempProject.setName(newProjects[i]);
			i++;
			System.out.println("values"+tempProject.getName());
			tempProject.setNumber(newProjects[i]);
			i++;
			tempProject.setPercentage(Integer.parseInt(newProjects[i]));
			i++;
			tempProject.setUfid(ufid);
			tempProject.setYear(employee.getYear());
			tempProject.setSemester(employee.getSemester());
					
			/*
			 * Get start date and end date values in projects string and set in project object
			 */
			
			try {
		 
				Date startDateValue = formatter.parse(newProjects[i]);
				i++;
				Date endDateValue = formatter.parse(newProjects[i]);
				tempProject.setStartDate(new Timestamp(startDateValue.getTime()));
				tempProject.setEndDate(new Timestamp(endDateValue.getTime()));
		 
			} catch (ParseException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			
			/*
			 * Get start date and end date values in projects string and set in project object
			 */
			
			newProjectList.add(tempProject);
			logger.debug("added"+i);
		}
		
		employee.setProjects(newProjectList);}
		logger.debug("After employee.setProjects");
		System.out.println(employee.getProjects().size());
		try {
			
			String ufid = employee.getUfid();
			String sem =  employee.getSemester();
			int Year = employee.getYear();
			
			System.out.println(ufid+ sem+ Year);
			List<Project> currentProjects = employeeDB.getProjects(
					employee.getUfid(), employee.getSemester(),employee.getYear());
			if(employee.getProjects().size()>0)
			{
				System.out.println("Size1"+currentProjects.size());
				System.out.println("Size2"+employee.getProjects().size());

				//System.out.println(isProjectsUpdateReq(currentProjects, employee.getProjects()));
			employeeDB.updateEmployee(employee,
					true);}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		message = "Data updated successfully";
		
		return view();
	}

	// This method determines if any change has been made in the projects
/*	public boolean isProjectsUpdateReq(List<Project> projects1,
			List<Project> projects2) {
		if (projects1.size() == projects2.size()) {
			Iterator<Project> it1 = projects2.iterator();

			for (Project project : projects1) {
				Project project1 = it1.next();
				if (!(project.getName().equalsIgnoreCase(project1.getName())
						&& project.getNumber().equalsIgnoreCase(project1.getNumber()) && project
							.getPercentage() == project1.getPercentage() && project.getStartDate() == project1.getStartDate() && project.getEndDate() == project1.getEndDate())) {
					return true;
				}
			}
		} else {
			return true;
		}

		return false;
	}*/
	
/*	
	 * Print the employee details
	 
	public String printEmployee()
	{
		EmployeeDetailsPdfWriter employeePdfWriter = new EmployeeDetailsPdfWriter(null);
		
		EmployeeDB employeeDB = new EmployeeDB();
		Employee searchEmployee = new Employee();
		searchEmployee.setId(employee.getId());
		Employee selectedEmployee = employeeDB.getEmployees(searchEmployee, false, false).get(0);
		String destPath = UtilFunctions.getFilePath(null,0,employee.getFirstName()).getAbsolutePath();
		employeePdfWriter.createPdf("Employee_details_template.pdf",selectedEmployee.getProjects().size());
		employeePdfWriter.manipulatePdf("Employee_details_template.pdf", destPath,selectedEmployee);
		
		return view();
	}*/

	/*
	* Print the employee details
	*/
	public String printEmployee() throws DocumentException, IOException
	{
        logger.debug("inside print employee method");
	EmployeeDetailsPdfWriter employeePdfWriter = new EmployeeDetailsPdfWriter(null);
	EmployeeDB employeeDB = new EmployeeDB();
	Employee searchEmployee = new Employee();
    logger.debug("id is"+employee.getId());

	searchEmployee.setId(employee.getId());
	Employee selectedEmployee = employeeDB.getEmployees(searchEmployee,
	false, false).get(0);
	//String destPath = UtilFunctions.getFilePath(null,0,employee.getFirstName()).getAbsolutePath();
	String destPath = null;

    logger.debug("destPath is"+destPath);

	employeePdfWriter.createPdf("Employee_details_template.pdf",selectedEmployee.getProjects().size());
	try {
		setPdfStream(PdfGenerator.manipulatePdf("Employee_details_template.pdf",
		destPath,selectedEmployee));
	} catch (GeneratorException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return "pdfView";
	
	
	}

	
	

	
}
