package edu.ufl.ece.studentemployment.utils;

import java.util.ArrayList;
import java.util.List;

public class CommonConstants {
	
	public static final String EXAM_TYPE = "EXAM_TYPE";
	public static final String COURSE_SAVED_IN_SESSION = "COURSE_SAVED_IN_SESSION";
	public static final String EXAM_TYPE_PHD ="PHD";
	public static final String EXAM_TYPE_MASTERS ="MASTERS";
	public static final String SEMESTER_FALL ="Fall";
	public static final String SEMESTER_SPRING ="Spring";
	public static final String SEMESTER_SUMMER ="Summer";
	public static final String FALL_HIRE_DATES = "08-16-,12-31-" ;
	public static final String SPRING_HIRE_DATES = "01-01-,05-15-" ;
	public static final String SUMMER_HIRE_DATES = "05-16-,08-15-" ;
//public static final String APPOINTMENTLETTERS_LOCATION = "C:\\Appointment_Letters";
	public static final String APPOINTMENTLETTERS_LOCATION = "/home/users/stdemploy/Appointment_Letters";
	public static final String CCFIELD="kriti.shaw@gmail.com";
	public static final String SIGNATURE_IMAGE="/home/users/stdemploy/Images/signature.jpg";
	//public static final String SIGNATURE_IMAGE="D:\\ECE Project\\StudentEmployment\\WebContent\\assets\\images\\signature.jpg";
	public static final String STUDENT_EXAM_VO = "STUDENT_EXAM_VO" ;
	public static final String EXAM_NAME = "examName";
	public static final String SELECTED_EXAM_FOR_SEARCH = "SELECTED_EXAM_FOR SEARCH";
	public static final String SUPERVISOR_NAME = "supervisor";
	public static final int RESET_FLAG = 0;
	public static final int SET_FLAG = 1;	
	public static final List<String> semesterList = new ArrayList<String>();
	public static final List<String> examTypeList = new ArrayList<String>();

	static{
		semesterList.add(SEMESTER_FALL);
		semesterList.add(SEMESTER_SPRING);
		semesterList.add(SEMESTER_SUMMER);
		
		examTypeList.add(EXAM_TYPE_MASTERS);
		examTypeList.add(EXAM_TYPE_PHD);
	}
	
	public static final int START_YEAR = 2011;
	public static final int MINIMUM_YEARS_IN_LIST = 3;
	public static final String EXAM_NAME_SEPARATOR = " ";
	public static final int EXAM_ACTIVE = 1;
	public static final int EXAM_INACTIVE = 0;
	public static final int COURSE_ACTIVE = 1;
	public static final int COURSE_INACTIVE = 0;
	public static final int EMAIL_SENT = 1;
	public static final String EXAM_OPEN = "open";
	public static final String EXAM_CLOSE = "close";
	public static final String EXAM_STATUS_PARAM = "exam_status";
	public static final String CURR_EXAM_REG_OPEN = "CURR_EXAM_REG_OPEN";
	
	/** Error Messages   **/
	public static final String EXAM_ALREADY_EXISTS_ERROR_MSG = "Exam you are trying to Start has already been created , please select different parameters." ;
	public static final String EXAM_CLOSED_FAILURE_MSG ="Exam cannot be closed due to some error. Please try after some time or contact administrator";
	public static final String REGISTRATION_ERROR_MSG = "There was an error in registration Process. Kindly close your browser and try again.<br>"
														+"Contact Shannon Chillingworth if the problem persists";
	public static final String COURSE_LIST_IS_EMPTY = "No course was selected for starting the exam. Please select at least one course.";
	public static final String EXAM_CLOSE_DATE_NOT_ENTERED = "Please enter a close date for the exam.";
	
	/** Success Messages **/
	public static final String EXAM_CREATED_SUCCESS_MSG = "Exam has been started successfully";
	public static final String EXAM_CLOSED_SUCCESS_MSG = "Exam has been closed successfully";
	//public static final String EVALUATION_LETTERS_LOCATION = "C:\\\\Evaluation_Forms";
	public static final String EVALUATION_LETTERS_LOCATION = "/home/users/stdemploy/Evaluation_Letters";
	
}
