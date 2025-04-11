package WEEK5;

import java.util.Scanner;

public class HammingCode {
    public static boolean isPowerOf2(int x){
        return (x & (x-1) ) == 0;
    }
    public static int [] generateHammingCode(int [] data){
        int r = 0;
        int m = data.length;

        while(Math.pow(2,r) < m+r+1){
            r++;
        }

        int [] hammingCode = new int[m+r+1];

        for(int i=0,j=1; i<m;j++){
            if(!isPowerOf2(j)){
                hammingCode[j] = data[i];
                i++;
            }
        }

        for(int i=0; i<r; i++){
            int position = (int) Math.pow(2,i);
            int parity =0;

            for(int j=1; j<hammingCode.length; j++){
                if((j & position) != 0){
                    parity ^= hammingCode[j];
                }
            }

            hammingCode[position] = parity;
        }

        return hammingCode;
    }
    public static void detectAndCorrect(int [] received){
        int r=0;
        while(Math.pow(2,r) < received.length){
            r++;
        }

        int errorPosition = 0;

        for(int i=0; i<r; i++){
            int position = (int) Math.pow(2,i);
            int parity = 0;

            for(int j=1; j<received.length; j++){
                if((j & position) != 0){
                    parity ^= received[j];
                }
            }

            if(parity != 0){
                errorPosition += position;
            }
        }

        if(errorPosition == 0){
            System.out.println("No error detected.");
        }
        else {
            System.out.println("Error detected at position: " + errorPosition);
            received[errorPosition] ^= 1;
            System.out.println("Corrected Hamming Code:");
            for (int i = 1; i < received.length; i++) {
                System.out.print(received[i] + " ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of data bits: ");
        int n = sc.nextInt();

        int[] data = new int[n];
        System.out.print("Enter data bits: ");
        for (int i = 0; i < n; i++) {
            data[i] = sc.nextInt();
        }

        int[] hammingCode = generateHammingCode(data);
        System.out.print("Hamming Code: ");
        for (int i = 1; i < hammingCode.length; i++) {
            System.out.print(hammingCode[i] + " ");
        }
        System.out.println();

        // Simulate error
        System.out.print("Enter position to flip (0 for no error): ");
        int pos = sc.nextInt();
        if (pos != 0 && pos < hammingCode.length) {
            hammingCode[pos] ^= 1; // Flip the bit
            System.out.println("Hamming Code with error:");
            for (int i = 1; i < hammingCode.length; i++) {
                System.out.print(hammingCode[i] + " ");
            }
            System.out.println();
        }

        // Detect and correct
        detectAndCorrect(hammingCode);
    }
}
