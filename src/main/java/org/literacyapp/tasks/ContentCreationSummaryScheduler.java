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
import org.literacyapp.model.content.Word;
import org.literacyapp.model.content.multimedia.Audio;
import org.literacyapp.model.content.multimedia.Image;
import org.literacyapp.model.content.multimedia.Video;
import org.literacyapp.model.contributor.ContentCreationEvent;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.util.Mailer;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
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
                String baseUrl = "http://localhost:8080/literacyapp-webapp";
                if (EnvironmentContextLoaderListener.env == Environment.TEST) {
                    baseUrl = "http://test.literacyapp.org";
                } else if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                    baseUrl = "http://literacyapp.org";
                }
                            
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
                    htmlText += "<hr style=\"border-color: #CCC; border-top: 0;\" />";
                    htmlText += "<h2>Allophones</h2>";
                    counterAllophones = 0;
                    for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                        String className = contentCreationEvent.getContent().getClass().getSimpleName();
                        logger.info("className: " + className);
                        if ("Allophone".equals(className)) {
                            Allophone allophone = (Allophone) contentCreationEvent.getContent();
                            htmlText += "<p>Language: " + contentCreationEvent.getContent().getLocale().getLanguage() + "</p>\n";
                            htmlText += "<p>IPA value: /" + allophone.getValueIpa() + "/ (X-SAMPA: " + allophone.getValueSampa() + ")</p>\n";
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
                for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                    String className = contentCreationEvent.getContent().getClass().getSimpleName();
                    logger.info("className: " + className);
                    if ("Letter".equals(className)) {
                        counterLetters++;
                    }
                }
                if (counterLetters > 0) {
                    htmlText += "<hr style=\"border-color: #CCC; border-top: 0;\" />";
                    htmlText += "<h2>Letters</h2>";
                    counterLetters = 0;
                    for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                        String className = contentCreationEvent.getContent().getClass().getSimpleName();
                        logger.info("className: " + className);
                        if ("Letter".equals(className)) {
                            Letter letter = (Letter) contentCreationEvent.getContent();
                            htmlText += "<p>Language: " + contentCreationEvent.getContent().getLocale().getLanguage() + "</p>\n";
                            htmlText += "<p>Text: /" + letter.getText() + "/</p>\n";
                            htmlText += "<p>";
                                htmlText += "Contributor: ";
                                if (StringUtils.isNotBlank(contentCreationEvent.getContributor().getImageUrl())) {
                                    htmlText += "<img src=\"" + contentCreationEvent.getContributor().getImageUrl() + "\" alt=\"\" style=\"max-height: 1em; border-radius: 50%;\"> ";
                                }
                                htmlText += contentCreationEvent.getContributor().getFirstName() + " " + contentCreationEvent.getContributor().getLastName() + "</p>\n";
                            htmlText += "</p>";
                            htmlText += "<p>&nbsp;</p>";

                            if (++counterLetters == 5) {
                                break;
                            }
                        }
                    }
                }
                
                
                int counterNumbers = 0;
                for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                    String className = contentCreationEvent.getContent().getClass().getSimpleName();
                    logger.info("className: " + className);
                    if ("Number".equals(className)) {
                        counterNumbers++;
                    }
                }
                if (counterNumbers > 0) {
                    htmlText += "<hr style=\"border-color: #CCC; border-top: 0;\" />";
                    htmlText += "<h2>Numbers</h2>";
                    counterNumbers = 0;
                    for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                        String className = contentCreationEvent.getContent().getClass().getSimpleName();
                        logger.info("className: " + className);
                        if ("Number".equals(className)) {
                            org.literacyapp.model.content.Number number = (org.literacyapp.model.content.Number) contentCreationEvent.getContent();
                            htmlText += "<p>Language: " + contentCreationEvent.getContent().getLocale().getLanguage() + "</p>\n";
                            htmlText += "<p>Value: " + number.getValue() + "</p>\n";
                            if (number.getSymbol() != null) {
                                htmlText += "<p>Symbol: " + number.getSymbol() + "</p>\n";
                            }
                            if (number.getWord() != null) {
                                htmlText += "<p>Number word: \"" + number.getWord().getText() + "\"</p>\n";
                            }
                            htmlText += "<p>";
                                htmlText += "Contributor: ";
                                if (StringUtils.isNotBlank(contentCreationEvent.getContributor().getImageUrl())) {
                                    htmlText += "<img src=\"" + contentCreationEvent.getContributor().getImageUrl() + "\" alt=\"\" style=\"max-height: 1em; border-radius: 50%;\"> ";
                                }
                                htmlText += contentCreationEvent.getContributor().getFirstName() + " " + contentCreationEvent.getContributor().getLastName() + "</p>\n";
                            htmlText += "</p>";
                            htmlText += "<p>&nbsp;</p>";

                            if (++counterNumbers == 5) {
                                break;
                            }
                        }
                    }
                }
                
                
                int counterWords = 0;
                for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                    String className = contentCreationEvent.getContent().getClass().getSimpleName();
                    logger.info("className: " + className);
                    if ("Word".equals(className)) {
                        counterWords++;
                    }
                }
                if (counterWords > 0) {
                    htmlText += "<hr style=\"border-color: #CCC; border-top: 0;\" />";
                    htmlText += "<h2>Words</h2>";
                    counterWords = 0;
                    for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                        String className = contentCreationEvent.getContent().getClass().getSimpleName();
                        logger.info("className: " + className);
                        if ("Word".equals(className)) {
                            Word word = (Word) contentCreationEvent.getContent();
                            htmlText += "<p>Language: " + contentCreationEvent.getContent().getLocale().getLanguage() + "</p>\n";
                            htmlText += "<p>Text: \"" + word.getText() + "\"</p>\n";
                            htmlText += "<p>";
                                htmlText += "Contributor: ";
                                if (StringUtils.isNotBlank(contentCreationEvent.getContributor().getImageUrl())) {
                                    htmlText += "<img src=\"" + contentCreationEvent.getContributor().getImageUrl() + "\" alt=\"\" style=\"max-height: 1em; border-radius: 50%;\"> ";
                                }
                                htmlText += contentCreationEvent.getContributor().getFirstName() + " " + contentCreationEvent.getContributor().getLastName() + "</p>\n";
                            htmlText += "</p>";
                            htmlText += "<p>&nbsp;</p>";

                            if (++counterWords == 5) {
                                break;
                            }
                        }
                    }
                }
                
                
                int counterAudios = 0;
                for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                    String className = contentCreationEvent.getContent().getClass().getSimpleName();
                    logger.info("className: " + className);
                    if ("Audio".equals(className)) {
                        counterAudios++;
                    }
                }
                if (counterAudios > 0) {
                    htmlText += "<hr style=\"border-color: #CCC; border-top: 0;\" />";
                    htmlText += "<h2>Audios</h2>";
                    counterAudios = 0;
                    for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                        String className = contentCreationEvent.getContent().getClass().getSimpleName();
                        logger.info("className: " + className);
                        if ("Audio".equals(className)) {
                            Audio audio = (Audio) contentCreationEvent.getContent();
                            htmlText += "<p>Language: " + contentCreationEvent.getContent().getLocale().getLanguage() + "</p>\n";
                            htmlText += "<p>Transcription: \"" + audio.getTranscription() + "\"</p>\n";
                            htmlText += "<p>Size: " + (audio.getBytes().length / 1024) + "kB</p>\n";
                            htmlText += "<p>Audio format: " + audio.getAudioFormat() + "</p>\n";
                            htmlText += "<p>";
                                htmlText += "Contributor: ";
                                if (StringUtils.isNotBlank(contentCreationEvent.getContributor().getImageUrl())) {
                                    htmlText += "<img src=\"" + contentCreationEvent.getContributor().getImageUrl() + "\" alt=\"\" style=\"max-height: 1em; border-radius: 50%;\"> ";
                                }
                                htmlText += contentCreationEvent.getContributor().getFirstName() + " " + contentCreationEvent.getContributor().getLastName() + "</p>\n";
                            htmlText += "</p>";
                            htmlText += "<p>&nbsp;</p>";

                            if (++counterAudios == 5) {
                                break;
                            }
                        }
                    }
                }
                
                
                int counterImages = 0;
                for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                    String className = contentCreationEvent.getContent().getClass().getSimpleName();
                    logger.info("className: " + className);
                    if ("Image".equals(className)) {
                        counterImages++;
                    }
                }
                if (counterImages > 0) {
                    htmlText += "<hr style=\"border-color: #CCC; border-top: 0;\" />";
                    htmlText += "<h2>Images</h2>";
                    counterImages = 0;
                    for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                        String className = contentCreationEvent.getContent().getClass().getSimpleName();
                        logger.info("className: " + className);
                        if ("Image".equals(className)) {
                            Image image = (Image) contentCreationEvent.getContent();
                            htmlText += "<p><img src=\"" + baseUrl + "/image/" + image.getId() + "." + image.getImageFormat().toString().toLowerCase() + "\" alt=\"\" style=\"max-height: 2em;\"></p>\n";
                            htmlText += "<p>Language: " + contentCreationEvent.getContent().getLocale().getLanguage() + "</p>\n";
                            htmlText += "<p>Title: \"" + image.getTitle() + "\"</p>\n";
                            htmlText += "<p>Size: " + (image.getBytes().length / 1024) + "kB</p>\n";
                            htmlText += "<p>Image format: " + image.getImageFormat() + "</p>\n";
                            htmlText += "<p>";
                                htmlText += "Contributor: ";
                                if (StringUtils.isNotBlank(contentCreationEvent.getContributor().getImageUrl())) {
                                    htmlText += "<img src=\"" + contentCreationEvent.getContributor().getImageUrl() + "\" alt=\"\" style=\"max-height: 1em; border-radius: 50%;\"> ";
                                }
                                htmlText += contentCreationEvent.getContributor().getFirstName() + " " + contentCreationEvent.getContributor().getLastName() + "</p>\n";
                            htmlText += "</p>";
                            htmlText += "<p>&nbsp;</p>";

                            if (++counterImages == 5) {
                                break;
                            }
                        }
                    }
                }
                
                
                int counterVideos = 0;
                for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                    String className = contentCreationEvent.getContent().getClass().getSimpleName();
                    logger.info("className: " + className);
                    if ("Video".equals(className)) {
                        counterVideos++;
                    }
                }
                if (counterVideos > 0) {
                    htmlText += "<hr style=\"border-color: #CCC; border-top: 0;\" />";
                    htmlText += "<h2>Videos</h2>";
                    counterVideos = 0;
                    for (ContentCreationEvent contentCreationEvent : contentCreationEvents) {
                        String className = contentCreationEvent.getContent().getClass().getSimpleName();
                        logger.info("className: " + className);
                        if ("Video".equals(className)) {
                            Video video = (Video) contentCreationEvent.getContent();
                            htmlText += "<p>Language: " + contentCreationEvent.getContent().getLocale().getLanguage() + "</p>\n";
                            htmlText += "<p>Title: \"" + video.getTitle() + "\"</p>\n";
                            htmlText += "<p>Size: " + (video.getBytes().length / 1024) + "kB</p>\n";
                            htmlText += "<p>Video format: " + video.getVideoFormat() + "</p>\n";
                            htmlText += "<p>";
                                htmlText += "Contributor: ";
                                if (StringUtils.isNotBlank(contentCreationEvent.getContributor().getImageUrl())) {
                                    htmlText += "<img src=\"" + contentCreationEvent.getContributor().getImageUrl() + "\" alt=\"\" style=\"max-height: 1em; border-radius: 50%;\"> ";
                                }
                                htmlText += contentCreationEvent.getContributor().getFirstName() + " " + contentCreationEvent.getContributor().getLastName() + "</p>\n";
                            htmlText += "</p>";
                            htmlText += "<p>&nbsp;</p>";

                            if (++counterVideos == 5) {
                                break;
                            }
                        }
                    }
                }
                
                
                htmlText += "<hr style=\"border-color: #CCC; border-top: 0;\" />";
                htmlText += "<h2>Can you help?</h2>";
                htmlText += "<p>Do you know about anyone else who might be interested in helping us with content creation? Please share our website with them :-)</p>";
                htmlText += "<p>Or to upload more content, click the button below:</p>";
                String buttonText = "Go to content editor";
                String buttonUrl = baseUrl + "/content";
                
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
