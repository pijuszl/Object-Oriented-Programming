//Pijus Zlatkus, 4 grupe

package chat;

import chat.gui.ChatController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class Main extends Application {
    private String username;

    @Override
    public void start(Stage stage) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("gui/login.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Chat");
            stage.setScene(scene);
            stage.show();
    }

    @Override
    public void stop() {
        System.out.println("[" + new Date().toString() + "] Program is closing...");
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
