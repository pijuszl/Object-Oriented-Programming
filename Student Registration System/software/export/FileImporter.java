package software.export;

import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class FileImporter {
	public abstract void fillData() throws IOException;
	
	public static void printExceptionMessage() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Import failed");
		alert.setHeaderText(null);
		alert.setContentText("There has been an error trying to read from the file. Make sure the file you are trying to import the data from is closed.");
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
