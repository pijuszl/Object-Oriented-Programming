package software.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import software.students.Student;

public class ExporterXLSX implements FileExporter {
	String fileName;
	
	public ExporterXLSX(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public void export(List<Student> students) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		XSSFRow firstRow = sheet.createRow(0);		
        for (int col = 0; col < 5; ++col){
        	String value = "";

        	switch(col) {
        	case 0:
        	{
        		value = "name";
        		break;
        	}
        	case 1:
        	{
        		value = "surname";
        		break;
        	}
        	case 2:
        	{
        		value = "studyProgramme";
        		break;
        	}
        	case 3:
        	{
        		value = "year";
        		break;
        	}
        	case 4:
        	{
        		value = "group";
        		break;
        	}
        	}
            firstRow.createCell(col).setCellValue(value);
        }
		
        int row = 0;
        for (Student student : students){

            XSSFRow xRow = sheet.createRow(row+1);
        
            String name = student.getName();
            String surname = student.getSurname();
            String programme = student.getStudyProgramme();
            Integer year = student.getYear();
            Integer group = student.getGroup();

            xRow.createCell(0).setCellValue(name);
            xRow.createCell(1).setCellValue(surname);
            xRow.createCell(2).setCellValue(programme);
            xRow.createCell(3).setCellValue(year);
            xRow.createCell(4).setCellValue(group);
            
            ++row;
        }
        try {
            workbook.write(new FileOutputStream(fileName));
            workbook.close();
			FileExporter.showAlert("Success","A file "+ fileName + " was filled with SRS data.");
        } catch (IOException e) {
			FileExporter.printExceptionMessage();
        }
	}
}
