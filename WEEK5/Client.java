package WEEK5;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        System.out.println("Connected to server.");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Hamming code (as string of 0s and 1s): ");
        String hammingCode = sc.nextLine();

        out.println(hammingCode); // Send to server

        String response = in.readLine(); // Server's reply
        System.out.println("Server says: " + response);

        socket.close();
    }
}
