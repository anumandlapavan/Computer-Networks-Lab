package WEEK6;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server5 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started. Waiting for client...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String word = in.readLine();
            if (word.equalsIgnoreCase("exit")) break;

            int count = Integer.parseInt(in.readLine());

            Map<Character, Integer> freq = new LinkedHashMap<>();
            for (char c : word.toCharArray()) {
                freq.put(c, freq.getOrDefault(c, 0) + 1);
            }

            boolean found = false;
            for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
                if (entry.getValue() == count) {
                    out.println("Letter '" + entry.getKey() + "' occurs " + count + " times.");
                    found = true;
                    break;
                }
            }

            if (!found) {
                int max = 0;
                char maxChar = ' ';
                for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
                    if (entry.getValue() > max) {
                        max = entry.getValue();
                        maxChar = entry.getKey();
                    }
                }
                out.println("No letter occurs " + count + " times. Max occurring letter: '" + maxChar + "' (" + max + " times)");
            }
        }

        socket.close();
        serverSocket.close();
    }
}
