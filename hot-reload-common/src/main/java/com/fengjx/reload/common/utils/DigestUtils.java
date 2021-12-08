package com.fengjx.reload.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author fengjianxin
 * @since 2021-12-05
 */
public class DigestUtils {

    public static String checksum(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(file);
        // Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }
        ;
        fis.close();
        byte[] bytes = digest.digest();

        StringBuilder sb = new StringBuilder();
        // loop through the bytes array
        for (byte aByte : bytes) {
            sb.append(Integer
                    .toString((aByte & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return sb.toString();
    }

}
