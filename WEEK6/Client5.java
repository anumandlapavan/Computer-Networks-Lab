package WEEK6;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client5 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a word (or 'exit' to quit): ");
            String word = scanner.nextLine();
            out.println(word);
            if (word.equalsIgnoreCase("exit")) break;

            System.out.print("Enter a number: ");
            String num = scanner.nextLine();
            out.println(num);

            String response = in.readLine();
            System.out.println("Server: " + response);
        }

        socket.close();
    }
}
