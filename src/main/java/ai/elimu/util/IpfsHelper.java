package ai.elimu.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import pinata.Pinata;
import pinata.PinataException;
import pinata.PinataResponse;

public class IpfsHelper {

    private static Logger logger = LogManager.getLogger();

    /**
     * PIN file to IPFS.
     * 
     * @param bytes The file bytes.
     * @param filename The file name.
     * @return The Content Identifier (CID) generated by Pinata.
     */
    public static String pinFileToIpfs(byte[] bytes, String filename) {
        logger.info("filename: " + filename);
        String apiKey = ConfigHelper.getProperty("pinata.api.key");
        String apiSecret = ConfigHelper.getProperty("pinata.api.secret");
        Pinata pinata = new Pinata(apiKey, apiSecret);
        try {
            PinataResponse pinataResponse = pinata.pinFileToIpfs(new ByteArrayInputStream(bytes), filename);
            logger.info("pinataResponse.getStatus(): " + pinataResponse.getStatus());
            logger.info("pinataResponse.getBody(): " + pinataResponse.getBody());
            // Sample response: {"IpfsHash":"QmSgd44s5UxQZ7SjYuQfwN86aFiiW1ZsZz9tniADHPJLiR","PinSize":25096,"Timestamp":"2024-11-04T16:05:05.813Z","isDuplicate":true}
            JSONObject jsonObject = new JSONObject(pinataResponse.getBody());
            String ipfsHash = jsonObject.getString("IpfsHash");
            logger.info("ipfsHash: " + ipfsHash);
            return ipfsHash;
        } catch (PinataException | IOException e) {
            logger.error(e);
            return null;
        }
    }
}
