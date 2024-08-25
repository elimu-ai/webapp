package selenium;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DomainHelper {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static String getBaseUrl() {
        String baseUrl = "http://localhost:8080/webapp";

        // Read "base.url" property set on the command line: 
        //    mvn clean verify -P regression-testing-rest -D base.url=https://eng.test.elimu.ai
        String baseUrlSystemProperty = System.getProperty("base.url");
        logger.info("baseUrlSystemProperty: \"" + baseUrlSystemProperty + "\"");
        if (StringUtils.isNotBlank(baseUrlSystemProperty)) {
            baseUrl = baseUrlSystemProperty;
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
