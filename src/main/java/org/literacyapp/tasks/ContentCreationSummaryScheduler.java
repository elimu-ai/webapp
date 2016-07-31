package org.literacyapp.tasks;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.literacyapp.dao.ContentCreationEventDao;
import org.literacyapp.dao.ContributorDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Allophone;
import org.literacyapp.model.content.Letter;
import org.literacyapp.model.contributor.ContentCreationEvent;
import org.literacyapp.util.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ContentCreationSummaryScheduler {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ContributorDao contributorDao;
    
    @Autowired
    private ContentCreationEventDao contentCreationEventDao;
    
//    @Scheduled(cron="00 00 16 * * *") // At 16:00 every day
    @Scheduled(cron="00 00 * * * *")
    public synchronized void execute() {
        logger.info("execute");
        
        Calendar calendarFrom = Calendar.getInstance();
//        calendarFrom.add(Calendar.DAY_OF_MONTH, -1);
        calendarFrom.add(Calendar.HOUR_OF_DAY, -1);
        Calendar calendarTo = Calendar.getInstance();
        List<ContentCreationEvent> contentCreationEvents = contentCreationEventDao.readAll(calendarFrom, calendarTo);
        logger.info("contentCreationEvents.size(): " + contentCreationEvents.size());
        if (!contentCreationEvents.isEmpty()) {
            // Send summary to Contributors
            for (Contributor contributor : contributorDao.readAll()) {
                String to = contributor.getEmail();
                String from = "LiteracyApp <info@literacyapp.org>";
                Locale locale = new Locale("en");
                String subject = "New content was uploaded";
                String title = subject;
                String firstName = StringUtils.isBlank(contributor.getFirstName()) ? "" : contributor.getFirstName();
                String htmlText = "<p>Hi, " + firstName + "</p>";
                htmlText += "<p>This is a summary of some of the content that was uploaded to the website during the past day.</p>";
                htmlText += "<p>The material will be used to enable children without access to school to <i>teach themselves</i> basic reading/writing/arithmetic.</p>";
                
                
                int counterAllophones = 0;
                for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                    String className = contentCreationEvent.getContent().getClass().getSimpleName();
                    logger.info("className: " + className);
                    if ("Allophone".equals(className)) {
                        counterAllophones++;
                    }
                }
                if (counterAllophones > 0) {
                    htmlText += "<p>&nbsp;</p>";
                    htmlText += "<h2>Allophones</h2>";
                    counterAllophones = 0;
                    for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                        String className = contentCreationEvent.getContent().getClass().getSimpleName();
                        logger.info("className: " + className);
                        if ("Allophone".equals(className)) {
                            Allophone allophone = (Allophone) contentCreationEvent.getContent();
                            htmlText += "<p>Language: " + contentCreationEvent.getContent().getLocale().getLanguage() + "</p>\n";
                            htmlText += "<p>IPA value: " + allophone.getValueIpa() + "</p>\n";
                            htmlText += "<p>X-SAMPA value: " + allophone.getValueSampa() + "</p>\n";
                            htmlText += "<p>";
                                htmlText += "Contributor: ";
                                if (StringUtils.isNotBlank(contentCreationEvent.getContributor().getImageUrl())) {
                                    htmlText += "<img src=\"" + contentCreationEvent.getContributor().getImageUrl() + "\" alt=\"\" style=\"max-height: 1em; border-radius: 50%;\"> ";
                                }
                                htmlText += contentCreationEvent.getContributor().getFirstName() + " " + contentCreationEvent.getContributor().getLastName() + "</p>\n";
                            htmlText += "</p>";
                            htmlText += "<p>&nbsp;</p>";

                            if (++counterAllophones == 5) {
                                break;
                            }
                        }
                    }
                }
                
                
                int counterLetters = 0;
//                for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
//                    String className = contentCreationEvent.getContent().getClass().getSimpleName();
//                    logger.info("className: " + className);
//                    if ("Letter".equals(className)) {
//                        counterLetters++;
//                    }
//                }
//                if (counterLetters > 0) {
//                    htmlText += "<p></p>";
//                    htmlText += "<h2>Letters</h2>";
//                    htmlText += "<table>\n";
//                        htmlText += "<thead>\n";
//                            htmlText += "<th>Language</th>\n";
//                            htmlText += "<th>Text</th>\n";
//                            htmlText += "<th>Contributor</th>\n";
//                        htmlText += "</thead>\n";
//                        htmlText += "<tbody>";
//                        counterLetters = 0;
//                        for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
//                            String className = contentCreationEvent.getContent().getClass().getSimpleName();
//                            logger.info("className: " + className);
//                            if ("Letter".equals(className)) {
//                                Letter letter = (Letter) contentCreationEvent.getContent();
//                                htmlText += "<tr>\n";
//                                    htmlText += "<td>\n";
//                                        htmlText += contentCreationEvent.getContent().getLocale().getLanguage() + "\n";
//                                    htmlText += "</td>\n";
//                                    htmlText += "<td>\n";
//                                        htmlText += "'" + letter.getText() + "'\n";
//                                    htmlText += "</td>\n";
//                                    htmlText += "<td>\n";
//                                        if (StringUtils.isNotBlank(contentCreationEvent.getContributor().getImageUrl())) {
//                                            htmlText += "<img src=\"" + contentCreationEvent.getContributor().getImageUrl() + "\" alt=\"\" style=\"max-height: 1em; border-radius: 50%;\"> ";
//                                        }
//                                        htmlText += contentCreationEvent.getContributor().getFirstName() + " " + contentCreationEvent.getContributor().getLastName()  + "</td>\n";
//                                    htmlText += "</td>\n";
//                                htmlText += "</tr>";
//
//                                if (++counterLetters == 5) {
//                                    break;
//                                }
//                            }
//                        }
//                        htmlText += "</tbody>\n";
//                    htmlText += "</table>";
//                }
                
                
                int counterNumbers = 0;
//                for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
//                    String className = contentCreationEvent.getContent().getClass().getSimpleName();
//                    logger.info("className: " + className);
//                    if ("Number".equals(className)) {
//                        counterNumbers++;
//                    }
//                }
//                if (counterNumbers > 0) {
//                    htmlText += "<p></p>";
//                    htmlText += "<h2>Numbers</h2>";
//                    htmlText += "<table>\n";
//                        htmlText += "<thead>\n";
//                            htmlText += "<th>Language</th>\n";
//                            htmlText += "<th>Value</th>\n";
//                            htmlText += "<th>Symbol</th>\n";
//                            htmlText += "<th>Number word</th>\n";
//                            htmlText += "<th>Contributor</th>\n";
//                        htmlText += "</thead>\n";
//                        htmlText += "<tbody>";
//                        counterNumbers = 0;
//                        for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
//                            String className = contentCreationEvent.getContent().getClass().getSimpleName();
//                            logger.info("className: " + className);
//                            if ("Number".equals(className)) {
//                                org.literacyapp.model.content.Number number = (org.literacyapp.model.content.Number) contentCreationEvent.getContent();
//                                htmlText += "<tr>\n";
//                                    htmlText += "<td>\n";
//                                        htmlText += contentCreationEvent.getContent().getLocale().getLanguage() + "\n";
//                                    htmlText += "</td>\n";
//                                    htmlText += "<td>\n";
//                                        htmlText += "/" + number.getValue() + "/\n";
//                                    htmlText += "</td>\n";
//                                    htmlText += "<td>\n";
//                                        htmlText += number.getSymbol() + "\n";
//                                    htmlText += "</td>\n";
//                                    htmlText += "<td>\n";
//                                        htmlText += number.getWord() + "\n";
//                                    htmlText += "</td>\n";
//                                    htmlText += "<td>\n";
//                                        if (StringUtils.isNotBlank(contentCreationEvent.getContributor().getImageUrl())) {
//                                            htmlText += "<img src=\"" + contentCreationEvent.getContributor().getImageUrl() + "\" alt=\"\" style=\"max-height: 1em; border-radius: 50%;\"> ";
//                                        }
//                                        htmlText += contentCreationEvent.getContributor().getFirstName() + " " + contentCreationEvent.getContributor().getLastName()  + "</td>\n";
//                                    htmlText += "</td>\n";
//                                htmlText += "</tr>";
//
//                                if (++counterNumbers == 5) {
//                                    break;
//                                }
//                            }
//                        }
//                        htmlText += "</tbody>\n";
//                    htmlText += "</table>";
//                }
                
                
                // TODO: Words
                
                
                // TODO: Audios
                
                
                // TODO: Images
                
                
                // TODO: Videos
                
                
                htmlText += "<p>&nbsp;</p>";
                htmlText += "<h2>Can you help?</h2>";
                htmlText += "<p>Do you know about anyone else who might be interested in helping us with content creation? Please share our website with them :-)</p>";
                htmlText += "<p>Or to upload more content, click the button below:</p>";
                String buttonText = "Go to content editor";
                String buttonUrl = "http://literacyapp.org/content";
                
                if ((counterAllophones > 0) 
                        || (counterLetters > 0) 
                        || (counterNumbers > 0)) {
                    Mailer.sendHtmlWithButton(to, from, from, subject, title, htmlText, buttonText, buttonUrl);
                }
            }
        }
        
        logger.info("execute complete");
    }
}
