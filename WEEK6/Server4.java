package WEEK6;

import java.io.*;
import java.net.*;

public class Server4 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started. Waiting for client...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String input;
        while ((input = in.readLine()) != null) {
            int num = Integer.parseInt(input);
            int result = 0;

            switch (num) {
                case 0:
                case 1:
                    result = 3;
                    break;
                case 2:
                    result = 7;
                    break;
                case 3:
                    result = 15;
                    break;
                case 4:
                    result = 27;
                    break;
                default:
                    out.println("Invalid input.");
                    continue;
            }

            out.println("Result: " + result);
        }

        socket.close();
        serverSocket.close();
    }
}
