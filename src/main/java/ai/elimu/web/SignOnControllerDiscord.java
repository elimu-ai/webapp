package ai.elimu.web;

import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Role;
import ai.elimu.model.v2.enums.Environment;
import ai.elimu.util.ConfigHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import com.github.scribejava.apis.DiscordApi;
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
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/sign-on/discord")
@RequiredArgsConstructor
@Slf4j
public class SignOnControllerDiscord {

  private static final String NETWORK_NAME = "discord";

  private static final String PROTECTED_RESOURCE_URL = "https://discordapp.com/api/users/@me";

  private OAuth20Service oAuth20Service;

  private final String secretState = "secret_" + new Random().nextInt(999_999);

  private final ContributorDao contributorDao;

  @GetMapping
  public String handleAuthorization(HttpServletRequest request) throws IOException {
    log.info("handleAuthorization");

    // EnvironmentContextLoaderListener.env == Environment.DEV
    String clientId = "978873481649348668";
    String clientSecret = "Lf0O65Zk0Qv9cXyC7igPgljIjSJJBZbv";
    String baseUrl = "http://localhost:8080/webapp";

    if (EnvironmentContextLoaderListener.env == Environment.TEST) {
      clientId = "979655702081597460";
      clientSecret = ConfigHelper.getProperty("discord.api.secret");
      baseUrl = "https://" + request.getServerName();
    } else if (EnvironmentContextLoaderListener.env == Environment.PROD) {
      clientId = "979686718909341716";
      clientSecret = ConfigHelper.getProperty("discord.api.secret");
      baseUrl = "https://" + request.getServerName();
    }

    oAuth20Service = new ServiceBuilder(clientId)
        .apiSecret(clientSecret)
        .defaultScope("identify email")
        .callback(baseUrl + "/sign-on/discord/callback")
        .build(DiscordApi.instance());

    log.info("Fetching the Authorization URL...");
    String authorizationUrl = oAuth20Service.createAuthorizationUrlBuilder()
        .state(secretState)
        .build();
    log.info("Redirecting to the Authorization URL: " + authorizationUrl);
    log.info("Got the Authorization URL!");

    return "redirect:" + authorizationUrl;
  }


  @GetMapping(value="/callback")
  public String handleCallback(HttpServletRequest request) throws IOException, InterruptedException, ExecutionException {
    log.info("handleCallback");


    String state = request.getParameter("state");
    log.debug("state: " + state);
    if (!secretState.equals(state)) {
      return "redirect:/sign-on?error=state_mismatch";
    } else {
      String code = request.getParameter("code");
      log.debug("verifierParam: " + code);
      log.info(code);
      String responseBody = null;

      log.info("Trading the Authorization Code for an Access Token...");
      try {
        OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
        log.debug("accessToken: " + accessToken);
        log.info("Got the Access Token!");
        log.info("Now let's go and ask for the protected resource...");
        // Access the protected resource
        OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        oAuth20Service.signRequest(accessToken, oAuthRequest);
        Response response = oAuth20Service.execute(oAuthRequest);
        responseBody = response.getBody();
        log.info("response.getCode(): " + response.getCode());
        log.info("response.getBody(): " + responseBody);
      } catch (IOException | InterruptedException | ExecutionException ex) {
        log.error(ex.getMessage());
        return "redirect:/sign-on?error=" + ex.getMessage();
      }

      JSONObject jsonObject = new JSONObject(responseBody);
      log.info("jsonObject: " + jsonObject);

      Contributor contributor = new Contributor();
      if (jsonObject.has("email") && !jsonObject.isNull("email")) {
        contributor.setEmail(jsonObject.getString("email"));
      }
      if (jsonObject.has("username")) {
        contributor.setUsernameDiscord(jsonObject.getString("username"));
      }
      if (jsonObject.has("id")) {
        Long idAsLong = jsonObject.getLong("id");
        String id = String.valueOf(idAsLong);
        contributor.setProviderIdDiscord(id);
      }
      if (!jsonObject.isNull("avatar")) {
        String uriAvatar = "https://cdn.discordapp.com/avatars/" + jsonObject.getLong("id") + "/" + jsonObject.getString("avatar") + ".png";
        log.info("Avatar URL: " + uriAvatar);
        contributor.setImageUrl(uriAvatar);
      }
      if (jsonObject.has("username")) {
        contributor.setFirstName(jsonObject.getString("username"));
      }
      if (jsonObject.has("discriminator")) {
        contributor.setLastName("#" + jsonObject.getString("discriminator"));
      }

      // Look for existing Contributor with matching e-mail address
      Contributor existingContributor = contributorDao.read(contributor.getEmail());
      if (existingContributor == null) {
        // Look for existing Contributor with matching Discord id
        existingContributor = contributorDao.readByProviderIdDiscord(contributor.getProviderIdDiscord());
      }
      log.info("existingContributor: " + existingContributor);
      if (existingContributor == null) {
        // Store new Contributor in database
        contributor.setRegistrationTime(Calendar.getInstance());
        contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
        if (contributor.getEmail() == null) {
          // Ask the Contributor to add their e-mail manually
          request.getSession().setAttribute("contributor", contributor);
          return "redirect:/content/contributor/add-email";
        }
        contributorDao.create(contributor);
        log.info("Contributor " + contributor.getEmail() + " was created at " + request.getServerName());
      } else {
        // Contributor already exists in database
        // Update existing contributor with latest values fetched from provider
        if (StringUtils.isNotBlank(contributor.getUsernameDiscord())) {
          existingContributor.setUsernameDiscord(contributor.getUsernameDiscord());
        }
        if (StringUtils.isNotBlank(contributor.getProviderIdDiscord())) {
          existingContributor.setProviderIdDiscord(contributor.getProviderIdDiscord());
        }
        if (StringUtils.isNotBlank(contributor.getImageUrl())) {
          existingContributor.setImageUrl(contributor.getImageUrl());
        }
        contributorDao.update(existingContributor);

        contributor = existingContributor;
      }

      // Add Contributor object to session
      request.getSession().setAttribute("contributor", contributor);

      return "redirect:/content";
    }
  }
}