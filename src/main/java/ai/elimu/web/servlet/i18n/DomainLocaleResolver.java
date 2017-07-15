package ai.elimu.web.servlet.i18n;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

public class DomainLocaleResolver extends SessionLocaleResolver {
    
    private Logger logger = Logger.getLogger(getClass());

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        logger.debug("resolveLocale");
        
        Contributor contributor = (Contributor) request.getSession().getAttribute("contributor");
        if ((contributor != null) && (contributor.getLocale() != null)) {
            Locale contributorLocale = new Locale(contributor.getLocale().getLanguage());
            logger.debug("contributorLocale: " + contributorLocale);
            return contributorLocale;
        }
        
        if (EnvironmentContextLoaderListener.env == Environment.DEV) {
            // Use "?lang" parameter instead of domain name
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
