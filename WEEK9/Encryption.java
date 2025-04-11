package WEEK9;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.util.*;

public class Encryption {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String SECRET_KEY = "CNLABASSIGNMENT";

    private SecretKey secretKey;
    private Cipher cipher;

    public Encryption() {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = sha.digest(SECRET_KEY.getBytes());
            keyBytes = Arrays.copyOf(keyBytes, 16);
            secretKey = new SecretKeySpec(keyBytes, ALGORITHM);

            cipher = Cipher.getInstance(TRANSFORMATION);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.err.println("Error initializing encryption: " +
                    e.getMessage());
        }
    }

    public String encrypt(String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.err.println("Error encrypting message: " +
                    e.getMessage());
            return plainText;
        }
    }

    public String decrypt(String encryptedText) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes =
                    Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            System.err.println("Error decrypting message: " +
                    e.getMessage());
            return encryptedText;
        }
    }
}