package WEEK9;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Discovery {
    private static final String MULTICAST_ADDRESS = "230.0.0.1";
    private static final int MULTICAST_PORT = 4446;
    private int servicePort;
    private String username;
    private boolean running;
    private List<PeerInfo> peers;

    public Discovery() {
        this.peers = new CopyOnWriteArrayList<>();
        this.running = true;
    }

    public Discovery(int servicePort, String username) {
        this();
        this.servicePort = servicePort;
        this.username = username;
    }

    public void startDiscovery() {
        new Thread(this::discoverPeers).start();
    }

    public void startAnnouncing() {
        new Thread(this::announcePeer).start();
    }

    private void discoverPeers() {
        try {
            MulticastSocket socket = new MulticastSocket(MULTICAST_PORT);
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket.joinGroup(group);

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer,
                    buffer.length);

            System.out.println("Listening for peer announcements...");

            while (running) {
                socket.receive(packet);

                String announcement = new String(packet.getData(), 0,
                        packet.getLength());
                String[] parts = announcement.split(":");

                if (parts.length == 3 && parts[0].equals("CHAT_PEER")) {
                    String peerUsername = parts[1];
                    int peerPort = Integer.parseInt(parts[2]);
                    String peerAddress =
                            packet.getAddress().getHostAddress();

                    boolean exists = false;
                    for (PeerInfo peer : peers) {
                        if (peer.getAddress().equals(peerAddress) &&
                                peer.getPort() == peerPort) {
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        PeerInfo peerInfo = new PeerInfo(peerUsername,
                                peerAddress, peerPort);
                        peers.add(peerInfo);
                        System.out.println("Discovered peer: " +
                                peerUsername + " at " +
                                peerAddress + ":" + peerPort);
                    }
                }
            }

            socket.leaveGroup(group);
            socket.close();

        } catch (IOException e) {
            System.err.println("Error in discovery service: " +
                    e.getMessage());
        }
    }

    private void announcePeer() {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);

            String announcement = "CHAT_PEER:" + username + ":" +
                    servicePort;
            byte[] buffer = announcement.getBytes();

            System.out.println("Announcing presence to network...");

            while (running) {
                DatagramPacket packet = new DatagramPacket(buffer,
                        buffer.length, group, MULTICAST_PORT);
                socket.send(packet);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            socket.close();

        } catch (IOException e) {
            System.err.println("Error in announcement service: " +
                    e.getMessage());
        }
    }

    public void displayDiscoveredPeers() {
        if (peers.isEmpty()) {
            System.out.println("No peers discovered yet.");
            return;
        }

        System.out.println("\nDiscovered Peers:");
        for (int i = 0; i < peers.size(); i++) {
            PeerInfo peer = peers.get(i);
            System.out.println((i + 1) + ". " + peer.getUsername() + " - "
                    +
                    peer.getAddress() + ":" + peer.getPort());
        }
    }

    public List<PeerInfo> getPeers() {
        return peers;
    }

    public void stop() {
        this.running = false;
    }

    public static class PeerInfo {
        private String username;
        private String address;
        private int port;

        public PeerInfo(String username, String address, int port) {
            this.username = username;
            this.address = address;
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public String getAddress() {
            return address;
        }

        public int getPort() {
            return port;
        }
    }
}
