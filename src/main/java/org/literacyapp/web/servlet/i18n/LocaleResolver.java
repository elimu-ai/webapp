package org.literacyapp.web.servlet.i18n;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public class LocaleResolver extends SessionLocaleResolver {
    
    private Logger logger = Logger.getLogger(getClass());

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        logger.info("resolveLocale");
        
        if (EnvironmentContextLoaderListener.env == Environment.DEV) {
            return super.resolveLocale(request);
        } else {
            Locale locale = RequestContextUtils.getLocale(request);
            logger.info("RequestContextUtils.getLocale(request): " + locale);

            String serverName = request.getServerName();
            logger.info("serverName: " + serverName);
            if (serverName.startsWith("ar.")) {
                locale = new Locale("ar");
            } else if (serverName.startsWith("en.")) {
                locale = new Locale("en");
            } else if (serverName.startsWith("es.")) {
                locale = new Locale("es");
            } else if (serverName.startsWith("sw.")) {
                locale = new Locale("sw");
            }
            logger.info("locale: " + locale);
            
            return locale;
        }
    }
}
