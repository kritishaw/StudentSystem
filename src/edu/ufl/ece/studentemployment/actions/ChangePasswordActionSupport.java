package edu.ufl.ece.studentemployment.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import studentemployee.db.EmployeeDB;
import studentemployee.vo.UserVO;
import edu.ufl.ece.studentemployment.properties.CommonUtils;
import edu.ufl.ece.studentemployment.utils.SHA1Hash;

public class ChangePasswordActionSupport extends BaseActionSupport{


	public static final long serialVersionUID = 1L;
	Log logger = LogFactory.getLog(ChangePasswordActionSupport.class);
	
	private String currentPassword;
	private String newPassword;
	private String confirmPassword;
	private String message;
	
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String oldPassword) {
		this.currentPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String view(){
		return "VIEW";
	}
	
	public String submit(){
		if(!newPassword.equals(confirmPassword)){
			message = "New password and confirm password does not match";
			return view();
		}		
		UserVO userVO = (UserVO)session.getAttribute(CommonUtils.USERVO);
		logger.debug("Password hash in session: "+userVO.getPassword());
		logger.debug("Password hash of the current password: "+SHA1Hash.SHA1(currentPassword));
		if(!SHA1Hash.checkHash(userVO.getPassword(), currentPassword)){
			message = "Current password is not correct";
			return view();
		} 
		userVO.setPassword(SHA1Hash.SHA1(newPassword));
		session.setAttribute(CommonUtils.USERVO, userVO);
		
		new EmployeeDB().storePassword(userVO.getEmail(), userVO.getPassword());
		return "SUCCESS_VIEW";
	} 
	
}
