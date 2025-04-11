package WEEK9;

import java.io.*;
import java.net.*;

public class FileTransfer {

    public static void sendFile(String host, int port, File file) throws
            IOException {
        Socket socket = new Socket(host, port);

        try (FileInputStream fileIn = new FileInputStream(file);
             OutputStream socketOut = socket.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fileIn.read(buffer)) != -1) {
                socketOut.write(buffer, 0, bytesRead);
            }

            socketOut.flush();
        } finally {
            socket.close();
        }
    }

    public static void receiveFile(Socket socket, String filePath, long
            fileSize) throws IOException {
        File outputFile = new File(filePath);
        outputFile.getParentFile().mkdirs();

        try (FileOutputStream fileOut = new FileOutputStream(outputFile);
             InputStream socketIn = socket.getInputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalBytesRead = 0;

            while (totalBytesRead < fileSize &&
                    (bytesRead = socketIn.read(buffer, 0,
                            (int)Math.min(buffer.length, fileSize - totalBytesRead))) != -1) {
                fileOut.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                int progress = (int)((totalBytesRead * 100) / fileSize);
                System.out.print("\rReceiving: " + progress + "% complete");
            }

            System.out.println("\nFile transfer complete");
        } finally {
            if (!socket.isClosed()) {
                socket.close();
            }
        }
    }
}