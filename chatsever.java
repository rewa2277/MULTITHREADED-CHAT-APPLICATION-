import java.io.*;
import java.net.*;
import java.util.*;

public class chatserver {

    private static final int PORT = 1234;
    private static Set<clienthandler> clients = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                clienthandler clienthandler = new clienthandler(socket, clients);
                clients.add(clienthandler);
                clienthandler.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
