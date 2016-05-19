package edu.ufl.ece.studentemployment.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import studentemployee.vo.UserVO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import edu.ufl.ece.studentemployment.properties.CommonUtils;

public class LoginInterceptor implements Interceptor {

	public static final long serialVersionUID = 1L;
	Log logger = LogFactory.getLog(LoginInterceptor.class);

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		logger.info("Inside login interceptor");
		final ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(CommonUtils.USERVO);
		String loginValue = request.getParameter("login");
		if(loginValue != null && loginValue.equalsIgnoreCase("true") && request.getParameter("supervisorId") != null)
		{
			session.setAttribute(CommonUtils.LOGINVALUE,loginValue);
			session.setAttribute(CommonUtils.SUPERVISORID,request.getParameter("supervisorId"));
			return "login"; 
		}
		if (userVO != null) {
			return invocation.invoke();
		} else {
			return "login";
		}
	}

}
