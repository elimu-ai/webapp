package ai.elimu.util;

import java.math.BigInteger;
import java.util.Arrays;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Web3Helper {
    
    public static boolean isSignatureValid(final String address, final String signature, final String message) {
        log.debug("isSignatureValid");
        
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
        final Sign.SignatureData sd = new Sign.SignatureData(
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
                log.debug("recoveredAddress: " + recoveredAddress);
                if (recoveredAddress.equalsIgnoreCase(address)) {
                    match = true;
                    break;
                }
            }
        }
        
        return match;
    }
}
