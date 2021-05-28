package ai.elimu.web;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

/**
 * Verifing that an ethereum signature was signed by a provided ethereum account.
 * If true, then we check for exsiting web3 user in the database, if not found add
 * the user, otherwise send an error message to the user.
 */
@Controller
public class SignOnControllerWeb3 {
    

    private Logger logger = LogManager.getLogger();
    private static String message = "sign-in";

    @Autowired
    private ContributorDao contributorDao;
    
    @RequestMapping(value="/sign-on/web3",
            method=RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String handleAuthorization(HttpServletRequest request, @RequestParam("address") String address, @RequestParam("signature") String signature) throws IOException {
        logger.info("handleAuthorization");
        logger.info(signature);
        logger.info(address);
        if (address == null || signature == null){
            return "redirect:/sign-on?error=empty web3 signature or address";
        }
        logger.info("isSignatureValid invoked for Address {} with Signature {} and Message {} ", address, signature,
        message);
        boolean valid = isSignatureValid( address, signature, message);
        if (!valid){
            logger.error("invalid web3 signature");
            return "redirect:/sign-on?error=invalid web3 signature";
        }
        logger.info("valid web3 signature");
        
        // Create new contributor instance.
        Contributor contributor = new Contributor();
        contributor.setProviderIdWeb3(address);
 
        // Look for existing Contributor with matching web3 address.
        Contributor existingContributor = contributorDao.read(contributor.getProviderIdWeb3());
        logger.info("existingContributor: " + existingContributor);
        if (existingContributor == null) {
            // Store new Contributor in database
            contributor.setRegistrationTime(Calendar.getInstance());
            contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
            if (contributor.getEmail() == null) {
                // Ask the Contributor to add her e-mail manually
                request.getSession().setAttribute("contributor", contributor);
                new CustomAuthenticationManager().authenticateUser(contributor);
                return "redirect:/content/contributor/add-email";
            }
            contributorDao.create(contributor);
        } 

        // Authenticate
        new CustomAuthenticationManager().authenticateUser(contributor);

        // Add Contributor object to session
        request.getSession().setAttribute("contributor", contributor);
        
        return "redirect:/content";
    }

    private boolean isSignatureValid(final String address, final String signature, final String message) {
        // This is part of the standard!
        String label = "\u0019Ethereum Signed Message:\n"+ String.valueOf(message.getBytes().length) + message;
        boolean match = false;

        // Get msg hash using sha3.
        final byte[] msgHash = Hash.sha3((label).getBytes());
        
        // Convert signature hex string to bytes.
        final byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }

        // Create signature data from the signature bytes.
        final SignatureData sd = new SignatureData(v,
                Arrays.copyOfRange(signatureBytes, 0, 32),
                Arrays.copyOfRange(signatureBytes, 32, 64));

        String addressRecovered = null;
        // Iterate for each possible key to recover.
        for (int i = 0; i < 4; i++) {
            final BigInteger publicKey = Sign.recoverFromSignature((byte) i, new ECDSASignature(
                            new BigInteger(1, sd.getR()),
                            new BigInteger(1, sd.getS())), msgHash);

            if (publicKey != null) {
                addressRecovered = "0x" + Keys.getAddress(publicKey);
                logger.info("recovered address: {}", addressRecovered);
                if (addressRecovered.equalsIgnoreCase(address)) {
                    match = true;
                    break;
                }
            }
        }
        return match;
    }
}
