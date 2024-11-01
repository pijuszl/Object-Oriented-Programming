package chat.server;

import chat.gui.ChatController;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.username = username;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[" + new Date().toString() + "] Error while opening a buffer reader/writer.");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(String messageToSend)
    {
        try {
            bufferedWriter.write(messageToSend);
            bufferedWriter.newLine ();
            bufferedWriter.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("[" + new Date().toString() + "] Error while sending message to the server.");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String messageFromSender = bufferedReader.readLine();
                        ChatController.addLabel(messageFromSender, vBox);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("[" + new Date().toString() + "] Error while receiving message from the server.");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[" + new Date().toString() + "] Error while closing a client.");
        }
    }
}
