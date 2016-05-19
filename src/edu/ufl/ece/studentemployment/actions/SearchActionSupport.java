package edu.ufl.ece.studentemployment.actions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import edu.ufl.ece.studentemployment.utils.CommonConstants;
import edu.ufl.ece.studentemployment.utils.UtilFunctions;
import studentemployee.data.Employee;
import studentemployee.data.Supervisor;
import studentemployee.db.EmployeeDB;

public class SearchActionSupport extends BaseActionSupport {

	private Logger logger = Logger.getLogger(SearchActionSupport.class);
	private String ufid;
	private Employee employee;
	private List<Employee> searchResult;
	private List<Supervisor> supervisors;
	private static List<String> yearList;
	private String message;
	private String filePath;

	private InputStream excelStream;

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}


	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	static {
		int startYear = Calendar.getInstance().get(Calendar.YEAR);
		yearList = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			yearList.add(Integer.toString((startYear - i)));
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

	public List<Employee> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List<Employee> searchResult) {
		this.searchResult = searchResult;
	}

	public List<Supervisor> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(List<Supervisor> supervisors) {
		this.supervisors = supervisors;
	}

	public List<String> getYearList() {
		return yearList;
	}

	public void setYearList(List<String> yearList) {
		this.yearList = yearList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String view() {
		EmployeeDB employeeDB = new EmployeeDB();
		supervisors = employeeDB.getSupervisors();

		return "SEARCH_VIEW";
	}

	public String showResult() {
		logger.debug("Inside show search result method");
		EmployeeDB employeeDB = new EmployeeDB();
		searchResult = employeeDB.getEmployees(employee, false,true);

		if (searchResult.size() == 0) {

			message = "No data found for the entered data. Try putting fewer fields.";

			return view();
		}

		return "RESULT_VIEW";
	}
	
	
	

	/*
	 * Dashboard which provides the view of all the new employees 
	 */
	public String newEmployeesView() 
	{
		logger.debug("Inside show search result method");
		EmployeeDB employeeDB = new EmployeeDB();
		searchResult = employeeDB.getNewEmployees();

		if (searchResult.size() == 0) {

			return view();
		}

		return "RESULT_VIEW";
	}
	
	
	/*
	 * Approve the employee by setting the admin flag as true
	 */
	public String approveEmployee() 
	{
		EmployeeDB employeeDB = new EmployeeDB();
		employee.setAdminFlag(CommonConstants.SET_FLAG);
		employee.setFacultyFlag(CommonConstants.SET_FLAG);
		employeeDB.updateFlags(employee);
		return newEmployeesView();
	}
	
	/*
	 * Display records for the selected employee 
	 */
	public String showEmployeeResult() 
	{
		logger.debug("Inside show employee result method");
		EmployeeDB employeeDB = new EmployeeDB();
		
		if(searchResult!=null)
			searchResult.clear();
		
		searchResult = employeeDB.getEmployeeRecords(employee);

		if (searchResult.size() == 0) {

			message = "No data found for the entered data. Try putting fewer fields.";

			return view();
		}
		
		return "RESULT_VIEW";
	}
	
	
	/*
	 * Show snapshot view
	 */
	
	public String showSnapshotView() 
	{
		return "RESULT_VIEW";
	}
	
	/*
	 * Export the snapshot view to Excel
	 */

	public String exportToExcel()
	{
		logger.debug("Inside export excel method");
		EmployeeDB employeeDB = new EmployeeDB();
		searchResult = employeeDB.getEmployees(employee, false,false);

		if (searchResult.size() == 0) 
		{
			message = "No data found for the entered data. Try putting fewer fields.";
			return view();
		}

		setExcelStream(UtilFunctions.exportToExcel(searchResult));
		message = "Snapshot is exported to excel. The excel sheet is saved on your desktop";
		return "EXCEL_VIEW";
	}
}
