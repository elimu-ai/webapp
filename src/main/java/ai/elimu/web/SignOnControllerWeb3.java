package ai.elimu.web;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Role;
import ai.elimu.util.Web3Helper;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignOnControllerWeb3 {
    
    /**
     * Must match the signature message used in /WEB-INF/jsp/sign-on-web3.jsp
     */
    private static final String SIGNATURE_MESSAGE = "I verify ownership of this account 👍";
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(value="/sign-on/web3", method=RequestMethod.GET)
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
            HttpSession session,
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
        if (!Web3Helper.isSignatureValid(address, signature, SIGNATURE_MESSAGE)) {
            logger.warn("Invalid signature");
            return "redirect:/sign-on/web3?error=Invalid signature";
        }
        logger.info("Valid signature ✍️");
        
        // Check if the Contributor is currently signed on (via Google/GitHub)
        Contributor authenticatedContributor = (Contributor) session.getAttribute("contributor");
        logger.info("authenticatedContributor: " + authenticatedContributor);
        if ((authenticatedContributor != null) && (authenticatedContributor.getId() != null)) {
            // Check if a Contributor with this ETH address already exists in the database.
            // If so, merge the two Contributors into one.
            // TODO

            // Update Web3 details of existing Contributor
            authenticatedContributor.setProviderIdWeb3(address);
            contributorDao.update(authenticatedContributor);
            
            return "redirect:/content";
        }
        
        Contributor contributor = new Contributor();
        contributor.setProviderIdWeb3(address);
        
        // Check if a Contributor with this ETH address already exists in the database
        Contributor existingContributor = contributorDao.readByProviderIdWeb3(address);
        logger.info("existingContributor: " + existingContributor);
        if (existingContributor == null) {
            // Store new Contributor in database
            contributor.setEmail(address + "@ethmail.cc");
            contributor.setRegistrationTime(Calendar.getInstance());
            contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
            contributorDao.create(contributor);
            logger.info("Contributor " + contributor.getEmail() + " was created at " + request.getServerName());
        } else {
            contributor = existingContributor;
        }
        
         // Authenticate
        new CustomAuthenticationManager().authenticateUser(contributor);

        // Add Contributor object to session
        request.getSession().setAttribute("contributor", contributor);

        return "redirect:/content";
    }
}
