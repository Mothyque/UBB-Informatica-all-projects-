package com.ubb.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordHasher
{
    public static String hash(String originalPassword)
    {
        try
        {
            MessageDigest digest =  MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalPassword.getBytes("UTF-8"));
            return bytesToHex(hash);
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private static String bytesToHex(byte[] bytes)
    {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for(int i = 0; i < bytes.length; i++)
        {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if(hex.length() == 1)
            {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
