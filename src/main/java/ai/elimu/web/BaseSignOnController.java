package ai.elimu.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import ai.elimu.dao.SignOnEventDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.contributor.SignOnEvent;
import ai.elimu.model.enums.Provider;
import ai.elimu.util.CookieHelper;
import ai.elimu.util.Mailer;

public class BaseSignOnController {

	protected String createSignOnEvent(SignOnEventDao signOnEventDao, HttpServletRequest request,
			Contributor contributor, Provider provider) {
		request.getSession().setAttribute(AttributeName.Contributor, contributor);
		SignOnEvent signOnEvent = new SignOnEvent();
		signOnEvent.setContributor(contributor);
		signOnEvent.setCalendar(Calendar.getInstance());
		signOnEvent.setServerName(request.getServerName());
		signOnEvent.setProvider(provider);
		signOnEvent.setRemoteAddress(request.getRemoteAddr());
		signOnEvent.setUserAgent(StringUtils.abbreviate(request.getHeader(AttributeName.UserAgent), 1000));
		signOnEvent.setReferrer(CookieHelper.getReferrer(request));
		signOnEvent.setUtmSource(CookieHelper.getUtmSource(request));
		signOnEvent.setUtmMedium(CookieHelper.getUtmMedium(request));
		signOnEvent.setUtmCampaign(CookieHelper.getUtmCampaign(request));
		signOnEvent.setUtmTerm(CookieHelper.getUtmTerm(request));
		signOnEvent.setReferralId(CookieHelper.getReferralId(request));
		signOnEventDao.create(signOnEvent);
		return "redirect:/content";
	}
	
	protected void sendWelcomeEmail(Contributor contributor) {
		String to = contributor.getEmail();
		String from = "elimu.ai <info@elimu.ai>";
		String subject = "Welcome to the community";
		String title = "Welcome!";
		String firstName = StringUtils.isBlank(contributor.getFirstName()) ? "" : contributor.getFirstName();
		String htmlText = "<p>Hi, " + firstName + "</p>";
		htmlText += "<p>Thank you very much for registering as a contributor to the elimu.ai community. We are glad to see you join us!</p>";
		htmlText += "<h2>Purpose</h2>";
		htmlText += "<p>The purpose of elimu.ai is to provide <i>every child</i> with access to quality basic education.</p>";
		htmlText += "<h2>Why?</h2>";
		htmlText += "<p>The word \"elimu\" is Swahili for \"education\". We believe that a free quality education is the right of every child no matter her social or geographical background.</p>";
		htmlText += "<h2>How?</h2>";
		htmlText += "<p>With your help, this is what we aim to achieve:</p>";
		htmlText += "<p><blockquote>\"We build tablet-based software that teaches a child to read, write and calculate fully autonomously, without guidance from qualified teachers.\"</blockquote></p>";
		htmlText += "<p><img src=\"http://elimu.ai/static/img/banner-en.jpg\" alt=\"\" style=\"width: 564px; max-width: 100%;\" /></p>";
		htmlText += "<h2>Chat</h2>";
		htmlText += "<p>At http://slack.elimu.ai you can chat with the other community members.</p>";
		Mailer.sendHtmlWithButton(to, null, from, subject, title, htmlText, "Open chat", "http://slack.elimu.ai");
	}

}
