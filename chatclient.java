import java.io.*;
import java.net.*;

public class chatclient {

    public static void main(String[] args) {

        String serverAddress = "localhost"; // or IP address
        int port = 1234;

        try (
            Socket socket = new Socket(serverAddress, port);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("Connected to chat server");

            // Thread to read messages from server
            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = input.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Send messages to server
            String userInput;
            while ((userInput = keyboard.readLine()) != null) {
                output.println(userInput);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
