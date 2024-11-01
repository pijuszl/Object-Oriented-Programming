package software.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import software.students.Student;

public class ExporterCSV implements FileExporter {
	String fileName;
	boolean append;
	
	public ExporterCSV(String fileName, boolean append) {
		this.fileName = fileName;
		this.append = append;
	}
	
	@Override
	public void export(List<Student> students) throws IOException {
		File file = null;
		BufferedWriter writer = null;
		FileWriter fw = null;
		try {
			file = new File(fileName);
			fw = new FileWriter(fileName, append);
		    writer = new BufferedWriter(fw);
		    
		    if(!file.exists() || !append) {
		    	writer.append("name,");
		    	writer.append("surname,");
		    	writer.append("studyProgramme,");
		    	writer.append("year,");
		    	writer.append("group,");
		    	writer.append("\n");		    
		    }

		    for (Student student : students) {
		    	writer.append(student.getName()+",");
		    	writer.append(student.getSurname()+",");
		    	writer.append(student.getStudyProgramme()+",");
		    	writer.append(student.getYear()+",");
		    	writer.append(student.getGroup()+",");
		    	writer.append("\n");
		    }

		    writer.flush();
			FileExporter.showAlert("Success","A file "+ fileName + " was filled with SRS data.");
		} catch (Exception e) {
			FileExporter.printExceptionMessage();
		}
		finally {
			if(writer != null)
				writer.close();
			if(fw != null)
				fw.close();
		}
	}
}