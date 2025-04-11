package WEEK9;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Secure Chat Network Application");
        System.out.println("1. Start as server");
        System.out.println("2. Connect as client");
        System.out.print("Choose option: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        if (option == 1) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter port for server: ");
            int port = scanner.nextInt();

            ChatServer server = new ChatServer(port);
            ChatClient client = new ChatClient(username, "localhost",
                    port);

            new Thread(server).start();
            new Thread(client).start();

            Discovery discovery = new Discovery(port, username);
            discovery.startAnnouncing();

        } else if (option == 2) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            Discovery discovery = new Discovery();
            discovery.startDiscovery();

            System.out.println("Discovering peers...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            discovery.displayDiscoveredPeers();

            System.out.print("Enter peer number to connect: ");
            int peerIndex = scanner.nextInt();

            Discovery.PeerInfo selectedPeer =
                    discovery.getPeers().get(peerIndex - 1);

            ChatClient client = new ChatClient(username,
                    selectedPeer.getAddress(), selectedPeer.getPort());
            new Thread(client).start();
        }
    }
}