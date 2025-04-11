package WEEK4;

import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        int port = 12345;
        String host = "localhost";

        try(
                Socket socket = new Socket(host,port);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
        ){
            System.out.println("Connected to the server");

            String message;

            while((message = reader.readLine()) != null){
                out.println(message);
                if(message.equalsIgnoreCase("exit")) break;
            }

            System.out.println("Disconnected from server.");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
