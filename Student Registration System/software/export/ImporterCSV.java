package software.export;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import software.students.DataSet;
import software.students.Student;

public class ImporterCSV extends FileImporter {
	String fileName;
	
	public ImporterCSV(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public void fillData() throws IOException {
		BufferedReader reader = null;
		String line;
		try (FileReader freader = new FileReader(fileName)) {
			reader = new BufferedReader(freader);
			int linesRead = 0;
			while((line = reader.readLine()) != null) {
				if(linesRead == 0)
				{
					++linesRead;
					continue;
				}
				String[] row = line.split(",");
				String name = row[0];
				String surname = row[1];
				String programme = row[2];
				String yearStr = row[3];
				int year = Integer.parseInt("0"+yearStr);
				String groupStr = row[4];
				int group = Integer.parseInt("0"+groupStr);
				Student s = new Student(name, surname, programme, year, group);
				DataSet.getInstance().addStudent(s);
			}
			FileImporter.showAlert("Success","Data was successfully imported from "+ fileName + ".");
		} catch (Exception e) {
			FileImporter.printExceptionMessage();
		} finally {
			if(reader != null)
				reader.close();
		}
	}
}
