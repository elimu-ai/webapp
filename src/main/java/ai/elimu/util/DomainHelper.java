package ai.elimu.util;

import ai.elimu.model.v2.enums.Environment;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainHelper {
    
    public static String getBaseUrl() {
        String baseUrl = "http://localhost:8080/webapp";
        if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            String language = ConfigHelper.getProperty("content.language");
            baseUrl = "http://" + language.toLowerCase() + ".elimu.ai";
        }
        log.info("baseUrl: \"" + baseUrl + "\"");
        return baseUrl;
    }
}
