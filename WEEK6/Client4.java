package WEEK6;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client4 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i <= 4; i++) {
            System.out.println("Sending: " + i);
            out.println(i);
            String response = in.readLine();
            System.out.println("Server: " + response);
        }

        socket.close();
    }
}
