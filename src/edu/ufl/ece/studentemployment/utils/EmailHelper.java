/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufl.ece.studentemployment.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import studentemployee.data.EmailTemplate;
import studentemployee.data.Employee;
import studentemployee.data.Supervisor;
import studentemployee.db.EmployeeDB;
import studentemployee.utils.SendMail;
import edu.ufl.ece.studentemployment.exceptions.EmailException;

/**
 *
 * @author Saurabh
 */
public class EmailHelper {

    public Properties emailSettingProps;
    private Properties emailTemplateProps;

    private static Logger logger = Logger.getLogger(EmailHelper.class);
    private static String opsEmailContent = "<html><body><p>Professor $professor</p><p>It is time to consider re-appointments, new hires and terminations for the upcoming semester.  </p><p>Please review the attached (below listed) information.<br>$nameList If there are changes that need to be made, please indicate on the form.</p><p>Questions can be directed to Marcus in the Payroll/Personnel office at 846-3945 or by sending an e-mail to payroll@ece.ufl.edu. </p><p>Regards,  <br> Marcus Moore <br>Electrical and Computer Engineering<br>Payroll/Personnel<br>University of Florida<br>226 Larsen Hall<br>Gainesville, FL  32611<br>352.846.3945<br>352.392.1860 (Fax)<br></body></html>";
    private String raEmailContent = "<html><body><p>Professor $professor</p><p>It is time to consider re-appointments, new hires and terminations for the upcoming semester.  </p><p>Please review the attached (below listed) information.  If there are changes that need to be made, please indicate on the form.</p><p>Questions can be directed to Marcus in the Payroll/Personnel office at 846-3945 or by sending an e-mail to payroll@ece.ufl.edu. </p><p>Regards,  <br> Marcus Moore <br>Electrical and Computer Engineering<br>Payroll/Personnel<br>University of Florida<br>226 Larsen Hall<br>Gainesville, FL  32611<br>352.846.3945<br>352.392.1860 (Fax)<br></body></html>";

    public EmailHelper() throws EmailException{
        File emailTemplateSetting = new File("EmailTemplateSetting.properties");
        emailTemplateProps = new Properties();
        if(emailTemplateSetting.exists()){
            try {
                emailTemplateProps.load(new FileInputStream(emailTemplateSetting));
            } catch (IOException e) {
                logger.error("Error while loading email template setting file");
                throw new EmailException();
            }

        }else{
          
        }
    }

    public void sendLOAMail(Supervisor supervisor, String dirLocation,
                            String[] ccRecipients ) throws EmailException {

        try {
        	EmployeeDB employeeDB = new EmployeeDB();
        	Message msg = new SendMail().setMailParameters(null, "Letter of Appointment", null, "payroll@ece.ufl.edu",ccRecipients);
        
            //Based on the name of the Professor get the list of Students under him
            //for each student, get the email, name, ufid
            //Attach the file created from the name and the email id of the student.
            //Send the mail to the student
        	Employee tempEmployee = new Employee();
        	tempEmployee.setSupervisor(supervisor);
        	String[] currentSemesterYear = UtilFunctions.getNextSemester();
        	tempEmployee.setSupervisor(supervisor);
        	tempEmployee.setSemester(currentSemesterYear[0]);
        	tempEmployee.setYear(Integer.parseInt(currentSemesterYear[1]));
        	//tempEmployee.setTitle(title);
            List<Employee> employeeList = employeeDB.getEmployees(tempEmployee, false, false);
            for(Employee employee : employeeList) {
            	if(employee.getTitle().equals("RA") || employee.getTitle().equals("TA")){
                    msg.setRecipient(Message.RecipientType.TO,
                            new InternetAddress(employee.getEmail()));
                    String fileName = employee.getFirstName() + "_" + employee.getLastName()
                            + "_" + employee.getUfid() + "_" + employee.getId()+".pdf";
                    String fileLoc = dirLocation + fileName;
                    // Create the message part
                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    // Fill the message
                    
                    // Get the message
                    EmailTemplate template = employeeDB.getTemplate("LOA_MAILS");
                    
            		String message = template.getTemplate();
            		message = message.replaceAll("\\$professor", supervisor.getName())
            				.replaceAll("\\$StudentName",
            						employee.getFirstName() + " " + employee.getLastName()).replaceAll("\\$Semester", employee.getSemester()).replaceAll("\\$Year", employee.getYear().toString());
;
            		
                    messageBodyPart.setText(message);
                    messageBodyPart.addHeaderLine("Content-Type: text/html; charset=\"iso-8859-1\"");
                    messageBodyPart.addHeaderLine("Content-Transfer-Encoding: quoted-printable");
                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);
                    logger.debug("Attachment: " + fileLoc);
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(fileLoc);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(fileName);
                    multipart.addBodyPart(messageBodyPart);
                    // Put parts in message
                    msg.setContent(multipart);
                    msg.setSentDate(new Date());
                    logger.debug("Sending a mail to " + employee.getEmail());
                    Transport.send(msg);
                
            }}
        }catch (FileNotFoundException e) {
            logger.error("Exception in sending mail ",e);
            throw new EmailException();
        }catch (IOException e) {
            logger.error("Exception in sending mail ",e);
            throw new EmailException();
        } catch (MessagingException e) {
            logger.debug("In message exception block");
            logger.error("Exception in sending mail ",e);

            throw new EmailException();
        }catch (Exception e) {
            logger.error("Exception in sending mail ",e);
            throw new EmailException();
        }


    }



    @SuppressWarnings("null")
	public boolean sendEvaluationFormMail(Supervisor supervisor, String dirLocation,
                            String[] ccRecipients ) throws EmailException 
    {
      try 
      {
    	  
    		Employee employee = new Employee();
    		EmployeeDB employeeDB = new EmployeeDB();
    		employeeDB.updateSecretaryEmail(supervisor.getSecretaryEmail(),
    				supervisor.getId());
    		supervisor = employeeDB.getSupervisor(supervisor.getId());
    		logger.debug(supervisor.getFirstName());
    		String[] currentSemesterYear = UtilFunctions.getCurrentSemester();
    		employee.setSupervisor(supervisor);
    		employee.setSemester(currentSemesterYear[0]);
    		employee.setYear(Integer.parseInt(currentSemesterYear[1]));
    		
    		List<Employee> searchResult = employeeDB.getEmployees(employee, false,false);

    		StringBuffer students = new StringBuffer();

    		for (Employee result : searchResult) {
    			
    			students.append(result.getLastName());
    			logger.debug(result.getFirstName());
    			students.append(", ");
    			students.append(result.getFirstName());
    			students.append("<br>");
    			//result.setAdminFlag(CommonConstants.RESET_FLAG);
    			//result.setFacultyFlag(CommonConstants.RESET_FLAG);
    			//employeeDB.updateFlags(result);
    		}

    	  
    	    logger.debug("inside sendEvaluationFormMail");

            // Modifying the location of directory location
            //dirLocation = getDefaultDir()+ dirLocation;

        	String[] recipients = new String[1];
        	logger.debug("supervisor.getEmail()"+supervisor.getEmail());
        	recipients[0]= supervisor.getEmail();
         String sender = "payroll@ece.ufl.edu";
        	Message msg = new SendMail().setMailParameters(recipients, "Evaluation forms", null, sender,ccRecipients);
        	
            /*File emailSettingFile = new File("EmailSetting.properties");
          
            emailSettingProps = new Properties();
            emailSettingProps.load(new FileInputStream(emailSettingFile));
            // Set the host smtp address
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", emailSettingProps.getProperty("mail.smtp.host"));
            props.put("mail.smtp.port", emailSettingProps.getProperty("mail.smtp.port"));

            props.put("mail.smtp.starttls.enable", "true");

            Session session;
            if(emailSettingProps.getProperty("authSelected") !=null
                              && emailSettingProps.getProperty("authSelected").equals("true")){
                props.put("mail.smtp.auth", "true");
              Authenticator auth = new SMTPAuthenticator();
              session = Session.getDefaultInstance(props, auth);
            }else{
                session = Session.getDefaultInstance(props);
            }



            //session.setDebugOut(LogUtils.getDebugStream(logger));
            //session.setDebug(debug);

            // create a message
            Message msg = new MimeMessage(session);

            // set the from and to address
            InternetAddress addressFrom = new InternetAddress(emailSettingProps.getProperty("fromMail"));
            msg.setFrom(addressFrom);
            List<InternetAddress> ccAddress = new ArrayList<InternetAddress>();
            for (int i = 0; i < ccRecipients.length; i++) {
                if(ccRecipients[i] !=null && !ccRecipients[i].trim().equals(""))
                ccAddress.add(new InternetAddress(ccRecipients[i]));
            }
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(profEmail));
            if(ccAddress.size() != 0)
                msg.setRecipients(Message.RecipientType.CC, ccAddress.toArray(new InternetAddress[ccAddress.size()]));

            //Changing the subject according to the form type
            subject = "RA Evaluation forms";
            if((StudentAssistantFormGeneratorView.formType.getSelectedItem().equals("TA"))){
                subject = "TA Evaluation forms";
            }
            // Setting the Subject and Content Type
            msg.setSubject(subject);
            //msg.setContent(message, "text/html");
*/
        	
        	

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            // Fill the message
            
            EmailTemplate template = employeeDB.getTemplate("EVALUATION_MAILS");
    		String message = template.getTemplate();
    		 String firstName = supervisor.getName().split(",")[0].trim();
             String lastName = supervisor.getName().split(",")[1].trim();
           supervisor.setName(firstName+" "+lastName);
    		message = message.replaceAll("\\$SupervisorName", supervisor.getName())
    				.replaceAll("\\$StudentName",
    						employee.getFirstName() + " " + employee.getLastName()).replaceAll("\\$Semester", employee.getSemester()).replaceAll("\\$Year", employee.getYear().toString()).replaceAll("\\$Title", employee.getTitle());
;
    		  
            messageBodyPart.setText(message);
            messageBodyPart.addHeaderLine("Content-Type: text/html; charset=\"iso-8859-1\"");
            messageBodyPart.addHeaderLine("Content-Transfer-Encoding: quoted-printable");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            //List all the files present in the given directory location.
            //Iterate over the list of files.
            //Add the individual files as an email attachment
            //Set the name of the file as the name of the file after the last
            // '/' sign
            File dir = new File(dirLocation);
            File [] evaluationFiles = dir.listFiles();
            if(evaluationFiles == null || evaluationFiles.length == 0){
                return false;
            }
    	    logger.debug("evaluationFiles"+evaluationFiles[0]);

            for(File fileLoc : evaluationFiles){
                // Create the message part
                logger.debug("Attachment: " + fileLoc);
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(fileLoc);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(fileLoc.getName());
                multipart.addBodyPart(messageBodyPart);
            }

            msg.setContent(multipart);
            msg.setSentDate(new Date());
            logger.debug("Sending a mail to " + supervisor.getEmail());
            Transport.send(msg);
            logger.debug("msg"+msg);
            return true;
        }catch (FileNotFoundException e) {
            logger.error("Exception in sending mail ",e);
            throw new EmailException();
        }catch (IOException e) {
            logger.error("Exception in sending mail ",e);
            throw new EmailException();
        } catch (MessagingException e) {
            logger.debug("In message exception block");
            logger.error("Exception in sending mail ",e);

            throw new EmailException();
        }catch (Exception e) {
            logger.error("Exception in sending mail ",e);
            throw new EmailException();
        }
    }
}
