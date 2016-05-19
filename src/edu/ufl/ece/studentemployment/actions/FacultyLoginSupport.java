package edu.ufl.ece.studentemployment.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

import edu.ufl.ece.studentemployment.properties.CommonUtils;
import studentemployee.data.Supervisor;
import studentemployee.db.EmployeeDB;
import studentemployee.vo.UserVO;

public class FacultyLoginSupport extends ActionSupport  implements
ServletRequestAware,ServletResponseAware {

	public static final long serialVersionUID = 1L;
	Log logger = LogFactory.getLog(FacultyLoginSupport.class);

	
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	    this.session = request.getSession();
	}

	public HttpServletRequest getServletRequest() {
		return request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getServletResponse() {
		return response;
	}

	
	
	protected void startHibernateSession(){
		
	}
	
	protected void closeHibernateSession(){
		
	}



	
	

	public String submit(String facultyId) throws Exception {
		

		EmployeeDB employeeDB = new EmployeeDB();
		
		HttpSession session = request.getSession();

				try {
					logger.debug("login faculty");
					logger.debug(facultyId);
System.out.println("bjbjkk");
					String supervisorId = facultyId;
					Supervisor supervisor1 = employeeDB.getSupervisor(Integer.parseInt(supervisorId));
logger.debug(supervisor1.getEmail());
					
					UserVO facultyVO = new UserVO();
					facultyVO.setEmail(supervisor1.getEmail());

				session.setAttribute(CommonUtils.USERVO, facultyVO);
					session.setAttribute(CommonUtils.ROLE, "Faculty");
					
					return "FACULTY_SUBMIT";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "error";

			
		}
		
		
	
	
	

}
