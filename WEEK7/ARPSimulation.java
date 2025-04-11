package WEEK7;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

class ARPRequest implements Serializable {
    String srcMAC;
    String srcIP;
    String destIP;
    String operation; // ARP_REQUEST or RARP_REQUEST

    public ARPRequest(String srcMAC, String srcIP, String destIP, String operation) {
        this.srcMAC = srcMAC;
        this.srcIP = srcIP;
        this.destIP = destIP;
        this.operation = operation;
    }
}

class ARPResponse implements Serializable {
    String srcMAC;
    String srcIP;
    String destMAC;
    String destIP;

    public ARPResponse(String srcMAC, String srcIP, String destMAC, String destIP) {
        this.srcMAC = srcMAC;
        this.srcIP = srcIP;
        this.destMAC = destMAC;
        this.destIP = destIP;
    }
}

class ARPServer extends Thread {
    private HashMap<String, String> arpTable = new HashMap<>();
    private ServerSocket serverSocket;

    public ARPServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void addMapping(String ip, String mac) {
        arpTable.put(ip, mac);
    }

    public void run() {
        while (true) {
            try (Socket socket = serverSocket.accept();
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

                Object request = in.readObject();

                if (request instanceof ARPRequest) {
                    ARPRequest arpRequest = (ARPRequest) request;

                    if (arpRequest.operation.equals("ARP_REQUEST")) {
                        String mac = arpTable.get(arpRequest.destIP);
                        if (mac != null) {
                            System.out.println("ARP Reply: IP = " + arpRequest.destIP + ", MAC = " + mac);
                            out.writeObject(new ARPResponse(arpRequest.srcMAC, arpRequest.srcIP, mac, arpRequest.destIP));
                        } else {
                            System.out.println("IP not found in ARP Table");
                        }
                    } else if (arpRequest.operation.equals("RARP_REQUEST")) {
                        for (Map.Entry<String, String> entry : arpTable.entrySet()) {
                            if (entry.getValue().equals(arpRequest.srcMAC)) {
                                System.out.println("RARP Reply: MAC = " + arpRequest.srcMAC + ", IP = " + entry.getKey());
                                out.writeObject(new ARPResponse(arpRequest.srcMAC, entry.getKey(), "", ""));
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Node extends Thread {
    private String mac;
    private String ip;
    private int serverPort;

    public Node(String mac, int serverPort) {
        this.mac = mac;
        this.serverPort = serverPort;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public void sendARPRequest(String targetIP) {
        try (Socket socket = new Socket("localhost", serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            ARPRequest request = new ARPRequest(mac, ip, targetIP, "ARP_REQUEST");
            out.writeObject(request);

            Object response = in.readObject();
            if (response instanceof ARPResponse) {
                ARPResponse arpResponse = (ARPResponse) response;
                System.out.println("Received ARP Response: IP = " + arpResponse.destIP + ", MAC = " + arpResponse.destMAC);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRARPRequest() {
        try (Socket socket = new Socket("localhost", serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            ARPRequest request = new ARPRequest(mac, "", "", "RARP_REQUEST");
            out.writeObject(request);

            Object response = in.readObject();
            if (response instanceof ARPResponse) {
                ARPResponse arpResponse = (ARPResponse) response;
                System.out.println("Received RARP Response: MAC = " + mac + ", IP = " + arpResponse.srcIP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000); // wait for server setup
            sendARPRequest("192.168.1.2");
            Thread.sleep(2000);
            sendRARPRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class ARPSimulation {
    public static void main(String[] args) throws Exception {
        ARPServer server = new ARPServer(5000);
        server.addMapping("192.168.1.1", "00:0a:95:9d:68:16");
        server.addMapping("192.168.1.2", "00:0a:95:9d:68:17");

        server.start();

        Node node1 = new Node("00:0a:95:9d:68:16", 5000);
        Node node2 = new Node("00:0a:95:9d:68:17", 5000);

        node1.setIP("192.168.1.1");
        node2.setIP("192.168.1.2");

        node1.start();
        node2.start();

        node1.join();
        node2.join();
    }
}