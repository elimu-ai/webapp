package ai.elimu.web;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import java.math.BigInteger;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;

@Controller
public class SignOnControllerWeb3 {
    
    /**
     * Must match the signature message used in /WEB-INF/jsp/sign-on-web3.jsp
     */
    private static final String SIGNATURE_MESSAGE = "I verify ownership of this account 👍";
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping("/sign-on/web3")
    public String handleGetRequest(HttpServletRequest request) throws IOException {
        logger.info("handleGetRequest");
		
        return "sign-on-web3";
    }
    
    /**
     * Verify that an Ethereum signature was signed by a given Ethereum account. If true, we 
     * proceed with the sign-on process. Otherwise, we return an error message.
     */
    @RequestMapping(value="/sign-on/web3", method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String handleAuthorization(
            HttpServletRequest request,
            @RequestParam String address,
            @RequestParam String signature
    ) throws IOException {
        logger.info("handleAuthorization");
        
        logger.info("address: " + address);
        if (StringUtils.isBlank(address)) {
            return "redirect:/sign-on/web3?error=Missing address";
        }
        
        logger.info("signature: " + signature);
        if (StringUtils.isBlank(signature)) {
            return "redirect:/sign-on/web3?error=Missing signature";
        }
        
        // Check if the signature is valid
        if (!isSignatureValid(address, signature, SIGNATURE_MESSAGE)) {
            logger.warn("Invalid signature");
            return "redirect:/sign-on/web3?error=Invalid signature";
        }
        logger.info("Valid signature 🎉");
        
        // Check if a Contributor with this ETH address already exists in the database
        // TODO
	
        return "sign-on-web3";
    }
    
    private boolean isSignatureValid(final String address, final String signature, final String message) {
        logger.info("isSignatureValid");
        
        boolean match = false;
        
        // Note: The label prefix is part of the standard
        String label = "\u0019Ethereum Signed Message:\n" + String.valueOf(message.getBytes().length) + message;
        
        // Get message hash using SHA-3
        final byte[] msgHash = Hash.sha3((label).getBytes());

        // Convert signature HEX string to bytes
        final byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }

        // Create signature data from the signature bytes
        final SignatureData sd = new SignatureData(
                v,
                Arrays.copyOfRange(signatureBytes, 0, 32),
                Arrays.copyOfRange(signatureBytes, 32, 64)
        );
        
        for (int i = 0; i < 4; i++) {
            final BigInteger publicKey = Sign.recoverFromSignature(
                    (byte) i,
                    new ECDSASignature(
                            new BigInteger(1, sd.getR()),
                            new BigInteger(1, sd.getS())
                    ),
                    msgHash
            );

            if (publicKey != null) {
                String recoveredAddress = "0x" + Keys.getAddress(publicKey);
                logger.info("recoveredAddress: " + recoveredAddress);
                if (recoveredAddress.equalsIgnoreCase(address)) {
                    match = true;
                    break;
                }
            }
        }
        
        return match;
    }
}
