package ai.elimu.web;

import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Role;
import ai.elimu.model.v2.enums.Environment;
import ai.elimu.util.ConfigHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * See https://github.com/organizations/elimu-ai/settings/applications and https://developer.github.com/v3/oauth/#web-application-flow
 */
@Controller
@RequestMapping("/sign-on/github")
@RequiredArgsConstructor
@Slf4j
public class SignOnControllerGitHub {

  private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";

  private static final String PROTECTED_RESOURCE_URL_EMAILS = PROTECTED_RESOURCE_URL + "/emails";

  private OAuth20Service oAuth20Service;

  private final String secretState = "secret_" + new Random().nextInt(999_999);

  private final ContributorDao contributorDao;

  @GetMapping
  public String handleAuthorization(HttpServletRequest request) throws IOException {
    log.info("handleAuthorization");

    String clientId = "75ab65504795daf525f5";
    String clientSecret = "4f6eba014e102f0ed48334de77dffc12c4d1f1d6";
    String baseUrl = "http://localhost:8080/webapp";
    if (EnvironmentContextLoaderListener.env == Environment.PROD) {
      clientId = "7018e4e57438eb0191a7";
      clientSecret = ConfigHelper.getProperty("github.api.secret");
      baseUrl = "https://" + request.getServerName();
    }
    log.info("baseUrl: " + baseUrl);

    oAuth20Service = new ServiceBuilder(clientId)
        .apiSecret(clientSecret)
        .defaultScope("user:email read:user") // See https://docs.github.com/en/free-pro-team@latest/developers/apps/scopes-for-oauth-apps
        .callback(baseUrl + "/sign-on/github/callback")
        .build(GitHubApi.instance());

    log.info("Fetching the Authorization URL...");
    String authorizationUrl = oAuth20Service.createAuthorizationUrlBuilder()
        .state(secretState)
        .build();
    log.info("Got the Authorization URL!");

    return "redirect:" + authorizationUrl;
  }

  @GetMapping(value="/callback")
  public String handleCallback(HttpServletRequest request, Model model) {
    log.info("handleCallback");

    String state = request.getParameter("state");
    log.debug("state: " + state);
    if (!secretState.equals(state)) {
      return "redirect:/sign-on?error=state_mismatch";
    } else {
      String code = request.getParameter("code");
      log.debug("verifierParam: " + code);

      String responseBodyUser;
      String responseBodyUserEmails;
      log.info("Trading the Authorization Code for an Access Token...");
      try {
        OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
        log.debug("accessToken: " + accessToken);
        log.info("Got the Access Token!");

        // Access the protected resource
        responseBodyUser = executeGithubRequest(accessToken, PROTECTED_RESOURCE_URL);
        responseBodyUserEmails = executeGithubRequest(accessToken, PROTECTED_RESOURCE_URL_EMAILS);
      } catch (IOException | InterruptedException | ExecutionException ex) {
        log.error(ex.getMessage());
        return "redirect:/sign-on?error=" + ex.getMessage();
      }

      JSONObject jsonObjectUser = new JSONObject(responseBodyUser);
      log.info("jsonObjectUser: " + jsonObjectUser);
      JSONArray jsonArrayUserEmails = new JSONArray(responseBodyUserEmails);
      log.info("jsonArrayUserEmails: " + jsonArrayUserEmails);
      JSONObject jsonObjectUserEmail = jsonArrayUserEmails.getJSONObject(0);
      log.info("jsonObjectUserEmail: " + jsonObjectUserEmail);

      Contributor contributor = new Contributor();
      if (jsonObjectUser.has("email") && !jsonObjectUser.isNull("email")) {
        contributor.setEmail(jsonObjectUser.getString("email"));
      } else if (jsonObjectUserEmail.has("email") && !jsonObjectUserEmail.isNull("email")) {
        contributor.setEmail(jsonObjectUserEmail.getString("email"));
      }
      if (jsonObjectUser.has("login")) {
        contributor.setUsernameGitHub(jsonObjectUser.getString("login"));
      }
      if (jsonObjectUser.has("id")) {
        Long idAsLong = jsonObjectUser.getLong("id");
        String id = String.valueOf(idAsLong);
        contributor.setProviderIdGitHub(id);
      }
      if (jsonObjectUser.has("avatar_url")) {
        contributor.setImageUrl(jsonObjectUser.getString("avatar_url"));
      }
      if (jsonObjectUser.has("name") && !jsonObjectUser.isNull("name")) {
        String name = jsonObjectUser.getString("name");
        String[] nameParts = name.split(" ");
        String firstName = nameParts[0];
        log.info("firstName: " + firstName);
        contributor.setFirstName(firstName);
        if (nameParts.length > 1) {
          String lastName = nameParts[nameParts.length - 1];
          log.info("lastName: " + lastName);
          contributor.setLastName(lastName);
        }
      }

      // Look for existing Contributor with matching e-mail address
      Contributor existingContributor = contributorDao.read(contributor.getEmail());
      if (existingContributor == null) {
        // Look for existing Contributor with matching GitHub id
        existingContributor = contributorDao.readByProviderIdGitHub(contributor.getProviderIdGitHub());
      }
      log.info("existingContributor: " + existingContributor);
      if (existingContributor == null) {
        // Store new Contributor in database
        contributor.setRegistrationTime(Calendar.getInstance());
        contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
        if (contributor.getEmail() == null) {
          // Ask the Contributor to add her e-mail manually
          request.getSession().setAttribute("contributor", contributor);
          return "redirect:/content/contributor/add-email";
        }
        contributorDao.create(contributor);
        log.info("Contributor " + contributor.getEmail() + " was created at " + request.getServerName());
      } else {
        // Contributor already exists in database
        // Update existing contributor with latest values fetched from provider
        if (StringUtils.isNotBlank(contributor.getUsernameGitHub())) {
          existingContributor.setUsernameGitHub(contributor.getUsernameGitHub());
        }
        if (StringUtils.isNotBlank(contributor.getProviderIdGitHub())) {
          existingContributor.setProviderIdGitHub(contributor.getProviderIdGitHub());
        }
        if (StringUtils.isNotBlank(contributor.getImageUrl())) {
          existingContributor.setImageUrl(contributor.getImageUrl());
        }
        if (StringUtils.isNotBlank(contributor.getFirstName())) {
          existingContributor.setFirstName(contributor.getFirstName());
        }
        if (StringUtils.isNotBlank(contributor.getLastName())) {
          existingContributor.setLastName(contributor.getLastName());
        }
        contributorDao.update(existingContributor);

        contributor = existingContributor;
      }

      // Add Contributor object to session
      request.getSession().setAttribute("contributor", contributor);

      return "redirect:/content";
    }
  }

  private String executeGithubRequest(OAuth2AccessToken accessToken, String url) throws IOException, ExecutionException, InterruptedException {
    log.info("executeGithubRequest");
    log.info("url: " + url);
    OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, url);
    oAuth20Service.signRequest(accessToken, oAuthRequest);
    Response response = oAuth20Service.execute(oAuthRequest);
    log.info("response.getCode(): " + response.getCode());
    log.info("response.getBody(): " + response.getBody());
    return response.getBody();
  }
}
