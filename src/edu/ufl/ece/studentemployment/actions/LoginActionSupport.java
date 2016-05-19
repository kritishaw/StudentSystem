package edu.ufl.ece.studentemployment.actions;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import studentemployee.data.Employee;
import studentemployee.data.Supervisor;
import studentemployee.db.EmployeeDB;
import studentemployee.vo.UserVO;
import Admission.db.FacultyHandleDB;
import edu.ufl.ece.studentemployment.properties.CommonUtils;
import edu.ufl.ece.studentemployment.utils.SHA1Hash;

public class LoginActionSupport extends BaseActionSupport {

	public static final long serialVersionUID = 1L;
	Log logger = LogFactory.getLog(LoginActionSupport.class);

	private String email;
	private String password;
	private List<Employee> searchResult;

	public List<Employee> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List<Employee> searchResult) {
		this.searchResult = searchResult;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String view() throws Exception {
		logger.info("Inside login view method");
		
		/*
		 * Faculty login from other system
		 */
		String loginValue = (String) session.getAttribute(CommonUtils.LOGINVALUE);
		if( loginValue != null && loginValue.equalsIgnoreCase("true"))
		{
			EmployeeDB employeeDB = new EmployeeDB();
			
			String supervisorId = (String) session.getAttribute(CommonUtils.SUPERVISORID);
			Supervisor supervisor = employeeDB.getSupervisor(Integer.parseInt(supervisorId));
		
			UserVO facultyVO = new UserVO();
			facultyVO.setEmail(supervisor.getEmail());

			session.setAttribute(CommonUtils.USERVO, facultyVO);
			session.setAttribute(CommonUtils.ROLE, "Faculty");
			
			return "FACULTY_SUBMIT";
		} 
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(CommonUtils.USERVO);
		if (userVO != null) {
			return "LOGIN_SUBMIT";
		}
		return "LOGIN_VIEW";
	}

	public String submit() throws Exception {
		Boolean login = null;
		try {
			login = (Boolean) session.getAttribute("login");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EmployeeDB employeeDB = new EmployeeDB();
		email=email.trim();
		password=password.trim();
		if(email.equals("") || password.equals(""))
		{
			request.setAttribute("loginErr", "Invalid username or password");
			return "LOGIN_VIEW";
		}
		Supervisor supervisor = employeeDB.getSupervisor(email);
		UserVO userVO = employeeDB.getUserVO(email);
		UserVO staffUserVO = employeeDB.getStaffVO(email);

		
		if (staffUserVO != null) {
			logger.debug("Match");

			if (SHA1Hash.checkHash(staffUserVO.getPassword(), password)) {
				logger.debug("Match");
				session.setAttribute(CommonUtils.USERVO, staffUserVO);
				
				session.setAttribute(CommonUtils.ROLE,"Faculty");					
				int supervisorId = supervisor.getId();
				Supervisor supervisor1 = employeeDB.getSupervisor(supervisorId);
				
				staffUserVO.setEmail(email);
				return "FACULTY_SUBMIT";
			} 
			else {
				request.setAttribute("loginErr", "Invalid username or password");
				return "LOGIN_VIEW";
			}
		}
		else if (userVO != null) {
			if (SHA1Hash.checkHash(userVO.getPassword(), password)) {
				session.setAttribute(CommonUtils.USERVO, userVO);
				if(supervisor == null){
					session.setAttribute(CommonUtils.ROLE, CommonUtils.ROLE_A);
				} else {
					session.setAttribute(CommonUtils.ROLE, CommonUtils.ROLE_AF);					
				}
				return "LOGIN_SUBMIT";
			} else {
				request.setAttribute("loginErr", "Invalid username or password");
				return "LOGIN_VIEW";
			}}
		else  {
			if (new FacultyHandleDB().validateFacultyLogin(email, password)) {
				UserVO facultyVO = new UserVO();
				facultyVO.setEmail(email);
				facultyVO.setPassword(password);

				session.setAttribute(CommonUtils.USERVO, facultyVO);
				session.setAttribute(CommonUtils.ROLE, "Faculty");
				return "FACULTY_SUBMIT";
			} 
			else if (login) {
				logger.debug("login faculty");
				String supervisorId = (String) session.getAttribute(CommonUtils.SUPERVISORID);
				Supervisor supervisor1 = employeeDB.getSupervisor(Integer.parseInt(supervisorId));
			
				UserVO facultyVO = new UserVO();
				facultyVO.setEmail(supervisor1.getEmail());

				session.setAttribute(CommonUtils.USERVO, facultyVO);
				session.setAttribute(CommonUtils.ROLE, "Faculty");
				
				return "FACULTY_SUBMIT";

			}
			
			else {

				request.setAttribute("loginErr", "Invalid username or password");
				return "LOGIN_VIEW";
			}
		}
		
		
		
	}
	
	
	public String loginSubmitFaculty() throws Exception {
		
		
		EmployeeDB employeeDB = new EmployeeDB();
		
		String supervisorId = (String) session.getAttribute(CommonUtils.SUPERVISORID);
		Supervisor supervisor = employeeDB.getSupervisor(Integer.parseInt(supervisorId));
	
		UserVO facultyVO = new UserVO();
		facultyVO.setEmail(supervisor.getEmail());

		session.setAttribute(CommonUtils.USERVO, facultyVO);
		session.setAttribute(CommonUtils.ROLE, "Faculty");
		
		return "FACULTY_SUBMIT";

	}

	public String signOut() throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute(CommonUtils.USER_NAME, null);
		session.invalidate();
		return "LOGIN_VIEW";
	}
	
	
	public String help()  {
	
		return "HELP_VIEW";
	}
	public String facultyhelp()  {
		
		return "HELP_VIEW";
	}
	
	
	
	
}
