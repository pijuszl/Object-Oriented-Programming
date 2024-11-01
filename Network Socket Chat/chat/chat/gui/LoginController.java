package chat.gui;

import chat.server.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private TextField tf_username;

    @FXML
    public void loginToChat(ActionEvent event) throws IOException {
        String username = tf_username.getText();
        if (!username.isEmpty()) {
            switchScenes(event, username);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
    protected void switchScenes(ActionEvent event, String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("chat/gui/chat.fxml"));
        loader.setControllerFactory(c -> {
            return new ChatController(username);
        });
        Parent root = loader.load();

        ChatController chatController = loader.getController();
        chatController.displayUsername(username);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
