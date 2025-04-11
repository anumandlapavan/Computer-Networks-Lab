package WEEK1.CRC;

public class CRC_MAIN {
    public static void main(String[] args) {
        String dataword = "11010011101100";
        String divisor = "1011";

        int m = divisor.length() - 1;
        String appendedData = dataword + "0".repeat(m);
        String remainder = mod2Division(divisor, appendedData);
        String codeword = dataword + remainder;

        System.out.println(dataword);
        System.out.println(divisor);
        System.out.println(appendedData);
        System.out.println(remainder);
        System.out.println(codeword);

        String received = codeword;
        String checkRemainder = mod2Division(divisor, received);

        if (checkRemainder.equals("0".repeat(m))) {
            System.out.println("No error detected");
        } else {
            System.out.println("Error detected");
        }
    }

    private static String mod2Division(String divisor, String dividend) {
        int pick = divisor.length();
        String temp = dividend.substring(0, pick);

        while (pick < dividend.length()) {
            if (temp.charAt(0) == '1') {
                temp = xor(divisor, temp) + dividend.charAt(pick);
            } else {
                temp = xor("0".repeat(pick), temp) + dividend.charAt(pick);
            }
            pick++;
        }

        if (temp.charAt(0) == '1') {
            temp = xor(divisor, temp);
        } else {
            temp = xor("0".repeat(pick), temp);
        }

        return temp;
    }

    private static String xor(String a, String b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < b.length(); i++) {
            sb.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }
        return sb.toString();
    }
}
