package com.example.exchangetoysback.ExchangeToysBack.tools;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptionTools {

    private static final byte[] key = "mafds--t7l-8yt23".getBytes();
    private static SecretKeySpec secretKey = new SecretKeySpec(key,"AES");

    /**
     *
     * @param data String value to encrypt
     * @return Bytes array with encrypted String which was delivered in param data
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static byte[] encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        data=replenishToMultipleOf16(data);
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte encrypted[] = cipher.doFinal(data.getBytes());
        return encrypted;
    }

    /**
     * @param encrypted array bytes to decrypt
     * @return String message with decrypted message
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static String decrypt(byte [] encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte decrypted[] = cipher.doFinal(encrypted);
        return new String(decrypted);
    }
    private static String replenishToMultipleOf16(String s)
    {
        if ((s.length() % 16) != 0) {
            int howManySignsIsLacking = 16- s.length() % 16;
            System.out.println("howManySignsIsLacking: "+howManySignsIsLacking);
            for (int i = 0; i < howManySignsIsLacking; i++) {
                s += ";";
            }
        }
        return s;
    }
}
