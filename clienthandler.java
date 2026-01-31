import java.io.*;
import java.net.*;
import java.util.*;

public class clienthandler extends Thread {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Set<clienthandler> clients;

    public clienthandler(Socket socket, Set<clienthandler> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Welcome to the chat! Type 'exit' to leave.");

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                broadcast(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                clients.remove(this);
                System.out.println("client disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcast(String message) {
        for (clienthandler client : clients) {
            if (client != this) {
                client.out.println("Client: " + message);
            }
        }
    }
}
