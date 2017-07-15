package ai.elimu.tasks;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.util.Mailer;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ContributorRegistrationSummaryScheduler {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ContributorDao contributorDao;
    
//    @Scheduled(cron="00 00 08 * * *") // At 08:00 every day
    public synchronized void execute() {
        logger.info("execute");
        
        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.add(Calendar.DAY_OF_MONTH, -1);
        Calendar calendarTo = Calendar.getInstance();
        List<Contributor> contributorsRegisteredRecently = contributorDao.readAll(calendarFrom, calendarTo);
        logger.info("contributorsRegisteredRecently.size(): " + contributorsRegisteredRecently.size());
        if (!contributorsRegisteredRecently.isEmpty()) {
            // Send summary to existing Contributors
            for (Contributor contributor : contributorDao.readAll()) {
                String baseUrl = "http://localhost:8080/webapp";
                if (EnvironmentContextLoaderListener.env == Environment.TEST) {
                    baseUrl = "http://test.elimu.ai";
                } else if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                    baseUrl = "http://elimu.ai";
                }
                
                String to = contributor.getEmail();
                String from = "elimu.ai <info@elimu.ai>";
                Locale locale = new Locale("en");
                String subject = contributorsRegisteredRecently.get(0).getFirstName() + " " + contributorsRegisteredRecently.get(0).getLastName() + " joined the community";
                String title = subject;
                String firstName = StringUtils.isBlank(contributor.getFirstName()) ? "" : contributor.getFirstName();
                String htmlText = "<p>Hi, " + firstName + "</p>";
                if (contributorsRegisteredRecently.size() == 1) {
                    htmlText += "<p>A new contributor joined the elimu.ai community:</p>";
                } else {
                    htmlText += "<p>New contributors joined the elimu.ai community:</p>";
                }
                
                int counter = 0;
                for (Contributor contributorRegisteredRecently : contributorsRegisteredRecently) {
                    if (contributorRegisteredRecently.getId().equals(contributor.getId())) {
                        // Skip if the Contributor is the same as the one registered recently
                        continue;
                    } else if (StringUtils.isEmpty(contributorRegisteredRecently.getMotivation())) {
                        // Skip if the contributor did not complete the on-boarding wizard
                        continue;
                    }
                    
                    htmlText += "<hr style=\"border-color: #CCC; border-top: 0;\" />";
                    htmlText += "<p>" + contributorRegisteredRecently.getFirstName() + " " + contributorRegisteredRecently.getLastName() + "</p>";
                    if (StringUtils.isNotBlank(contributorRegisteredRecently.getImageUrl())) {
                        htmlText += "<img src=\"" + contributorRegisteredRecently.getImageUrl() + "\" alt=\"\" style=\"max-height: 5em; border-radius: 50%;\">";
                    }
                    htmlText += "<p>Language: " + messageSource.getMessage("language." + contributorRegisteredRecently.getLocale().getLanguage(), null, locale) + "</p>";
                    htmlText += "<p>Teams: " + contributorRegisteredRecently.getTeams() + "</p>";
                    htmlText += "<p>Personal motivation:</p>";
                    htmlText += "<p><blockquote>\"" + contributorRegisteredRecently.getMotivation() + "\"</blockquote></p>";
                    
                    if (++counter == 5) {
                        break;
                    }
                }
                
                htmlText += "<hr style=\"border-color: #CCC; border-top: 0;\" />";
                htmlText += "<p>Do you want to learn more about the new (and existing) contributors?</p>";
                String buttonText = "See complete list of contributors";
                String buttonUrl = baseUrl + "/content/community/contributors";
                
                if (counter > 0) {
                    Mailer.sendHtmlWithButton(to, null, from, subject, title, htmlText, buttonText, buttonUrl);
                }
            }
        }
        
        logger.info("execute complete");
    }
}
