package WEEK6;


import java.io.*;
import java.net.*;

public class Server2 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started. Waiting for client...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String input = in.readLine();
        int number = Integer.parseInt(input);
        long factorial = 1;
        for (int i = 2; i <= number; i++) {
            factorial *= i;
        }

        out.println("Factorial of " + number + " is: " + factorial);

        socket.close();
        serverSocket.close();
    }
}

