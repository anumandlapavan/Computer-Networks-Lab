package WEEK6;

import java.net.*;
import java.util.*;
import java.io.*;

public class Server1 {
    private static final Map<String, String> dictionary = new HashMap<>();

    public static void main(String[] args) throws IOException {
        dictionary.put("apple", "A fruit");
        dictionary.put("java", "A programming language");
        dictionary.put("sun", "The star at the center of the solar system");
        dictionary.put("book", "A set of printed pages");
        dictionary.put("pen", "An instrument for writing");
        dictionary.put("car", "A road vehicle");
        dictionary.put("computer", "An electronic device");
        dictionary.put("earth", "Our planet");
        dictionary.put("light", "Electromagnetic radiation");
        dictionary.put("tree", "A perennial plant");
        dictionary.put("water", "A liquid essential to life");
        dictionary.put("air", "The invisible gaseous substance");
        dictionary.put("fire", "Combustion producing heat and light");
        dictionary.put("phone", "Device to make calls");
        dictionary.put("cat", "A small domesticated carnivorous mammal");

        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String word = in.readLine();
        System.out.println("Received word from client: " + word);

        if (dictionary.containsKey(word.toLowerCase())) {
            out.println("Meaning: " + dictionary.get(word.toLowerCase()));
        } else {
            out.println("Word not found. Please send meaning.");
            String meaning = in.readLine();
            dictionary.put(word.toLowerCase(), meaning);
            out.println("Word added to dictionary.");
            System.out.println("Added '" + word + "' with meaning: " + meaning);
        }

        socket.close();
        serverSocket.close();
    }
}
