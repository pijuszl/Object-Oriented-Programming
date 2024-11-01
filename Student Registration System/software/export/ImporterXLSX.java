package software.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import software.students.DataSet;
import software.students.Student;


public class ImporterXLSX extends FileImporter {
	String fileName;
	
	public ImporterXLSX(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public void fillData() throws IOException {
		FileInputStream inputStream = null;
		Workbook workbook = null;
		try {
			inputStream = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();
			rowIterator.next();
			while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                
                String name = null, surname = null, programme = null;
                Integer year = null, group = null;
                while (cellIterator.hasNext()) {
                	Cell nextCell = cellIterator.next();
                	int columnIndex = nextCell.getColumnIndex();
                	switch (columnIndex) {
                	case 0:
                	{
                		name = nextCell.getStringCellValue();
                		break;
                	}
                	case 1:
                	{
                		surname = nextCell.getStringCellValue();
                		break;
                	}
                	case 2:
                	{
                		programme = nextCell.getStringCellValue();
                		break;
                	}
                	case 3:
                	{
                		year = (int) nextCell.getNumericCellValue();
                		break;
                	}
                	case 4:
                	{
                		group = (int) nextCell.getNumericCellValue();
                		break;
                	}
                	}
                }
                Student s = new Student(name, surname, programme, year, group);
				DataSet.getInstance().addStudent(s);
			}
			workbook.close();
			inputStream.close();
			FileImporter.showAlert("Success","Data was successfully imported from "+ fileName + ".");
		} catch (Exception e){
			FileImporter.printExceptionMessage();
		} finally {
			if(inputStream != null) {
				inputStream.close();
			}
			if(workbook != null) {
				workbook.close();
			}
		}		
	}

}
