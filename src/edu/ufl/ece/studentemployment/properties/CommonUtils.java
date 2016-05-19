package edu.ufl.ece.studentemployment.properties;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.ufl.ece.studentemployment.utils.CommonConstants;


public class CommonUtils {
	private static Log logger = LogFactory.getLog(CommonUtils.class);

	public static final String USER_NAME = "username";

	public static final String USERVO = "userVO";
	
	public static final String ROLE = "role";
	
	public static final String ROLE_A = "Admin";
	
	public static final String ROLE_F = "Faculty";
	
	public static final String ROLE_AF = "Admin_Faculty";
	
	public static final String LOGINVALUE = "loginValue";
	
	public static final String SUPERVISORID = "supervisorId";

	public static final String PASSWORD = "password";
	public static final String SEARCH_CRITERIA = "searchCriteria";


	public static boolean isNullOrEmpty(String str){
		return (str==null || str.isEmpty());
	}

	public static boolean isValidEmailAddress(String email) {

		// Set the email pattern string
		// ^([0-9a-zA-Z]+[-._+&amp;])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$
		// Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Pattern p = Pattern.compile("^([0-9a-zA-Z]+[-._+&amp;])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");

		// Match the given string with the pattern
		Matcher m = p.matcher(email);

		// check whether match is found
		boolean matchFound = m.matches();

		return matchFound;
	}

	public static boolean isValidUfid(String ufid) {
		boolean result = false;

		if (ufid.length() == 8) {
			if (isInteger(ufid)) {
				result = true;
			}
		}

		return result;
	}

	public static boolean isInteger(String str1) {
		boolean result = false;       

		try {
			int x = Integer.parseInt(str1);
			result = true;
		} catch (NumberFormatException nFE) {
			logger.debug("isInteger:not integer:" + str1, nFE);
			result = false;
		}

		return result;
	}

	public static List<String> initializeYearList(){
		List<String> yearList = new ArrayList<String>(); 
		int startYear = CommonConstants.START_YEAR;
		int currentYear  = Calendar.getInstance().get(Calendar.YEAR);		
		int endYear = (currentYear - startYear) >=3 ? currentYear : (startYear + CommonConstants.MINIMUM_YEARS_IN_LIST);
		
		for(int year=startYear ; year <= endYear ; year++){
			yearList.add(Integer.toString(year));
		}
		return yearList;
	}	
	

}
