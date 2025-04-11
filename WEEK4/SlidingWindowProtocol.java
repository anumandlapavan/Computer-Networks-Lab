package WEEK4;


import java.util.*;

class Frame{
    int id;
    boolean acknowledged;

    Frame(int id){
        this.id = id;
        this.acknowledged = false;
    }
}


public class SlidingWindowProtocol {
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void goBackN(int totalFrames, int windowSize){
        int base = 0;
        while(base < totalFrames){
            System.out.println("Sending frames");
            for (int i = base; i <Math.min(base+windowSize,totalFrames) ; i++) {
                System.out.println("sent frame "+i);
            }

            int ack = random.nextInt(windowSize+1);

            if(ack == 0){
                System.out.println("Timeout/Error! Resending from frame: "+base);
            }
            else{
                System.out.println("Acknowledged till frame: " + (base + ack - 1));
                base += ack;
            }
        }
        System.out.println("\nAll frames sent successfully using Go-Back-N.");
    }
    public static void selectiveRepeat(int totalFrames, int windowSize){
        Frame[] frames = new Frame[totalFrames];

        for (int i = 0; i < totalFrames; i++) {
            frames[i] = new Frame(i);
        }

        int base = 0;
        while (base < totalFrames){
            System.out.println("sending frames");

            for (int i = base; i <Math.min(base+windowSize,totalFrames) ; i++) {
                if(!frames[i].acknowledged){
                    System.out.println("Sent Frame: " + i);
                }
            }

            for (int i = base; i <Math.min(base+windowSize,totalFrames) ; i++) {
                if(!frames[i].acknowledged && random.nextBoolean()){
                    frames[i].acknowledged = true;
                    System.out.println("Acknowledged Frame: " + i);
                }
            }

            while (base<totalFrames && frames[base].acknowledged){
                base++;
            }
        }
        System.out.println("\nAll frames sent successfully using Selective Repeat.");
    }
    public static void main(String[] args) {
        System.out.print("Enter total number of frames: ");
        int totalFrames = sc.nextInt();

        System.out.print("Enter window size: ");
        int windowSize = sc.nextInt();

        System.out.println("\n--- Go-Back-N Protocol ---");
        selectiveRepeat(totalFrames, windowSize);
    }
}