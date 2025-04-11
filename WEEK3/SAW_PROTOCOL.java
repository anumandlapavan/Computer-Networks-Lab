package WEEK3;

import java.util.Random;
import java.util.concurrent.TimeUnit;

class Sender{
    int frame = 0;
    Receiver receiver ;

    Sender(Receiver receiver){
        this.receiver = receiver;
    }

    public void sendMessages(String [] messages){
        for(String message : messages){
            boolean acknowledged = false;

            while (!acknowledged){
                System.out.println("Sender: Sending Frame " + frame + " with message: " + message);

                acknowledged = receiver.receivedMessage(frame,message);

                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }

                if(!acknowledged){
                    System.out.println("Sender: No ACK received. Retrying...");
                }
                else{
                    System.out.println("Sender: ACK received for Frame " + frame + "\n");
                    frame = 1 - frame;
                }
            }
        }
    }
}

class Receiver{
    int expected_frame = 0;
    Random random = new Random();

    public boolean receivedMessage(int frame,String message) {
        if(random.nextInt(10) < 2){
            System.out.println("Receiver: Frame lost or corrupted. No ACK sent.");
            return false;
        }
        if(expected_frame == frame){
            System.out.println("Receiver: Received Frame " + frame + " with message: " + message);
            System.out.println("Receiver: Sending ACK for Frame " + frame);
            expected_frame = 1 - expected_frame; // alternate expected frame
            return true;
        }
        else{
            System.out.println("Receiver: Duplicate Frame " + frame + " received. Ignoring.");
            System.out.println("Receiver: Resending ACK for Frame " + (1 - expected_frame));
            return true;
        }
    }
}

public class SAW_PROTOCOL {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Sender sender = new Sender(receiver);

        String[] messages = {"Hello","Pavan","How are you?", "Bye"};
        sender.sendMessages(messages);

    }
}