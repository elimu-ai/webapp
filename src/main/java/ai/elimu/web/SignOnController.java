package ai.elimu.web;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.SignOnEventDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Provider;
import ai.elimu.model.enums.Role;
import ai.elimu.model.enums.Team;
import ai.elimu.web.context.EnvironmentContextLoaderListener;

@Controller
@RequestMapping("/sign-on")
public class SignOnController extends BaseSignOnController {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ContributorDao contributorDao;

	@Autowired
	private SignOnEventDao signOnEventDao;

	@RequestMapping(method = RequestMethod.GET)
	public String handleRequest(ModelMap model) {
		logger.debug("handleRequest");
		model.addAttribute("teams", Team.values());
		return "sign-on";
	}

	/**
	 * To make it possible to sign on without an active Internet connection,
	 * enable sign-on with a test user during offline development.
	 */
	@RequestMapping("/offline")
	public String handleOfflineSignOnRequest(HttpServletRequest request) {
		logger.info("handleOfflineSignOnRequest");

		if (EnvironmentContextLoaderListener.env == Environment.DEV) {
			Contributor contributor = contributorDao.read(AttributeName.ELIMU_TESTUSER_MAIL);
			if (contributor == null) {
				contributor = new Contributor();
				contributor.setEmail(AttributeName.ELIMU_TESTUSER_MAIL);
				contributor.setFirstName(AttributeName.ELIMU_TESTUSER_FIRSTNAME);
				contributor.setLastName(AttributeName.ELIMU_TESTUSER_LASTNAME);
				contributor.setRoles(
						new HashSet<>(Arrays.asList(Role.ADMIN, Role.ANALYST, Role.CONTRIBUTOR, Role.PROJECT_MANAGER)));
				contributor.setRegistrationTime(Calendar.getInstance());
				contributorDao.create(contributor);
			}
			new CustomAuthenticationManager().authenticateUser(contributor);
			return createSignOnEvent(signOnEventDao, request, contributor, Provider.OFFLINE);
		} else {
			return null;
		}
	}
}
