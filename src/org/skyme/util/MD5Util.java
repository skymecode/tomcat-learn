package org.skyme.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author:Skyme
 * @create: 2023-08-28 16:16
 * @Description:
 */
public class MD5Util {
    private static final String SALT = "!@{skyme}"; // 固定盐值
    public static String getMD5Str(String plainText) throws  NoSuchAlgorithmException {
        try {
            String saltedText = plainText + SALT;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(saltedText.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    }

