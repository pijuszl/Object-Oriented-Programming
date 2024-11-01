package chat.gui;

import chat.server.Client;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.lang.management.PlatformLoggingMXBean;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    private Client client;

    @FXML
    private ScrollPane sp_text;

    @FXML
    private VBox vbox_messages;

    @FXML
    private TextField tf_message;

    @FXML
    private Label lbl_username;

    private String username;
    public void displayUsername(String username) {
        lbl_username.setText("Username: " + username);
    }

    @FXML
    public void sendMessage(ActionEvent event) throws IOException {
        String messageToSend = tf_message.getText();
        if (!messageToSend.isEmpty())
        {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 10));

            Text text = new Text(messageToSend);
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle("-fx-color: rgb(239,242,255); " + "-fx-background-color: rgb(15,125,242);" + " -fx-background-radius: 20px;");
            textFlow.setPadding (new Insets(5, 10, 5, 10));
            text.setFill(Color.color(0.934, 0.945, 0.996));

            hBox.getChildren().add(textFlow);
            vbox_messages.getChildren().add(hBox);

            client.sendMessage(messageToSend);
            tf_message.clear();
        }
    }

    public ChatController(String username)
    {
        this.username = username;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try {
            Socket socket = new Socket("localhost", 8787);
            System.out.println("[" + new Date().toString() + "] Successfully joined a server on port 8787.");
            client = new Client(socket, username);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("[" + new Date().toString() + "] Error while connecting to the server.");
        }

        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_text.setVvalue((Double) newValue);
            }
        });

        client.listenForMessage(vbox_messages);
        client.sendMessage(username);
    }

    public static void addLabel(String messageFromClient, VBox vbox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233, 233, 235);" + " -fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);
            }
        });
    }


}
