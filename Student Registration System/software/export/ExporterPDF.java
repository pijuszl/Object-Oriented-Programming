package software.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import software.students.DataSet;
import software.students.DateEntry;
import software.students.Student;


public class ExporterPDF implements FileExporter {
	String fileName;
	boolean isFiltered = false;
	LocalDate filter1, filter2;
	
	public ExporterPDF(String fileName) {
		this.fileName = fileName;
	}
	
	public ExporterPDF(String fileName, LocalDate filter1, LocalDate filter2) {
		this.fileName = fileName;
		this.isFiltered = true;
		this.filter1 = filter1;
		this.filter2 = filter2;
	}
	
	@Override
	public void export(List<Student> students) throws IOException {		
		Document doc = new Document(PageSize.A4.rotate());
		try  
		{  
		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(fileName));
		doc.open(); 
		int howManyDays = 0;
		if(DataSet.getInstance().getList().isEmpty()) {
			FileExporter.showAlert("Error","No elements to export were found.");
			return;
		}
		if(isFiltered)
		{
			if(!DataSet.getInstance().getList().isEmpty()) {
				for(DateEntry de : DataSet.getInstance().getList().get(0).getAttendanceValues()) {
					if(!de.isFiltered(filter1, filter2)) {
						++howManyDays;
					}
				}
			}
		} else {
			howManyDays = DataSet.getInstance().getList().get(0).getAttendanceValues().size();
		}
		
		int numColumns = 5 + howManyDays;
        PdfPTable table = new PdfPTable(numColumns);
        table.addCell(new Phrase("name", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, BaseColor.BLACK)));
        table.addCell(new Phrase("surname", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, BaseColor.BLACK)));
        table.addCell(new Phrase("programme", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, BaseColor.BLACK)));
        table.addCell(new Phrase("yr", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, BaseColor.BLACK)));
        table.addCell(new Phrase("gr", FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, BaseColor.BLACK)));
        
        for(DateEntry dt : DataSet.getInstance().getList().get(0).getAttendanceValues()) {
        	if(DataSet.getInstance().isDataFiltered()) {
        		if(dt.isFiltered(DataSet.getInstance().getFilter1(), DataSet.getInstance().getFilter2()))
        			continue;
        	}
    		String value = dt.getDate().toString();
            table.addCell(new Phrase(value, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, BaseColor.BLACK)));
        }
    	
        for(Student student : students) {
            table.addCell(student.getName());
            table.addCell(student.getSurname());
            table.addCell(student.getStudyProgramme());
            table.addCell("" + student.getYear());
            table.addCell("" + student.getGroup());
            for(DateEntry dt : DataSet.getInstance().getList().get(0).getAttendanceValues()) {
            	if(DataSet.getInstance().isDataFiltered()) {
            		if(dt.isFiltered(DataSet.getInstance().getFilter1(), DataSet.getInstance().getFilter2()))
            			continue;
            	}
				Boolean value = dt.getAttendance();
				table.addCell(value.toString());
            }
        }
        doc.add(table);
		doc.close();  
		writer.close();
			FileExporter.showAlert("Success","A file "+ fileName + " was filled with SRS data.");
		}   
		catch (Exception e) { 
			e.printStackTrace();
			FileExporter.printExceptionMessage();
		}   
	}
}