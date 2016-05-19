
package edu.ufl.ece.studentemployment.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import edu.ufl.ece.studentemployment.actions.AccountManagementActionSupport;

import studentemployee.data.Employee;
import studentemployee.data.Project;

public class UtilFunctions {
	private static  Logger logger = Logger.getLogger(UtilFunctions.class);

	public static String[] getCurrentSemester() {
		Calendar currentDate = Calendar.getInstance();
		Integer year = currentDate.get(Calendar.YEAR);

		int month = currentDate.get(Calendar.MONTH);
		int date = currentDate.get(Calendar.DATE);

		String semester = "";

		if ((month >= 0 && month < 5) || (month == 5 && date <= 15)) {
			semester = "Spring";
		} else if ((month == 7 && date >= 15) || (month > 7)) {
			semester = "Fall";
		} else {
			semester = "Summer";
		}

		return new String[] { semester, year.toString() };
	}

	public static String[] getNextSemester() {
		String[] current = getCurrentSemester();

		if (current[0].equals("Spring")) {
			return new String[] { "Summer", current[1] };
		} else if (current[0].equals("Summer")) {
			return new String[] { "Fall", current[1] };
		} else {
			Integer year = Integer.parseInt(current[1]) + 1;
			return new String[] { "Spring", year.toString() };
		}
	}
	  private JFrame frame;
	    public UtilFunctions() {
	        frame = new JFrame();

	        frame.setVisible(true);
	        BringToFront();
	    }
	    private void BringToFront() {                  
          frame.setExtendedState(JFrame.ICONIFIED);
  frame.setExtendedState(JFrame.NORMAL);

}
	
	/*
	 * Method used to export the snapshot view to excel
	 */
	    public static InputStream exportToExcel(List<Employee> employeeList)
		{
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				
			HSSFWorkbook workbook = new HSSFWorkbook();

		    HSSFSheet sheet = workbook.createSheet("Employee details");
		    int rownum = 0;

		    /*
		     * Set the headings for the excel sheet
		     */
		    
		    Row row = sheet.createRow(rownum++);
	        int cellnum = 0;
	        
	        Cell cell = row.createCell(cellnum++);
	        cell.setCellValue("Ufid");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("First Name");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Last Name");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Semester");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Year");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("From date");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("To date");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Hire date");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Supervisor");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("email");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Fellowship");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("FedWorkStudy");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Target Amount");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Relative");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Building");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Phone Number");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("fte");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Hourly");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Duties");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Notes");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Title");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Status");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Bi-weekly Rate");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("Annual Rate");
	        cell = row.createCell(cellnum++);
	        cell.setCellValue("EMPL Record");
	        
	        
	        /*
	         * Populate all the rows from the employee list
	         */
		    for (Employee employee : employeeList)
		    {
		        row = sheet.createRow(++rownum);
		        cellnum = 0;
		        
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getUfid());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getFirstName());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getLastName());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getSemester());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((Integer) employee.getYear());
		      
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) formatter.format(employee.getFromDate()));
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) formatter.format(employee.getToDate()));
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) formatter.format(employee.getHireDate()));
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getSupervisor().getName());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getEmail());
		        
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getFellowship());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((Boolean) employee.getFedWorkStudy());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getTargetAmount());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((Boolean) employee.getRelative());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getBuilding());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getPhoneNumber());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getFte());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getHourly());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getDuties());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getNotes());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getTitle());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((String) employee.getStatus());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((Float) employee.getBiweeklyRate());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((Float) employee.getAnnualRate());
	            cell = row.createCell(cellnum++);
	            cell.setCellValue((Integer) employee.getEmplRecord());
	            
		    }

		    String currentSemester = employeeList.get(0).getSemester();
		    Integer currentYear = employeeList.get(0).getYear();
		    try
		    {
		        String path = System.getProperty("user.home")+"\\Desktop\\employees_"+currentSemester+"_"+currentYear+".xls";
		        FileOutputStream out =
		                new FileOutputStream(new File(path));
		        workbook.write(baos);
		        workbook.write(out);
		        out.close();
		        System.out.println("Excel written successfully..");

		    }
		    catch (FileNotFoundException e)
		    {
		        System.out.println("FileNotFoundException :" + e);
		    }
		    catch (IOException e)
		    {
		        System.out.println("IOException :" + e);
		    }
			return new ByteArrayInputStream(baos.toByteArray());


		}
	
	public static boolean isProjectsUpdateReq(List<Project> projects1,
			List<Project> projects2) {
		if (projects1.size() == projects2.size()) {
			Iterator<Project> it1 = projects2.iterator();

			for (Project project : projects1) {
				Project project1 = it1.next();
				if (!(project.getName().equalsIgnoreCase(project1.getName())
						&& project.getNumber().equalsIgnoreCase(project1.getNumber()) && project
							.getPercentage() == project1.getPercentage() && project.getStartDate() == project1.getStartDate() && project.getEndDate() == project1.getEndDate())) {
					return true;
				}
			}
		} else {
			return true;
		}

		return false;
	}
	
	/*public static File getFilePath(String semester,int year,String employeeName)
	{
	JFileChooser chooser = new JFileChooser();
	chooser.setCurrentDirectory(new
	java.io.File(System.getProperty("user.home")+"\\Desktop\\"));
	chooser.setDialogType(JFileChooser.SAVE_DIALOG);
	if(semester!=null)
	{
	chooser.setSelectedFile(new File(semester+"_"+String.valueOf(year)+".xls"));
	}
	else
	{
	chooser.setSelectedFile(new File(employeeName +".pdf"));
	}
	chooser.setDialogTitle("Save file");
	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	chooser.setAcceptAllFileFilterUsed(true);
	chooser.setRequestFocusEnabled(true);
	chooser.setFocusable(true);
	chooser.setRequestFocusEnabled(true);

	if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
	return chooser.getSelectedFile();
	} else {
	return null;
	}
	}*/
	  /*
	public static File getFilePath(String semester,int year,String employeeName)
	{
	JFileChooser chooser = new JFileChooser();
	chooser.setCurrentDirectory(new
	java.io.File(System.getProperty("user.home")+"\\Desktop\\"));
	chooser.setDialogType(JFileChooser.SAVE_DIALOG);
	if(semester!=null)
	{
	chooser.setSelectedFile(new File(semester+"_"+String.valueOf(year)+".xls"));
	}
	else
	{
	chooser.setSelectedFile(new File(employeeName +".pdf"));
	}
	chooser.setDialogTitle("Savecxc file");
	chooser.setCurrentDirectory(new
			java.io.File(System.getProperty("user.home")+"\\Desktop\\"));
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	chooser.setAcceptAllFileFilterUsed(true);
	chooser.setRequestFocusEnabled(true);
	chooser.setFocusable(true);
	chooser.setRequestFocusEnabled(true);
File file = chooser.getSelectedFile();
	
	FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("pdf files (*.pdf)", "pdf");
	chooser.setFileFilter(pdfFilter);

	
	
	if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
		
		String filename = chooser.getSelectedFile().getName();
		

		file = new File(filename +".pdf"); 
	return file;
	} else {
	return null;
	}
	}
	    
	    
		
	}

*/
	
	
	
	public static File getFilePath(String semester,int year,String employeeName)
	{
	JFileChooser chooser = new JFileChooser();
	chooser.setCurrentDirectory(new
	java.io.File(System.getProperty("user.home")+"\\Desktop\\"));
	chooser.setDialogType(JFileChooser.SAVE_DIALOG);
	if(semester!=null)
	{
	chooser.setSelectedFile(new File(semester+"_"+String.valueOf(year)+".xls"));

	}
	else
	{
	chooser.setSelectedFile(new File(employeeName +".pdf"));

	}
String path2 = chooser.getSelectedFile().getAbsolutePath();
System.out.println(path2);
	chooser.setDialogTitle("Saved file");
	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	
	chooser.setSelectedFile(new File("Details.pdf"));

	chooser.setAcceptAllFileFilterUsed(true);
	chooser.setRequestFocusEnabled(true);
	chooser.setFocusable(true);
	chooser.setRequestFocusEnabled(true);
	
	if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

		File file = chooser.getSelectedFile();
		
/*String filename = file.getName();
		
file = new File(filename +".pdf"); */
return file;
	} else {
	return null;
	}
	}}