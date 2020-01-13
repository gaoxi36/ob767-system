package cn.ob767.systemservice.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static String getMD5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] bytes = md.digest();
            int i;
            StringBuilder password = new StringBuilder();
            for (byte b : bytes) {
                i = b;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    password.append(0);
                password.append(Integer.toHexString(i));
            }
            return password.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}