package WEEK9;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ChatClient implements Runnable {
    private String username;
    private String serverAddress;
    private int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Encryption encryption;
    private Scanner scanner;
    private boolean running;

    public ChatClient(String username, String serverAddress, int port) {
        this.username = username;
        this.serverAddress = serverAddress;
        this.port = port;
        this.encryption = new Encryption();
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(serverAddress, port);
            System.out.println("Connected to chat server: " +
                    serverAddress + ":" + port);

            in = new BufferedReader(new
                    InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println(encryption.encrypt(username));

            new Thread(this::receiveMessages).start();

            while (running) {
                System.out.print("[" + username + "]: ");
                String message = scanner.nextLine();

                if (message.startsWith("/file ")) {
                    sendFile(message.substring(6));
                } else if (message.equals("/quit")) {
                    running = false;
                    out.println(encryption.encrypt("QUIT"));
                    break;
                } else {
                    out.println(encryption.encrypt(message));
                }
            }

            socket.close();
            System.out.println("Disconnected from server.");

        } catch (IOException e) {
            System.err.println("Error in client: " + e.getMessage());
        }
    }

    private void receiveMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("FILE:")) {
                    receiveFile(message.substring(5));
                } else {
                    System.out.println(encryption.decrypt(message));
                }
            }
        } catch (IOException e) {
            if (running) {
                System.err.println("Connection to server lost: " +
                        e.getMessage());
            }
        }
    }

    private void sendFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File not found: " + filePath);
                return;
            }

            out.println(encryption.encrypt("FILE:" + file.getName() + ":"
                    + file.length()));

            FileTransfer.sendFile(serverAddress, port + 1, file);

            System.out.println("File sent successfully: " +
                    file.getName());

        } catch (Exception e) {
            System.err.println("Error sending file: " + e.getMessage());
        }
    }

    private void receiveFile(String fileInfo) {
        try {
            String[] parts = encryption.decrypt(fileInfo).split(":");
            String fileName = parts[0];
            long fileSize = Long.parseLong(parts[1]);

            System.out.println("Receiving file: " + fileName + " (" +
                    fileSize + " bytes)");

            Socket fileSocket = new Socket(serverAddress, port + 1);

            FileTransfer.receiveFile(fileSocket, "downloads/" + fileName,
                    fileSize);

            System.out.println("File received: downloads/" + fileName);

        } catch (Exception e) {
            System.err.println("Error receiving file: " + e.getMessage());
        }
    }
}