package WEEK8;

import java.util.Scanner;

public class SubnetCalculator {

    public static int ipToInt(String ipAddress) {
        String[] parts = ipAddress.split("\\.");
        int ip = 0;
        for (String part : parts) {
            ip = (ip << 8) | Integer.parseInt(part);
        }
        return ip;
    }

    public static String intToIp(int ip) {
        return ((ip >> 24) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                (ip & 0xFF);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter base IP address (e.g., 192.168.0.0): ");
        String baseIp = sc.nextLine();

        System.out.print("Enter subnet mask in CIDR (e.g., 24): ");
        int subnetMaskCIDR = sc.nextInt();

        System.out.print("Enter number of subnets: ");
        int numSubnets = sc.nextInt();

        int baseIpInt = ipToInt(baseIp);
        int subnetBits = 32 - subnetMaskCIDR;
        int totalHostsPerSubnet = (int) Math.pow(2, subnetBits);
        int usableHosts = totalHostsPerSubnet - 2;

        System.out.println("\n--- Forwarding Table ---");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                "Subnet Addr", "1st Host", "Last Host", "Broadcast", "Subnet Mask", "Hosts/Subnet", "Subnet #");

        for (int i = 0; i < numSubnets; i++) {
            int subnetStart = baseIpInt + (i * totalHostsPerSubnet);
            int firstHost = subnetStart + 1;
            int lastHost = subnetStart + totalHostsPerSubnet - 2;
            int broadcast = subnetStart + totalHostsPerSubnet - 1;

            String subnetAddress = intToIp(subnetStart);
            String firstHostIp = intToIp(firstHost);
            String lastHostIp = intToIp(lastHost);
            String broadcastIp = intToIp(broadcast);
            String subnetMask = intToIp(-1 << subnetBits);

            System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15d %-15d\n",
                    subnetAddress, firstHostIp, lastHostIp, broadcastIp, subnetMask, usableHosts, i + 1);
        }

        sc.close();
    }
}
