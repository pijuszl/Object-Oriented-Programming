//Pijus Zlatkus, 4 grupe

package chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {

    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            closeServerSocket();
            System.out.println("[" + new Date().toString() + "] Error while connecting to a client.");
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[" + new Date().toString() + "] Error while closing the server socket.");
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 8787;
        ServerSocket serverSocket = new ServerSocket(port);
        Server server = new Server(serverSocket);
        System.out.println("[" + new Date().toString() + "] Server is starting on port " + port + ".");
        server.startServer();
    }

}
