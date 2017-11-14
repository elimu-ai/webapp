package ai.elimu.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.SignOnEventDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Provider;
import ai.elimu.model.enums.Role;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.CookieHelper;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;

/**
 * See https://github.com/organizations/elimu-ai/settings/applications See
 * https://developer.github.com/v3/oauth/#web-application-flow
 */
@Controller
public class SignOnControllerGitHub extends BaseSignOnController {

	private static final String APPLICATION_BASE_URL = "http://localhost:8080/webapp";

	private static final String GITHUP_PROD_APIKEY = "7018e4e57438eb0191a7";

	private static final String GITHUP_TEST_APIKEY = "57aad0f85f09ef18d8e6";

	private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";

	private OAuth20Service oAuth20Service;

	private String secretState;

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ContributorDao contributorDao;

	@Autowired
	private SignOnEventDao signOnEventDao;

	/**
	 * https://developer.github.com/v3/oauth/#1-redirect-users-to-request-github-access
	 */
	@RequestMapping("/sign-on/github")
	public String handleAuthorization(HttpServletRequest request) throws IOException {
		logger.info("handleAuthorization");

		String apiKey = "75ab65504795daf525f5";
		String apiSecret = "4f6eba014e102f0ed48334de77dffc12c4d1f1d6";
		String baseUrl = APPLICATION_BASE_URL;
		if (EnvironmentContextLoaderListener.env == Environment.TEST) {
			apiKey = GITHUP_TEST_APIKEY;
			apiSecret = ConfigHelper.getProperty(AttributeName.GithupApiSecret);
			baseUrl = "http://" + request.getServerName();
		} else if (EnvironmentContextLoaderListener.env == Environment.PROD) {
			apiKey = GITHUP_PROD_APIKEY;
			apiSecret = ConfigHelper.getProperty(AttributeName.GithupApiSecret);
			baseUrl = "http://" + request.getServerName();
		}

		secretState = "secret_" + new Random().nextInt(999_999);

		oAuth20Service = new ServiceBuilder().apiKey(apiKey).apiSecret(apiSecret).state(secretState)
				.callback(baseUrl + "/sign-on/github/callback").scope("user:email") // https://developer.github.com/v3/oauth/#scopes
				.build(GitHubApi.instance());

		logger.info("Fetching the Authorization URL...");
		String authorizationUrl = oAuth20Service.getAuthorizationUrl();
		logger.info("Got the Authorization URL!");
		logger.info("authorizationUrl: " + authorizationUrl);

		return "redirect:" + authorizationUrl;
	}

	/**
	 * See
	 * https://developer.github.com/v3/oauth/#2-github-redirects-back-to-your-site
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/sign-on/github/callback", method = RequestMethod.GET)
	public String handleCallback(HttpServletRequest request, Model model) {
		logger.info("handleCallback");

		String state = request.getParameter(AttributeName.State);
		logger.debug("state: " + state);
		if (!secretState.equals(state)) {
			return "redirect:/sign-on?error=state_mismatch";
		} else {
			String code = request.getParameter(AttributeName.Code);
			logger.debug("verifierParam: " + code);

			String responseBody = null;
			try {
				OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
				logger.debug("accessToken: " + accessToken);
				OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
				oAuth20Service.signRequest(accessToken, oAuthRequest);
				Response response = oAuth20Service.execute(oAuthRequest);
				responseBody = response.getBody();
				logger.info("response.getCode(): " + response.getCode());
				logger.info("response.getBody(): " + responseBody);
			} catch (IOException | InterruptedException | ExecutionException ex) {
				logger.error(null, ex);
				return "redirect:/sign-on?login_error=" + ex.getMessage();
			}

			Contributor contributor = new Contributor();
			contributor.setReferrer(CookieHelper.getReferrer(request));
			contributor.setUtmSource(CookieHelper.getUtmSource(request));
			contributor.setUtmMedium(CookieHelper.getUtmMedium(request));
			contributor.setUtmCampaign(CookieHelper.getUtmCampaign(request));
			contributor.setUtmTerm(CookieHelper.getUtmTerm(request));
			contributor.setReferralId(CookieHelper.getReferralId(request));
			try {
				JSONObject jsonObject = new JSONObject(responseBody);
				logger.info("jsonObject: " + jsonObject);

				if (jsonObject.has(AttributeName.Email)) {
					if (!jsonObject.isNull(AttributeName.Email)) {
						// TODO: validate e-mail
						contributor.setEmail(jsonObject.getString(AttributeName.Email));
					}
				}
				if (jsonObject.has(AttributeName.Login)) {
					contributor.setUsernameGitHub(jsonObject.getString(AttributeName.Login));
				}
				if (jsonObject.has(AttributeName.Id)) {
					Long idAsLong = jsonObject.getLong(AttributeName.Id);
					String id = String.valueOf(idAsLong);
					contributor.setProviderIdGitHub(id);
				}
				if (jsonObject.has(AttributeName.AvatarUrl)) {
					contributor.setImageUrl(jsonObject.getString(AttributeName.AvatarUrl));
				}
				if (jsonObject.has(AttributeName.Name)) {
					if (!jsonObject.isNull(AttributeName.Name)) {
						String name = jsonObject.getString(AttributeName.Name);
						String[] nameParts = name.split(" ");
						String firstName = nameParts[0];
						logger.info("firstName: " + firstName);
						contributor.setFirstName(firstName);
						if (nameParts.length > 1) {
							String lastName = nameParts[nameParts.length - 1];
							logger.info("lastName: " + lastName);
							contributor.setLastName(lastName);
						}
					}
				}
			} catch (JSONException e) {
				logger.error(null, e);
			}

			Contributor existingContributor = contributorDao.read(contributor.getEmail());
			if (existingContributor == null) {
				// Look for existing Contributor with matching GitHub id
				existingContributor = contributorDao.readByProviderIdGitHub(contributor.getProviderIdGitHub());
			}
			if (existingContributor == null) {
				// Store new Contributor in database
				contributor.setRegistrationTime(Calendar.getInstance());
				if (StringUtils.isNotBlank(contributor.getEmail())
						&& contributor.getEmail().endsWith(AttributeName.ElimuEndEmailAddress)) {
					contributor.setRoles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.ANALYST, Role.CONTRIBUTOR)));
				} else {
					contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
				}
				if (contributor.getEmail() == null) {
					request.getSession().setAttribute(AttributeName.Contributor, contributor);
					new CustomAuthenticationManager().authenticateUser(contributor);
					return "redirect:/content/contributor/add-email";
				}
				contributorDao.create(contributor);
				sendWelcomeEmail(contributor);
				if (EnvironmentContextLoaderListener.env == Environment.PROD) {
					// Post notification in Slack
					String name = "";
					if (StringUtils.isNotBlank(contributor.getFirstName())) {
						name += "(";
						name += contributor.getFirstName();
						if (StringUtils.isNotBlank(contributor.getLastName())) {
							name += " " + contributor.getLastName();
						}
						name += ")";
					}
					String text = URLEncoder.encode("A new contributor " + name + " just joined the community: ")
							+ "http://elimu.ai/content/community/contributors";
					String iconUrl = contributor.getImageUrl();
					SlackApiHelper.postMessage(null, text, iconUrl, null);
				}
			} else {
				contributor = updateExistingContributor(contributor, existingContributor);
			}
			new CustomAuthenticationManager().authenticateUser(contributor);
			return createSignOnEvent(signOnEventDao, request, contributor, Provider.GITHUB);
		}
	}

	private Contributor updateExistingContributor(Contributor contributor, Contributor existingContributor) {
		if (StringUtils.isNotBlank(contributor.getUsernameGitHub())) {
			existingContributor.setUsernameGitHub(contributor.getUsernameGitHub());
		}
		if (StringUtils.isNotBlank(contributor.getProviderIdGitHub())) {
			existingContributor.setProviderIdGitHub(contributor.getProviderIdGitHub());
		}
		if (StringUtils.isNotBlank(contributor.getImageUrl())) {
			existingContributor.setImageUrl(contributor.getImageUrl());
		}
		// TODO: firstName/lastName
		if (StringUtils.isBlank(existingContributor.getReferrer())) {
			existingContributor.setReferrer(contributor.getReferrer());
		}
		if (StringUtils.isBlank(existingContributor.getUtmSource())) {
			existingContributor.setUtmSource(contributor.getUtmSource());
		}
		if (StringUtils.isBlank(existingContributor.getUtmMedium())) {
			existingContributor.setUtmMedium(contributor.getUtmMedium());
		}
		if (StringUtils.isBlank(existingContributor.getUtmCampaign())) {
			existingContributor.setUtmCampaign(contributor.getUtmCampaign());
		}
		if (StringUtils.isBlank(existingContributor.getUtmTerm())) {
			existingContributor.setUtmTerm(contributor.getUtmTerm());
		}
		if (existingContributor.getReferralId() == null) {
			existingContributor.setReferralId(contributor.getReferralId());
		}
		contributorDao.update(existingContributor);
		contributor = existingContributor;
		return contributor;
	}
}
