package edu.ufl.ece.studentemployment.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseActionSupport extends ActionSupport  implements
       ServletRequestAware,ServletResponseAware {
	
	public static final long serialVersionUID = 1L;
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

	public BaseActionSupport() {
		super();
	}	
	
	protected void startHibernateSession(){
		
	}
	
	protected void closeHibernateSession(){
		
	}

}
