package com.example.exchangetoysback.ExchangeToysBack.tools;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

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
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte encrypted[] = cipher.doFinal(data.getBytes());
        return encrypted;
    }

    /**
     *
     * @param encrypted array od bytes do decrypt
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
}
