package WEEK6;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client3 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your marks (0-100): ");
        String marks = scanner.nextLine();
        out.println(marks);

        String response = in.readLine();
        System.out.println(response);

        socket.close();
    }
}
