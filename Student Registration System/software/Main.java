// Pijus Zlatkus, 4 grupe

package software;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("gui/Menu.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Student Registration System");
		stage.setScene(scene);
		stage.show();
	}
}