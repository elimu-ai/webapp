package ai.elimu.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
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

import com.github.scribejava.apis.FacebookApi;
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
 * See https://developers.facebook.com/apps
 */
@Controller
public class SignOnControllerFacebook extends BaseSignOnController {

	private OAuth20Service oAuth20Service;

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ContributorDao contributorDao;

	@Autowired
	private SignOnEventDao signOnEventDao;

	@RequestMapping("/sign-on/facebook")
	public String handleAuthorization(HttpServletRequest request) throws IOException {
		logger.info("handleAuthorization");

		String apiKey = "1130171497015759";
		String apiSecret = "d8b49268dacd1e29eca82de8edd88c1c";
		String baseUrl = "http://localhost:8080/webapp";
		if (EnvironmentContextLoaderListener.env == Environment.TEST) {
			apiKey = "1130170237015885";
			apiSecret = ConfigHelper.getProperty("facebook.api.secret");
			baseUrl = "http://" + request.getServerName();
		} else if (EnvironmentContextLoaderListener.env == Environment.PROD) {
			apiKey = "1130160227016886";
			apiSecret = ConfigHelper.getProperty("facebook.api.secret");
			baseUrl = "http://" + request.getServerName();
		}

		oAuth20Service = new ServiceBuilder().apiKey(apiKey).apiSecret(apiSecret)
				.callback(baseUrl + "/sign-on/facebook/callback").scope("email,user_about_me") // https://developers.facebook.com/docs/facebook-login/permissions
				.build(FacebookApi.instance());

		logger.info("Fetching the Authorization URL...");
		String authorizationUrl = oAuth20Service.getAuthorizationUrl();
		logger.info("Got the Authorization URL!");

		return "redirect:" + authorizationUrl;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/sign-on/facebook/callback", method = RequestMethod.GET)
	public String handleCallback(HttpServletRequest request, Model model) {
		logger.info("handleCallback");

		if (request.getParameter("param_denied") != null) {
			return "redirect:/sign-on?error=" + request.getParameter(AttributeName.ParamDenied);
		} else if (request.getParameter("error") != null) {
			return "redirect:/sign-on?error=" + request.getParameter(AttributeName.Error);
		} else {
			String code = request.getParameter(AttributeName.Code);
			logger.debug("code: " + code);

			String responseBody = null;
			try {
				OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
				logger.debug("accessToken: " + accessToken);
				String fields = "?fields=id,email,name,first_name,last_name,gender,link,picture,birthday,age_range,education,work,timezone,hometown,location,friends";
				OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, "https://graph.facebook.com/v2.8/me" + fields);
				oAuth20Service.signRequest(accessToken, oAuthRequest);
				Response response = oAuth20Service.execute(oAuthRequest);
				responseBody = response.getBody();
				logger.info("response.getCode(): " + response.getCode());
				logger.info("response.getBody(): " + responseBody);
			} catch (InterruptedException | ExecutionException | IOException e) {
				logger.error(null, e);
				return "redirect:/sign-on?login_error=" + e.getMessage();
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
					// TODO: validate e-mail
					contributor.setEmail(jsonObject.getString(AttributeName.Email));
				}
				if (jsonObject.has(AttributeName.Id)) {
					contributor.setProviderIdFacebook(jsonObject.getString(AttributeName.Id));
				}
				if (jsonObject.has(AttributeName.Picture)) {
					JSONObject picture = jsonObject.getJSONObject(AttributeName.Picture);
					JSONObject pictureData = picture.getJSONObject(AttributeName.Data);
					contributor.setImageUrl(pictureData.getString(AttributeName.Url));
				}
				if (jsonObject.has(AttributeName.FirstName)) {
					contributor.setFirstName(jsonObject.getString(AttributeName.FirstName));
				}
				if (jsonObject.has(AttributeName.LastName)) {
					contributor.setLastName(jsonObject.getString(AttributeName.LastName));
				}
			} catch (JSONException e) {
				logger.error(null, e);
			}

			Contributor existingContributor = contributorDao.read(contributor.getEmail());
			if (existingContributor == null) {
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
			return createSignOnEvent(signOnEventDao, request, contributor, Provider.FACEBOOK);
		}
	}

	private Contributor updateExistingContributor(Contributor contributor, Contributor existingContributor) {
		if (StringUtils.isNotBlank(contributor.getProviderIdFacebook())) {
			existingContributor.setProviderIdFacebook(contributor.getProviderIdFacebook());
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
