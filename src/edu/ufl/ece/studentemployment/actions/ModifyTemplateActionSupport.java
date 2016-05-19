package edu.ufl.ece.studentemployment.actions;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import Admission.data.FacultyList;
import Admission.data.FacultySign;
import Admission.db.FacultyHandleDB;
import studentemployee.data.EmailTemplate;
import studentemployee.data.Employee;
import studentemployee.data.Supervisor;
import studentemployee.db.EmployeeDB;
import edu.ufl.ece.studentemployment.exceptions.SEActionException;
import edu.ufl.ece.studentemployment.properties.SRSProperties;

public class ModifyTemplateActionSupport extends BaseActionSupport {

	private Logger logger = Logger.getLogger(ModifyTemplateActionSupport.class);
	private List<String> templateNames;
	private EmailTemplate template;

	public List<String> getTemplateNames() {
		return templateNames;
	}

	public void setTemplateNames(List<String> templateNames) {
		this.templateNames = templateNames;
	}

	public EmailTemplate getTemplate() {
		return template;
	}

	public void setTemplate(EmailTemplate template) {
		this.template = template;
	}

	public String view() {
		EmployeeDB employeeDB = new EmployeeDB();
		templateNames = employeeDB.getEmailTemplateNames();
		return "MODIFYTEMPLATE_VIEW";
	}

	public String getTemplateDetails() {
		EmployeeDB employeeDB = new EmployeeDB();
		logger.debug("getTemplateDetails");

		template = employeeDB.getTemplate(template.getTemplateName());
		return view();
	}

	public String updateTemplate() {
		EmployeeDB employeeDB = new EmployeeDB();
		logger.debug("updateTemplate"+template.getId());
		employeeDB.updateTemplate(template);

		return view();
	}
}
