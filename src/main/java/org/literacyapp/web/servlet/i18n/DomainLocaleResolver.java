package org.literacyapp.web.servlet.i18n;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public class DomainLocaleResolver extends SessionLocaleResolver {
    
    private Logger logger = Logger.getLogger(getClass());

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        logger.info("debug");
        
        if (EnvironmentContextLoaderListener.env == Environment.DEV) {
            return super.resolveLocale(request);
        } else {
            Locale locale = new Locale("en", "US");

            String serverName = request.getServerName();
            logger.debug("serverName: " + serverName);
            if (serverName.startsWith("ar.")) {
                locale = new Locale("ar");
            } else if (serverName.startsWith("en.")) {
                locale = new Locale("en");
            } else if (serverName.startsWith("es.")) {
                locale = new Locale("es");
            } else if (serverName.startsWith("sw.")) {
                locale = new Locale("sw");
            }
            logger.debug("locale: " + locale);
            
            return locale;
        }
    }
}
