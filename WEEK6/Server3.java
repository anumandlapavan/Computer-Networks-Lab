package WEEK6;

import java.io.*;
import java.net.*;

public class Server3 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started. Waiting for client...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String input = in.readLine();
        int marks = Integer.parseInt(input);
        String grade;

        if (marks >= 85 && marks <= 100) {
            grade = "Grade A";
        } else if (marks >= 70 && marks < 85) {
            grade = "Grade B";
        } else if (marks >= 60 && marks < 70) {
            grade = "Grade C";
        } else if (marks >= 50 && marks < 60) {
            grade = "Grade D";
        } else if (marks >= 0 && marks < 50) {
            grade = "Fail";
        } else {
            grade = "Invalid marks entered";
        }

        out.println("Server: Your grade is " + grade);

        socket.close();
        serverSocket.close();
    }
}
