package edu.ufl.ece.studentemployment.actions;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import edu.ufl.ece.studentemployment.utils.CommonConstants;
import studentemployee.data.EmailTemplate;
import studentemployee.data.Supervisor;
import studentemployee.db.EmployeeDB;
import studentemployee.utils.SendMail;
import studentemployee.vo.UserVO;

public class AccountManagementActionSupport extends BaseActionSupport 
{
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(AccountManagementActionSupport.class);
	private UserVO user;
	private String message;
	private List<UserVO> users;
	private String firstName;
	private String lastName;
	private String userMessage;
	private String email;
	private String id;
	private String password;

	
	
	public List<UserVO> getUsers() {
		return users;
	}

	public void setUsers(List<UserVO> users) {
		this.users = users;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public String addUserView()
	{
		return "RESULT_VIEW";
	}

	public String addUserInfo() 
	{
		EmployeeDB employeeDB = new EmployeeDB();
		employeeDB.addUser(user);
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String email = user.getEmail();
		System.out.println(email);
		String password = user.getPassword();
		message = "User has been added";
		
	EmailTemplate template = employeeDB.getTemplate("NEW_USER");
		String message = template.getTemplate();
		message = message.replaceAll("\\$AdminName", firstName + " " + lastName)
				.replaceAll("\\$Email",
						email).replaceAll("\\$Password",
								password);
		try {
			new SendMail().sendAdminNotification(message, template,
					template.getToAddress());
			
			System.out.println("Mail sent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addUserView();
	
	}	
	public String editUserView()
	{
		EmployeeDB employeeDB = new EmployeeDB();
		users = employeeDB.getUsers();
		for(UserVO userObj: users)
		{
			if(userObj.getFirstName()!=null)
			{
				String nameParts1 = userObj.getFirstName();
				

				userObj.setFirstName(nameParts1);
				
				
			}
			if(userObj.getLastName()!=null)
			{
				String nameParts2 = userObj.getLastName();
				userObj.setLastName(nameParts2);
				System.out.println(nameParts2);
				email = userObj.getEmail();
				id = userObj.getId();



			}
		}
		
		if(user!=null)
		{
			String nameParts1 = user.getFirstName();
			user.setFirstName(nameParts1);
			String nameParts2 = user.getLastName();
			user.setLastName(nameParts2);
			email = user.getEmail();
			id = user.getId();
			
			

			session.setAttribute(user.getFirstName(), firstName);
			session.setAttribute(user.getFirstName(), lastName);
			session.setAttribute(user.getEmail(), email);
			session.setAttribute(user.getId(), id);
		


		}
		else
		{
			user = users.get(0);
			session.setAttribute(user.getFirstName(), "");
			session.setAttribute(user.getFirstName(), "");
			session.setAttribute(user.getEmail(), "");
			session.setAttribute(user.getId(), "");

		}
			return "RESULT_VIEW";
	}
	
	public String getUserInfo() 
	{
		

		EmployeeDB employeeDB = new EmployeeDB();
        String email = user.getEmail();
        String id = user.getId();
        System.out.println("The id is"+ id);
	
        
		user = employeeDB.getUser(user.getEmail());
		if(user!=null)
		{
			 firstName = user.getFirstName();
			lastName = user.getLastName();
             email = user.getEmail();
             id = user.getId();
             session.setAttribute(user.getFirstName(), "");
 			session.setAttribute(user.getFirstName(), "");
 			session.setAttribute(user.getEmail(), "");
 			session.setAttribute(user.getId(), "");

		}
		return editUserView();
	}
	
	public String editUserInfo() 
	{
		
		
			/*EmployeeDB employeeDB = new EmployeeDB();
			StringBuilder supervisorName = new StringBuilder();
			supervisorName.append(firstName).append( " , ").append(lastName);
			user.setFirstName(firstName);
			employeeDB.editSupervisor(supervisor);
			facultyMessage = "Details has been updated for Supervisor : " + supervisor.getName();
			return editFacultyView();*/
		
		System.out.println("editUserInfo");

		EmployeeDB employeeDB = new EmployeeDB();
		StringBuilder userName = new StringBuilder();
		String firstName1 = user.getFirstName();
		String lastName1 = user.getLastName();
		String email1 = user.getEmail();
		String id1= user.getId();
		System.out.println(email1);


		userName.append(firstName).append( " , ").append(lastName);
		System.out.println(firstName1);

		user.setFirstName(firstName1);
		user.setLastName(lastName1);
		user.setEmail(email1);
		user.setId(id1);

		session.setAttribute(user.getFirstName(), "");
		session.setAttribute(user.getFirstName(), "");
		session.setAttribute(user.getEmail(), "");
		session.setAttribute(user.getId(), "");

		

		employeeDB.editUser(user);
		message = "Details has been updated for User : " + user.getFirstName()+ user.getLastName();
		return editUserView();
		
		
		
	}
	public String resetPassword() 
	{
		String id1= user.getId();
		user.setId(id1);
			/*EmployeeDB employeeDB = new EmployeeDB();
			StringBuilder supervisorName = new StringBuilder();
			supervisorName.append(firstName).append( " , ").append(lastName);
			user.setFirstName(firstName);
			employeeDB.editSupervisor(supervisor);
			facultyMessage = "Details has been updated for Supervisor : " + supervisor.getName();
			return editFacultyView();*/
		
		System.out.println("resetPassword");

		EmployeeDB employeeDB = new EmployeeDB();
		
		String password = user.getPassword();
		
		System.out.println(password);
		System.out.println(id1);


		
		user.setPassword(password);

		
		

		employeeDB.editUserPassword(user);
		message = "Password has been reset for " + user.getFirstName()+ user.getLastName();
		return editUserView();
		
		
		
	}
}
