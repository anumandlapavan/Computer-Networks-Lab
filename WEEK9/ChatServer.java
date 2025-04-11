package WEEK9;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer implements Runnable {
    private int port;
    private ServerSocket serverSocket;
    private ServerSocket fileServerSocket;
    private ConcurrentHashMap<String, ClientHandler> clients;
    private Encryption encryption;
    private boolean running;

    public ChatServer(int port) {
        this.port = port;
        this.clients = new ConcurrentHashMap<>();
        this.encryption = new Encryption();
        this.running = true;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Chat server started on port " + port);

            fileServerSocket = new ServerSocket(port + 1);
            System.out.println("File transfer server started on port " +
                    (port + 1));

            new Thread(this::handleFileTransfers).start();

            while (running) {
                Socket clientSocket = serverSocket.accept();

                ClientHandler clientHandler = new
                        ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
                if (fileServerSocket != null &&
                        !fileServerSocket.isClosed()) {
                    fileServerSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing server: " +
                        e.getMessage());
            }
        }
    }

    private void handleFileTransfers() {
        try {
            while (running) {
                Socket fileSocket = fileServerSocket.accept();
                System.out.println("File transfer connection accepted from: " +
                fileSocket.getInetAddress().getHostAddress());

                new Thread(() -> {
                    try {
                        InputStream in = fileSocket.getInputStream();
                        OutputStream out = fileSocket.getOutputStream();

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }

                        fileSocket.close();
                    } catch (IOException e) {
                        System.err.println("Error in file transfer: " +
                                e.getMessage());
                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println("File server error: " + e.getMessage());
        }
    }

    private void broadcast(String message, String excludeUsername) {
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet())
        {
            if (!entry.getKey().equals(excludeUsername)) {
                entry.getValue().sendMessage(message);
            }
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new
                        InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                username = encryption.decrypt(in.readLine());
                System.out.println("New client connected: " + username + " from " +
                        socket.getInetAddress().getHostAddress());

                clients.put(username, this);

                broadcast(encryption.encrypt(username + " has joined the chat."), username);

                        String message;
                while ((message = in.readLine()) != null) {
                    message = encryption.decrypt(message);

                    if (message.equals("QUIT")) {
                        break;
                    } else if (message.startsWith("FILE:")) {
                        broadcast(encryption.encrypt("FILE:" +
                                message.substring(5)), username);
                    } else {
                        broadcast(encryption.encrypt(username + ": " +
                                message), null);
                    }
                }

            } catch (IOException e) {
                System.err.println("Error handling client " + username +
                        ": " + e.getMessage());
            } finally {
                if (username != null) {
                    clients.remove(username);
                    broadcast(encryption.encrypt(username + " has left the chat."), username);
                }

                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error closing client socket: " +
                            e.getMessage());
                }
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }
}
