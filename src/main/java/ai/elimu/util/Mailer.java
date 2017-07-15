package ai.elimu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import ai.elimu.model.enums.Environment;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class Mailer {
    
    private static final Logger logger = Logger.getLogger(Mailer.class);
    
    private static final String ADMIN_EMAIL = "info@elimu.ai";
    
    public static void sendPlainText(String to, String cc, String from, String subject, String text) {
        logger.info("sendPlainText");
        
        if (to.contains(",")) {
            to = to.replace(",", "");
        }
        if (to.contains(":")) {
            to = to.replace(":", "");
        }
                
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        if (StringUtils.isNotBlank(cc)) {
            simpleMailMessage.setCc(cc);
        }
        simpleMailMessage.setBcc(ADMIN_EMAIL);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        String smtpHost = ConfigHelper.getProperty("smtp.host");
        javaMailSenderImpl.setHost(smtpHost);

        logger.info("Sending e-mail to " + simpleMailMessage.getTo()[0] + " with subject \"" + simpleMailMessage.getSubject() + "\"...");
        logger.info("Text: " + simpleMailMessage.getText());
        if (EnvironmentContextLoaderListener.env != Environment.DEV) {
            javaMailSenderImpl.send(simpleMailMessage);
        }
    }
    
    public static void sendHtml(String to, String cc, String from, String subject, String title, String text) {
        logger.info("sendPlainText");
        
        if (to.contains(",")) {
            to = to.replace(",", "");
        }
        if (to.contains(":")) {
            to = to.replace(":", "");
        }
        
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        String smtpHost = ConfigHelper.getProperty("smtp.host");
        javaMailSenderImpl.setHost(smtpHost);
        
        MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            if (StringUtils.isNotBlank(cc)) {
                mimeMessageHelper.setCc(cc);
            }
            mimeMessageHelper.setBcc(ADMIN_EMAIL);
            mimeMessageHelper.setSubject(subject);
            
            String html = "";
            
            ResourceLoader resourceLoader = new ClassRelativeResourceLoader(Mailer.class);
            logger.info("Loading file email_template.html...");
            Resource resource = resourceLoader.getResource("email_template.html");
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            try {
                inputStream = resource.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains("${subject}")) {
                        line = line.replace("${subject}", subject);
                    } else if (line.contains("${title}")) {
                        line = line.replace("${title}", title);
                    } else if (line.contains("${text}")) {
                        line = line.replace("${text}", text);
                    }
                    
                    html += line;
                }
            } catch (IOException ex) {
                logger.error(null, ex);
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        logger.error(null, e);
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        logger.error(null, e);
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        logger.error(null, e);
                    }
                }
            }
            
            mimeMessageHelper.setText(text, html);
            
            logger.info("Sending MIME message to " + to + " with subject \"" + subject + "\"...");
            logger.info("title: " + title);
            logger.info("text: " + text);
            if (EnvironmentContextLoaderListener.env != Environment.DEV) {
                javaMailSenderImpl.send(mimeMessage);
            }
        } catch (MessagingException ex) {
            logger.error(null, ex);
        }
    }
    
    public static void sendHtmlWithButton(String to, String cc, String from, String subject, String title, String text, String buttonText, String buttonUrl) {
        logger.info("sendHtmlWithButton");
        
        if (to.contains(",")) {
            to = to.replace(",", "");
        }
        if (to.contains(":")) {
            to = to.replace(":", "");
        }
        
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        String smtpHost = ConfigHelper.getProperty("smtp.host");
        javaMailSenderImpl.setHost(smtpHost);
        
        MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            if (StringUtils.isNotBlank(cc)) {
                mimeMessageHelper.setCc(cc);
            }
            mimeMessageHelper.setBcc(ADMIN_EMAIL);
            mimeMessageHelper.setSubject(subject);
            
            String html = "";
            
            ResourceLoader resourceLoader = new ClassRelativeResourceLoader(Mailer.class);
            logger.info("Loading file email_template_button.html...");
            Resource resource = resourceLoader.getResource("email_template_button.html");
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            try {
                inputStream = resource.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains("${subject}")) {
                        line = line.replace("${subject}", subject);
                    }
                    if (line.contains("${title}")) {
                        line = line.replace("${title}", title);
                    }
                    if (line.contains("${text}")) {
                        line = line.replace("${text}", text);
                    }
                    if (line.contains("${buttonText}")) {
                        line = line.replace("${buttonText}", buttonText);
                    }
                    if (line.contains("${buttonUrl}")) {
                        line = line.replace("${buttonUrl}", buttonUrl);
                    }
                    
                    html += line;
                }
            } catch (IOException ex) {
                logger.error(null, ex);
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        logger.error(null, e);
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        logger.error(null, e);
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        logger.error(null, e);
                    }
                }
            }
            
            mimeMessageHelper.setText(text, html);
            
            logger.info("Sending MIME message to " + to + " with subject \"" + subject + "\"...");
            logger.info("title: " + title);
            logger.info("text: " + text);
            logger.info("buttonText: " + buttonText);
            logger.info("buttonUrl: " + buttonUrl);
            if (EnvironmentContextLoaderListener.env != Environment.DEV) {
                javaMailSenderImpl.send(mimeMessage);
            }
        } catch (MessagingException ex) {
            logger.error(null, ex);
        }
    }
}
