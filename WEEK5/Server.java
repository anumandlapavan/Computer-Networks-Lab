package WEEK5;


import java.io.*;
import java.net.*;

public class Server {

    // Detect and correct single-bit errors in Hamming code
    public static String detectAndCorrect(int[] received) {
        int r = 0;
        while (Math.pow(2, r) < received.length) {
            r++;
        }

        int errorPosition = 0;

        for (int i = 0; i < r; i++) {
            int position = (int) Math.pow(2, i);
            int parity = 0;

            for (int j = 1; j < received.length; j++) {
                if ((j & position) != 0) {
                    parity ^= received[j];
                }
            }

            if (parity != 0) {
                errorPosition += position;
            }
        }

        if (errorPosition == 0) {
            System.out.println("Server: No error detected.");
            return "Good data (no error)";
        } else {
            System.out.println("Server: Error detected at position: " + errorPosition);
            received[errorPosition] ^= 1; // flip the bit to correct it
            System.out.print("Server: Corrected Hamming Code: ");
            for (int i = 1; i < received.length; i++) {
                System.out.print(received[i]);
            }
            System.out.println();
            return "Bad data (error at position " + errorPosition + ")";
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("Server started. Waiting for the client...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected.");

        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Read the Hamming code string from client
        String hammingCodeString = reader.readLine();
        System.out.println("Hamming code received from client: " + hammingCodeString);

        // Convert the string to int array (1-based indexing)
        int[] hammingCode = new int[hammingCodeString.length() + 1];
        for (int i = 1; i < hammingCode.length; i++) {
            hammingCode[i] = hammingCodeString.charAt(i - 1) - '0';
        }

        // Check and correct using the method
        String result = detectAndCorrect(hammingCode);
        out.println(result); // send result back to client

        clientSocket.close();
        serverSocket.close();
    }
}
