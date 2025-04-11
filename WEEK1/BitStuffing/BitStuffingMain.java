package WEEK1.BitStuffing;

import java.util.Scanner;

public class BitStuffingMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter input string : ");
        String input = sc.nextLine();

        String stuffedString = stuffString(input);
        System.out.println("stuffedString : "+stuffedString);

        String deStuffedString = deStuffString(stuffedString);
        System.out.println("deStuffedString : "+deStuffedString);
    }

    private static String deStuffString(String stuffedString) {
        StringBuilder sc = new StringBuilder();
        int count = 0;

        for(char c : stuffedString.toCharArray()){
            sc.append(c);
            if(count == 5){
                sc.deleteCharAt(sc.length()-1);
                count =0;
            }
            if(c == '1'){
                count ++;
            }
            else {
                count = 0;
            }
        }
        return sc.toString();
    }

    private static String stuffString(String input) {
        StringBuilder sc = new StringBuilder();

        int count = 0;
        for(char c : input.toCharArray()){
            sc.append(c);
            if(c == '1'){
                count++;
                if(count == 5){
                    sc.append('0');
                    count = 0;
                }
            }
            else{
                count = 0;
            }
        }

        return sc.toString();
    }
}
