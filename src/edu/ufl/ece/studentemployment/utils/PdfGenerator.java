/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufl.ece.studentemployment.utils;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseField;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RadioCheckField;

import edu.ufl.ece.studentemployment.exceptions.GeneratorException;
import studentemployee.data.Employee;
import studentemployee.data.Project;
import studentemployee.data.Supervisor;
import studentemployee.db.EmployeeDB;

import java.awt.Color;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

/**
 *
 * @author Saurabh
 */
public class PdfGenerator {

    private static Logger logger = Logger.getLogger(PdfGenerator.class);
    private static String specialCondition = "You are required to carry a minimum of $creditHours credit hours this semester. Your workload is to average $hours hours biweekly. Your tution for those specified credit hours will be paid for each semester and you will be responsible for the miscellaneous fees associated with each specified credit.";
    private static String terms = "This appointment between you, the appointee, and the University of Florida, is subject to the constitution and laws of the State of Florida and the rules of the Florida Board of Governors and the University of Florida, Board of Trustees and the collective bargaining agreement. By signing below, you accept this offer. Such acceptance will not be considered a waiver of your right to process a grievance concerning this appointment, pursuant to any applicable law, rule, or collective bargaining agreement. This appointment shall not create any right, interest, or expectancy in any other appointment beyond its specific term. The UFF-GAU website is http://www.ufgau.org.";
    private static String agreement = "\"I understand that all tuition payments will be voided and rescinded if, at any time, I do not meet all of the eligibility requirements outlined in the Graduate Student Handbook, including maintaining the minimum registration required by my appointment. Any change in my eligibility, academic or employment status after the ";
    private static String agreement2 = "graduate tuition payment is processed will result in the original payment liability being reassigned to the student.\"";
    private static final String taSpecificDuties = "Your duties is to instruct/grade an assigned lab/course, maintain office and/or evaluate students performance according to the instructions from your assigned supervisor";
    static String pathSep = System.getProperty("file.separator");

    	static /*
    	 * Generate appointment letters for students
    	 */
    ByteArrayOutputStream boas = new ByteArrayOutputStream();
    static String src = null;
        public void generateAppointmentLetters(Supervisor supervisor, String semester, String year,
            String fromDate, String toDate, String evaluationDate,String title) throws GeneratorException 
        {
        try 
        {
        	System.out.println("Inside generateAppointmentLetters ");
        	int year1 = Integer.parseInt(year);
            EmployeeDB employeeDB = new EmployeeDB();
            Employee tempEmployee =  new Employee();
            tempEmployee.setSupervisor(supervisor);
            tempEmployee.setSemester(semester);
            tempEmployee.setYear(year1);
            //tempEmployee.setTitle(title);
            List<Employee> employeeList = employeeDB.getEmployees(tempEmployee, false, false);
        	System.out.println("Inside generateAppointmentLetters before listing" +employeeList.size());
    		//logger.debug(employee.getTitle()+"dcfdfcd");

            for(Employee employee : employeeList) {
            	logger.debug(employee.getTitle()+"dcfdfcd");
            	if(employee.getTitle().equals("RA") || employee.getTitle().equals("TA")){
            		logger.debug("pdf generating");
                Document doc = new Document();
                System.out.println(employee.getFirstName());
                String directories = CommonConstants.APPOINTMENTLETTERS_LOCATION+ pathSep
                        + year + pathSep + semester + pathSep +supervisor.getName().split(",")[1].trim()+"_"+supervisor.getName().split(",")[0].trim();
System.out.println("directories"+directories);
//                if(title.equals("RA")){
//                    directories = directories+pathSep+"RA";
//                }else if((title.equals("TA"))){
//                    directories = directories+pathSep+"TA";
//                }
                File dirs = new File(directories);
                dirs.mkdirs();
                
                File appointmentLetter = new File(directories + pathSep + employee.getFirstName() + "_"
                        + employee.getLastName() + "_" + employee.getUfid() + "_" + employee.getId()+".pdf");
                appointmentLetter.createNewFile();
                PdfWriter.getInstance(doc, new FileOutputStream(appointmentLetter));
                doc.open();
                Chunk letterOfApptHeader = new Chunk("Letter of Appointment",
                        new Font(Font.TIMES_ROMAN, 18, Font.BOLD));
                Chunk researchAsstHeader = new Chunk("Research Assistant",
                        new Font(Font.TIMES_ROMAN, 16, Font.BOLD));
                if(title.equals("TA")){
                    researchAsstHeader = new Chunk("Teaching/Grader Assistant",
                        new Font(Font.TIMES_ROMAN, 16, Font.BOLD));
                }
                Chunk apptPeriod = new Chunk(semester + " " + year,
                        new Font(Font.TIMES_ROMAN, 14, Font.BOLDITALIC));
                Paragraph headerPara = new Paragraph();
                headerPara.add(letterOfApptHeader);
                headerPara.add(Chunk.NEWLINE);
                headerPara.add(researchAsstHeader);
                headerPara.add(Chunk.NEWLINE);
                headerPara.add(apptPeriod);
                SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy");
                Date currentDate = new Date();


                PdfPTable table = new PdfPTable(1);
                table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(headerPara);
                doc.add(table);
                doc.add(new Paragraph(Chunk.NEWLINE));
                float[] colRatio = new float[]{1.2f, 2};
                PdfPTable formTable = new PdfPTable(colRatio);
                formTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                formTable.getDefaultCell().setPaddingBottom(10);
                formTable.setWidthPercentage(100f);
                formTable.getDefaultCell().setPaddingLeft(0);
                formTable.getDefaultCell().setPaddingRight(0);
                Font fieldNameFont = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
                Font fieldValueFont = new Font(Font.TIMES_ROMAN, 12);
                //Date field
                formTable.addCell(new Paragraph("Date:", fieldNameFont));
                formTable.addCell(new Paragraph(sdf.format(currentDate), fieldValueFont));
                //Name of  Appointee
                formTable.addCell(new Paragraph("Name of Appointee:", fieldNameFont));
                formTable.addCell(new Paragraph(employee.getFirstName() + " " + employee.getLastName(), fieldValueFont));
                //UFID
                formTable.addCell(new Paragraph("UFID:", fieldNameFont));
                formTable.addCell(new Paragraph(employee.getUfid(), fieldValueFont));
                //Title
                formTable.addCell(new Paragraph("Title:", fieldNameFont));
                formTable.addCell(new Paragraph(employee.getTitle(), fieldValueFont));
                //Employing Department
                formTable.addCell(new Paragraph("Employing Department:", fieldNameFont));
                formTable.addCell(new Paragraph("Electrical and Computer Engineering", fieldValueFont));
                //FTE
                formTable.addCell(new Paragraph("FTE:", fieldNameFont));
                formTable.addCell(new Paragraph(employee.getFte(), fieldValueFont));
                //Biweekly rate of pay
                formTable.addCell(new Paragraph("Biweekly Rate of Pay($):", fieldNameFont));
                formTable.addCell(new Paragraph(String.valueOf(employee.getBiweeklyRate()), fieldValueFont));
                //Annual Rate of Pay
                formTable.addCell(new Paragraph("Annual Rate of Pay($):", fieldNameFont));
                formTable.addCell(new Paragraph(String.valueOf(employee.getAnnualRate()), fieldValueFont));
                //Effective Dates
                formTable.addCell(new Paragraph("Effective Dates:", fieldNameFont));
                formTable.addCell(new Paragraph(semester + " " + year + " " + "(" + fromDate + " - " + toDate + ")", fieldValueFont));
                //Evaluation Date
                formTable.addCell(new Paragraph("Evaluation Date:", fieldNameFont));
                formTable.addCell(new Paragraph(sdf.format(getDate(evaluationDate)), fieldValueFont));

                //Special Conditions
                logger.debug("Semester is : "+semester);
                String creditHours = "9";
                if(semester.equals("Summer"))
                    creditHours = "6";
                
                float fte, hours;
                if(employee.getFte().length()>0)
                {
                	fte = Float.parseFloat(employee.getFte());
                	hours = fte*80.00f;
                }
                else
                {
                	fte = 0.00f;
                	hours = 0.00f;
                }
                DecimalFormat dFormat = new DecimalFormat("#.##");
                formTable.addCell(new Paragraph("Special Conditions:", fieldNameFont));
                formTable.addCell(new Paragraph(
                        specialCondition.replace("$creditHours", creditHours).replace("$hours", dFormat.format(hours)),
                        fieldValueFont));
                //Duties
                formTable.addCell(new Paragraph("Duties:", fieldNameFont));
                if(title.equals("TA")){
                    formTable.addCell(new Paragraph(taSpecificDuties, fieldValueFont));
                }else{
                    formTable.addCell(new Paragraph(employee.getDuties(), fieldValueFont));
                }
                //Name of  Appointee
                
                String firstName = supervisor.getName().split(",")[0].trim();
                String lastName = supervisor.getName().split(",")[1].trim();
                employee.getSupervisor().setName(firstName+" "+lastName);
                formTable.addCell(new Paragraph("Supervisor:", fieldNameFont));
                formTable.addCell(new Paragraph("Dr. " +employee.getSupervisor().getName(), fieldValueFont));

                doc.add(formTable);

                doc.add(new Paragraph(Chunk.NEWLINE));
                doc.add(new Chunk(terms, new Font(Font.TIMES_ROMAN, 9, Font.ITALIC)));
                doc.add(new Paragraph(Chunk.NEWLINE));
                doc.add(new Chunk(agreement, fieldValueFont));
                doc.add(new Chunk(agreement2, fieldNameFont));

                //Adding the last line in the document for signatures
                PdfPTable sigTable = new PdfPTable(2);
                sigTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                sigTable.setWidthPercentage(100f);
                sigTable.getDefaultCell().setPadding(0f);
                // to add signature
                //Adding line for signature
                sigTable.addCell("");
                String imageLoc = CommonConstants.SIGNATURE_IMAGE;
                if(imageLoc !=null && !imageLoc.equals(""))
                {
                    Image sigImage = Image.getInstance(imageLoc);
                    sigTable.addCell(new Paragraph(new Chunk(sigImage,0,0)));
                }
                else
                {
                    throw new GeneratorException();
                }
                
                sigTable.addCell("__________________________________");
                sigTable.addCell("__________________________________");
                sigTable.addCell("Employee                       Date");
                sigTable.addCell("Department/Unit Representative");

                doc.add(sigTable);
                doc.close();
               
                
            }}
        } catch (DocumentException e) {
            logger.error("Error while generating pdf document", e);
            throw new GeneratorException();
        } catch (IOException e) {
            logger.error("Error while doing file I/O", e);
            throw new GeneratorException();
        }
        
    }
        
        
        public static java.io.InputStream manipulatePdf(String src, String dest, Employee selectedEmployee) throws GeneratorException
        {
        
    		
    		try{
       			byte[] bytes= null;
       			
       			Document doc = new Document();
       			
       			logger.debug(selectedEmployee.getFirstName());
       			File emplLetter = new File(selectedEmployee.getFirstName()+"_"+selectedEmployee.getFirstName()+".pdf");
       			emplLetter.createNewFile();
       			logger.debug(selectedEmployee.getFirstName());

                PdfPTable printTable = null;
				try {
					PdfWriter.getInstance(doc, new FileOutputStream(emplLetter));
					doc.open();
					logger.debug(selectedEmployee.getFirstName());

					Chunk letterOfApptHeader = new Chunk("Student Employment Details",
					        new Font(Font.TIMES_ROMAN, 18, Font.BOLD));
					Chunk researchAsstHeader = new Chunk("Teaching/Grader Assistant",
					        new Font(Font.TIMES_ROMAN, 16, Font.BOLD));
					logger.debug(selectedEmployee.getFirstName());

					if(selectedEmployee.getTitle().equals("RA")){
					    researchAsstHeader = new Chunk("Research Assistant",
					        new Font(Font.TIMES_ROMAN, 16, Font.BOLD));
					}
					if(selectedEmployee.getTitle().equals("OPS")){
					    researchAsstHeader = new Chunk("Other Personal Services",
					        new Font(Font.TIMES_ROMAN, 16, Font.BOLD));
					}
					
					
					logger.debug(selectedEmployee.getFirstName());

					Chunk apptPeriod = new Chunk(selectedEmployee.getSemester() + " " + selectedEmployee.getYear(),
					        new Font(Font.TIMES_ROMAN, 14, Font.BOLDITALIC));
					Paragraph headerPara = new Paragraph();
					headerPara.add(letterOfApptHeader);
					headerPara.add(Chunk.NEWLINE);
					headerPara.add(researchAsstHeader);
					headerPara.add(Chunk.NEWLINE);
					headerPara.add(apptPeriod);
					SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy");
					Date currentDate = new Date();
					logger.debug(selectedEmployee.getFirstName());


					PdfPTable table = new PdfPTable(1);
					table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
					table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(headerPara);
					doc.add(table);
					doc.add(new Paragraph(Chunk.NEWLINE));
					float[] colRatio = new float[]{1.2f, 2};
					PdfPTable formTable = new PdfPTable(colRatio);
					formTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
					formTable.getDefaultCell().setPaddingBottom(10);
					formTable.setWidthPercentage(100f);
					formTable.getDefaultCell().setPaddingLeft(0);
					formTable.getDefaultCell().setPaddingRight(0);
					Font fieldNameFont = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
					Font fieldValueFont = new Font(Font.TIMES_ROMAN, 12);
					
					//Name of  Appointee
					formTable.addCell(new Paragraph("Start Date:", fieldNameFont));
					formTable.addCell(new Paragraph(sdf.format(selectedEmployee.getFromDate()), fieldValueFont));
					//UFID
					formTable.addCell(new Paragraph("End Date:", fieldNameFont));
					formTable.addCell(new Paragraph(sdf.format(selectedEmployee.getToDate()), fieldValueFont));
					//Title
					formTable.addCell(new Paragraph("Name", fieldNameFont));
					formTable.addCell(new Paragraph(selectedEmployee.getFirstName() +" "+ selectedEmployee.getLastName(), fieldValueFont));
					//Employing Department
					formTable.addCell(new Paragraph("Email:", fieldNameFont));
					formTable.addCell(new Paragraph(selectedEmployee.getEmail(), fieldValueFont));
					//FTE
					formTable.addCell(new Paragraph("UFID:", fieldNameFont));
					formTable.addCell(new Paragraph(selectedEmployee.getUfid(), fieldValueFont));
					//Biweekly rate of pay
					formTable.addCell(new Paragraph("Supervisor:", fieldNameFont));
					formTable.addCell(new Paragraph("Dr. "+selectedEmployee.getSupervisor().getName().split(",")[1].trim()+" "+selectedEmployee.getSupervisor().getName().split(",")[0].trim(), fieldValueFont));
					//Annual Rate of Pay
					formTable.addCell(new Paragraph("Phone:", fieldNameFont));
					formTable.addCell(new Paragraph(selectedEmployee.getPhoneNumber(), fieldValueFont));
					//Effective Dates
					formTable.addCell(new Paragraph("Fellowship:", fieldNameFont));
					formTable.addCell(new Paragraph(selectedEmployee.getFellowship(), fieldValueFont));
					//Evaluation Date
					formTable.addCell(new Paragraph("FederalWork Study:", fieldNameFont));
					formTable.addCell(new Paragraph(String.valueOf(selectedEmployee.getFedWorkStudy()), fieldValueFont));
					
					formTable.addCell(new Paragraph("Target Amount:", fieldNameFont));
					formTable.addCell(new Paragraph(selectedEmployee.getTargetAmount(), fieldValueFont)); 
					
					formTable.addCell(new Paragraph("FTE:", fieldNameFont));
					formTable.addCell(new Paragraph(selectedEmployee.getFte(), fieldValueFont));
					
					formTable.addCell(new Paragraph("Biweekly Rate:", fieldNameFont));
					formTable.addCell(new Paragraph(String.valueOf(selectedEmployee.getBiweeklyRate()), fieldValueFont));
					
                    /*if(selectedEmployee.getStatus()!=null)
                    {
                    	formTable.addCell(new Paragraph("Status:", fieldNameFont));
    					formTable.addCell(new Paragraph(String.valueOf(selectedEmployee.getStatus()), fieldValueFont));
					
                    }
                    else 
                    {
                    	formTable.addCell(new Paragraph("Status:", fieldNameFont));
    					formTable.addCell(new Paragraph("REAPPOINTED", fieldValueFont));
                    }*/
                    
					if(selectedEmployee.getTitle().equals("RA")||selectedEmployee.getTitle().equals("TA")){
						
					formTable.addCell(new Paragraph("Annual Rate:", fieldNameFont));
					formTable.addCell(new Paragraph(String.valueOf(selectedEmployee.getAnnualRate()), fieldValueFont));
						
					}else if (selectedEmployee.getTitle().equals("OPS")){
					formTable.addCell(new Paragraph("Hourly Rate:", fieldNameFont));
					formTable.addCell(new Paragraph(selectedEmployee.getHourly(), fieldValueFont));
					}
					
					formTable.addCell(new Paragraph("Duties:", fieldNameFont));
					formTable.addCell(new Paragraph(selectedEmployee.getDuties(), fieldValueFont));
					
					String notes =selectedEmployee.getNotes();
					
					
					if(notes!=null){
					notes = notes.replace("<br>","\n");
					}
					formTable.addCell(new Paragraph("Notes:", fieldNameFont));
					formTable.addCell(new Paragraph(notes, fieldValueFont));
					
					logger.debug(selectedEmployee.getFirstName());

					
					doc.add(formTable);  
					
					
					Chunk projectDetails = new Chunk("Project Details:",
					        new Font(Font.TIMES_ROMAN, 14, Font.BOLD));
					doc.add(new Paragraph(projectDetails));
					doc.add(new Paragraph(Chunk.NEWLINE));
					
            printTable = new PdfPTable(new float[] { 5	, 4, 5,5,5});
            printTable.setWidthPercentage(100);
            printTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            printTable.addCell("Project Name");
            printTable.addCell("Project Number");
            printTable.addCell("Percentage");
            printTable.addCell("Project Start Date");
            printTable.addCell("Project End Date");
       	 
            printTable.setHeaderRows(1);
       	 
       	 
          logger.debug("pdf done");
					
          if(selectedEmployee.getProjects()!=null)
          {
          for(Project project : selectedEmployee.getProjects())
					{
						                    
						//printTable.addCell(new Paragraph("ProjectName_"+i, fieldNameFont));
						printTable.addCell(project.getName());
					    //formTable.addCell(new Paragraph("ProjectNumber_"+i, fieldNameFont));
					    printTable.addCell(project.getNumber());
					    //formTable.addCell(new Paragraph("Percentage_"+i, fieldNameFont));
					    printTable.addCell(String.valueOf(project.getPercentage()));
					    //formTable.addCell(new Paragraph("project Start Date_"+i, fieldNameFont));
					    printTable.addCell(sdf.format(project.getStartDate()));
					    //formTable.addCell(new Paragraph("Project End Date_"+i, fieldNameFont));
					    printTable.addCell(sdf.format(project.getEndDate()));
					    
					    
					   
					}}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
          doc.add(printTable);  
          
              
                doc.close();
                
                
               
                File file = emplLetter;
                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                try {
                    for (int readNum; (readNum = fis.read(buf)) != -1;) {
                        bos.write(buf, 0, readNum); //no doubt here is 0
                      
                        logger.debug("read " + readNum + " bytes,");
                    }
                } catch (IOException ex) {
                	ex.printStackTrace();
                }
                 bytes = bos.toByteArray();
                File someFile = new File("Empl.pdf");
                
                FileOutputStream fos = new FileOutputStream(someFile);
                fos.write(bytes);
                fos.close();
               return new ByteArrayInputStream(bytes);
                
    		   } catch (DocumentException e) {
    		       logger.error("Error while generating pdf document", e);
    		       throw new GeneratorException();
    		   } catch (IOException e) {
    		       logger.error("Error while doing file I/O", e);
    		       throw new GeneratorException();
    		   }
    	  	
       		
        }
     

   
        
    public void generateEvaluationForms(Supervisor supervisor, String semester, String year,
            String fromDate, String toDate) throws GeneratorException {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy");
        try 
        {
            EmployeeDB employeeDB = new EmployeeDB();
            Employee tempEmployee = new Employee();
        	int year1 = Integer.parseInt(year);

            //tempEmployee.setSupervisor(supervisor);
            tempEmployee.setSupervisor(supervisor);
            tempEmployee.setSemester(semester);
            tempEmployee.setYear(year1);
            //tempEmployee.setTitle(title);
         
            logger.debug("Inside generate Evaluation Forms");
            List<Employee> employeeList = employeeDB.getEmployees(tempEmployee, false, false);
           
            for(Employee employee : employeeList) {
            	if(employee.getTitle().equals("RA") || employee.getTitle().equals("TA")){
                Document doc = new Document();
     String title = employee.getTitle();
                String directories = CommonConstants.EVALUATION_LETTERS_LOCATION+ pathSep
                        + year + pathSep + semester + pathSep +supervisor.getName().split(",")[1].trim()+"_"+supervisor.getName().split(",")[0].trim()+ pathSep;
                logger.debug("Inside "+directories);
               /* if(title.equals("RA")){
                    directories = directories+pathSep+"RA";
                }else if((title.equals("TA"))){
                    directories = directories+pathSep+"TA";
                }*/
                File dirs = new File(directories);
                dirs.mkdirs();
                File appointmentLetter = new File(directories + pathSep + employee.getFirstName() + "_"
                        + employee.getLastName() + "_" + employee.getUfid() + "_" + employee.getId()+".pdf");
                
                appointmentLetter.createNewFile();
                PdfWriter.getInstance(doc, new FileOutputStream(appointmentLetter));
                doc.open();
                Chunk researchEvaluationHeader = new Chunk("GRADUATE ASSISTANT - RESEARCH EVALUATION",
                    new Font(Font.TIMES_ROMAN, 14, Font.BOLD));
            if(title.equals("TA")){
                researchEvaluationHeader = new Chunk("GRADUATE ASSISTANT - TEACHING EVALUATION",
                    new Font(Font.TIMES_ROMAN, 14, Font.BOLD));
            }
            Chunk termHeader = new Chunk(semester+" "+year, new Font(Font.TIMES_ROMAN, 12, Font.BOLD));
            Chunk termPeriodHeader = new Chunk(sdf.format(getDate(fromDate))+" - "+sdf.format(getDate(toDate)),
                    new Font(Font.TIMES_ROMAN, 12, Font.BOLD));
            Paragraph para = new Paragraph();
            para.add(researchEvaluationHeader);
            para.add(Chunk.NEWLINE);
            para.add(termHeader);
            para.add(Chunk.NEWLINE);
            para.add(termPeriodHeader);
            para.add(Chunk.NEWLINE);

            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100f);
            headerTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            headerTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            headerTable.addCell(para);
            doc.add(headerTable);

            float[] columnRatio = new float[]{1, 4};
            PdfPTable formTable = new PdfPTable(columnRatio);
            formTable.setWidthPercentage(100f);
            formTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            formTable.getDefaultCell().setPaddingTop(10);
            formTable.getDefaultCell().setPaddingBottom(10f);
            //formTable.getDefaultCell().setHorizontalAlignment(P);
            Font fieldNameFont = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
            Font fieldValueFont = new Font(Font.TIMES_ROMAN, 12);
            //Name
            formTable.addCell(new Paragraph("Name:", fieldNameFont));
            formTable.addCell(new Paragraph(employee.getFirstName() + " " + employee.getLastName(), fieldValueFont));
            //UFID
            formTable.addCell(new Paragraph("UFID#:", fieldNameFont));
            formTable.addCell(new Paragraph(employee.getUfid(), fieldValueFont));

             if(title.equals("TA")){
                   //course information
                formTable.addCell(new Paragraph("Course:", fieldNameFont));
                formTable.addCell(new Paragraph(employee.getDuties(), fieldValueFont));
             }


            //Supervisor
            formTable.addCell(new Paragraph("Supervisor:", fieldNameFont));
            String firstName = employee.getSupervisor().getName().split(",")[0].trim();
            String lastName = employee.getSupervisor().getName().split(",")[1].trim();
            employee.getSupervisor().setName(firstName+" "+lastName);
            formTable.addCell(new Paragraph("Dr. " +employee.getSupervisor().getName(), fieldValueFont));


            PdfPCell checkBoxCell = new PdfPCell();
            checkBoxCell.setBorder(PdfPCell.NO_BORDER);
            checkBoxCell.setCellEvent(new PSFtest("cell1"));

            formTable.addCell(checkBoxCell);
            formTable.addCell(new Paragraph("Has satisfactorily completed all "
                    + "duties and responsibilities required for the appointment "
                    + "period indicated.", fieldValueFont));

            PdfPCell checkBoxCell2 = new PdfPCell();
            checkBoxCell2.setBorder(PdfPCell.NO_BORDER);
            checkBoxCell2.setCellEvent(new PSFtest("cell2"));

            formTable.addCell(checkBoxCell2);
            formTable.addCell(new Paragraph("Has completed all duties and responsiblilities"
                   + " required but needs improvement in the following areas:", fieldValueFont));

            formTable.addCell("");
            formTable.addCell("\n\n\n\n\n\n\n");

            


            PdfPCell checkBoxCell3 = new PdfPCell();
            checkBoxCell3.setBorder(PdfPCell.NO_BORDER);
            checkBoxCell3.setCellEvent(new PSFtest("cell3"));

            formTable.addCell(checkBoxCell3);
            System.out.println("Cell 3 added");
            formTable.addCell(new Paragraph("Failure to show improvement may "
                    + "prevent continuing appointment for this research project.", fieldValueFont));

            PdfPCell checkBoxCell4 = new PdfPCell();
            checkBoxCell4.setBorder(PdfPCell.NO_BORDER);
            checkBoxCell4.setCellEvent(new PSFtest("cell4"));

            formTable.addCell(checkBoxCell4);
            formTable.addCell(new Paragraph("Has not completed all duties and responsibilities"
                    + " and will not be reappointed.(Prior written notice of unsatisfactory "
                    + "performance is required.)", fieldValueFont));

            formTable.addCell("");
            if((title.equals("TA"))){
                    formTable.addCell("\n\n\n\n");
            }else{
                  formTable.addCell("\n\n\n\n\n\n\n");
            }
            


            formTable.addCell("");
            formTable.addCell(new Paragraph("_________________________________________________\n"
                    + "Approved: Faculty Advisor                    Date", fieldNameFont));

            
            doc.add(formTable);

            doc.add(new Paragraph("I have reviewed this evaluation and I understand "
                    + "that I have the right to attach a statement.\n", fieldValueFont));


            PdfPTable formTable2 = new PdfPTable(columnRatio);
            formTable2.setWidthPercentage(100f);
            formTable2.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            formTable2.getDefaultCell().setPaddingTop(10f);
            formTable2.getDefaultCell().setPaddingBottom(10f);

             formTable2.addCell("");
            formTable2.addCell("\n");

             PdfPCell checkBoxCell5 = new PdfPCell();
            checkBoxCell5.setBorder(PdfPCell.NO_BORDER);
            checkBoxCell5.setCellEvent(new PSFtest("cell5"));

            formTable2.addCell(checkBoxCell5);
            System.out.println("Cell 5 added");
            formTable2.addCell(new Paragraph("I would "
                    + "like to attach a statement.", fieldValueFont));


              PdfPCell checkBoxCell6 = new PdfPCell();
            checkBoxCell6.setBorder(PdfPCell.NO_BORDER);
            checkBoxCell6.setCellEvent(new PSFtest("cell6"));

            formTable2.addCell(checkBoxCell6);
            System.out.println("Cell 6 added");
            formTable2.addCell(new Paragraph("I would not "
                    + "like to attach a statement.", fieldValueFont));

             formTable2.addCell("");
            formTable2.addCell("\n\n");
             doc.add(formTable2);


            PdfPTable signTable = new PdfPTable(columnRatio);
            signTable.setWidthPercentage(100f);
            signTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            //signTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            signTable.addCell("");
            signTable.addCell(new Paragraph("_________________________________________________\n"
                    + "Employee Signature                    Date", fieldNameFont));
            doc.add(signTable);
            doc.close();
            }}
        } catch (DocumentException e) {
            logger.error("Error while generating pdf document", e);
            throw new GeneratorException();
        } catch (IOException e) {
            logger.error("Error while doing file I/O", e);
            throw new GeneratorException();
        }
    }

    private Date getDate(String date) {

        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("M/dd/yyyy");
        SimpleDateFormat sdf3 = new SimpleDateFormat("MM/d/yyyy");
        SimpleDateFormat sdf4 = new SimpleDateFormat("M/d/yyyy");
        SimpleDateFormat sdf5 = new SimpleDateFormat("MM-dd-yyyy");


        Date testDate = null;

        try {
            testDate = sdf1.parse(date);
        } catch (ParseException e) {
            try {
                testDate = sdf2.parse(date);
            } catch (ParseException e1) {
                try {
                    testDate = sdf3.parse(date);
                } catch (ParseException e2) {
                    try {
                        testDate = sdf4.parse(date);
                    } catch (ParseException e3) {
                    	try {
                            testDate = sdf5.parse(date);
                        } catch (ParseException e4) {
                        logger.error("Error while parsing a date in the method to " +
                                " generate evaluation forms",e3);
                    }}

                }

            }

        }

        return testDate;

    }

    private class PSFtest implements PdfPCellEvent {

        private String fieldName;

        public PSFtest(String fieldName){
            this.fieldName = fieldName;
        }

        public void cellLayout(PdfPCell cell,
                Rectangle position, PdfContentByte[] canvases) {
            try {
                System.out.println("Cell bottom coordinates " + cell.getBottom());
                System.out.println("rectangle's left most x" + position.getLeft());
                RadioCheckField bt = new RadioCheckField(canvases[0].getPdfWriter(),
                        new Rectangle(position.getLeft()+50,position.getBottom()+10,
                                         position.getLeft()+70,position.getBottom()+30), fieldName, "v1");
                bt.setBorderWidth(BaseField.BORDER_WIDTH_THIN);
                bt.setBorderColor(Color.black);
                bt.setCheckType(RadioCheckField.TYPE_CROSS);
                PdfFormField checkField = bt.getCheckField();
                canvases[0].getPdfWriter().addAnnotation(checkField);
            } catch (IOException iOException) {
                iOException.printStackTrace();
            } catch (DocumentException documentException) {
                documentException.printStackTrace();
            }
        }
    }

    private void copyFileInTempDir(List<String> fileList) {
        try {
            File tempDir= new File("temp");
            //Deleting all the files from the temp folder
            File[] files = tempDir.listFiles();
            for(File file: files){
                file.delete();
            }
            for (String fileName : fileList) {
                InputStream in = new FileInputStream(new File(fileName));
                FileOutputStream fos = new FileOutputStream("temp/" + fileName);
                int nextChar;
                while ((nextChar = in.read()) != -1) {
                    fos.write(nextChar);
                }
                fos.flush();
                fos.close();
                logger.debug("New hire file " + fileName + " created.");
            }
            File temp = new File("temp");
            Desktop.getDesktop().browse(temp.toURI());
        } catch (IOException iOException) {
            logger.error("Error while creating new hire pdf forms in the installation directory", iOException);
        } catch (Exception iOException) {
            logger.error("Error while creating new hire pdf forms in the installation directory", iOException);
        }
    }

    private void makeSingleFile(List<String> fileList) {
        try {
            PdfCopyFields pcf = new PdfCopyFields(new FileOutputStream("test5.pdf"));
            for (String file : fileList) {
                PdfReader reader = new PdfReader(file);
                logger.debug("Inside for loop, file is  " + file + " reader object is " + reader);
                pcf.addDocument(reader);
                reader.close();
            }
            pcf.close();
            SimpleDateFormat sdf = new SimpleDateFormat("HHmmssDDD");
            String fileExtension = sdf.format(new Date());
            File finalFile = new File("CombinedFile_" + fileExtension + ".pdf");
            FileOutputStream fos = new FileOutputStream(finalFile);
            PdfReader pr = new PdfReader("test5.pdf");
            PdfStamper ps = new PdfStamper(pr, fos);
            ps.setFormFlattening(true);
            ps.close();
            fos.flush();
            fos.close();
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + finalFile.getAbsolutePath());
        } catch (DocumentException e) {
            logger.error("Exception while cocatenating files" + e);
        } catch (IOException e) {
            logger.error("Exception while cocatenating files" + e);
        }

    }

    private void makeSingleFile2(List<String> fileList) {
        try {
//            Document doc = new Document();
//            ByteArrayOutputStream boas = new ByteArrayOutputStream();
//            PdfCopy pc = new PdfCopy(doc, boas);
//            doc.open();
//            int i = 0;
            //PdfReader reader = new PdfReader(fileList.get(0));
            PdfCopyFields pcf = new PdfCopyFields(new FileOutputStream("test5.pdf"));
            //pc.addPage(pc.getImportedPage(reader, 1));
            for (String file : fileList) {
                PdfReader reader = new PdfReader(file);
                logger.debug("Inside for loop, file is  " + file + " reader object is " + reader);
                //pc.copyAcroForm(reader);
                pcf.addDocument(reader);
                //pcf.
                //pc.addPage(pc.getImportedPage(reader, 1));
                reader.close();
                // i++;
            }
            pcf.close();
//            pc.close();

//            FileOutputStream fos = new FileOutputStream("test.pdf");
//            boas.writeTo(fos);
//            fos.flush();
            //doc.close();
        } catch (DocumentException e) {
            logger.error("Exception while cocatenating files" + e);
        } catch (IOException e) {
            logger.error("Exception while cocatenating files" + e);
        }

    }

    public static void concatPDFs(List<InputStream> streamOfPDFFiles, OutputStream outputStream, boolean paginate) {

        Document document = new Document();
        try {
            List<InputStream> pdfs = streamOfPDFFiles;
            List<PdfReader> readers = new ArrayList<PdfReader>();
            int totalPages = 0;
            Iterator<InputStream> iteratorPDFs = pdfs.iterator();

            // Create Readers for the pdfs.
            while (iteratorPDFs.hasNext()) {
                InputStream pdf = iteratorPDFs.next();
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
            // Create a writer for the outputstream
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            document.open();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
            // data

            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();

            // Loop through the PDF files and add to the output.
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();

                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
                    cb.addTemplate(page, 0, 0);

                    // Code for pagination.
                    if (paginate) {
                        cb.beginText();
                        cb.setFontAndSize(bf, 9);
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "" + currentPageNumber + " of " + totalPages, 520, 5, 0);
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            outputStream.flush();
            document.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private String getDefaultDir() throws GeneratorException{
        String defaultDir = CommonConstants.APPOINTMENTLETTERS_LOCATION;
        if(defaultDir ==null || defaultDir.trim().equals("")){
                /*JOptionPane.showMessageDialog(StudentAssistantFormGeneratorApp.getApplication().getMainFrame(),
                "Signature image details not present", "Error", JOptionPane.ERROR_MESSAGE);*/
                throw new GeneratorException();
            }
        return defaultDir;
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public static InputStream viewAppointmentLetters2(List<Employee> employeeList, String semester, String year) throws GeneratorException 
    {
	
   String fileLoc = null;
    	System.out.println("Inside generateAppointmentLetters ");
        
    logger.debug("Inside generateAppointmentLetters before listing" +employeeList.size());
   

        byte[] bytes= null;
		for(Employee employee : employeeList) {
        	
			try {
				String directories =CommonConstants.APPOINTMENTLETTERS_LOCATION+ pathSep
	                    + year + pathSep + semester + pathSep +employee.getSupervisor().getName().split(",")[1].trim()+"_"+employee.getSupervisor().getName().split(",")[0].trim()+pathSep;

	            String fileName = employee.getFirstName() + "_"
                        + employee.getLastName() + "_" + employee.getUfid() + "_" + employee.getId()+".pdf";
	            
	            
	           // File appointmentLetter = new File(directories + pathSep + employee.getFirstName() + "_"
                        //+ employee.getLastName() + "_" + employee.getUfid() + "_" + employee.getId()+".pdf");
	            fileLoc = directories + fileName;
	            File file = new File(fileLoc);
	            FileInputStream fis = new FileInputStream(file);
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            byte[] buf = new byte[1024];
	            try {
	                for (int readNum; (readNum = fis.read(buf)) != -1;) {
	                    bos.write(buf, 0, readNum); //no doubt here is 0
	                  
	                    logger.debug("read " + readNum + " bytes,");
	                }
	            } catch (IOException ex) {
	            	ex.printStackTrace();
	            }
	           bytes = bos.toByteArray();
	            File someFile = new File("LOA.pdf");
	            
	            FileOutputStream fos = new FileOutputStream(someFile);
	            fos.write(bytes);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
	        
            
          logger.debug(employee.getFirstName());
            
            logger.debug(fileLoc);
           
         
           
        
        }
        
 
		return new ByteArrayInputStream(bytes);


    
}

    
public static InputStream viewEvalLetters(List<Employee> employeeList, String semester, String year) throws GeneratorException 
{

String fileLoc = null;
	logger.debug("Inside viewEvalLetters ");
    
logger.debug("Inside viewEvalLetters before listing" +employeeList.size());


    byte[] bytes= null;
	for(Employee employee : employeeList) {
    	
		try {
			String directories =CommonConstants.EVALUATION_LETTERS_LOCATION+ pathSep
                    + year + pathSep + semester + pathSep + employee.getSupervisor().getName().split(",")[1].trim()+"_"+employee.getSupervisor().getName().split(",")[0].trim()+ pathSep;

			   String fileName = employee.getFirstName() + "_"
                       + employee.getLastName() + "_" + employee.getUfid() + "_" + employee.getId()+".pdf";
            fileLoc = directories + fileName;
            File file = new File(fileLoc);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum); //no doubt here is 0
                  
                    logger.debug("read " + readNum + " bytes,");
                }
            } catch (IOException ex) {
            	ex.printStackTrace();
            }
           bytes = bos.toByteArray();
            File someFile = new File("Eval.pdf");
            
            FileOutputStream fos = new FileOutputStream(someFile);
            fos.write(bytes);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
        
        
      logger.debug(employee.getFirstName());
        
        logger.debug(fileLoc);
       
     
       
    
    }
    

	return new ByteArrayInputStream(bytes);



}


}


   

