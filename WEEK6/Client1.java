package WEEK6;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client1 {

    public static void main(String[] args) throws IOException {
        List<String> wordList = Arrays.asList(
                "apple", "banana", "computer", "dolphin", "elephant",
                "football", "guitar", "happiness", "internet", "jungle",
                "kite", "lion", "mountain", "notebook", "ocean",
                "penguin", "queen", "river", "sun", "tiger"
        );

        Socket socket = new Socket("localhost", 12345);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        Random random = new Random();
        String wordToSend = wordList.get(random.nextInt(wordList.size()));


        System.out.println("Sending word to server: " + wordToSend);
        out.println(wordToSend);

        String serverResponse = in.readLine();
        System.out.println("Server: " + serverResponse);

        if (serverResponse.contains("not found")) {
            System.out.print("Enter the meaning for '" + wordToSend + "': ");
            String meaning = scanner.nextLine();
            out.println(meaning);
            System.out.println("Server: " + in.readLine());
        }

        socket.close();
    }
}
