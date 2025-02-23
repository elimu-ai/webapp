package selenium.util;

import org.apache.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainHelper {
    
    public static String getBaseUrl() {
        String baseUrl = "http://localhost:8080/webapp";

        // Read "base.url" property set on the command line: 
        //    mvn clean verify -P regression-test-rest -D base.url=https://eng.test.elimu.ai
        String baseUrlSystemProperty = System.getProperty("base.url");
        log.info("baseUrlSystemProperty: \"" + baseUrlSystemProperty + "\"");
        if (StringUtils.isNotBlank(baseUrlSystemProperty)) {
            baseUrl = baseUrlSystemProperty;
        }
        
        log.info("baseUrl: \"" + baseUrl + "\"");
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
