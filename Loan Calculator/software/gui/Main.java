//Pijus Zlatkus 4 grupe
package software.gui;

import javafx.scene.control.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.TabPane;
import javafx.fxml.*;

public class Main extends Application {

	private Window program;
	private TabScene scene;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		scene = new TabScene(new TabPane());

		program = new Window(primaryStage);
		program.setWindow(scene);
		program.show();
	}

}