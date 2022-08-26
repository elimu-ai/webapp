package ai.elimu.util;

import java.util.Properties;
import ai.elimu.web.context.EnvironmentContextLoaderListener;

public class ConfigHelper {

    /**
     * This only works _after_ the WebApplicationContext has been initialized in 
     * {@link EnvironmentContextLoaderListener}.
     */
    public static String getProperty(String key) {
        Properties properties = EnvironmentContextLoaderListener.PROPERTIES;
        return properties.getProperty(key);
    }

    public static boolean getBooleanProperty(String key) {
        return Boolean.valueOf(getProperty(key));
    }
}
