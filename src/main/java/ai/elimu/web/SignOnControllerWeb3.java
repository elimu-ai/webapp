package ai.elimu.web;

import ai.elimu.dao.ContributorDao;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.enums.Role;
import ai.elimu.util.Web3Helper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/sign-on/web3")
@RequiredArgsConstructor
@Slf4j
public class SignOnControllerWeb3 {

  /**
   * Must match the signature message used in /WEB-INF/jsp/sign-on-web3.jsp
   */
  private static final String SIGNATURE_MESSAGE = "elimu.ai's mission is to build innovative learning software that empowers out-of-school children to teach themselves basic reading📖, writing✍🏽 and math🔢 **within 6 months**.";

  private final ContributorDao contributorDao;

  @GetMapping
  public String handleGetRequest(HttpServletRequest request) throws IOException {
    log.info("handleGetRequest");

    return "sign-on-web3";
  }

  /**
   * Verify that an Ethereum signature was signed by a given Ethereum account. If true, we proceed with the sign-on process. Otherwise, we return an error message.
   */
  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String handleAuthorization(
      HttpServletRequest request,
      HttpSession session,
      @RequestParam String address,
      @RequestParam String signature
  ) throws IOException {
    log.info("handleAuthorization");
    log.info("address: " + address);
    if (StringUtils.isBlank(address)) {
      return "redirect:/sign-on/web3?error=Missing address";
    }
    address = address.toLowerCase();

    log.info("signature: " + signature);
    if (StringUtils.isBlank(signature)) {
      return "redirect:/sign-on/web3?error=Missing signature";
    }

    // Check if the signature is valid
    if (!Web3Helper.isSignatureValid(address, signature, SIGNATURE_MESSAGE)) {
      log.warn("Invalid signature");
      return "redirect:/sign-on/web3?error=Invalid signature";
    }
    log.info("Valid signature ✍️");

    // Check if the Contributor is currently signed on (via Google/GitHub)
    Contributor authenticatedContributor = (Contributor) session.getAttribute("contributor");
    log.info("authenticatedContributor: " + authenticatedContributor);
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
    log.info("existingContributor: " + existingContributor);
    if (existingContributor == null) {
      // Store new Contributor in database
      contributor.setEmail(address + "@ethmail.cc");
      contributor.setRegistrationTime(Calendar.getInstance());
      contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
      contributorDao.create(contributor);
      log.info("Contributor " + contributor.getEmail() + " was created at " + request.getServerName());
    } else {
      contributor = existingContributor;
    }

    // Add Contributor object to session
    request.getSession().setAttribute("contributor", contributor);

    return "redirect:/content";
  }
}
