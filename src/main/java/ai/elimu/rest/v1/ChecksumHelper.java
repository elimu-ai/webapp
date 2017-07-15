package ai.elimu.rest.v1;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;
import ai.elimu.model.enums.Environment;
import ai.elimu.util.ConfigHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;

public class ChecksumHelper {
    
    private static final Logger logger = Logger.getLogger(ChecksumHelper.class);
    
    public static String getChecksum(String deviceId) {
        logger.info("getChecksum");
        
        String secret = "simba";
        if (EnvironmentContextLoaderListener.env != Environment.DEV) {
            secret = ConfigHelper.getProperty("appstore.secret");
        }
        
        String checksum = null;
        
        String input = deviceId + secret;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] md5AsBytes = messageDigest.digest(input.getBytes("UTF-8"));
            checksum = new BigInteger(1, md5AsBytes).toString(16);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }

        return checksum;
    }
}
