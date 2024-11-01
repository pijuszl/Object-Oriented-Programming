package software.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window {
    private Stage stage;

    public Window(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Loan calculator");
    }

    public void setWindow(Scene scene) {
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }
}
