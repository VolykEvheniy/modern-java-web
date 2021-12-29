package com.example.project.utils;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class PasswordEncryptor {

    public static String getHashedPassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);
            md.update(saltBytes, 0, saltBytes.length);
            byte[] passwordBytes = passwordToHash.getBytes(StandardCharsets.UTF_8);
            md.update(passwordBytes, 0, passwordBytes.length);
            byte[] hashBytes = md.digest();

            StringBuilder hexHashBytes = new StringBuilder();
            for (int i = 0; i < hashBytes.length; i++) {
                hexHashBytes.append(Integer.toHexString(i));
            }

            generatedPassword = hexHashBytes.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static String getSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        return Arrays.toString(salt);
    }
}
