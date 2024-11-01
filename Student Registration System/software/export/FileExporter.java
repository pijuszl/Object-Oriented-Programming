package software.export;

import java.io.IOException;
import java.util.List;

import software.students.Student;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public interface FileExporter {
	public void export(List<Student> students) throws IOException;
	
	public static void printExceptionMessage() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Export failed");
		alert.setHeaderText(null);
		alert.setContentText("There has been an error trying to write to the file. Make sure the file you are trying to export the data to is closed.");
		alert.showAndWait();	
	}
	
	public static void showAlert(String title, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.showAndWait();
	}
}
