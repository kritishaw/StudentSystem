package edu.ufl.ece.studentemployment.utils;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import studentemployee.data.Employee;
import studentemployee.data.Project;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfBorderDictionary;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.mysql.jdbc.Constants;

 
public class EmployeeDetailsPdfWriter implements PdfPCellEvent 
{
    /** The text field index of a TextField that needs to be added to a cell. */
    protected String tf;
 
    /**
     * Creates a cell event that will add a text field to a cell.
     * @param tf a text field index.
     */
    public EmployeeDetailsPdfWriter(String tf) 
    {
        this.tf = tf;
    }
 
    /**
     * Manipulates a PDF file src with the file dest as result
     * @param src the original PDF
     * @param dest the resulting PDF
     * @throws IOException
     * @throws DocumentException
     */
    
	ByteArrayOutputStream boas = new ByteArrayOutputStream();

    
    public java.io.InputStream manipulatePdf(String src, String dest, Employee employee)
    {
    try
    	{
    	SimpleDateFormat dateFormater = new SimpleDateFormat("MM-dd-yyyy");
    	PdfReader reader = new PdfReader(src);
    	PdfStamper stamper = new PdfStamper(reader, boas);
        //PdfReader reader = new PdfReader(src);
        //PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        AcroFields form = stamper.getAcroFields();
        form.setField("text_hireDate", dateFormater.format(employee.getHireDate()));
        form.setField("text_semester", employee.getSemester());
        form.setField("text_year", String.valueOf(employee.getYear()));
        form.setField("text_startDate", dateFormater.format(employee.getFromDate()));
        form.setField("text_endDate", dateFormater.format(employee.getToDate()));
        form.setField("text_lastName", employee.getLastName());
        form.setField("text_firstName", employee.getFirstName());
        form.setField("text_email", employee.getEmail());
        form.setField("text_ufid", employee.getUfid());
        form.setField("text_supervisor", employee.getSupervisor().getName());
        form.setField("text_phone", employee.getPhoneNumber());
        form.setField("text_fellowship", employee.getFellowship());
        form.setField("text_title", employee.getTitle());
        form.setField("text_supervisor", employee.getSupervisor().getName());
        form.setField("text_federalWorkStudy", String.valueOf(employee.getFedWorkStudy()));
        form.setField("text_targetAmount", employee.getTargetAmount());
        if(employee.getFte()!=null)
        	form.setField("text_fte", employee.getFte());
        if(employee.getHourly()!=null)
        	form.setField("text_hourlyRate", employee.getHourly());
      
        form.setField("text_duties", employee.getDuties());
        form.setField("text_notes", employee.getNotes());
        
        
        int i=0;
        for(Project project : employee.getProjects())
        {
        	form.setField("text_projectName_"+i, project.getName());
            form.setField("text_projectNumber_"+i, project.getNumber());
            form.setField("text_percentage_"+i, String.valueOf(project.getPercentage()));
            form.setField("text_projectStartDate_"+i, dateFormater.format(project.getStartDate()));
            form.setField("text_projectEndDate_"+i, dateFormater.format(project.getEndDate()));
            i++;
        }
        
       stamper.close();
       reader.close();
       
       Desktop.getDesktop().open(new File(dest));
      
    	}
    	catch(IOException ioe) 
    	{
           throw new ExceptionConverter(ioe);
    	}
    	catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    	catch(Exception e) {
            System.out.println(e.getMessage());
        }
		return new ByteArrayInputStream(boas.toByteArray());
		
		
		
		
    }
 
    /**
     * Creates a PDF document.
     * @param filename the path to the new PDF document
     * @throws    DocumentException 
     * @throws    IOException 
     */
    /*public void createPdf(String filename,int projectCount)
    {
    	try
    	{
        Document document = new Document();
       
        PdfWriter.getInstance(document, new FileOutputStream(filename));
     
        document.open();
    
        document.addHeader("test", "test1");
        PdfPCell cell;
        PdfPCell fieldCell;
        PdfPTable hireDateTable = new PdfPTable(4);
        hireDateTable.setWidths(new int[]{ 1, 2, 1, 2 });
        hireDateTable.setSpacingAfter(5);
        
        // Hire Date 
        fieldCell = new PdfPCell(new Phrase("Hire Date"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        hireDateTable.addCell(fieldCell);
        
        // Hire Date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("hireDate"));
        cell.setBorder(Rectangle.NO_BORDER);
        hireDateTable.addCell(cell);
      
        // Add empty cells 
        fieldCell = new PdfPCell(new Phrase(""));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        hireDateTable.addCell(fieldCell);
        fieldCell = new PdfPCell(new Phrase(""));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        hireDateTable.addCell(fieldCell);
        
        PdfPTable semesterYearTable = new PdfPTable(4);
        semesterYearTable.setWidths(new int[]{ 1, 2, 1, 2 });
        semesterYearTable.setSpacingAfter(5);
       
        // Semester
        fieldCell = new PdfPCell(new Phrase("Semester"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        semesterYearTable.addCell(fieldCell);
     
        // Semester text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("semester"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingTop(10);
        semesterYearTable.addCell(cell);
 
        // Year
        fieldCell = new PdfPCell(new Phrase("Year"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        semesterYearTable.addCell(fieldCell);
        
        // year text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("year"));
        cell.setBorder(Rectangle.NO_BORDER);
        semesterYearTable.addCell(cell);
 
        
        PdfPTable dateTable = new PdfPTable(4);
        dateTable.setWidths(new int[]{ 1, 2, 1, 2 });
        dateTable.setSpacingAfter(5);
        
        // Start date
        fieldCell = new PdfPCell(new Phrase("Start Date"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        dateTable.addCell(fieldCell);
        
        // Start Date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("startDate"));
        cell.setBorder(Rectangle.NO_BORDER);
        dateTable.addCell(cell);
 
        // End date
        fieldCell = new PdfPCell(new Phrase("End Date"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        dateTable.addCell(fieldCell);
        
        // End date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("endDate"));
        cell.setBorder(Rectangle.NO_BORDER);
        dateTable.addCell(cell);
        
        
         * Table for name
         
        
        PdfPTable nameTable = new PdfPTable(4);
        nameTable.setWidths(new int[]{ 1, 2, 1, 2 });
        nameTable.setSpacingBefore(20);
        nameTable.setSpacingAfter(5);
        
        // Last name
        fieldCell = new PdfPCell(new Phrase("Last Name"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        nameTable.addCell(fieldCell);
        
        // Last name text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("lastName"));
        cell.setBorder(Rectangle.NO_BORDER);
        nameTable.addCell(cell);
 
        //First name
        fieldCell = new PdfPCell(new Phrase("First Name"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        nameTable.addCell(fieldCell);
        
        // First name text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("firstName"));
        cell.setBorder(Rectangle.NO_BORDER);
        nameTable.addCell(cell);
        
        
         * Table for details table
         
        
        PdfPTable emailUfidTable = new PdfPTable(4);
        emailUfidTable.setWidths(new int[]{ 1, 2, 1, 2 });
        emailUfidTable.setSpacingAfter(5);
        
        // Email
        fieldCell = new PdfPCell(new Phrase("Email"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        emailUfidTable.addCell(fieldCell);
        
        // email text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("email"));
        cell.setBorder(Rectangle.NO_BORDER);
        emailUfidTable.addCell(cell);
 
        // UFID
        fieldCell = new PdfPCell(new Phrase("UFID"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        emailUfidTable.addCell(fieldCell);
        
        // Ufid text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("ufid"));
        cell.setBorder(Rectangle.NO_BORDER);
        emailUfidTable.addCell(cell);
        
        
        
         * Table for supervisor table
         
        
        PdfPTable supervisorPhoneTable = new PdfPTable(4);
        supervisorPhoneTable.setWidths(new int[]{ 1, 2, 1, 2 });
        supervisorPhoneTable.setSpacingAfter(5);
        
        // Start date
        fieldCell = new PdfPCell(new Phrase("Supervisor"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        supervisorPhoneTable.addCell(fieldCell);
        
        // Start Date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("supervisor"));
        cell.setBorder(Rectangle.NO_BORDER);
        supervisorPhoneTable.addCell(cell);
 
        // End date
        fieldCell = new PdfPCell(new Phrase("Phone"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        supervisorPhoneTable.addCell(fieldCell);
        
        // End date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("phone"));
        cell.setBorder(Rectangle.NO_BORDER);
        supervisorPhoneTable.addCell(cell);
        
        
        
         * Table for fellowship table
         
        
        PdfPTable fellowShipTitleTable = new PdfPTable(4);
        fellowShipTitleTable.setWidths(new int[]{ 1, 2, 1, 2 });
        fellowShipTitleTable.setSpacingAfter(5);
        fellowShipTitleTable.setSpacingBefore(20);
        
        // Start date
        fieldCell = new PdfPCell(new Phrase("Fellowship"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        fellowShipTitleTable.addCell(fieldCell);
        
        // Start Date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("fellowship"));
        cell.setBorder(Rectangle.NO_BORDER);
        fellowShipTitleTable.addCell(cell);
 
        // End date
        fieldCell = new PdfPCell(new Phrase("Title"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        fellowShipTitleTable.addCell(fieldCell);
        
        // End date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("title"));
        cell.setBorder(Rectangle.NO_BORDER);
        fellowShipTitleTable.addCell(cell);
        
        
        
         * Table for supervisor table
         
        
        PdfPTable workDetailsTable = new PdfPTable(4);
        workDetailsTable.setWidths(new int[]{ 2, 1, 2, 1 });
        workDetailsTable.setSpacingAfter(5);
        
        // Start date
        fieldCell = new PdfPCell(new Phrase("Federal Work Study"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        workDetailsTable.addCell(fieldCell);
        
        // Start Date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("federalWorkStudy"));
        cell.setBorder(Rectangle.NO_BORDER);
        workDetailsTable.addCell(cell);
 
        // End date
        fieldCell = new PdfPCell(new Phrase("Target Amount"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        workDetailsTable.addCell(fieldCell);
        
        // End date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("targetAmount"));
        cell.setBorder(Rectangle.NO_BORDER);
        workDetailsTable.addCell(cell);
        
        
         * Table for supervisor table
         
        
        PdfPTable fteHourlyTable = new PdfPTable(4);
        fteHourlyTable.setWidths(new int[]{ 2, 1, 2, 1 });
        fteHourlyTable.setSpacingAfter(5);
        
        // Start date
        fieldCell = new PdfPCell(new Phrase("FTE"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        fteHourlyTable.addCell(fieldCell);
        
        // Start Date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("fte"));
        cell.setBorder(Rectangle.NO_BORDER);
        fteHourlyTable.addCell(cell);
 
        // End date
        fieldCell = new PdfPCell(new Phrase("Rate/Hour"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        fteHourlyTable.addCell(fieldCell);
        
        // End date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("hourlyRate"));
        cell.setBorder(Rectangle.NO_BORDER);
        fteHourlyTable.addCell(cell);
        
        
        
         * Table for dutiesNotes table
         
        
        PdfPTable dutiesNotesTable = new PdfPTable(4);
        dutiesNotesTable.setWidths(new int[]{ 1, 2, 1, 2 });
        dutiesNotesTable.setSpacingAfter(5);
        dutiesNotesTable.setSpacingBefore(20);
        
        
        // Duties
        fieldCell = new PdfPCell(new Phrase("Duties"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        dutiesNotesTable.addCell(fieldCell);
        
        // Duties text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("duties"));
        cell.setBorder(Rectangle.NO_BORDER);
        dutiesNotesTable.addCell(cell);
 
        // Notes
        fieldCell = new PdfPCell(new Phrase("Notes"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        dutiesNotesTable.addCell(fieldCell);
        
        // Notes text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("notes"));
        cell.setBorder(Rectangle.NO_BORDER);
        dutiesNotesTable.addCell(cell);
        
        
        
         * Table for projects
         
        PdfPTable projectTable = new PdfPTable(5);
        projectTable.setWidths(new int[]{ 1, 1, 1, 1, 1 });
        projectTable.setSpacingAfter(5);
        projectTable.setSpacingBefore(20);
        
     // Project Name
    	fieldCell = new PdfPCell(new Phrase("Project Name"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        projectTable.addCell(fieldCell);
        
       
        
        // Project Number
        fieldCell = new PdfPCell(new Phrase("Project #"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        projectTable.addCell(fieldCell);
        
        
        
        
        // Percentage
        fieldCell = new PdfPCell(new Phrase("Percentage"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        projectTable.addCell(fieldCell);
        
       
        
        
     // start date
        fieldCell = new PdfPCell(new Phrase("Start Date"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        projectTable.addCell(fieldCell);
        
      
        
        
     // End date
        fieldCell = new PdfPCell(new Phrase("End date"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        projectTable.addCell(fieldCell);
        
        
        
        for(int i=0;i<projectCount;i++)
        {
        	 // Project Name text field
            cell = new PdfPCell();
            cell.setCellEvent(new EmployeeDetailsPdfWriter("projectName_"+i));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(15);
            projectTable.addCell(cell);
            
         // Project Number field
            cell = new PdfPCell();
            cell.setCellEvent(new EmployeeDetailsPdfWriter("projectNumber_"+i));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(15);
            projectTable.addCell(cell);
          
            //percentage text field
            cell = new PdfPCell();
            cell.setCellEvent(new EmployeeDetailsPdfWriter("percentage_"+i));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(15);
            projectTable.addCell(cell);
            
            // start date text field
            cell = new PdfPCell();
            cell.setCellEvent(new EmployeeDetailsPdfWriter("projectStartDate_"+i));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(15);
            projectTable.addCell(cell);
            
         // End date text field
            cell = new PdfPCell();
            cell.setCellEvent(new EmployeeDetailsPdfWriter("projectEndDate_"+i));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(15);
            projectTable.addCell(cell);
          
        }
        
        document.add(hireDateTable);
        document.add(semesterYearTable);
        document.add(dateTable);
        document.add(nameTable);
        document.add(emailUfidTable);
        document.add(supervisorPhoneTable);
        document.add(fellowShipTitleTable);
        document.add(workDetailsTable);
        document.add(dutiesNotesTable);
        document.add(projectTable);
        
     
        document.close();
    	}
    	
    	catch(IOException ioe) {
            throw new ExceptionConverter(ioe);
        }
        catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }*/
 
    /**
     * Creates and adds a text field that will be added to a cell.
     * @see com.itextpdf.text.pdf.PdfPCellEvent#cellLayout(com.itextpdf.text.pdf.PdfPCell,
     *      com.itextpdf.text.Rectangle, com.itextpdf.text.pdf.PdfContentByte[])
     */
    public void cellLayout(PdfPCell cell, Rectangle rectangle, PdfContentByte[] canvases) {
        PdfWriter writer = canvases[0].getPdfWriter();
        TextField text = new TextField(writer, rectangle,
                String.format("text_%s", tf));
        text.setBackgroundColor(new GrayColor(0.75f));
        text.setBorderStyle(PdfBorderDictionary.STYLE_SOLID);
       
 
        try {
            PdfFormField field = text.getTextField();
            writer.addAnnotation(field);
        }
        catch(IOException ioe) {
            throw new ExceptionConverter(ioe);
        }
        catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }
 
    /**
     * Main method
     * @param args no arguments needed
     * @throws IOException
     * @throws DocumentException
     *//*
    public static void main(String[] args) throws DocumentException, IOException {
    	EmployeeDetailsPdfWriter example = new EmployeeDetailsPdfWriter(null);
        example.createPdf(RESULT1);
        example.manipulatePdf(RESULT1, RESULT2);
    }*/
    
    public void createPdf(String filename,int projectCount)
    {
    try
    {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();

        PdfPCell cell;
        PdfPCell fieldCell;

        
       
        
        
        PdfPTable headingTable = new PdfPTable(1);
        headingTable.setWidths(new int[]{1});
        headingTable.setSpacingAfter(10);

        // Hire Date
        fieldCell = new PdfPCell(new Phrase("Employee Appointment Details", new Font(FontFamily.HELVETICA, 20)));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        fieldCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fieldCell.setPaddingTop(3);
        fieldCell.setPaddingBottom(6);
        headingTable.addCell(fieldCell);

        PdfPTable hireDateTable = new PdfPTable(4);
        hireDateTable.setWidths(new int[]{ 1, 2, 1, 2 });
        hireDateTable.setSpacingAfter(5);

        // Hire Date
        fieldCell = new PdfPCell(new Phrase("Hire Date"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        hireDateTable.addCell(fieldCell);

        // Hire Date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("hireDate"));
        cell.setBorder(Rectangle.NO_BORDER);
        hireDateTable.addCell(cell);

        // Add empty cells
        fieldCell = new PdfPCell(new Phrase(""));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        hireDateTable.addCell(fieldCell);
        fieldCell = new PdfPCell(new Phrase(""));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        hireDateTable.addCell(fieldCell);

        PdfPTable semesterYearTable = new PdfPTable(4);
        semesterYearTable.setWidths(new int[]{ 1, 2, 1, 2 });
        semesterYearTable.setSpacingAfter(5);

        // Semester
        fieldCell = new PdfPCell(new Phrase("Semester"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        semesterYearTable.addCell(fieldCell);

        // Semester text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("semester"));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingTop(10);
        semesterYearTable.addCell(cell);

        // Year
        fieldCell = new PdfPCell(new Phrase("Year"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        semesterYearTable.addCell(fieldCell);

        // year text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("year"));
        cell.setBorder(Rectangle.NO_BORDER);
        semesterYearTable.addCell(cell);


        PdfPTable dateTable = new PdfPTable(4);
        dateTable.setWidths(new int[]{ 1, 2, 1, 2 });
        dateTable.setSpacingAfter(5);

        // Start date
        fieldCell = new PdfPCell(new Phrase("Start Date"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        dateTable.addCell(fieldCell);

        // Start Date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("startDate"));
        cell.setBorder(Rectangle.NO_BORDER);
        dateTable.addCell(cell);

        // End date
        fieldCell = new PdfPCell(new Phrase("End Date"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        dateTable.addCell(fieldCell);

        // End date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("endDate"));
        cell.setBorder(Rectangle.NO_BORDER);
        dateTable.addCell(cell);

        /*
         * Table for name
         */

        PdfPTable nameTable = new PdfPTable(4);
        nameTable.setWidths(new int[]{ 1, 2, 1, 2 });
        nameTable.setSpacingBefore(20);
        nameTable.setSpacingAfter(5);

        // Last name
        fieldCell = new PdfPCell(new Phrase("Last Name"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        nameTable.addCell(fieldCell);

        // Last name text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("lastName"));
        cell.setBorder(Rectangle.NO_BORDER);
        nameTable.addCell(cell);

        //First name
        fieldCell = new PdfPCell(new Phrase("First Name"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        nameTable.addCell(fieldCell);

        // First name text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("firstName"));
        cell.setBorder(Rectangle.NO_BORDER);
        nameTable.addCell(cell);

        /*
         * Table for details table
         */

        PdfPTable emailUfidTable = new PdfPTable(4);
        emailUfidTable.setWidths(new int[]{ 1, 2, 1, 2 });
        emailUfidTable.setSpacingAfter(5);

        // Email
        fieldCell = new PdfPCell(new Phrase("Email"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        emailUfidTable.addCell(fieldCell);

        // email text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("email"));
        cell.setBorder(Rectangle.NO_BORDER);
        emailUfidTable.addCell(cell);

        // UFID
        fieldCell = new PdfPCell(new Phrase("UFID"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        emailUfidTable.addCell(fieldCell);

        // Ufid text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("ufid"));
        cell.setBorder(Rectangle.NO_BORDER);
        emailUfidTable.addCell(cell);


        /*
         * Table for supervisor table
         */

        PdfPTable supervisorPhoneTable = new PdfPTable(4);
        supervisorPhoneTable.setWidths(new int[]{ 1, 2, 1, 2 });
        supervisorPhoneTable.setSpacingAfter(5);

        // Start date
        fieldCell = new PdfPCell(new Phrase("Supervisor"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        supervisorPhoneTable.addCell(fieldCell);

        // Start Date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("supervisor"));
        cell.setBorder(Rectangle.NO_BORDER);
        supervisorPhoneTable.addCell(cell);

        // End date
        fieldCell = new PdfPCell(new Phrase("Phone"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        supervisorPhoneTable.addCell(fieldCell);

        // End date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("phone"));
        cell.setBorder(Rectangle.NO_BORDER);
        supervisorPhoneTable.addCell(cell);


        /*
         * Table for fellowship table
         */

        PdfPTable fellowShipTitleTable = new PdfPTable(4);
        fellowShipTitleTable.setWidths(new int[]{ 1, 2, 1, 2 });
        fellowShipTitleTable.setSpacingAfter(5);
        fellowShipTitleTable.setSpacingBefore(20);

        // Start date
        fieldCell = new PdfPCell(new Phrase("Fellowship"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        fellowShipTitleTable.addCell(fieldCell);

        // Start Date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("fellowship"));
        cell.setBorder(Rectangle.NO_BORDER);
        fellowShipTitleTable.addCell(cell);

        // End date
        fieldCell = new PdfPCell(new Phrase("Title"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        fellowShipTitleTable.addCell(fieldCell);

        // End date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("title"));
        cell.setBorder(Rectangle.NO_BORDER);
        fellowShipTitleTable.addCell(cell);


        /*
         * Table for supervisor table
         */

        PdfPTable workDetailsTable = new PdfPTable(4);
        workDetailsTable.setWidths(new int[]{ 2, 1, 2, 1 });
        workDetailsTable.setSpacingAfter(5);

        // Start date
        fieldCell = new PdfPCell(new Phrase("Federal Work Study"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        workDetailsTable.addCell(fieldCell);

        // Start Date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("federalWorkStudy"));
        cell.setBorder(Rectangle.NO_BORDER);
        workDetailsTable.addCell(cell);

        // End date
        fieldCell = new PdfPCell(new Phrase("Target Amount"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        workDetailsTable.addCell(fieldCell);

        // End date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("targetAmount"));
        cell.setBorder(Rectangle.NO_BORDER);
        workDetailsTable.addCell(cell);

        /*
         * Table for supervisor table
         */

        PdfPTable fteHourlyTable = new PdfPTable(4);
        fteHourlyTable.setWidths(new int[]{ 2, 1, 2, 1 });
        fteHourlyTable.setSpacingAfter(5);

        // Start date
        fieldCell = new PdfPCell(new Phrase("FTE"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        fteHourlyTable.addCell(fieldCell);

        // Start Date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("fte"));
        cell.setBorder(Rectangle.NO_BORDER);
        fteHourlyTable.addCell(cell);

        // End date
        fieldCell = new PdfPCell(new Phrase("Rate/Hour"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        fteHourlyTable.addCell(fieldCell);

        // End date text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("hourlyRate"));
        cell.setBorder(Rectangle.NO_BORDER);
        fteHourlyTable.addCell(cell);


        /*
         * Table for dutiesNotes table
         */

        PdfPTable dutiesNotesTable = new PdfPTable(4);
        dutiesNotesTable.setWidths(new int[]{ 1, 2, 1, 2 });
        dutiesNotesTable.setSpacingAfter(5);
        dutiesNotesTable.setSpacingBefore(20);


        // Duties
        fieldCell = new PdfPCell(new Phrase("Duties"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        dutiesNotesTable.addCell(fieldCell);

        // Duties text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("duties"));
        cell.setBorder(Rectangle.NO_BORDER);
        dutiesNotesTable.addCell(cell);

        // Notes
        fieldCell = new PdfPCell(new Phrase("Notes"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        dutiesNotesTable.addCell(fieldCell);

        // Notes text field
        cell = new PdfPCell();
        cell.setCellEvent(new EmployeeDetailsPdfWriter("notes"));
        cell.setBorder(Rectangle.NO_BORDER);
        dutiesNotesTable.addCell(cell);


        /*
         * Table for projects
         */
        PdfPTable projectTable = new PdfPTable(5);
        projectTable.setWidths(new int[]{ 1, 1, 1, 1, 1 });
        projectTable.setSpacingAfter(5);
        projectTable.setSpacingBefore(20);

     // Project Name
    fieldCell = new PdfPCell(new Phrase("Project Name"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        projectTable.addCell(fieldCell);



        // Project Number
        fieldCell = new PdfPCell(new Phrase("Project #"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        projectTable.addCell(fieldCell);




        // Percentage
        fieldCell = new PdfPCell(new Phrase("Percentage"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        projectTable.addCell(fieldCell);




     // start date
        fieldCell = new PdfPCell(new Phrase("Start Date"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        projectTable.addCell(fieldCell);




     // End date
        fieldCell = new PdfPCell(new Phrase("End date"));
        fieldCell.setBorder(Rectangle.NO_BORDER);
        projectTable.addCell(fieldCell);



        for(int i=0;i<projectCount;i++)
        {
        // Project Name text field
            cell = new PdfPCell();
            cell.setCellEvent(new EmployeeDetailsPdfWriter("projectName_"+i));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(15);
            projectTable.addCell(cell);

         // Project Number field
            cell = new PdfPCell();
            cell.setCellEvent(new EmployeeDetailsPdfWriter("projectNumber_"+i));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(15);
            projectTable.addCell(cell);

            //percentage text field
            cell = new PdfPCell();
            cell.setCellEvent(new EmployeeDetailsPdfWriter("percentage_"+i));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(15);
            projectTable.addCell(cell);

            // start date text field
            cell = new PdfPCell();
            cell.setCellEvent(new
EmployeeDetailsPdfWriter("projectStartDate_"+i));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(15);
            projectTable.addCell(cell);

         // End date text field
            cell = new PdfPCell();
            cell.setCellEvent(new
EmployeeDetailsPdfWriter("projectEndDate_"+i));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setFixedHeight(15);
            projectTable.addCell(cell);

        }

        document.add(headingTable);
        document.add(hireDateTable);
        document.add(semesterYearTable);
        document.add(dateTable);
        document.add(nameTable);
        document.add(emailUfidTable);
        document.add(supervisorPhoneTable);
        document.add(fellowShipTitleTable);
        document.add(workDetailsTable);
        document.add(dutiesNotesTable);
        document.add(projectTable);


        document.close();
    }

    catch(IOException ioe) {
            throw new ExceptionConverter(ioe);
        }
        catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

	
}