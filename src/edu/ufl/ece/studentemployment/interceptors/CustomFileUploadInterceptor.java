package edu.ufl.ece.studentemployment.interceptors;

import java.io.File;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.FileUploadInterceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;

public class CustomFileUploadInterceptor extends FileUploadInterceptor {

	private boolean isFileSizeLessThanMax =true;
	Log logger = LogFactory.getLog(CustomFileUploadInterceptor.class);

	public CustomFileUploadInterceptor() {
		super();
	}

	@Override
	protected boolean acceptFile(Object arg0, File arg1, String arg2,
			String arg3, String arg4, ValidationAware arg5, Locale arg6) {
		// TODO Auto-generated method stub
		isFileSizeLessThanMax =  super.acceptFile(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
		return isFileSizeLessThanMax;
	}
	
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		logger.debug("Inside custom file upload interceptor");
		logger.debug("Accept file status: "+isFileSizeLessThanMax);
	    if(isFileSizeLessThanMax){
	    	return super.intercept(invocation);	    	
	    }else{
	    	HttpServletRequest request = ServletActionContext.getRequest();
	    	request.setAttribute("fileSizeExceeded",true);
	    	return invocation.invoke();	
	    }		
	}

}
