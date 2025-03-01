package ai.elimu.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;

/**
 * Based on https://github.com/CyanogenMod/android_packages_apps_CMUpdater/blob/cm-10.2/src/com/cyanogenmod/updater/utils/MD5.java
 */
@Slf4j
public class ChecksumHelper {

    public static String calculateMD5(byte[] bytes) {
        log.info("calculateMD5");
        
        InputStream inputStream = new ByteArrayInputStream(bytes);
        
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            log.error(ex.getMessage());
            return null;
        }

        byte[] buffer = new byte[8192]; // 8MB (8 x 1024)
        int bytesRead;
        try {
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                messageDigest.update(buffer, 0, bytesRead);
            }
            byte[] md5Sum = messageDigest.digest();
            
            BigInteger bigInteger = new BigInteger(1, md5Sum);
            String output = bigInteger.toString(16);
            
            // Fill to 32 chars
            output = String.format("%32s", output).replace(' ', '0');
            
            return output;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}