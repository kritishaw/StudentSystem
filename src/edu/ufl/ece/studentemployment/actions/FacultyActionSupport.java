package edu.ufl.ece.studentemployment.actions;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import studentemployee.data.EmailTemplate;
import studentemployee.data.Employee;
import studentemployee.data.Project;
import studentemployee.data.Supervisor;
import studentemployee.db.EmployeeDB;
import studentemployee.utils.SendMail;
import studentemployee.vo.UserVO;
import edu.ufl.ece.studentemployment.properties.CommonUtils;
import edu.ufl.ece.studentemployment.utils.CommonConstants;
import edu.ufl.ece.studentemployment.utils.UtilFunctions;

public class FacultyActionSupport extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(FacultyActionSupport.class);
	private SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

	
	
	private String ufid;
	private Employee employee;
	private static List<String> yearList;
	private String newNote;
	private String facultyMessage;
	private List<Employee> searchResult;
	public List<Employee> getSearchResult1() {
		return searchResult1;
	}

	public void setSearchResult1(List<Employee> searchResult1) {
		this.searchResult1 = searchResult1;
	}

	private List<Employee> searchResult1;

	private String projects;
	private int projectCount;
	private int updateFlag=0;
	private String firstName;
	private String lastName;
	private String email;
	private String message;

	

	private List<Supervisor> supervisors;
	private Supervisor supervisor;
	
	public List<Supervisor> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(List<Supervisor> supervisors) {
		this.supervisors = supervisors;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public int getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(int updateFlag) {
		this.updateFlag = updateFlag;
	}

	public List<String> getYearList() {
		return yearList;
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

	public String getFacultyMessage() {
		return facultyMessage;
	}

	public void setFacultyMessage(String facultyMessage) {
		this.facultyMessage = facultyMessage;
	}

	public List<Employee> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List<Employee> searchResult) {
		this.searchResult = searchResult;
	}
	
	public String getProjects() {
		return projects;
	}

	public void setProjects(String projects) {
		this.projects = projects;
	}

	public int getProjectCount() {
		return projectCount;
	}

	public void setProjectCount(int projectCount) {
		this.projectCount = projectCount;
	}

	public String homeView() 
	{
		EmployeeDB employeeDB = new EmployeeDB();
		employee = new Employee();
		
		String loginValue = (String) session.getAttribute(CommonUtils.LOGINVALUE);
		if(loginValue != null && loginValue.equalsIgnoreCase("true"))
		{
			String supervisorId = (String) session.getAttribute(CommonUtils.SUPERVISORID);
			Supervisor supervisor = employeeDB.getSupervisor(Integer.parseInt(supervisorId));
			employee.setSupervisor(supervisor);
		}
		else
		{
			employee.setSupervisor(employeeDB.getSupervisor(((UserVO) session
				.getAttribute(CommonUtils.USERVO)).getEmail()));
		}
		String[] currentSemesterYear = UtilFunctions.getCurrentSemester();
		employee.setSemester(currentSemesterYear[0]);
		employee.setYear(Integer.parseInt(currentSemesterYear[1]));
String[] nextSemesterYear = UtilFunctions.getNextSemester();
		
		
	Employee employee1 = new Employee();
	
		employee1.setSemester(nextSemesterYear[0]);
		employee1.setYear(Integer.parseInt(nextSemesterYear[1]));
		if(loginValue != null && loginValue.equalsIgnoreCase("true"))
		{
			String supervisorId = (String) session.getAttribute(CommonUtils.SUPERVISORID);
			Supervisor supervisor = employeeDB.getSupervisor(Integer.parseInt(supervisorId));
			employee1.setSupervisor(supervisor);
		}
		else
		{
			employee1.setSupervisor(employeeDB.getSupervisor(((UserVO) session
				.getAttribute(CommonUtils.USERVO)).getEmail()));
		}
		
		session.setAttribute(CommonUtils.SUPERVISORID,null);
		session.setAttribute(CommonUtils.LOGINVALUE,null);
		
		searchResult = employeeDB.getEmployees(employee, false,true);
		searchResult1 = employeeDB.getEmployees(employee1, false,true);
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
			
		return "HOME_VIEW";
	}

	
	

	public String showResultFaculty() {
		logger.debug("Inside show faculty result method");
		//Employee tempEmployee = new Employee();
		EmployeeDB employeeDB = new EmployeeDB();
		/*tempEmployee.setSemester("Fall");
		tempEmployee.setYear(2015);*/
		employee.setSupervisor(employeeDB.getSupervisor(((UserVO) session
				.getAttribute(CommonUtils.USERVO)).getEmail()));
		
		searchResult = employeeDB.getEmployees(employee, false,true);

		if (searchResult.size() == 0) {

			message = "No data found for the entered data. Try putting fewer fields.";

			return homeView();
		}

		return "RESULT_VIEW";
	}
	public String newHireView() {
		EmployeeDB employeeDB = new EmployeeDB();
		employee = new Employee();
		employee.setSupervisor(employeeDB.getSupervisor(((UserVO) session
				.getAttribute(CommonUtils.USERVO)).getEmail()));
		
		session.setAttribute("FALL_DATES", CommonConstants.FALL_HIRE_DATES);
		session.setAttribute("SPRING_DATES", CommonConstants.SPRING_HIRE_DATES);
		session.setAttribute("SUMMER_DATES", CommonConstants.SUMMER_HIRE_DATES);
		session.setAttribute("CURRENT_YEAR",Calendar.getInstance().get(Calendar.YEAR));
		
		String[] nextSemesterYear = UtilFunctions.getNextSemester();
		session.setAttribute("NEXT_SEMESTER",nextSemesterYear[0]);
		session.setAttribute("NEXT_YEAR",nextSemesterYear[1]);
		/*
		 * Code added for populating dates automatically
		 */
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		try 
		{
			employee.setHireDate(dateFormat.format(new Date()));
		} catch (ParseException e) 
		{
			logger.debug("Parse exception while seting the date"+e.getMessage());
		}
			
		return "HIRE_VIEW";
	}

	public String updateNewHire() throws IOException {
		EmployeeDB employeeDB = new EmployeeDB();
		
		ufid = employee.getUfid();
		
		String[] newProjects = projects.split(";;&");
		for(int j=0; j<newProjects.length; j++)
		{
			logger.debug(newProjects[j]);
		}
		List<Project> newProjectList = new ArrayList<Project>();
		for (int i = 0; i < newProjects.length; i++) {
			Project tempProject = new Project();
			tempProject.setName(newProjects[i]);
			i++;
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
			
			SimpleDateFormat timeStampFormatter = new SimpleDateFormat("MM-dd-yyyy");
			try {
		 
				Date startDateValue = timeStampFormatter.parse(newProjects[i]);
				i++;
				Date endDateValue = timeStampFormatter.parse(newProjects[i]);
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
		}
		
		employee.setProjects(newProjectList);
		
		Supervisor supervisor = employeeDB.getSupervisor(((UserVO) session
				.getAttribute(CommonUtils.USERVO)).getEmail());
		logger.debug("After getting supervisor");
		employee.setSupervisor(supervisor);
		if((request.getParameter("employee.annualRate")) != null)
		{
		employee.setAnnualRate(Float.parseFloat(request.getParameter("employee.annualRate")));}
		if((request.getParameter("employee.biweeklyRate")) != null)
		{
		employee.setBiweeklyRate(Float.parseFloat(request.getParameter("employee.biweeklyRate")));}
		if((request.getParameter("employee.emplRecord")) != null)
		{
		employee.setEmplRecord(Integer.parseInt(request.getParameter("employee.emplRecord")));}

		employee.setAdminFlag(CommonConstants.RESET_FLAG);
		employee.setFacultyFlag(CommonConstants.SET_FLAG);
		
		logger.debug(employee.getBiweeklyRate());
		logger.debug(employee.getAnnualRate());
		logger.debug(employee.getEmplRecord());

		
		
		try {
			
			
			employeeDB.insertEmployee(employee);
			if((employee.getNotes()!=null)||(!employee.getNotes().equals("")))
			{
			logger.debug(employee.getNotes());
			logger.debug("Inside add Notes");
			String userName = ((UserVO) session.getAttribute(CommonUtils.USERVO))
					.getFirstName();
			String currDate = new SimpleDateFormat("MM/dd/yy")
					.format(new java.util.Date());

			String noteToAdd = new StringBuffer("On ").append(currDate).append(" ")
					.append(userName).append(" : ").append(employee.getNotes()).append("<br>")
					.toString();
			
			
			employeeDB.updateNotes(noteToAdd, employee.getId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("After inserting");

	EmailTemplate template = employeeDB.getTemplate("NEW_HIRE");
		String message = template.getTemplate();

		message = message.replaceAll("\\$professor", supervisor.getName())
				.replaceAll("\\$Student",
						employee.getFirstName() + " " + employee.getLastName());
		new SendMail().sendAdminNotification(message, template,
				template.getToAddress());
		
		
		
		 
		template = employeeDB.getTemplate("NEW_HIRE_FACULTY");
		message = template.getTemplate();

		message = message.replaceAll("\\$professor", supervisor.getName())
				.replaceAll("\\$Student",
						employee.getFirstName() + " " + employee.getLastName());
		new SendMail().sendAdminNotification(message, template,
				supervisor.getEmail());
		
		facultyMessage = employee.getFirstName() + " " + employee.getLastName()
				+ " has been added and payroll office has been notified.";

		return homeView();
	}

	/*
	 * Get the ufid of the employee and returns the employee history
	 */
	public String employeeHistoryView() 
	{
		logger.debug("Inside employeeHistoryView");
		EmployeeDB employeeDB = new EmployeeDB();
		String ufid=employee.getUfid();
		employee = new Employee();
		employee.setUfid(ufid);
		logger.debug("Inside employeeHistoryView");


		

		employee.setSupervisor(employee.getSupervisor());
		logger.debug("Inside employeeHistoryView");

		searchResult = employeeDB.getEmployees(employee, false,false);
		return "RESULT_VIEW";
	}
	
	public String detailsView() {
		
		session.setAttribute("FALL_DATES", CommonConstants.FALL_HIRE_DATES);
		session.setAttribute("SPRING_DATES", CommonConstants.SPRING_HIRE_DATES);
		session.setAttribute("SUMMER_DATES", CommonConstants.SUMMER_HIRE_DATES);
		session.setAttribute("CURRENT_YEAR",Calendar.getInstance().get(Calendar.YEAR));
		
		/*
		 * Code added for getting the current semester and year
		 */
		String[] nextSemester = UtilFunctions.getCurrentSemester();
		session.setAttribute("CURRENT_SEMESTER", nextSemester[0]);
		session.setAttribute("CURRENT_YEAR", Integer.parseInt(nextSemester[1]));
		
		
		EmployeeDB employeeDB = new EmployeeDB();
		employee = employeeDB.getEmployees(employee, false,false).get(0);
		logger.debug(employee.getFirstName());
		session.setAttribute("TITLE", employee.getTitle());
		
		if((employee.getFacultyFlag()==1 && employee.getAdminFlag()==0)||(employee.getFacultyFlag()==0 && employee.getAdminFlag()==0))
		{
			logger.debug("inside flag");

	updateFlag=1;
		}
	else
		{
		updateFlag=0;
		}
		
		projectCount = employee.getProjects().size();

		if (projectCount > 0) {
			logger.debug(projectCount);

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

	return detailsView();
}


	public String reappoint() throws IOException {
		EmployeeDB employeeDB = new EmployeeDB();
		ufid = employee.getUfid();
		logger.debug("Inside reappoint");
		
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
			
		
		
		employee.setProjects(newProjectList);
		
		if(updateFlag==1) 
		{
			List<Project> currentProjects = employeeDB.getProjects(
					employee.getUfid(), employee.getSemester(),employee.getYear());
			employee.setFacultyFlag(CommonConstants.SET_FLAG);
			employee.setAdminFlag(CommonConstants.RESET_FLAG);
			employeeDB.updateEmployee(employee,
					true);

			facultyMessage = "Data updated successfully";
			return detailsView();
			
		}
		
		Supervisor supervisor = employeeDB.getSupervisor(((UserVO) session
				.getAttribute(CommonUtils.USERVO)).getEmail());
		employee.setSupervisor(supervisor);

		/*
		 * Set faculty flag and reset admin flag
		 */
		employee.setAdminFlag(CommonConstants.RESET_FLAG);
		employee.setFacultyFlag(CommonConstants.SET_FLAG);
		
		if(employee.getStatus().equals(null) || (!employee.getStatus().equals(null) && employee.getStatus().length()==0) || (!employee.getStatus().equals(null) && employee.getStatus().equalsIgnoreCase("TERMINATED")))
		{
			logger.debug("updating reappoint");
			employeeDB.updateFlags(employee);
			employeeDB.updateStatus("REAPPOINTED", employee.getId());
		
			employeeDB.insertEmployee(employee);
			
		}
		else if(employee.getStatus().equalsIgnoreCase("REAPPOINTED"))
		{
			String[] nextSemester = UtilFunctions.getNextSemester();
			
			Employee reappointedEmployee = new Employee();
			reappointedEmployee.setUfid(ufid);
			reappointedEmployee.setSemester(nextSemester[0]);
			reappointedEmployee.setYear(Integer.parseInt(nextSemester[1]));
			
			Employee removeEmployee = employeeDB.getEmployees(reappointedEmployee, false, false).get(0);
			employeeDB.deleteEmployee(removeEmployee);
			employeeDB.insertEmployee(employee);
		}

		
		EmailTemplate template = employeeDB.getTemplate("REAPPOINT");
		String message = template.getTemplate();
		message = message.replaceAll("\\$professor", supervisor.getName())
				.replaceAll("\\$Student",
						employee.getFirstName() + " " + employee.getLastName());
		new SendMail().sendAdminNotification(message, template,
				template.getToAddress());

		/*
		 * Code added to send notification mail to faculty
		 */
		template = employeeDB.getTemplate("REAPPOINT_FACULTY");
		message = template.getTemplate();
		message = message.replaceAll("\\$professor", supervisor.getName())
				.replaceAll("\\$Student",
						employee.getFirstName() + " " + employee.getLastName());
		new SendMail().sendAdminNotification(message, template,
				supervisor.getEmail());
		
		facultyMessage = employee.getFirstName()
				+ " "
				+ employee.getLastName()
				+ " has been reappointed for next semester and payroll office has been notified.";

		return homeView();
	}

	public String terminate() throws IOException {
		EmployeeDB employeeDB = new EmployeeDB();
		logger.debug("terminate");
		Supervisor supervisor = employeeDB.getSupervisor(((UserVO) session
				.getAttribute(CommonUtils.USERVO)).getEmail());
		
		employee = employeeDB.getEmployees(employee, false, false).get(0);
		String terminateText = (String) request.getParameter("terminationText");
		
		/*
		 * set faculty flag and reset admin flag
		 */
		employee.setAdminFlag(CommonConstants.RESET_FLAG);
		employee.setFacultyFlag(CommonConstants.SET_FLAG);
		if(employee.getNotes()==null)//|| employee.getStatus().equals(""))
		{
			logger.debug("sdsds"+employee.getNotes());
			employee.setNotes( terminateText + ": Terminated by :" +supervisor.getName());}
		else
			employee.setNotes( terminateText + ": Terminated by :" +supervisor.getName());
		
		if(employee.getStatus()==null || employee.getStatus().equals(""))
		{
			employeeDB.updateStatus("TERMINATED", employee.getId());
			employeeDB.updateFlags(employee);
			employeeDB.updateNotes(employee.getNotes(), employee.getId());
		}
		else if(employee.getStatus().equalsIgnoreCase("REAPPOINTED"))
		{
			String[] nextSemester = UtilFunctions.getNextSemester();
			
			Employee terminatedEmployee = new Employee();
			terminatedEmployee.setUfid(employee.getUfid());
			terminatedEmployee.setSemester(nextSemester[0]);
			terminatedEmployee.setYear(Integer.parseInt(nextSemester[1]));
			//terminatedEmployee.setId(employee.getId());
			
			Employee removeEmployee = employeeDB.getEmployees(terminatedEmployee, false, false).get(0);
			logger.debug(removeEmployee.getFirstName());

			employeeDB.deleteEmployee(removeEmployee);
			employeeDB.updateStatus("TERMINATED", employee.getId());
			employeeDB.updateFlags(employee);
			employeeDB.updateNotes(employee.getNotes(), employee.getId());
		}
		
		EmailTemplate template = employeeDB.getTemplate("TERMINATE");
		String message = template.getTemplate();
		message = message.replaceAll("\\$professor", supervisor.getName())
				.replaceAll("\\$Student",
						employee.getFirstName() + " " + employee.getLastName());
		new SendMail().sendAdminNotification(message, template,
				template.getToAddress());

		
		/*
		 * Code added to send notification mail to faculty
		 */
		template = employeeDB.getTemplate("TERMINATE_FACULTY");
		message = template.getTemplate();
		message = message.replaceAll("\\$professor", supervisor.getName())
				.replaceAll("\\$Student",
						employee.getFirstName() + " " + employee.getLastName());
		new SendMail().sendAdminNotification(message, template,
				supervisor.getEmail());
		
		facultyMessage = employee.getFirstName()
				+ " "
				+ employee.getLastName()
				+ " has been terminated for next semester and payroll office has been notified.";
		logger.debug(facultyMessage);
		return homeView();
	}
	
	public String addFacultyView()
	{
		return "RESULT_VIEW";
	}

	public String addFacultyInfo() 
	{
		//System.out.println("cfvfvfvf");
		EmployeeDB employeeDB = new EmployeeDB();
		
		String firstName = supervisor.getFirstName().trim();
		String email = supervisor.getEmail();
		System.out.println(firstName);
		String lastName = supervisor.getLastName().trim();
		System.out.println(lastName);
		
		System.out.println(email);
		String secretaryEmail = supervisor.getSecretaryEmail();
		System.out.println(secretaryEmail);
		
		StringBuilder supervisorName = new StringBuilder();
		supervisorName.append(lastName).append( ",").append(firstName);
		supervisor.setName(supervisorName.toString());
		supervisor.setActive(1);
		System.out.println(email);
		supervisor.setEmail(email);
		supervisor.setSecretaryEmail(secretaryEmail);
		employeeDB.addSupervisor(supervisor);
		message = "Supervisor : " + supervisor.getName() + " has been added";
		
		/*EmailTemplate template = employeeDB.getTemplate("NEW_FACULTY");
		String message = template.getTemplate();
		message = message.replaceAll("\\$SupervisorName", firstName + " " + lastName)
				.replaceAll("\\$Email",
						email);
		try {
			new SendMail().sendAdminNotification(message, template,
					template.getToAddress());
			
			System.out.println("Mail sent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return addFacultyView();
	}
	
	public String editFacultyView()
	{
		EmployeeDB employeeDB = new EmployeeDB();
		supervisors = employeeDB.getSupervisorList();
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
			session.setAttribute(CommonConstants.SUPERVISOR_NAME,supervisor.getName());
		}
		else
		{
			supervisor = supervisors.get(0);
			session.setAttribute(CommonConstants.SUPERVISOR_NAME,"");
		}
		
		String[] nameParts = supervisor.getName().split(",");
		supervisor.setName(nameParts[1].trim() + ", " + nameParts[0].trim());
		firstName = nameParts[1].trim();
		lastName = nameParts[0].trim();
		
		return "RESULT_VIEW";
	}
	
	public String getFacultyInfo() 
	{
		System.out.println("getfacultyinfo");
		EmployeeDB employeeDB = new EmployeeDB();
		supervisor = employeeDB.getSupervisor(supervisor.getId());
		if(supervisor!=null)
		{
			String[] nameParts = supervisor.getName().split(",");
			firstName = nameParts[1].trim();
			lastName = nameParts[0].trim();
		}
		return editFacultyView();
	}
	
	public String editFacultyInfo() 
	{
		EmployeeDB employeeDB = new EmployeeDB();
		int   selectedItem=Integer.parseInt(request.getParameter("slvalue"));

		StringBuilder supervisorName = new StringBuilder();
		supervisorName.append(lastName).append( ",").append(firstName);
		supervisor.setName(supervisorName.toString());
		int active = supervisor.getActive();
		supervisor.setActive(selectedItem);
		String secretaryEmail = supervisor.getSecretaryEmail();
		System.out.println("fdcdcd"+secretaryEmail);

		employeeDB.editSupervisor(supervisor);
		facultyMessage = "Details has been updated for Supervisor : " + supervisor.getName();
		return editFacultyView();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
