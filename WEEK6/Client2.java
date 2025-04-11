package WEEK6;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter an integer to find its factorial: ");
        String number = scanner.nextLine();
        out.println(number);

        String response = in.readLine();
        System.out.println("Server: " + response);

        socket.close();
    }
}
