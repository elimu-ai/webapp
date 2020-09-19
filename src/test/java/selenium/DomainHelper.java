package selenium;

import ai.elimu.model.enums.Language;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;

public class DomainHelper {
    
    private static final Logger logger = Logger.getLogger(DomainHelper.class.getName());
    
    public static String getBaseUrl() {
        // Read property set on the command line: 
        //    mvn clean verify -P regression-testing-rest -D base.url=http://eng.test.elimu.ai
        String baseUrlSystemProperty = System.getProperty("base.url");
        logger.info("baseUrlSystemProperty: \"" + baseUrlSystemProperty + "\"");
        
        String baseUrl = baseUrlSystemProperty;
        
        if (StringUtils.isBlank(baseUrl)) {
            // Read property set in the "config.properties" file
            // This will trigger if no "base.url" property is set on the command line
            InputStream inputStream = DomainHelper.class.getClassLoader().getResourceAsStream("config.properties");
            Properties properties = new Properties();
            try {
                properties.load(inputStream);
                String contentLanguageProperty = properties.getProperty("content.language");
                logger.info("contentLanguageProperty: \"" + contentLanguageProperty + "\"");
                Language language = Language.valueOf(contentLanguageProperty);
                baseUrl = "http://" + language.toString().toLowerCase() + ".test.elimu.ai";
            } catch (IOException ex) {
                logger.error(null, ex);
            }
        }
        
        logger.info("baseUrl: \"" + baseUrl + "\"");
        return baseUrl;
    }
    
    @Deprecated
    public static String getRestUrlV1() {
        return getBaseUrl() + "/rest/v1";
    }
    
    public static String getRestUrlV2() {
        return getBaseUrl() + "/rest/v2";
    }
}
