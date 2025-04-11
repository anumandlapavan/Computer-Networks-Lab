package WEEK4;

import java.nio.Buffer;
import java.util.*;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 12345;

        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server started.Waiting for the client");

            Socket clientSocket = serverSocket.accept();

            System.out.println("Client connected to the server");

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message;

            while((message = reader.readLine()) != null){
                System.out.println("Client : "+message);
            }

            reader.close();
            clientSocket.close();
            System.out.println("Client disconnected.");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
