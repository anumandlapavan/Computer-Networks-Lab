package WEEK2;

import java.util.Scanner;

public class CheckSum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of data segments ( n ): ");
        int n = scanner.nextInt();

        System.out.print("Enter the size of each segment (in bits) ( m ): ");
        int segmentSize = scanner.nextInt();

        int[] dataSegments = new int[n];
        System.out.println("Enter the data segments in binary:");
        for (int i = 0; i < n; i++) {
            System.out.print("Segment " + (i + 1) + ": ");
            dataSegments[i] = scanner.nextInt(2);
        }

        int checksum = calculateChecksum(dataSegments, segmentSize);
        System.out.println("Calculated Checksum: " +
                Integer.toBinaryString(checksum));

        System.out.println("Received data with checksum: ");
        int[] receivedSegments = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            if(i==n){
                System.out.print("CheckSum " + ": ");
                receivedSegments[i] = scanner.nextInt(2);
            }
            else{
                System.out.print("Segment " + (i + 1) + ": ");
                receivedSegments[i] = scanner.nextInt(2);;

            }
        }

        int receiverChecksum = calculateChecksum(receivedSegments,
                segmentSize);
        if (receiverChecksum == 0) {
            System.out.println("No error detected in the received data.");
        } else {
            System.out.println("Error detected in the received data.");
        }

        scanner.close();
    }

    private static int calculateChecksum(int[] segments, int segmentSize) {
        int sum = 0;
        for (int segment : segments) {
            sum += segment;
            int carry = sum >> segmentSize;
            sum = sum & ((1 << segmentSize) - 1);
            sum += carry;
        }
        return (~sum) & ((1 << segmentSize) - 1);
    }
}

